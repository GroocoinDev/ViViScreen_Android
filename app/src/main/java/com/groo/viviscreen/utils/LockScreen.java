package com.groo.viviscreen.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class LockScreen {
    private static LockScreen singleton;
    Context context;
    boolean disableHomeButton=false;

    SharedPreferences prefs = null;

    public static LockScreen getInstance() {
        if(singleton==null) {
            singleton = new LockScreen();
        }
        return singleton;
    }

    public void init(Context context){
        this.context = context;
    }

    public void init(Context context, boolean disableHomeButton){
        this.context = context;
        this.disableHomeButton = disableHomeButton;
    }

    public void active(){
        if(disableHomeButton){
            //showSettingAccesability();
        }

        if(context!=null) {
            context.startService(new Intent(context, com.groo.viviscreen.utils.LockscreenService.class));
        }
    }

    public void deactivate(){
        if(context!=null) {
            context.stopService(new Intent(context, com.groo.viviscreen.utils.LockscreenService.class));
        }
    }
    public boolean isActive(){
        if(context!=null) {
            return isMyServiceRunning(com.groo.viviscreen.utils.LockscreenService.class);
        }else{
            return false;
        }
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
