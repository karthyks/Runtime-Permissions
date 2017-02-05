package com.github.karthyks.runtimepermissions.googleapi;


import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.LocationRequest;

public class LocationSettingsHelper {

  private AppCompatActivity activity;
  private LocationRequest locationRequest;
  private boolean alwaysShow;
  private boolean needBle;

  public LocationSettingsHelper(AppCompatActivity activity, LocationRequest locationRequest,
                                boolean alwaysShow, boolean needBle) {
    this.activity = activity;
    this.locationRequest = locationRequest;
    this.alwaysShow = alwaysShow;
    this.needBle = needBle;
  }

  public void checkLocationRequest() {
    activity.startActivityForResult(LocationSettingsActivity.getInstance(activity,
        locationRequest, alwaysShow, needBle), LocationSettingsActivity.REQUEST_LOCATION_SETTINGS);
  }
}