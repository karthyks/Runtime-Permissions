package com.github.karthyks.runtimepermissions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

public abstract class PermissionUtil {
  public static boolean verifyPermissions(int[] grantResults) {
    if (grantResults.length < 1) {
      return false;
    }

    for (int result : grantResults) {
      if (result != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }
    return true;
  }

  public static void openAppSettings(AppCompatActivity activity) {
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
    intent.setData(uri);
    activity.startActivity(intent);
  }
}
