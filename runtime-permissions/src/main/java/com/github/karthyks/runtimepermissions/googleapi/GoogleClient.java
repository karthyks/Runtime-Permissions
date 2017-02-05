package com.github.karthyks.runtimepermissions.googleapi;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.Serializable;

import static com.google.android.gms.common.api.GoogleApiClient.Builder;
import static com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import static com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class GoogleClient implements ConnectionCallbacks,
    OnConnectionFailedListener, Serializable {
  private GoogleApiClient googleApiClient;
  private GoogleClientCallback googleClientCallback;

  public GoogleClient(Context context, Api<NoOptions> api) {
    googleApiClient = new Builder(context)
        .addApi(api)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this).build();
  }

  public GoogleClient(Context context, Api<NotRequiredOptions> api, NoOptions noOptions) {
    googleApiClient = new Builder(context)
        .addApi(api)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this).build();
  }

  public GoogleClient(GoogleApiClient googleApiClient) {
    this.googleApiClient = googleApiClient;
  }

  public GoogleApiClient getGoogleApiClient() {
    return googleApiClient;
  }

  public void connectGoogleApi() {
    if (googleApiClient != null && !googleApiClient.isConnected())
      googleApiClient.connect();
  }

  public void disconnectGoogleApi() {
    if (googleApiClient != null && googleApiClient.isConnected()) {
      googleApiClient.disconnect();
    }
  }

  public void setGoogleClientCallback(GoogleClientCallback googleClientCallback) {
    this.googleClientCallback = googleClientCallback;
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    if (googleClientCallback != null) {
      googleClientCallback.onConnected();
    }
  }

  @Override
  public void onConnectionSuspended(int i) {
    if (googleClientCallback != null) {
      googleClientCallback.onConnectionSuspended();
    }
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    if (googleClientCallback != null) {
      googleClientCallback.onConnectionFailed();
    }
  }

  public interface GoogleClientCallback {
    void onConnected();

    void onConnectionSuspended();

    void onConnectionFailed();
  }
}
