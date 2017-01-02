package com.wzh.permissiontest;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.jar.Manifest;


public class MainActivity extends BaseActivityy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClick(View view) {
        requestRuntimePermission(new String[]{android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.CAMERA}, new PermissionListener() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "所有权限都同意了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    Toast.makeText(MainActivity.this, "被拒绝权限：" + permission, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
