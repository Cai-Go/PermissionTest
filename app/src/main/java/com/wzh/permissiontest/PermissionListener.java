package com.wzh.permissiontest;

import java.util.List;

/**
 * Created by WZH on 2017/1/2.
 */

public interface PermissionListener {
    void onGranted();

    //告知调用方啥权限被拒绝了
    void onDenied(List<String> deniedPermission);

}
