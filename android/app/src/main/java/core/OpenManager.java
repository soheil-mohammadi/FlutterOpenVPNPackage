package core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.VpnService;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.soheil.flutter_openvpn_android.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import core.model.OpenDataModel;
import de.blinkt.openvpn.core.ConfigParser;
import de.blinkt.openvpn.core.ConnectionStatus;
import de.blinkt.openvpn.core.IOpenVPNServiceInternal;
import de.blinkt.openvpn.core.OpenVPNService;
import de.blinkt.openvpn.core.VPNLaunchHelper;
import de.blinkt.openvpn.core.VpnStatus;
import io.flutter.embedding.android.FlutterActivity;

import static de.blinkt.openvpn.VpnProfile.TYPE_USERPASS_CERTIFICATES;
import static de.blinkt.openvpn.core.OpenVPNService.DISCONNECT_VPN;

public class OpenManager implements VpnStatus.StateListener {


    private static final String TAG = "OpenManager";


    private Handler handler;
    private static OpenManager instance ;

    private static Boolean isStarted = false;
    private Timer checkerData ;

    private  static OpenDataModel openDataModel;

    private IOpenVPNServiceInternal mServiceOpen2X;
    private ServiceConnection mConnectionOpen2X ;

    public static OpenManager builder(){

        if(instance == null)
            instance = new OpenManager();

        return instance;
    }

    public OpenManager() {
        handler  =  new Handler(Looper.getMainLooper());
        initOpen2X();
    }

    public void config(FlutterActivity activity , OpenDataModel openDataModel) {

        VpnConnector.setConnectionState(StateType.CONNECTING);

        OpenManager.openDataModel = openDataModel;

        Intent intent = VpnService.prepare(App.context);

        if (intent != null)  activity.startActivityForResult(intent, 350);

        else start();


    }


    private void initOpen2X() {

        mConnectionOpen2X = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className,
                                           IBinder service) {

                mServiceOpen2X = IOpenVPNServiceInternal.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                mServiceOpen2X = null;
            }

        };

        App.context.bindService(new Intent(App.context , OpenVPNService.class)
                        .setAction(OpenVPNService.START_SERVICE), mConnectionOpen2X,
                Context.BIND_AUTO_CREATE);

        VpnStatus.addStateListener(this);

    }



    private void writeToFile(String data) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    new FileOutputStream(App.WORKPATH + "/test.ovpn"));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            // Log.e("Exception", "File write failed: " + e.toString());
        }
    }



    private de.blinkt.openvpn.VpnProfile doImport(InputStream inputStream) throws IOException {
        ConfigParser cp = new ConfigParser();
        try {
            InputStreamReader isr = new InputStreamReader(inputStream);

            cp.parseConfig(isr);
            return cp.convertProfile();

        } catch (IOException e) {

        } catch (ConfigParser.ConfigParseError e) {

        } finally {
            inputStream.close();
        }

        return null;

    }

    public void start() {

        writeToFile(openDataModel.getConfig());

        try {


            File configFile = new File(App.WORKPATH + "/test.ovpn");
            de.blinkt.openvpn.VpnProfile vpnProfile = doImport(new FileInputStream(configFile));

            vpnProfile.mName = "OpenVPN";
            vpnProfile.setUUID(UUID.fromString("f000aa01-0451-4000-b000-000000000000"));
            vpnProfile.mAuthenticationType = TYPE_USERPASS_CERTIFICATES;
            vpnProfile.mUsername = openDataModel.getUserName() ;
            vpnProfile.mPassword = openDataModel.getUserPassword() ;

            de.blinkt.openvpn.core.ProfileManager profileManager = de.blinkt.openvpn.core.ProfileManager.getInstance(App.context);

            profileManager.addProfile(vpnProfile);
            profileManager.saveProfile(App.context, vpnProfile);
            profileManager.saveProfileList(App.context);

            VPNLaunchHelper.startOpenVpn(vpnProfile , App.context);

            configFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void stop() {

        Intent intent = new Intent();
        intent.setAction(DISCONNECT_VPN);
        App.context.sendBroadcast(intent);
        OpenVPNService.closeTimeOutChecker();
    }

    public void startCheckerData() {

        if(checkerData != null) {
            checkerData.cancel();
            checkerData = null;
            startCheckerData();

        } else {
            checkerData = new Timer();
            checkerData.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (VpnConnector.getConnectionState() == StateType.CONNECTED ) {

                        VpnConnector.notifyTransferData(OpenVPNService.humanReadableByteCount(App.inByteOpen , false
                                , App.context.getResources()) ,
                                OpenVPNService.humanReadableByteCount(App.outByteOpen , false
                                        , App.context.getResources()) );

                    }else  if(VpnConnector.getConnectionState() == StateType.DISCONNECTED) {
                        cancel();
                        checkerData = null;
                    }

                }
            } , 0  , 1000);
        }

    }



    public void stopListener() {

        try {

            if(checkerData != null) {
                checkerData.cancel();
                checkerData = null;
            }

            //VpnStatus.removeStateListener(this);

        }catch (IllegalArgumentException e) {

        }

    }


    @Override
    public void updateState(String state, String logmessage, int localizedResId, ConnectionStatus level, Intent Intent) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                switch (level) {

                    case LEVEL_CONNECTED:
                        isStarted = false;
                        VpnConnector.notifyState(StateType.CONNECTED);
                        break;


                    case LEVEL_START:
                        isStarted = true;
                        break;


                    case LEVEL_TIMEOUT:
                        failedToConnect();
                        break;


                    case LEVEL_NOTCONNECTED:

//                        if(isStarted) {
//
//                            if(VpnConnector.getConnectionState() == StateType.CONNECTED) {
//                                VpnConnector.notifyState(StateType.DISCONNECTED);
//                            } else {
//                                failedToConnect();
//                            }
//
//                        }

                        break;


                    case LEVEL_AUTH_FAILED:
                        failedToConnect();
                        break;
                }
            }
        });

    }

    private void failedToConnect() {
        isStarted = false;
        stop();
        OpenVPNService.closeTimeOutChecker();
        VpnConnector.notifyState(StateType.FAILED);
    }


    @Override
    public void setConnectedVPN(String uuid) {

    }



    public static void stopCore() {
        //instance = null;
        //onConnectionState = null;
    }
}
