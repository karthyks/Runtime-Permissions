package com.github.karthyks.permissionchecker.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.karthyks.permissionchecker.R;
import com.github.karthyks.runtimepermissions.Permission;
import com.github.karthyks.runtimepermissions.PermissionActivity;
import com.github.karthyks.runtimepermissions.PermissionUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  public static final int REQUEST_CODE = 111;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button btnRequest = (Button) findViewById(R.id.btn_request);
    btnRequest.setOnClickListener(this);

    Button btnCamera = (Button) findViewById(R.id.btn_camera);
    btnCamera.setOnClickListener(this);
  }

  private void checkPermission() {
    Permission permission = new Permission.PermissionBuilder(Permission.REQUEST_LOCATION)
        .usingActivity(this).withRationale("This app requires your location for no reason!")
        .build();
    permission.requestPermission(REQUEST_CODE);
  }

  private void checkCameraPermission() {
    Permission permission = new Permission.PermissionBuilder(Permission.REQUEST_CAMERA)
        .usingActivity(this).withRationale("This app uses your camera for no reason!").build();
    permission.requestPermission(REQUEST_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE) {
      switch (resultCode) {
        case PermissionActivity.PERMISSION_GRANTED:
          Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
          break;
        case PermissionActivity.PERMISSION_DENIED:
          Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
          break;
        case PermissionActivity.PERMISSION_PERMANENTLY_DENIED:
          Toast.makeText(this, "Permanently denied", Toast.LENGTH_SHORT).show();
          PermissionUtil.openAppSettings(this);
          break;
        default:
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_request:
        checkPermission();
        break;
      case R.id.btn_camera:
        checkCameraPermission();
        break;
      default:
    }
  }
}
