package com.rafael.alexandre.alves.gistlist.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utils {

    public static String checkText(String text) {
        String returningText = "N/A";

        if (text != null && text.length() > 0) {
            returningText = text;
        }

        return returningText;
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}