package com.rafael.alexandre.alves.gistlist.utils;

public class Utils {

    public static String checkText(String text) {
        String returningText = "N/A";

        if (text != null && text.length() > 0) {
            returningText = text;
        }

        return returningText;
    }
}