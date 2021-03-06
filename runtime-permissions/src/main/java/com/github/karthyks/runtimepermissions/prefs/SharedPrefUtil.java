package com.github.karthyks.runtimepermissions.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.github.karthyks.runtimepermissions.Permission;

public class SharedPrefUtil {
  public static final String PREF_FILE_NAME = "runtime_permission_checker";
  public static final String LOCATION = "location";
  public static final String CONTACTS = "contacts";
  public static final String CALENDAR = "calendar";
  public static final String CAMERA = "camera";
  public static final String MICROPHONE = "microphone";
  public static final String PHONE = "phone";
  public static final String SENSORS = "sensors";
  public static final String SMS = "sms";
  public static final String STORAGE = "storage";

  public static void putInSharedPref(Context context, int permissionCode, boolean isDenied) {
    SharedPreferences.Editor sharedPref = context.getSharedPreferences(PREF_FILE_NAME,
        Context.MODE_PRIVATE).edit();
    switch (permissionCode) {
      case Permission.REQUEST_CONTACTS:
        sharedPref.putBoolean(CONTACTS, isDenied);
        break;
      case Permission.REQUEST_CALENDAR:
        sharedPref.putBoolean(CALENDAR, isDenied);
        break;
      case Permission.REQUEST_CAMERA:
        sharedPref.putBoolean(CAMERA, isDenied);
        break;
      case Permission.REQUEST_LOCATION:
        sharedPref.putBoolean(LOCATION, isDenied);
        break;
      case Permission.REQUEST_MICROPHONE:
        sharedPref.putBoolean(MICROPHONE, isDenied);
        break;
      case Permission.REQUEST_PHONE:
        sharedPref.putBoolean(PHONE, isDenied);
        break;
      case Permission.REQUEST_SENSORS:
        sharedPref.putBoolean(SENSORS, isDenied);
        break;
      case Permission.REQUEST_SMS:
        sharedPref.putBoolean(SMS, isDenied);
        break;
      case Permission.REQUEST_STORAGE:
        sharedPref.putBoolean(STORAGE, isDenied);
        break;
      default:
    }
    boolean result = sharedPref.commit();
    Log.d("SharedPref", "putInSharedPref:Commit " + result);
  }

  public static boolean isDeniedOnce(Context context, int permissionCode) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,
        Context.MODE_PRIVATE);
    switch (permissionCode) {
      case Permission.REQUEST_CONTACTS:
        return sharedPreferences.getBoolean(CONTACTS, false);
      case Permission.REQUEST_CALENDAR:
        return sharedPreferences.getBoolean(CALENDAR, false);
      case Permission.REQUEST_CAMERA:
        return sharedPreferences.getBoolean(CAMERA, false);
      case Permission.REQUEST_LOCATION:
        return sharedPreferences.getBoolean(LOCATION, false);
      case Permission.REQUEST_MICROPHONE:
        return sharedPreferences.getBoolean(MICROPHONE, false);
      case Permission.REQUEST_PHONE:
        return sharedPreferences.getBoolean(PHONE, false);
      case Permission.REQUEST_SENSORS:
        return sharedPreferences.getBoolean(SENSORS, false);
      case Permission.REQUEST_SMS:
        return sharedPreferences.getBoolean(SMS, false);
      case Permission.REQUEST_STORAGE:
        return sharedPreferences.getBoolean(STORAGE, false);
      default:
        return false;
    }
  }
}
