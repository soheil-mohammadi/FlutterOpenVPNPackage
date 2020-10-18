package com.soheil.flutter_openvpn_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import core.OpenManager;
import core.StateType;
import core.VpnConnector;
import core.model.OpenDataModel;
import events.ConnectionStateEvent;
import events.FailedToConnectEvent;
import events.TransferDataEvent;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {

    private static final String TAG = "MainActivity";

    private static final String OPEN_VPN_CHANNEL = "openVPNChannel";
    private static final String STATE_OPEN_VPN_CHANNEL = "openVPNStateChannel";
    private static final String STATE_OPEN_VPN_DATA_USAGE_CHANNEL = "openVPNStateDataUsageChannel";

    private  EventChannel.EventSink stateOpenVPN;
    private  EventChannel.EventSink openVPNDataUsage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerListener();
        Log.e(TAG, "onCreate: !!" );
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        new EventChannel(flutterEngine.getDartExecutor(), STATE_OPEN_VPN_CHANNEL)
                .setStreamHandler(new EventChannel.StreamHandler() {
                    @Override
                    public void onListen(Object arguments, EventChannel.EventSink events) {
                        stateOpenVPN = events;
                        Log.e(TAG, "stateOpenVPN: !!!!" );
                        setState(VpnConnector.getConnectionState());
                    }

                    @Override
                    public void onCancel(Object arguments) {

                    }
                });

        new EventChannel(flutterEngine.getDartExecutor(), STATE_OPEN_VPN_DATA_USAGE_CHANNEL)
                .setStreamHandler(new EventChannel.StreamHandler() {
                    @Override
                    public void onListen(Object arguments, EventChannel.EventSink events) {
                        openVPNDataUsage = events;
                        Log.e(TAG, "openVPNDataUsage: !!!!" );
                    }

                    @Override
                    public void onCancel(Object arguments) {

                    }
                });

        new MethodChannel(flutterEngine.getDartExecutor(), OPEN_VPN_CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, MethodChannel.Result result) {


                        switch (call.method) {

                            case "startOpenVPN" :
                                startOpenVPN(call.argument("config") ,
                                        call.argument("userName")  , call.argument("userPassword") );

                                result.success(null);

                                break;


                            case "stopOpenVPN" :

                                stopOpenVPN();
                                result.success(null);
                                break;

                            case "getLastConnectionState" :

                                getLastConnectionState();
                                result.success(null);
                                break;

                            default:
                                result.notImplemented();
                                break;
                        }
                        if (call.method.equals("startOpenVPN")) {


                        } else {
                        }
                    }
                }
        );
    }



    private void registerListener() {

        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
            registerListener();
        }else {
            EventBus.getDefault().register(this);
        }
    }

    private void unregisterListener() {

        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ConnectionStateEvent event) {

        switch (event.getStateType()) {

            case CONNECTED:
                Log.e(TAG, "CONNECTED: " );
                setState(StateType.CONNECTED);
                break;

            case CONNECTING:
                Log.e(TAG, "CONNECTING: " );
                setState(StateType.CONNECTING);
                break;

            case DIS_FROM_ANDROID_SYSTEM:
                setState(StateType.DISCONNECTED);
                break;

            case DISCONNECTED:
                setState(StateType.DISCONNECTED);
                break;

            case DISCONNECTED_FAILED:
                setState(StateType.DISCONNECTED_FAILED);
                break;

        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FailedToConnectEvent event) {
        setState(StateType.DISCONNECTED);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTransferDataEvent(TransferDataEvent event) {
        openVPNDataUsage.success(event.getDownloaded() + "~" + event.getUploaded() );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if(requestCode == 350) {
                OpenManager.builder().start();
            }else {
                setState(StateType.DISCONNECTED);
            }
        }
    }



    private void setState(StateType stateType) {

        switch (stateType) {

            case CONNECTED:
                VpnConnector.builder().startCheckerData();
                stateOpenVPN.success("CONNECTED");
                break;


            case CONNECTING:
                stateOpenVPN.success("CONNECTING");
                break;


            case DISCONNECTED:
                stateOpenVPN.success("DISCONNECTED");
                break;


            case GETTING_CONFIG:
                stateOpenVPN.success("GETTING_CONFIG");
                break;


            case DISCONNECTED_FAILED:
                stateOpenVPN.success("DISCONNECTED_FAILED");
                break;
        }


    }


    private String getLastConnectionState () {
        Log.e(TAG, "getLastConnectionState: " + VpnConnector.getConnectionState().name() );
        return VpnConnector.getConnectionState().name();
    }
    private void startOpenVPN (String config , String userName , String userPassword ) {
        VpnConnector.builder().configOpen(this ,
                new OpenDataModel(config , userName ,  userPassword));
    }


    private void stopOpenVPN () {
        if(VpnConnector.getConnectionState() == StateType.CONNECTED)
            VpnConnector.stop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterListener();
    }


}
