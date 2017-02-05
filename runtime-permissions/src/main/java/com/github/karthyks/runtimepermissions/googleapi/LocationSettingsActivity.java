package com.github.karthyks.runtimepermissions.googleapi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.karthyks.runtimepermissions.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import static com.github.karthyks.runtimepermissions.PermissionActivity.TAG;

public class LocationSettingsActivity extends AppCompatActivity
    implements GoogleClient.GoogleClientCallback {

  public static final int REQUEST_LOCATION_SETTINGS = 701;
  private static final int REQUEST_CHECK_SETTINGS = 700;
  public static final String EXTRAS_LOCATION_REQUEST = "extras_location_settings_api";
  public static final String EXTRAS_ALWAYS_SHOW_REQUEST = "extras_always_show_request";
  public static final String EXTRAS_LOCATION_NEEDBLE = "extras_location_needble";

  private GoogleClient googleClient;
  private LocationRequest locationRequest;
  private boolean alwaysShowRequest;
  private boolean needBleRequest;

  public static Intent getInstance(Context context, LocationRequest request, boolean alwaysShow,
                                   boolean needBle) {
    Intent intent = new Intent(context, LocationSettingsActivity.class);
    intent.putExtra(EXTRAS_LOCATION_REQUEST, request);
    intent.putExtra(EXTRAS_ALWAYS_SHOW_REQUEST, alwaysShow);
    intent.putExtra(EXTRAS_LOCATION_NEEDBLE, needBle);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_run_time_permission);
    locationRequest = getIntent().getParcelableExtra(EXTRAS_LOCATION_REQUEST);
    alwaysShowRequest = getIntent().getBooleanExtra(EXTRAS_ALWAYS_SHOW_REQUEST, true);
    needBleRequest = getIntent().getBooleanExtra(EXTRAS_LOCATION_NEEDBLE, false);
    connectGoogleClient();
  }

  private void connectGoogleClient() {
    googleClient = new GoogleClient(this, LocationServices.API);
    googleClient.setGoogleClientCallback(this);
    googleClient.connectGoogleApi();
  }

  private void endActivity(int code) {
    setResult(code);
    finish();
  }

  public void checkLocationRequest(GoogleApiClient googleApiClient,
                                   LocationSettingsRequest.Builder builder) {
    PendingResult<LocationSettingsResult> result =
        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
      @Override
      public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        final LocationSettingsStates states = locationSettingsResult.getLocationSettingsStates();
        switch (status.getStatusCode()) {
          case LocationSettingsStatusCodes.SUCCESS:
            endActivity(Activity.RESULT_OK);
            break;
          case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
            try {
              status.startResolutionForResult(LocationSettingsActivity.this,
                  REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException e) {
              Log.d(TAG, "onResult: " + e.getMessage());
              endActivity(Activity.RESULT_CANCELED);
            }
            break;
          case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
            endActivity(Activity.RESULT_CANCELED);
            break;
        }
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CHECK_SETTINGS) {
      endActivity(resultCode);
    }
  }

  @Override
  public void onConnected() {
    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest);
    builder.setAlwaysShow(alwaysShowRequest);
    builder.setNeedBle(needBleRequest);
    checkLocationRequest(googleClient.getGoogleApiClient(), builder);
  }

  @Override
  public void onConnectionSuspended() {
    endActivity(Activity.RESULT_CANCELED);
  }

  @Override
  public void onConnectionFailed() {
    endActivity(Activity.RESULT_CANCELED);
  }

  @Override
  protected void onDestroy() {
    googleClient.disconnectGoogleApi();
    super.onDestroy();
  }
}
