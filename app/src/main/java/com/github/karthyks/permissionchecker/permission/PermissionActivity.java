package com.github.karthyks.permissionchecker.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.karthyks.permissionchecker.R;
import com.github.karthyks.permissionchecker.prefs.SharedPrefUtil;

public class PermissionActivity extends AppCompatActivity {

  public static final String TAG = PermissionActivity.class.getSimpleName();
  public static final String EXTRAS_PERMISSION_CODE = "extras_request_code";
  public static final String EXTRAS_PERMISSIONS = "extras_permissions";
  public static final String EXTRAS_RATIONALE = "extras_rationale";
  public static final int PERMISSION_GRANTED = 101;
  public static final int PERMISSION_DENIED = 102;
  public static final int PERMISSION_PERMANENTLY_DENIED = 103;

  public static Intent getInstance(Context context, int permissionCode, String[] permissions,
                                   String rationale) {
    Intent intent = new Intent(context, PermissionActivity.class);
    intent.putExtra(EXTRAS_PERMISSION_CODE, permissionCode);
    intent.putExtra(EXTRAS_PERMISSIONS, permissions);
    intent.putExtra(EXTRAS_RATIONALE, rationale);
    return intent;
  }

  private CoordinatorLayout layoutParent;

  private int permissionCode;
  private String[] permissions;
  private String rationaleDialog;

  private boolean shouldShowRationale = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_run_time_permission);
    layoutParent = (CoordinatorLayout) findViewById(R.id.layout_permission);
    permissionCode = getIntent().getIntExtra(EXTRAS_PERMISSION_CODE, -1);
    permissions = getIntent().getStringArrayExtra(EXTRAS_PERMISSIONS);
    rationaleDialog = getIntent().getStringExtra(EXTRAS_RATIONALE);
    boolean isGranted = false;
    for (String permission : permissions) {
      if (ActivityCompat.checkSelfPermission(this, permission)
          != PackageManager.PERMISSION_GRANTED) {
        checkPermission();
        break;
      }
      isGranted = true;
    }

    if (isGranted) {
      endActivity(PERMISSION_GRANTED);
    }
  }

  private void checkPermission() {
    for (String permission : permissions) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
        shouldShowRationale = true;
      }
    }
    if (shouldShowRationale) {
      Snackbar.make(layoutParent, rationaleDialog, Snackbar.LENGTH_INDEFINITE)
          .setAction(android.R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              ActivityCompat.requestPermissions(PermissionActivity.this, permissions,
                  permissionCode);
            }
          })
          .setActionTextColor(ContextCompat.getColor(this, android.R.color.background_light))
          .show();
    } else {
      ActivityCompat.requestPermissions(PermissionActivity.this, permissions, permissionCode);
    }
  }

  private void endActivity(int result) {
    setResult(result);
    finish();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    if (requestCode == permissionCode) {
      if (PermissionUtil.verifyPermissions(grantResults)) {
        endActivity(PERMISSION_GRANTED);
      } else {
        if (!SharedPrefUtil.isDeniedOnce(this, permissionCode)) {
          SharedPrefUtil.putInSharedPref(this, permissionCode, true);
          endActivity(PERMISSION_DENIED);
        } else {
          boolean permanentlyDenied = false;
          for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
              permanentlyDenied = true;
            }
          }
          if (permanentlyDenied) {
            endActivity(PERMISSION_PERMANENTLY_DENIED);
          } else {
            endActivity(PERMISSION_DENIED);
          }
        }
      }
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }
}
