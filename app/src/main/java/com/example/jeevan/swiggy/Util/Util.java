package com.example.jeevan.swiggy.Util;

import android.content.Context;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.jeevan.swiggy.R;

import java.util.Calendar;

/**
 * Created by jeevan on 7/15/17.
 */

public class Util {
    static int[] months = {R.string.Jan, R.string.Feb, R.string.Mar, R.string.Apr, R.string.May, R.string.June,
            R.string.July, R.string.Aug, R.string.Sept, R.string.Oct, R.string.Nov, R.string.Dec, R.string.UnDec};
    static ColorGenerator generator = ColorGenerator.MATERIAL;

    public static TextDrawable getNameDrawable(String name) {
        if (name == null || name.isEmpty()) name = " ";
        TextDrawable nameDrawable = TextDrawable.builder().buildRect(name.charAt(0)+"", generator.getColor(name));
        return nameDrawable;
    }


    public static String getDateString(Context context, int year, int month, int dayOfMonth) {
        return context.getString(months[month]) + " " + dayOfMonth + ", " + year;
    }

    public static String getDateString(Context context, long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        return getDateString(context, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    public static double getRoundedDouble(double debt) {
        return Math.ceil(debt*100)/100;
    }

    public static String getString(String[] arr) {
        StringBuilder ret = new StringBuilder();
        for (String a : arr) ret.append(a).append(", ");
        ret.delete(ret.length()-2, ret.length());
        return ret.toString();
    }
}
