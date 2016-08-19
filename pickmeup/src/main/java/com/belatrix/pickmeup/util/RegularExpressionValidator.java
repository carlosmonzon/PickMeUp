package com.belatrix.pickmeup.util;

import android.util.Patterns;

/**
 * Created by root on 19/08/16.
 */
public class RegularExpressionValidator {

    public static boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
