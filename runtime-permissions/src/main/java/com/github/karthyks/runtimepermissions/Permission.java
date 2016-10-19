package com.github.karthyks.runtimepermissions;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class Permission {

  public static final int REQUEST_CAMERA = 200;
  public static final int REQUEST_CONTACTS = 201;
  public static final int REQUEST_LOCATION = 202;
  public static final int REQUEST_CALENDAR = 203;
  public static final int REQUEST_MICROPHONE = 204;

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
