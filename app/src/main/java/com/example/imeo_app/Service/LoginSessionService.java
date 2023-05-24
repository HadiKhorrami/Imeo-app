package com.example.imeo_app.Service;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;

import java.util.ArrayList;

public class LoginSessionService extends IntentService {
    public Context context;
    public String username = "";
    public String password = "";
    ArrayList<Long> localInspectionsId;
    JSONArray inspectionList;
    public LoginSessionService() {
        super("LoadDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            while(true)
            {
                Thread.sleep(604800000);//end session every week
                SharedPreferences pref = getApplicationContext().getSharedPreferences("userInfo", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("user", "");
                editor.putString("pass", "");
                editor.commit();

                Log.d(TAG, "LoginSessionService Running...");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

}
