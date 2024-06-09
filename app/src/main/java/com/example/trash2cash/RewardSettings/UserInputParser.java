package com.example.trash2cash.RewardSettings;

import android.text.Editable;

// Class used when parsing editable text fields
public class UserInputParser {
    public static int parseEditableTextToInt(Editable text){
        try{
            return Integer.parseInt(String.valueOf(text));
        } catch(Exception e) {
            return 0;
        }
    }

    public static String parseEditableTextToString(Editable text){
        try{
            return String.valueOf(text);
        } catch(Exception e) {
            return "";
        }
    }
}
