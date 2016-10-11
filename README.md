# Runtime-Permissions

# Usage 

To ask the permission from the user, use the following code. 

```java
Permission permission = new Permission.PermissionBuilder(Permission.REQUEST_LOCATION)
        .using(this).withRationale("Some rationale message!")
        .build();
permission.requestPermission(REQUEST_CODE);
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





