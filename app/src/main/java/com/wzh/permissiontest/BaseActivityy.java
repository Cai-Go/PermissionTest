package com.wzh.permissiontest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WZH on 2017/1/2.
 * 基类
 */

public class BaseActivityy extends AppCompatActivity {



    private static PermissionListener mListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    public static void requestRuntimePermission(String[] permissions, PermissionListener listener) {
        Activity topActivity = ActivityCollector.getTopActivity();
        if (topActivity  == null){
            return;
        }


        mListener = listener;
        List<String> permissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }


        if (!permissionList.isEmpty()) {
            //如果没有授权则申请权限
            ActivityCompat.requestPermissions(topActivity, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            //如果已经授权，则处理该做的事情
            mListener.onGranted();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        //为空
                        mListener.onGranted();
                    }else {
                        //不为空
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
