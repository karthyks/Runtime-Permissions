package com.github.karthyks.runtimepermissions;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BODY_SENSORS;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.PROCESS_OUTGOING_CALLS;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.USE_FINGERPRINT;
import static android.Manifest.permission.USE_SIP;
import static android.Manifest.permission.WRITE_CALENDAR;
import static android.Manifest.permission.WRITE_CALL_LOG;
import static android.Manifest.permission.WRITE_CONTACTS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Permission {

  public static final int REQUEST_CAMERA = 0x01AA;
  public static final int REQUEST_CONTACTS = 0x01AB;
  public static final int REQUEST_LOCATION = 0x01AC;
  public static final int REQUEST_CALENDAR = 0x01AD;
  public static final int REQUEST_MICROPHONE = 0x01AE;
  public static final int REQUEST_SMS = 0x01AF;
  public static final int REQUEST_PHONE = 0x01BA;
  public static final int REQUEST_SENSORS = 0x01BB;
  public static final int REQUEST_STORAGE = 0x01BC;

  private static String[] PERMISSIONS_CONTACT = {READ_CONTACTS, WRITE_CONTACTS, GET_ACCOUNTS};
  private static String[] PERMISSIONS_LOCATION = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};
  private static String[] PERMISSIONS_CALENDAR = {READ_CALENDAR, WRITE_CALENDAR};
  private static String[] PERMISSIONS_SMS = {READ_SMS, SEND_SMS, RECEIVE_SMS};
  private static String[] PERMISSIONS_PHONE = {READ_PHONE_STATE, CALL_PHONE, READ_CALL_LOG,
      WRITE_CALL_LOG, PROCESS_OUTGOING_CALLS, USE_SIP};
  private static String[] PERMISSIONS_STORAGE = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

  private int permissionCode;
  private String[] permissions;
  private AppCompatActivity requestedActivity;
  private String rationale;
  private Fragment requestedFragment;


  private Permission(PermissionBuilder builder) {
    this.permissionCode = builder.permissionCode;
    this.permissions = builder.permissions;
    if (builder.activity != null) {
      this.requestedActivity = builder.activity;
    }
    if (builder.fragment != null) {
      this.requestedFragment = builder.fragment;
    }
    this.rationale = builder.rationale;
  }

  public void requestPermission(int requestCode) {
    if (requestedActivity != null) {
      requestedActivity.startActivityForResult(PermissionActivity.getInstance(requestedActivity,
          this.permissionCode, permissions, rationale), requestCode);
    } else if (requestedFragment != null) {
      requestedFragment.startActivityForResult(PermissionActivity.getInstance(
          requestedFragment.getContext(), this.permissionCode, permissions, rationale),
          requestCode);
    } else {
      throw new RuntimeException("Null Activity or Fragment");
    }
  }

  public static class PermissionBuilder {
    private int permissionCode;
    private String[] permissions;
    private AppCompatActivity activity;
    private Fragment fragment;
    private String rationale;

    public PermissionBuilder(int permissionCode) {
      this.permissionCode = permissionCode;
      this.permissions = getPermissions(permissionCode);
    }

    public PermissionBuilder usingActivity(AppCompatActivity activity) {
      this.activity = activity;
      return this;
    }

    public PermissionBuilder usingFragment(Fragment fragment) {
      this.fragment = fragment;
      return this;
    }

    public PermissionBuilder withRationale(String rationaleDialog) {
      this.rationale = rationaleDialog;
      return this;
    }

    public Permission build() {
      return new Permission(this);
    }

    private String[] getPermissions(int requestCode) {
      switch (requestCode) {
        case REQUEST_CAMERA:
          return new String[]{CAMERA};
        case REQUEST_CONTACTS:
          return PERMISSIONS_CONTACT;
        case REQUEST_LOCATION:
          return PERMISSIONS_LOCATION;
        case REQUEST_CALENDAR:
          return PERMISSIONS_CALENDAR;
        case REQUEST_MICROPHONE:
          return new String[]{RECORD_AUDIO};
        case REQUEST_SMS:
          return PERMISSIONS_SMS;
        case REQUEST_PHONE:
          return PERMISSIONS_PHONE;
        case REQUEST_STORAGE:
          return PERMISSIONS_STORAGE;
        case REQUEST_SENSORS:
          return new String[]{BODY_SENSORS, USE_FINGERPRINT};
        default:
          return new String[]{};
      }
    }
  }
}
