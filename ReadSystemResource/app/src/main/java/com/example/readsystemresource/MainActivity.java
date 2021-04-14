package com.example.readsystemresource;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static String TAG = "ReadSystemResource";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean config_unplugTurnsOnScreen = getAndroidBoolean("config_unplugTurnsOnScreen");
        Log.e(TAG, "config_unplugTurnsOnScreen=" + config_unplugTurnsOnScreen);
        Log.e(TAG, "getBatteryCapacity=" + getBatteryCapacity());
        //
        TextView autoBrightnessLevels = findViewById(R.id.config_autoBrightnessLevels);
        autoBrightnessLevels.setText(getBrightnessCurve("config_autoBrightnessLevels"));
        //
        TextView autoBrightnessDisplayValuesNits = findViewById(R.id.config_autoBrightnessDisplayValuesNits);
        autoBrightnessDisplayValuesNits.setText(getBrightnessCurve("config_autoBrightnessDisplayValuesNits"));
        //
        TextView screenBrightnessBacklight = findViewById(R.id.config_screenBrightnessBacklight);
        screenBrightnessBacklight.setText(getBrightnessCurve("config_screenBrightnessBacklight"));
        //
        TextView screenBrightnessNits = findViewById(R.id.config_screenBrightnessNits);
        screenBrightnessNits.setText(getBrightnessCurve("config_screenBrightnessNits"));

//        PowerProfile profile = new PowerProfile(this);
//        Log.e(TAG, "PowerProfile.sPowerItemMap=" + PowerProfile.sPowerItemMap);
//        StringBuilder text = new StringBuilder(PowerProfile.sPowerItemMap.toString());
//        for (String key : PowerProfile.sPowerArrayMap.keySet()) {
//            Log.e(TAG, "PowerProfile.sPowerArrayMap key=" + key
//                    + " array=" + Arrays.toString(PowerProfile.sPowerArrayMap.get(key)));
//            text.append("\n").append("key=").append(key).append(" array=").append(Arrays.toString(PowerProfile.sPowerArrayMap.get(key)));
//        }
//        TextView tv = findViewById(R.id.text);
//        tv.setText(text.toString());
    }

    public static String getBrightnessCurve(String name) {
        int array[] = getAndroidIntengerArray(name);
        String text = name + " = [";
        for (int i = 0; i < array.length; i++)
            text = text + array[i] + " ";
        text = text + "]" + " length=" + array.length;
        return text;
    }


    public static String[] getAndroidStringArray(String id) {
        int resourceId = Resources.getSystem().getIdentifier(id, "array", "android");
        Log.e(TAG, "getAndroidStringArray=" + resourceId);
        if (resourceId == 0) {
            return null;
        } else {
            return Resources.getSystem().getStringArray(resourceId);
        }
    }

    public static int[] getAndroidIntengerArray(String id) {
        int resourceId = Resources.getSystem().getIdentifier(id, "array", "android");
        Log.e(TAG, "getAndroidStringArray=" + resourceId);
        if (resourceId == 0) {
            return null;
        } else {
            return Resources.getSystem().getIntArray(resourceId);
        }
    }

    public static String getAndroidString(String id) {
        int resourceId = Resources.getSystem().getIdentifier(id, "string", "android");
        Log.e(TAG, "getAndroidString=" + resourceId);
        if (resourceId == 0) {
            return null;
        } else {
            return Resources.getSystem().getString(resourceId);
        }
    }

    public static boolean getAndroidBoolean(String id) {
        int resourceId = Resources.getSystem().getIdentifier(id, "bool", "android");
        Log.e(TAG, "getAndroidBoolean=" + resourceId);
        if (resourceId == 0) {
            return false;
        } else {
            return Resources.getSystem().getBoolean(resourceId);
        }
    }

    public Double getBatteryCapacity() {

        // Power profile class instance
        Object mPowerProfile_ = null;

        // Reset variable for battery capacity
        double batteryCapacity = 0;

        // Power profile class name
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {

            // Get power profile class and create instance. We have to do this
            // dynamically because android.internal package is not part of public API
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(this);

        } catch (Exception e) {

            // Class not found?
            e.printStackTrace();
        }

        try {

            // Invoke PowerProfile method "getAveragePower" with param "battery.capacity"
            batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile_);

//            // Invoke PowerProfile method "getAveragePower" with param "battery.capacity"
//            batteryCapacity = (Double) Class
//                    .forName(POWER_PROFILE_CLASS)
//                    .getMethod("getAveragePower", java.lang.String.class)
//                    .invoke(mPowerProfile_, "battery.capacity");

        } catch (Exception e) {

            // Something went wrong
            e.printStackTrace();
        }

        return batteryCapacity;
    }


}