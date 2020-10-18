package core;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;


import core.model.OpenDataModel;
import events.ConnectionStateEvent;
import events.FailedToConnectEvent;
import events.TransferDataEvent;
import events.VpnTypeEvent;
import io.flutter.embedding.android.FlutterActivity;

public class VpnConnector {


    private static final String TAG = "VpnConnector";

    private static VpnConnector instance ;

    private static StateType state = StateType.DISCONNECTED;

    private static final String currentTypeShared = "currentTypeShared" ;

    private static ConnectionType latestVpnType = ConnectionType.OPENVPN_CONNECTION ;


    public static VpnConnector builder() {

        if(instance == null)
            instance = new VpnConnector( );

        return instance;
    }


    public VpnConnector() {
        this.latestVpnType = initVpnType();
    }




    private ConnectionType initVpnType() {
        return ConnectionType.OPENVPN_CONNECTION ;
    }


    public  static void notifyState(StateType state ) {

        if(getConnectionState() != state) {

            if(state == StateType.FAILED) {
                EventBus.getDefault().post(new FailedToConnectEvent(true));
                return;
            }

//            if(state != StateType.NET_PROBLEM) {
//
//                if(state == StateType.DISCONNECTED_FAILED)
//                    setConnectionState(StateType.DISCONNECTED);
//                else
//                    setConnectionState(state);
//            }

            EventBus.getDefault().post(new ConnectionStateEvent(state , getLatestVpnType()));
            Log.e(TAG, "notifyState: !!" );
        }
    }


    public  static void notifyTransferData(String downloaded , String uploaded) {
        EventBus.getDefault().post(new TransferDataEvent(downloaded , uploaded));
    }


    public  static void notifySelectedConnectionType(ConnectionType connectionType) {
        EventBus.getDefault().post(new VpnTypeEvent(connectionType));
    }


    public void startCheckerData() {

        switch (latestVpnType) {

            case OPENVPN_CONNECTION:
                OpenManager.builder().startCheckerData();
                break;

        }
    }


    public  static void stopListener() {

        OpenManager.builder().stopListener();
        // instance = null;
    }

    public void startListener() {

        OpenManager.builder().startCheckerData();
    }



    public StateType getState() {
        return state;
    }

    public static ConnectionType getLatestVpnType() {
        return latestVpnType;
    }


    public static void setConnectionType(ConnectionType connectionType) {
        latestVpnType = connectionType;
    }


    public static void setConnectionState(StateType vpnState) {


        if(vpnState == StateType.DIS_FROM_ANDROID_SYSTEM) {
            state = StateType.DISCONNECTED;
        }else {
            notifyState(vpnState);
        }

        state = vpnState;


    }

    public static StateType getConnectionState() {
        return  state;
    }


    public void refreshState() {
        notifySelectedConnectionType(latestVpnType);
    }


    public static void startOpen() {
        setConnectionType(ConnectionType.OPENVPN_CONNECTION);
        OpenManager.builder().start();
    }


    public void configOpen(FlutterActivity activity , OpenDataModel openDataModel ) {
        OpenManager.builder().config(activity , openDataModel);
    }

    public static void stop() {
        stopSmart();
    }

    private  static void stopSmart() {
        stopOpen();
        stopListener();
    }


    public void stopCore() {
        instance = null;
        OpenManager.stopCore();
    }


    private static void stopOpen() {
        OpenManager.builder().stop();
        VpnConnector.setConnectionState(StateType.DISCONNECTED);
    }

}
