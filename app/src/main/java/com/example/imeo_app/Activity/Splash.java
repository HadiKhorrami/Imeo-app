package com.example.imeo_app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.imeo_app.R;
import com.example.imeo_app.Service.LoadDataWorker;
import com.example.imeo_app.Service.LoginSessionWorker;


public class Splash extends AppCompatActivity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    String username,password;
    long userId,groupId;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
         userId = shared.getLong("userId",0);
         groupId = shared.getLong("groupId",0);
         username = shared.getString("user","");
         password = shared.getString("pass","");
        System.out.println("user" + username + "pass" + password);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        OneTimeWorkRequest onetimeJob = new OneTimeWorkRequest.Builder(LoadDataWorker.class)
                .setConstraints(constraints).build(); // or PeriodicWorkRequest

        OneTimeWorkRequest onetimeJob1 = new OneTimeWorkRequest.Builder(LoginSessionWorker.class)
                .setConstraints(constraints).build(); // or PeriodicWorkRequest

        WorkManager.getInstance(getApplicationContext()).enqueue(onetimeJob);
        WorkManager.getInstance(getApplicationContext()).enqueue(onetimeJob1);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(username.equals("") && password.equals("")){
                    Intent mainIntent = new Intent(Splash.this, LogIn.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();
                }else if(!username.equals("") && !password.equals("")){
                    if(isNetworkConnected()){
                        Intent intent = new Intent(Splash.this,LoadData.class);
                        intent.putExtra("userId",String.valueOf(userId));
                        intent.putExtra("groupId",String.valueOf(groupId));
                        intent.putExtra("username",username);
                        intent.putExtra("password",password);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(Splash.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm != null && cm.getActiveNetworkInfo() != null;
    }
}
