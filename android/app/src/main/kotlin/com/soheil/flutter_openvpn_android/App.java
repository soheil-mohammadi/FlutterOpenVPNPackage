package com.soheil.flutter_openvpn_android;

import android.app.NotificationManager;
import android.content.Context;

import java.io.File;

import io.flutter.app.FlutterApplication;

public class App extends FlutterApplication {

    private static App instance ;
    public static Context context;
    public static  File WORKPATH ;

    public static long inByteOpen = 0 ;
    public static long outByteOpen = 0 ;

    public static  long connectTimeVpn = 0 ;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        WORKPATH = new File( context.getFilesDir().getAbsolutePath() + "/FlutterOpenVPN");

        instance = this;

        if(!WORKPATH.exists())
            WORKPATH.mkdir();

    }

    public static App getInstance() {
        return instance;
    }

    public void cancelNotification(int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager !=null)
            notificationManager.cancel(id);
    }

}
