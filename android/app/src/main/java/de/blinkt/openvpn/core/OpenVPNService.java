package de.blinkt.openvpn.core;

import android.Manifest.permission;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.VpnService;
import android.os.Build;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

import android.system.OsConstants;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import core.StateType;
import core.VpnConnector;
import de.blinkt.openvpn.VpnProfile;
import de.blinkt.openvpn.api.ExternalAppDatabase;
import io.flutter.app.FlutterActivity;

import com.soheil.flutter_openvpn_android.App;
import com.soheil.flutter_openvpn_android.MainActivity;
import com.soheil.flutter_openvpn_android.R;


import static de.blinkt.openvpn.core.ConnectionStatus.LEVEL_CONNECTED;
import static de.blinkt.openvpn.core.ConnectionStatus.LEVEL_NOTCONNECTED;
import static de.blinkt.openvpn.core.ConnectionStatus.LEVEL_WAITING_FOR_USER_INPUT;

public class OpenVPNService extends VpnService implements VpnStatus.StateListener, Callback,
        VpnStatus.ByteCountListener, IOpenVPNServiceInternal {
    
    
    private BroadcastReceiver stopBroadcast ;

    public static final String START_SERVICE = "flutter.vpn.open.2x.START_SERVICE";
    public static final String START_SERVICE_STICKY = "flutter.vpn.open.2x.START_SERVICE_STICKY";
    public static final String ALWAYS_SHOW_NOTIFICATION = "flutter.vpn.open.2x.NOTIFICATION_ALWAYS_VISIBLE";
    public static final String DISCONNECT_VPN = "flutter.vpn.open.2x.DISCONNECT_VPN";
    public static final String NOTIFICATION_CHANNEL_BG_ID = "openvpn_bg";
    public static final String NOTIFICATION_CHANNEL_NEWSTATUS_ID = "openvpn_newstat";
    public static final String NOTIFICATION_CHANNEL_USERREQ_ID = "openvpn_userreq";

    public static final String VPNSERVICE_TUN = "vpnservice-tun";
    public final static String ORBOT_PACKAGE_NAME = "org.torproject.android";
    private static final String PAUSE_VPN = "flutter.vpn.open.2x.PAUSE_VPN";
    private static final String RESUME_VPN = "flutter.vpn.open.2x.RESUME_VPN";

    public static final String EXTRA_CHALLENGE_TXT = "flutter.vpn.open.2x.core.CR_TEXT_CHALLENGE";

    private static final int PRIORITY_MIN = -2;
    private static final int PRIORITY_DEFAULT = 0;
    private static final int PRIORITY_MAX = 2;
    private static boolean mNotificationAlwaysVisible = false;
    private static Class mNotificationActivityClass;
    private final Vector<String> mDnslist = new Vector<>();
    private final NetworkSpace mRoutes = new NetworkSpace();
    private final NetworkSpace mRoutesv6 = new NetworkSpace();
    private final Object mProcessLock = new Object();
    private String lastChannel;
    private Thread mProcessThread = null;
    private VpnProfile mProfile;
    private String mDomain = null;
    private CIDRIP mLocalIP = null;
    private int mMtu;
    private String mLocalIPv6 = null;
    private DeviceStateReceiver mDeviceStateReceiver;
    private boolean mDisplayBytecount = false;
    private boolean mStarting = false;
    private long mConnecttime;
    private OpenVPNManagement mManagement;

    public   static Timer timeOutChecker ;


    private final IBinder mBinder = new Stub() {

        @Override
        public boolean protect(int fd) throws RemoteException {
            return OpenVPNService.this.protect(fd);
        }

        @Override
        public void userPause(boolean shouldbePaused) throws RemoteException {
            OpenVPNService.this.userPause(shouldbePaused);
        }

        @Override
        public boolean stopVPN(boolean replaceConnection) throws RemoteException {
            return OpenVPNService.this.stopVPN(replaceConnection);
        }

        @Override
        public void addAllowedExternalApp(String packagename) throws RemoteException {
            OpenVPNService.this.addAllowedExternalApp(packagename);
        }

        @Override
        public boolean isAllowedExternalApp(String packagename) throws RemoteException {
            return OpenVPNService.this.isAllowedExternalApp(packagename);

        }

        @Override
        public void challengeResponse(String repsonse) throws RemoteException {
            OpenVPNService.this.challengeResponse(repsonse);
        }


    };
    private String mLastTunCfg;
    private String mRemoteGW;
    private Handler guiHandler;
    private Toast mlastToast;
    private Runnable mOpenVPNThread;

    public static String humanReadableByteCount(long bytes, boolean speed, Resources res) {
        if (speed)
            bytes = bytes * 8;
        int unit = speed ? 1000 : 1024;


        int exp = Math.max(0, Math.min((int) (Math.log(bytes) / Math.log(unit)), 3));

        float bytesUnit = (float) (bytes / Math.pow(unit, exp));

        if (speed)
            switch (exp) {
                case 0:
                    return res.getString(R.string.bits_per_second, bytesUnit);
                case 1:
                    return res.getString(R.string.kbits_per_second, bytesUnit);
                case 2:
                    return res.getString(R.string.mbits_per_second, bytesUnit);
                default:
                    return res.getString(R.string.gbits_per_second, bytesUnit);
            }
        else
            switch (exp) {
                case 0:
                    return res.getString(R.string.volume_byte, bytesUnit);
                case 1:
                    return res.getString(R.string.volume_kbyte, bytesUnit);
                case 2:
                    return res.getString(R.string.volume_mbyte, bytesUnit);
                default:
                    return res.getString(R.string.volume_gbyte, bytesUnit);

            }


    }


    public static void setNotificationActivityClass(Class<? extends FlutterActivity> activityClass) {
        mNotificationActivityClass = activityClass;
    }

    @Override
    public void addAllowedExternalApp(String packagename) throws RemoteException {
        ExternalAppDatabase extapps = new ExternalAppDatabase(OpenVPNService.this);
        extapps.addApp(packagename);
    }

    @Override
    public boolean isAllowedExternalApp(String packagename) throws RemoteException {
        ExternalAppDatabase extapps = new ExternalAppDatabase(OpenVPNService.this);
        return extapps.checkRemoteActionPermission(this, packagename);
    }

    @Override
    public void challengeResponse(String response) throws RemoteException {
        if(mManagement != null) {
            String b64response = Base64.encodeToString(response.getBytes(Charset.forName("UTF-8")), Base64.DEFAULT);
            mManagement.sendCRResponse(b64response);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(START_SERVICE))
            return mBinder;
        else
            return super.onBind(intent);
    }

    @Override
    public void onRevoke() {
        VpnStatus.logError(R.string.permission_revoked);
        mManagement.stopVPN(false);
        endVpnService();
    }



    private void initStopBroadcast() {


        stopBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Log.e(TAG, "onReceive: !!!" );
                ProfileManager.setConntectedVpnProfileDisconnected(OpenVPNService.this);
                try {
                    stopVPN(false);
                    endVpnService();
                } catch (RemoteException e) {
                    VpnStatus.logException(e);
                }

            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DISCONNECT_VPN);
        try {
            registerReceiver(stopBroadcast , intentFilter);
        }catch (Exception e) {

        }
    }

    // Similar to revoke but do not try to stop process
    public void openvpnStopped() {
        endVpnService();
    }

    private void endVpnService() {

        synchronized (mProcessLock) {
            mProcessThread = null;
        }
        VpnStatus.removeByteCountListener(this);
        unregisterDeviceStateReceiver();
        ProfileManager.setConntectedVpnProfileDisconnected(this);
//        OpenVpnManagementThread.stopSvc();
//        OpenVPNThreadv3.stopSvc();
        mOpenVPNThread = null;
        if (!mStarting) {
            stopForeground(!mNotificationAlwaysVisible);

            if (!mNotificationAlwaysVisible) {
                stopSelf();
                VpnStatus.removeStateListener(this);

                //  VpnStatus.removeStateListener(this);
            }
        }

//        if(stopBroadcast != null){
//            try{
//                Log.e(TAG, "endVpnService: !!!" );
//                unregisterReceiver(stopBroadcast);
//            }catch (Exception e) {
//
//            }
//        }

        App.connectTimeVpn = 0 ;

        App.getInstance().cancelNotification(NOTIFICATION_CHANNEL_NEWSTATUS_ID.hashCode());
        stopForeground(true);

        if(VpnConnector.getConnectionState() == StateType.CONNECTED) {
            VpnConnector.setConnectionState(StateType.DISCONNECTED);
        }

    }

    private void showNotification(final String msg, String tickerText, @NonNull String channel,
                                  long when, ConnectionStatus status, Intent intent) {

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = getIconByConnectionStatus(status);
        int notificationId = channel.hashCode();

        if(VpnConnector.getConnectionState() == StateType.DISCONNECTED) {
            stopForeground(true);
            App.getInstance().cancelNotification(notificationId);

        }else  {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(String.valueOf(notificationId),
                        "FlutterOpenVPN",
                        NotificationManager.IMPORTANCE_DEFAULT);
                mChannel.setShowBadge(false);
                mChannel.setSound(null,null);
                mChannel.enableLights(false);
                mChannel.enableVibration(false);
                mNotificationManager.createNotificationChannel(mChannel);
            }

            Notification.Builder nbuilder = new Notification.Builder(this);

            int priority;
            if (channel.equals(NOTIFICATION_CHANNEL_BG_ID))
                priority = PRIORITY_MIN;
            else if (channel.equals(NOTIFICATION_CHANNEL_USERREQ_ID))
                priority = PRIORITY_MAX;
            else
                priority = PRIORITY_DEFAULT;


            if (mProfile != null)
                
                nbuilder.setContentTitle("FlutterVPN");
            else
                nbuilder.setContentTitle(getString(R.string.notifcation_title_notconnect));

            nbuilder.setContentText(msg);
            nbuilder.setOnlyAlertOnce(true);
            nbuilder.setOngoing(true);

            nbuilder.setSmallIcon(icon);
            if (status == LEVEL_WAITING_FOR_USER_INPUT) {
                PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
                nbuilder.setContentIntent(pIntent);
            } else {
                nbuilder.setContentIntent(getGraphPendingIntent());
            }

            if (when != 0)
                nbuilder.setWhen(when);


            // Try to set the priority available since API 16 (Jellybean)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                jbNotificationExtras(priority, nbuilder);
                // addVpnActionsToNotification(nbuilder);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                lpNotificationExtras(nbuilder, Notification.CATEGORY_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //noinspection NewApi
                nbuilder.setChannelId(String.valueOf(notificationId));
                if (mProfile != null)
                    //noinspection NewApi
                    nbuilder.setShortcutId(mProfile.getUUIDString());

            }

            if (tickerText != null && !tickerText.equals(""))
                nbuilder.setTicker(tickerText);

            @SuppressWarnings("deprecation")
            Notification notification = nbuilder.getNotification();

            mNotificationManager.notify(notificationId, notification);

            startForeground(notificationId, notification);

            if (lastChannel != null && !channel.equals(lastChannel)) {
                // Cancel old notification
                mNotificationManager.cancel(lastChannel.hashCode());
            }

            // Check if running on a TV
            if (runningOnAndroidTV() && !(priority < 0))
                guiHandler.post(new Runnable() {

                    @Override
                    public void run() {

                        if (mlastToast != null)
                            mlastToast.cancel();
                        String toastText = String.format(Locale.getDefault(), "%s - %s", mProfile.mName, msg);
                        mlastToast = Toast.makeText(getBaseContext(), toastText, Toast.LENGTH_SHORT);
                        mlastToast.show();
                    }
                });

        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void lpNotificationExtras(Notification.Builder nbuilder, String category) {
        nbuilder.setCategory(category);
        nbuilder.setLocalOnly(true);

    }

    private boolean runningOnAndroidTV() {
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        return uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;
    }

    private int getIconByConnectionStatus(ConnectionStatus level) {
        switch (level) {
            case LEVEL_CONNECTED:
                return R.drawable.vpn;
            case LEVEL_AUTH_FAILED:
            case LEVEL_NONETWORK:
            case LEVEL_NOTCONNECTED:
                return R.drawable.vpn;
            case LEVEL_CONNECTING_NO_SERVER_REPLY_YET:
            case LEVEL_WAITING_FOR_USER_INPUT:
                return R.drawable.vpn;
            case LEVEL_CONNECTING_SERVER_REPLIED:
                return R.drawable.vpn;
            case LEVEL_VPNPAUSED:
                return android.R.drawable.ic_media_pause;
            case UNKNOWN_LEVEL:
            default:
                return R.drawable.vpn;

        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void jbNotificationExtras(int priority,
                                      Notification.Builder nbuilder) {
        try {
            if (priority != 0) {
                Method setpriority = nbuilder.getClass().getMethod("setPriority", int.class);
                setpriority.invoke(nbuilder, priority);

                Method setUsesChronometer = nbuilder.getClass().getMethod("setUsesChronometer", boolean.class);
                setUsesChronometer.invoke(nbuilder, true);

            }

            //ignore exception
        } catch (NoSuchMethodException | IllegalArgumentException |
                InvocationTargetException | IllegalAccessException e) {
            VpnStatus.logException(e);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addVpnActionsToNotification(Notification.Builder nbuilder) {
        Intent disconnectVPN = new Intent(this, OpenVPNService.class);
        disconnectVPN.setAction(DISCONNECT_VPN);
        PendingIntent disconnectPendingIntent = PendingIntent.getService(this, 0, disconnectVPN, 0);

        nbuilder.addAction(R.drawable.vpn,
                getString(R.string.cancel_connection), disconnectPendingIntent);

        Intent pauseVPN = new Intent(this, OpenVPNService.class);
        if (mDeviceStateReceiver == null || !mDeviceStateReceiver.isUserPaused()) {
            pauseVPN.setAction(PAUSE_VPN);
            PendingIntent pauseVPNPending = PendingIntent.getService(this, 0, pauseVPN, 0);
            nbuilder.addAction(R.drawable.vpn,
                    getString(R.string.pauseVPN), pauseVPNPending);

        } else {
            pauseVPN.setAction(RESUME_VPN);
            PendingIntent resumeVPNPending = PendingIntent.getService(this, 0, pauseVPN, 0);
            nbuilder.addAction(R.drawable.vpn,
                    getString(R.string.resumevpn), resumeVPNPending);
        }
    }


    PendingIntent getGraphPendingIntent() {

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this, MainActivity.class));

        intent.putExtra("PAGE", "graph");
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent startLW = PendingIntent.getActivity(this, 0, intent, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return startLW;

    }

    synchronized void registerDeviceStateReceiver(OpenVPNManagement magnagement) {
        // Registers BroadcastReceiver to track network connection changes.
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        mDeviceStateReceiver = new DeviceStateReceiver(magnagement);

        // Fetch initial network state
        mDeviceStateReceiver.networkStateChange(this);

        registerReceiver(mDeviceStateReceiver, filter);
        VpnStatus.addByteCountListener(mDeviceStateReceiver);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            addLollipopCMListener(); */
    }

    synchronized void unregisterDeviceStateReceiver() {
        if (mDeviceStateReceiver != null)
            try {
                VpnStatus.removeByteCountListener(mDeviceStateReceiver);
                this.unregisterReceiver(mDeviceStateReceiver);
            } catch (IllegalArgumentException iae) {
                // I don't know why  this happens:
                // java.lang.IllegalArgumentException: Receiver not registered: flutter.vpn.open.2x.NetworkSateReceiver@41a61a10
                // Ignore for now ...
                iae.printStackTrace();
            }
        mDeviceStateReceiver = null;

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            removeLollipopCMListener();*/

    }

    public void userPause(boolean shouldBePaused) {
        if (mDeviceStateReceiver != null)
            mDeviceStateReceiver.userPause(shouldBePaused);
    }

    @Override
    public boolean stopVPN(boolean replaceConnection) throws RemoteException {
        if (getManagement() != null) {
//            if (!replaceConnection)
//               // StreamCapture.getInstance().closeIgnoreException();
            return getManagement().stopVPN(replaceConnection);
        }
        else
            return false;
    }


    private static final String TAG = "OpenVPNService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getBooleanExtra(ALWAYS_SHOW_NOTIFICATION, false))
            mNotificationAlwaysVisible = true;


        VpnStatus.addStateListener(this);
        VpnStatus.addByteCountListener(this);

        guiHandler = new Handler(getMainLooper());
        

        if (intent != null && DISCONNECT_VPN.equals(intent.getAction())) {

            ProfileManager.setConntectedVpnProfileDisconnected(this);
            try {
                stopVPN(false);
                endVpnService();
            } catch (RemoteException e) {
                VpnStatus.logException(e);
            }

            return START_NOT_STICKY;
        }


        if (intent != null && PAUSE_VPN.equals(intent.getAction())) {
            if (mDeviceStateReceiver != null)
                mDeviceStateReceiver.userPause(true);
            return START_NOT_STICKY;
        }

        if (intent != null && RESUME_VPN.equals(intent.getAction())) {
            if (mDeviceStateReceiver != null)
                mDeviceStateReceiver.userPause(false);
            return START_NOT_STICKY;
        }


        if (intent != null && START_SERVICE.equals(intent.getAction()))
            return START_NOT_STICKY;
        if (intent != null && START_SERVICE_STICKY.equals(intent.getAction())) {
            return START_REDELIVER_INTENT;
        }

        // Always show notification here to avoid problem with startForeground timeout
        VpnStatus.logInfo(R.string.building_configration);
        startTimeOutConnecting();
        VpnStatus.updateStateString("VPN_GENERATE_CONFIG", "", R.string.building_configration, ConnectionStatus.LEVEL_START);
        showNotification(VpnStatus.getLastCleanLogMessage(this),
                VpnStatus.getLastCleanLogMessage(this), NOTIFICATION_CHANNEL_NEWSTATUS_ID, 0, ConnectionStatus.LEVEL_START, null);


        if (intent != null && intent.hasExtra(getPackageName() + ".profileUUID")) {
            String profileUUID = intent.getStringExtra(getPackageName() + ".profileUUID");
            int profileVersion = intent.getIntExtra(getPackageName() + ".profileVersion", 0);
            mProfile = ProfileManager.get(this, profileUUID, profileVersion, 100);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                updateShortCutUsage(mProfile);
            }

        } else {
            mProfile = ProfileManager.getLastConnectedProfile(this);
            VpnStatus.logInfo(R.string.service_restarted);

            if (mProfile == null) {
                Log.d("OpenVPN", "Got no last connected profile on null intent. Assuming always on.");
                mProfile = ProfileManager.getAlwaysOnVPN(this);

                if (mProfile == null) {
                    stopSelf(startId);
                    return START_NOT_STICKY;
                }
            }

            mProfile.checkForRestart(this);
        }

        if (mProfile == null) {
            stopSelf(startId);
            return START_NOT_STICKY;
        }


        /* start the OpenVPN process itself in a background thread */
        new Thread(new Runnable() {
            @Override
            public void run() {
                startOpenVPN();
            }
        }).start();


        ProfileManager.setConnectedVpnProfile(this, mProfile);
        VpnStatus.setConnectedVPNProfile(mProfile.getUUIDString());

        initStopBroadcast();
        return START_STICKY;
    }



    private void startTimeOutConnecting() {
        if(timeOutChecker == null) {
            timeOutChecker = new Timer();
            timeOutChecker.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(VpnConnector.getConnectionState() != StateType.CONNECTED) {
                        try {

                            ProfileManager.setConntectedVpnProfileDisconnected(OpenVPNService.this);
                            stopVPN(false);
                            endVpnService();

                            timeOutChecker = null;
                            VpnStatus.updateStateString("TIMEOUT", "Time out to connect.",
                                    R.string.state_noprocess, ConnectionStatus.LEVEL_TIMEOUT);

                            VpnStatus.updateStateString("Not Connected", "Failed To Connect.",
                                    R.string.state_noprocess, LEVEL_NOTCONNECTED);

                            //Log.e(TAG, "TIMEOUT ---> " );

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } , 10000);

        } else {
            closeTimeOutChecker();
            startTimeOutConnecting();
        }
    }


    public static void closeTimeOutChecker() {
        if(timeOutChecker != null) {
            timeOutChecker.cancel();
            timeOutChecker = null;
        }
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    private void updateShortCutUsage(VpnProfile profile) {
        if (profile == null)
            return;
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.reportShortcutUsed(profile.getUUIDString());
    }

    private void startOpenVPN() {
        try {
            mProfile.writeConfigFile(this);
        } catch (IOException e) {
            VpnStatus.logException("Error writing config file", e);
            Log.e(TAG, "startOpenVPN: "  +e.getMessage() );
            endVpnService();
            return;
        }
        String nativeLibraryDirectory = getApplicationInfo().nativeLibraryDir;

        // Write OpenVPN binary
        String[] argv = VPNLaunchHelper.buildOpenvpnArgv(this);


        // Set a flag that we are starting a new VPN
        mStarting = true;
        // Stop the previous session by interrupting the thread.

        stopOldOpenVPNProcess();
        // An old running VPN should now be exited
        mStarting = false;

        boolean useOpenVPN3 = true;

        if (!useOpenVPN3) {
            // start a Thread that handles incoming messages of the managment socket
            OpenVpnManagementThread ovpnManagementThread = new OpenVpnManagementThread(mProfile, this);
            if (ovpnManagementThread.openManagementInterface(this)) {
                Thread mSocketManagerThread = new Thread(ovpnManagementThread, "OpenVPNManagementThread");
                mSocketManagerThread.start();
                mManagement = ovpnManagementThread;
                VpnStatus.logInfo("started Socket Thread");
            } else {
                endVpnService();
                return;
            }
        }

        Runnable processThread;
        if (useOpenVPN3) {
            OpenVPNManagement mOpenVPN3 = instantiateOpenVPN3Core();
            processThread = (Runnable) mOpenVPN3;
            mManagement = mOpenVPN3;
        } else {
            processThread = new OpenVPNThread(this, argv, nativeLibraryDirectory);
            mOpenVPNThread = processThread;
        }

        synchronized (mProcessLock)

        {
            mProcessThread = new Thread(processThread, "OpenVPNProcessThread");
            mProcessThread.start();
        }

        new Handler(getMainLooper()).post(new Runnable() {
                                              @Override
                                              public void run() {
                                                  if (mDeviceStateReceiver != null)
                                                      unregisterDeviceStateReceiver();

                                                  registerDeviceStateReceiver(mManagement);
                                              }
                                          }

        );
    }


    private void stopOldOpenVPNProcess() {
        if (mManagement != null) {
            if (mOpenVPNThread != null)
                ((OpenVPNThread) mOpenVPNThread).setReplaceConnection();
            if (mManagement.stopVPN(true)) {
                // an old was asked to exit, wait 1s
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }

        forceStopOpenVpnProcess();
    }

    public void forceStopOpenVpnProcess() {
        synchronized (mProcessLock) {
            if (mProcessThread != null) {
                mProcessThread.interrupt();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }
    }

    private OpenVPNManagement instantiateOpenVPN3Core() {
        return  new OpenVPNThreadv3(this , mProfile);
    }

    @Override
    public IBinder asBinder() {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = NOTIFICATION_CHANNEL_NEWSTATUS_ID;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "FlutterVPN",
                    NotificationManager.IMPORTANCE_LOW);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("FlutterVPN")
                    .setContentText("").build();

            try{
                startForeground(NOTIFICATION_CHANNEL_NEWSTATUS_ID.hashCode(), notification);
            }catch (Exception e) {

            }
        }


        // Log.e(TAG, "onCreate: !!" );


    }

    @Override
    public void onDestroy() {
        synchronized (mProcessLock) {
            if (mProcessThread != null) {
                mManagement.stopVPN(true);
            }

          //  StreamCapture.getInstance().closeIgnoreException();
        }

        if (mDeviceStateReceiver != null) {
            this.unregisterReceiver(mDeviceStateReceiver);
        }
        // Just in case unregister for state
        VpnStatus.removeStateListener(this);
        VpnStatus.flushLog();
    }

    private String getTunConfigString() {
        // The format of the string is not important, only that
        // two identical configurations produce the same result
        String cfg = "TUNCFG UNQIUE STRING ips:";

        if (mLocalIP != null)
            cfg += mLocalIP.toString();
        if (mLocalIPv6 != null)
            cfg += mLocalIPv6;


        cfg += "routes: " + TextUtils.join("|", mRoutes.getNetworks(true)) + TextUtils.join("|", mRoutesv6.getNetworks(true));
        cfg += "excl. routes:" + TextUtils.join("|", mRoutes.getNetworks(false)) + TextUtils.join("|", mRoutesv6.getNetworks(false));
        cfg += "dns: " + TextUtils.join("|", mDnslist);
        cfg += "domain: " + mDomain;
        cfg += "mtu: " + mMtu;
        return cfg;
    }

    public ParcelFileDescriptor openTun() {

        //Debug.startMethodTracing(getExternalFilesDir(null).toString() + "/opentun.trace", 40* 1024 * 1024);

        Builder builder = new Builder();

        VpnStatus.logInfo(R.string.last_openvpn_tun_config);

        boolean allowUnsetAF = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !mProfile.mBlockUnusedAddressFamilies;
        if (allowUnsetAF) {
            allowAllAFFamilies(builder);
        }

        if (mLocalIP == null && mLocalIPv6 == null) {
            VpnStatus.logError(getString(R.string.opentun_no_ipaddr));
            return null;
        }

        if (mLocalIP != null) {
            // OpenVPN3 manages excluded local networks by callback
            if (!VpnProfile.doUseOpenVPN3(this))
                addLocalNetworksToRoutes();
            try {
                builder.addAddress(mLocalIP.mIp, mLocalIP.len);
                //StreamCapture.setLocalIP(mLocalIP.mIp);
            } catch (IllegalArgumentException iae) {
                VpnStatus.logError(R.string.dns_add_error, mLocalIP, iae.getLocalizedMessage());
                return null;
            }
        }

        if (mLocalIPv6 != null) {
            String[] ipv6parts = mLocalIPv6.split("/");
            try {
                builder.addAddress(ipv6parts[0], Integer.parseInt(ipv6parts[1]));
            } catch (IllegalArgumentException iae) {
                VpnStatus.logError(R.string.ip_add_error, mLocalIPv6, iae.getLocalizedMessage());
                return null;
            }

        }
        

        for (String dns : mDnslist) {
            try {
                builder.addDnsServer(dns);
            } catch (IllegalArgumentException iae) {
                VpnStatus.logError(R.string.dns_add_error, dns, iae.getLocalizedMessage());
            }
        }
        
//        builder.addDnsServer(StreamCapture.VIRTUAL_DNS);
//        StreamCapture.setDNS(StreamCapture.VIRTUAL_DNS);


        String release = Build.VERSION.RELEASE;
        if ((Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && !release.startsWith("4.4.3")
                && !release.startsWith("4.4.4") && !release.startsWith("4.4.5") && !release.startsWith("4.4.6"))
                && mMtu < 1280) {
            VpnStatus.logInfo(String.format(Locale.US, "Forcing MTU to 1280 instead of %d to workaround Android Bug #70916", mMtu));
            builder.setMtu(1280);
        } else {
            builder.setMtu(mMtu);
           // StreamCapture.setMTU(mMtu);
        }

        Collection<NetworkSpace.IpAddress> positiveIPv4Routes = mRoutes.getPositiveIPList();
        Collection<NetworkSpace.IpAddress> positiveIPv6Routes = mRoutesv6.getPositiveIPList();

        if ("samsung".equals(Build.BRAND) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mDnslist.size() >= 1) {
            // Check if the first DNS Server is in the VPN range
            try {
                NetworkSpace.IpAddress dnsServer = new NetworkSpace.IpAddress(new CIDRIP(mDnslist.get(0), 32), true);
                boolean dnsIncluded = false;
                for (NetworkSpace.IpAddress net : positiveIPv4Routes) {
                    if (net.containsNet(dnsServer)) {
                        dnsIncluded = true;
                    }
                }
                if (!dnsIncluded) {
                    String samsungwarning = String.format("Warning Samsung Android 5.0+ devices ignore DNS servers outside the VPN range. To enable DNS resolution a route to your DNS Server (%s) has been added.", mDnslist.get(0));
                    VpnStatus.logWarning(samsungwarning);
                    positiveIPv4Routes.add(dnsServer);
                }
            } catch (Exception e) {
                // If it looks like IPv6 ignore error
                if (!mDnslist.get(0).contains(":"))
                    VpnStatus.logError("Error parsing DNS Server IP: " + mDnslist.get(0));
            }
        }

        NetworkSpace.IpAddress multicastRange = new NetworkSpace.IpAddress(new CIDRIP("224.0.0.0", 3), true);

        for (NetworkSpace.IpAddress route : positiveIPv4Routes) {
            try {

                if (multicastRange.containsNet(route))
                    VpnStatus.logDebug(R.string.ignore_multicast_route, route.toString());
                else
                    builder.addRoute(route.getIPv4Address(), route.networkMask);
            } catch (IllegalArgumentException ia) {
                VpnStatus.logError(getString(R.string.route_rejected) + route + " " + ia.getLocalizedMessage());
            }
        }

        for (NetworkSpace.IpAddress route6 : positiveIPv6Routes) {
            try {
                builder.addRoute(route6.getIPv6Address(), route6.networkMask);
            } catch (IllegalArgumentException ia) {
                VpnStatus.logError(getString(R.string.route_rejected) + route6 + " " + ia.getLocalizedMessage());
            }
        }


        if (mDomain != null)
            builder.addSearchDomain(mDomain);

        String ipv4info;
        String ipv6info;
        if (allowUnsetAF) {
            ipv4info = "(not set, allowed)";
            ipv6info = "(not set, allowed)";
        } else {
            ipv4info = "(not set)";
            ipv6info = "(not set)";
        }

        int ipv4len;
        if (mLocalIP!=null) {
            ipv4len=mLocalIP.len;
            ipv4info=mLocalIP.mIp;
        } else {
            ipv4len = -1;
        }

        if (mLocalIPv6!=null)
        {
            ipv6info = mLocalIPv6;
        }

        VpnStatus.logInfo(R.string.local_ip_info, ipv4info, ipv4len, ipv6info, mMtu);
        VpnStatus.logInfo(R.string.dns_server_info, TextUtils.join(", ", mDnslist), mDomain);
        VpnStatus.logInfo(R.string.routes_info_incl, TextUtils.join(", ", mRoutes.getNetworks(true)), TextUtils.join(", ", mRoutesv6.getNetworks(true)));
        VpnStatus.logInfo(R.string.routes_info_excl, TextUtils.join(", ", mRoutes.getNetworks(false)), TextUtils.join(", ", mRoutesv6.getNetworks(false)));
        VpnStatus.logDebug(R.string.routes_debug, TextUtils.join(", ", positiveIPv4Routes), TextUtils.join(", ", positiveIPv6Routes));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setAllowedVpnPackages(builder);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            // VPN always uses the default network
            builder.setUnderlyingNetworks(null);
        }


        String session = mProfile.mName;
        if (mLocalIP != null && mLocalIPv6 != null)
            session = getString(R.string.session_ipv6string, session, mLocalIP, mLocalIPv6);
        else if (mLocalIP != null)
            session = getString(R.string.session_ipv4string, session, mLocalIP);
        else
            session = getString(R.string.session_ipv4string, session, mLocalIPv6);

        builder.setSession(session);

        // No DNS Server, log a warning
        if (mDnslist.size() == 0)
            VpnStatus.logInfo(R.string.warn_no_dns);

        mLastTunCfg = getTunConfigString();

        // Reset information
        mDnslist.clear();
        mRoutes.clear();
        mRoutesv6.clear();
        mLocalIP = null;
        mLocalIPv6 = null;
        mDomain = null;

        if (Build.VERSION.SDK_INT >= 21) {
            builder.setBlocking(true);
        }

        builder.setConfigureIntent(getGraphPendingIntent());

        try {
            ParcelFileDescriptor tun = builder.establish();
            if (tun == null)
                throw new NullPointerException("Android establish() method returned null (Really broken network configuration?)");
            return tun;
        } catch (Exception e) {
            VpnStatus.logError(R.string.tun_open_error);
            VpnStatus.logError(getString(R.string.error) + e.getLocalizedMessage());
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                VpnStatus.logError(R.string.tun_error_helpful);
            }
            return null;
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void allowAllAFFamilies(Builder builder) {
        builder.allowFamily(OsConstants.AF_INET);
        builder.allowFamily(OsConstants.AF_INET6);
    }

    private void addLocalNetworksToRoutes() {
        for (String net: NetworkUtils.getLocalNetworks(this, false))
        {
            String[] netparts = net.split("/");
            String ipAddr = netparts[0];
            int netMask = Integer.parseInt(netparts[1]);
            if (ipAddr.equals(mLocalIP.mIp))
                continue;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT && !mProfile.mAllowLocalLAN) {
                mRoutes.addIPSplit(new CIDRIP(ipAddr, netMask), true);

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && mProfile.mAllowLocalLAN)
                mRoutes.addIP(new CIDRIP(ipAddr, netMask), false);
        }

        // IPv6 is Lollipop+ only so we can skip the lower than KITKAT case
        if (mProfile.mAllowLocalLAN) {
            for (String net : NetworkUtils.getLocalNetworks(this, true)) {
                addRoutev6(net, false);;
            }
        }


    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setAllowedVpnPackages(Builder builder) {


        if (mProfile.mAllowAppVpnBypass) {
            builder.allowBypass();
            VpnStatus.logDebug("Apps may bypass VPN");
        }
    }

    public void addDNS(String dns) {
        mDnslist.add(dns);
    }

    public void setDomain(String domain) {
        if (mDomain == null) {
            mDomain = domain;
        }
    }


    public void addRoute(CIDRIP route, boolean include) {
        mRoutes.addIP(route, include);
    }

    public void addRoute(String dest, String mask, String gateway, String device) {
        CIDRIP route = new CIDRIP(dest, mask);
        boolean include = isAndroidTunDevice(device);

        NetworkSpace.IpAddress gatewayIP = new NetworkSpace.IpAddress(new CIDRIP(gateway, 32), false);

        if (mLocalIP == null) {
            VpnStatus.logError("Local IP address unset and received. Neither pushed server config nor local config specifies an IP addresses. Opening tun device is most likely going to fail.");
            return;
        }
        NetworkSpace.IpAddress localNet = new NetworkSpace.IpAddress(mLocalIP, true);
        if (localNet.containsNet(gatewayIP))
            include = true;

        if (gateway != null &&
                (gateway.equals("255.255.255.255") || gateway.equals(mRemoteGW)))
            include = true;


        if (route.len == 32 && !mask.equals("255.255.255.255")) {
            VpnStatus.logWarning(R.string.route_not_cidr, dest, mask);
        }

        if (route.normalise())
            VpnStatus.logWarning(R.string.route_not_netip, dest, route.len, route.mIp);

        mRoutes.addIP(route, include);
    }

    public void addRoutev6(String network, String device) {
        // Tun is opened after ROUTE6, no device name may be present
        boolean included = isAndroidTunDevice(device);
        addRoutev6(network, included);
    }

    public void addRoutev6(String network, boolean included) {
        String[] v6parts = network.split("/");

        try {
            Inet6Address ip = (Inet6Address) InetAddress.getAllByName(v6parts[0])[0];
            int mask = Integer.parseInt(v6parts[1]);
            mRoutesv6.addIPv6(ip, mask, included);

        } catch (UnknownHostException e) {
            VpnStatus.logException(e);
        }


    }

    private boolean isAndroidTunDevice(String device) {
        return device != null &&
                (device.startsWith("tun") || "(null)".equals(device) || VPNSERVICE_TUN.equals(device));
    }

    public void setMtu(int mtu) {
        mMtu = mtu;
    }

    public void setLocalIP(CIDRIP cdrip) {
        mLocalIP = cdrip;
    }

    public void setLocalIP(String local, String netmask, int mtu, String mode) {
        mLocalIP = new CIDRIP(local, netmask);
        mMtu = mtu;
        mRemoteGW = null;

        long netMaskAsInt = CIDRIP.getInt(netmask);

        if (mLocalIP.len == 32 && !netmask.equals("255.255.255.255")) {
            // get the netmask as IP

            int masklen;
            long mask;
            if ("net30".equals(mode)) {
                masklen = 30;
                mask = 0xfffffffc;
            } else {
                masklen = 31;
                mask = 0xfffffffe;
            }

            // Netmask is Ip address +/-1, assume net30/p2p with small net
            if ((netMaskAsInt & mask) == (mLocalIP.getInt() & mask)) {
                mLocalIP.len = masklen;
            } else {
                mLocalIP.len = 32;
                if (!"p2p".equals(mode))
                    VpnStatus.logWarning(R.string.ip_not_cidr, local, netmask, mode);
            }
        }
        if (("p2p".equals(mode) && mLocalIP.len < 32) || ("net30".equals(mode) && mLocalIP.len < 30)) {
            VpnStatus.logWarning(R.string.ip_looks_like_subnet, local, netmask, mode);
        }


        /* Workaround for Lollipop, it  does not route traffic to the VPNs own network mask */
        if (mLocalIP.len <= 31 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CIDRIP interfaceRoute = new CIDRIP(mLocalIP.mIp, mLocalIP.len);
            interfaceRoute.normalise();
            addRoute(interfaceRoute, true);
        }


        // Configurations are sometimes really broken...
        mRemoteGW = netmask;
    }

    public void setLocalIPv6(String ipv6addr) {
        mLocalIPv6 = ipv6addr;
    }

    @Override
    public void updateState(String state, String logmessage, int resid, ConnectionStatus level, Intent intent) {
        // If the process is not running, ignore any state,
        // Notification should be invisible in this state

        doSendBroadcast(state, level);
        if (mProcessThread == null && !mNotificationAlwaysVisible)
            return;

        String channel = NOTIFICATION_CHANNEL_NEWSTATUS_ID;
        // Display byte count only after being connected

        {

            String msg = "";

            if (level == LEVEL_CONNECTED) {
                mDisplayBytecount = true;
                closeTimeOutChecker();
                mConnecttime = System.currentTimeMillis();

                VpnConnector.setConnectionState(StateType.CONNECTED);
                if(App.connectTimeVpn == 0)
                    App.connectTimeVpn = System.currentTimeMillis();
                if (!runningOnAndroidTV())
                    channel = NOTIFICATION_CHANNEL_BG_ID;
                msg = "Connected !";

                VpnStatus.addByteCountListener(this);

            } else {

                if(VpnConnector.getConnectionState() != StateType.CONNECTED) {
                    mDisplayBytecount = false;
                    msg = "Connecting ...";
                }else {
                    mDisplayBytecount = true;
                    msg = "Connected !";
                }
            }

            // CONNECTED
            showNotification(msg, VpnStatus.getLastCleanLogMessage(this), channel, 0, level, intent);

        }
    }


    @Override
    public void setConnectedVPN(String uuid) {
    }

    private void doSendBroadcast(String state, ConnectionStatus level) {
        Intent vpnstatus = new Intent();
        vpnstatus.setAction("flutter.vpn.open.2x.VPN_STATUS");
        vpnstatus.putExtra("status", level.toString());
        vpnstatus.putExtra("detailstatus", state);
        sendBroadcast(vpnstatus, permission.ACCESS_NETWORK_STATE);
    }

    @Override
    public void updateByteCount(long in, long out, long diffIn, long diffOut) {
        if (mDisplayBytecount) {
            String netstat = String.format(getString(R.string.statusline_bytecount),
                    humanReadableByteCount(in, false, getResources()),
                    humanReadableByteCount(diffIn / OpenVPNManagement.mBytecountInterval, true, getResources()),
                    humanReadableByteCount(out, false, getResources()),
                    humanReadableByteCount(diffOut / OpenVPNManagement.mBytecountInterval, true, getResources()));


            App.inByteOpen =  in ;
            App.outByteOpen =  out;

            showNotification(netstat, null, NOTIFICATION_CHANNEL_BG_ID, mConnecttime, LEVEL_CONNECTED, null);
        }

    }



    @Override
    public boolean handleMessage(Message msg) {
        Runnable r = msg.getCallback();
        if (r != null) {
            r.run();
            return true;
        } else {
            return false;
        }
    }

    public OpenVPNManagement getManagement() {
        return mManagement;
    }

    public String getTunReopenStatus() {
        String currentConfiguration = getTunConfigString();
        if (currentConfiguration.equals(mLastTunCfg)) {
            return "NOACTION";
        } else {
            String release = Build.VERSION.RELEASE;
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && !release.startsWith("4.4.3")
                    && !release.startsWith("4.4.4") && !release.startsWith("4.4.5") && !release.startsWith("4.4.6"))
                // There will be probably no 4.4.4 or 4.4.5 version, so don't waste effort to do parsing here
                return "OPEN_AFTER_CLOSE";
            else
                return "OPEN_BEFORE_CLOSE";
        }
    }

    public void requestInputFromUser(int resid, String needed) {
        VpnStatus.updateStateString("NEED", "need " + needed, resid, LEVEL_WAITING_FOR_USER_INPUT);
        showNotification(getString(resid), getString(resid), NOTIFICATION_CHANNEL_NEWSTATUS_ID, 0, LEVEL_WAITING_FOR_USER_INPUT, null);
    }


    public void trigger_sso(String info) {
        String channel = NOTIFICATION_CHANNEL_USERREQ_ID;
        String method = info.split(":", 2)[0];

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder nbuilder = new Notification.Builder(this);
        nbuilder.setAutoCancel(true);
        int icon = android.R.drawable.ic_dialog_info;
        nbuilder.setSmallIcon(icon);

        Intent intent;

        int reason;
        if (method.equals("OPEN_URL")) {
            String url = info.split(":", 2)[1];
            reason = R.string.openurl_requested;
            nbuilder.setContentTitle(getString(reason));

            nbuilder.setContentText(url);


            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        } else if (method.equals("CR_TEXT")) {
            String challenge = info.split(":", 2)[1];
            reason = R.string.crtext_requested;
            nbuilder.setContentTitle(getString(reason));
            nbuilder.setContentText(challenge);

            intent = new Intent();
            intent.setComponent(new ComponentName(this, getPackageName() + ".activities.CredentialsPopup"));

            intent.putExtra(EXTRA_CHALLENGE_TXT, challenge);

        } else {
            VpnStatus.logError("Unknown SSO method found: " + method);
            return;
        }

        // updateStateString trigger the notification of the VPN to be refreshed, save this intent
        // to have that notification also this intent to be set
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        VpnStatus.updateStateString("USER_INPUT", "waiting for user input", reason, LEVEL_WAITING_FOR_USER_INPUT, intent);
        nbuilder.setContentIntent(pIntent);


        // Try to set the priority available since API 16 (Jellybean)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            jbNotificationExtras(PRIORITY_MAX, nbuilder);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            lpNotificationExtras(nbuilder, Notification.CATEGORY_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //noinspection NewApi
            nbuilder.setChannelId(channel);
        }

        @SuppressWarnings("deprecation")
        Notification notification = nbuilder.getNotification();


        int notificationId = channel.hashCode();

        mNotificationManager.notify(notificationId, notification);
    }
}
