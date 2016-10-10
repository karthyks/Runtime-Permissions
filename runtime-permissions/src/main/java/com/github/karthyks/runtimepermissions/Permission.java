package com.github.karthyks.runtimepermissions;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;

public class Permission {

  public static final int REQUEST_CAMERA = 4900;
  public static final int REQUEST_CONTACTS = 4901;
  public static final int REQUEST_LOCATION = 4902;
  public static final int REQUEST_CALENDAR = 4903;
  public static final int REQUEST_MICROPHONE = 4904;

  private static String[] PERMISSIONS_CONTACT = {Manifest.permission.READ_CONTACTS,
      Manifest.permission.WRITE_CONTACTS};

  private static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION,
      Manifest.permission.ACCESS_FINE_LOCATION};

  private static String[] PERMISSIONS_CALENDAR = {Manifest.permission.READ_CALENDAR,
      Manifest.permission.WRITE_CALENDAR};

  private int permissionCode;
  private String[] permissions;
  private AppCompatActivity requestedActivity;
  private String rationale;


  private Permission(PermissionBuilder builder) {
    this.permissionCode = builder.permissionCode;
    this.permissions = builder.permissions;
    this.requestedActivity = builder.activity;
    this.rationale = builder.rationale;
  }

  public void requestPermission(int requestCode) {
    requestedActivity.startActivityForResult(PermissionActivity.getInstance(requestedActivity,
        this.permissionCode, permissions, rationale), requestCode);
  }

  public static class PermissionBuilder {
    private int permissionCode;
    private String[] permissions;
    private AppCompatActivity activity;
    private String rationale;

    public PermissionBuilder(int permissionCode) {
      this.permissionCode = permissionCode;
      this.permissions = getPermissions(permissionCode);
    }

    public PermissionBuilder using(AppCompatActivity activity) {
      this.activity = activity;
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
          return new String[]{Manifest.permission.CAMERA};
        case REQUEST_CONTACTS:
          return PERMISSIONS_CONTACT;
        case REQUEST_LOCATION:
          return PERMISSIONS_LOCATION;
        case REQUEST_CALENDAR:
          return PERMISSIONS_CALENDAR;
        case REQUEST_MICROPHONE:
          return new String[]{Manifest.permission.RECORD_AUDIO};
        default:
          return new String[]{};
      }
    }
  }
}
