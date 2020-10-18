package de.blinkt.openvpn.core;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arne on 08.01.17.
 */


public class Preferences {

    static SharedPreferences getSharedPreferencesMulti(String name, Context c) {
        return c.getSharedPreferences(name, Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);

    }


    public static SharedPreferences getDefaultSharedPreferences(Context c) {
        return c.getSharedPreferences(c.getPackageName() + "_preferences", Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);

    }


}
