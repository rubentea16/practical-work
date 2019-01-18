package com.example.ruben.rfid_hampir;

import android.app.Application;

/**
 * Created by Ruben on 5/27/2017.
 */

public class global extends Application {

    public static String DataAbsensi = "" ;
    public static String gHari = "";
    public static String gJam = "";
    public static String gMenit = "";
    public static String namanotif = "";
    public static Integer notif = 0;


    private static global singleton;

    public static global getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
