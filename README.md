[![Wercker](https://api.travis-ci.org/karthyks/Runtime-Permissions.svg?branch=master)](https://travis-ci.org/karthyks/Runtime-Permissions) 
[![Wercker](https://img.shields.io/badge/jcenter-v1.7-green.svg)](https://bintray.com/karthik-logs/karthyks/Runtime-Permissions/1.7)
[![Wercker](https://img.shields.io/badge/Android--Arsenal-Runtime--Permissions-brightgreen.svg)](https://android-arsenal.com/details/1/4522)

# Runtime-Permissions

# Usage 

To ask the permission from the user, use the following code. 

```java
Permission permission = new Permission.PermissionBuilder(Permission.REQUEST_LOCATION)
        .usingActivity(AppCompatActivity).withRationale("Some rationale message!")
        .build();
permission.requestPermission(PermissionActivity.REQUEST_PERMISSION_CODE);
```

or

```java
Permission permission = new Permission.PermissionBuilder(Permission.REQUEST_LOCATION)
        .usingFragment(Fragment).withRationale("Some rationale message!")
        .build();
permission.requestPermission(PermissionActivity.REQUEST_PERMISSION_CODE);
```

The user's response to the permission request will be reflected onActivityResult, as shown below.

```java
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
```

#Simple Location Settings API implementation:
  To check the user's location priority which uses SettingsAPI is simplified by the following method.
  ```
  LocationRequest locationRequest = new LocationRequest()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    LocationSettingsHelper settingsApi = new LocationSettingsHelper(MainActivity.this,
        locationRequest, true, false);
    settingsApi.checkLocationRequest();
  ```
  
 And capture the result on onActivityResult.
 
 ```
 @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  if (requestCode == LocationSettingsActivity.REQUEST_LOCATION_SETTINGS) {
      switch (resultCode) {
        case Activity.RESULT_OK:
          Toast.makeText(this, "Allowed Location Settings", Toast.LENGTH_SHORT).show();
          break;
        case Activity.RESULT_CANCELED:
          Toast.makeText(this, "Location Settings canceled", Toast.LENGTH_SHORT).show();
          break;
        default:
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }
 ```
#Note
If it throws "Permission Denied" even after allowing it, check whether all the permission is added in the manifest for the permission group.
For example, for location permission add both COARSE and FINE_LOCATION permission in the manifest.

For ease of use, the permissions to be added in the manifest, for the particular dangerous permission groups are mentioned below.

```
    <!--
     PERMISSION_GROUP PHONE
    -->
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.CALL_PHONE"/>
    <uses-permission android:name="android.permission.READ_CALL_LOG"/>
    <uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
    <uses-permission android:name="android.permission.USE_SIP"/>
    <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>

    <!--
     PERMISSION_GROUP CONTACTS
    -->
    <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
    <uses-permission android:name="android.permission.READ_CONTACTS"/>
    <uses-permission android:name="android.permission.WRITE_CONTACTS"/>

    <!--
     PERMISSION_GROUP STORAGE
    -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

    <!--
     PERMISSION_GROUP SENSORS
    -->
    <uses-permission android:name="android.permission.BODY_SENSORS"/>
    <uses-permission android:name="android.permission.USE_FINGERPRINT"/>

    <!--
     PERMISSION_GROUP AUDIO
    -->
    <uses-permission android:name="android.permission.RECORD_AUDIO"/>

    <!--
     PERMISSION_GROUP CALENDAR
    -->
    <uses-permission android:name="android.permission.READ_CALENDAR"/>
    <uses-permission android:name="android.permission.WRITE_CALENDAR"/>

    <!--
     PERMISSION_GROUP CAMERA
    -->
    <uses-permission android:name="android.permission.CAMERA"/>

    <!--
     PERMISSION_GROUP LOCATION
    -->
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

    <!--
     PERMISSION_GROUP SMS
    -->
    <uses-permission android:name="android.permission.READ_SMS"/>
    <uses-permission android:name="android.permission.RECEIVE_SMS"/>
    <uses-permission android:name="android.permission.SEND_SMS"/>
    <uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"/>
    <uses-permission android:name="android.permission.RECEIVE_MMS"/>
```
