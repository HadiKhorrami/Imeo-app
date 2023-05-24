package com.example.imeo_app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MemberShipLocalServiceUtil;
import com.example.imeo_app.db.tables.Membership;
import com.example.imeo_app.db.util.JsonInsertUtil;
//import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LogIn extends AppCompatActivity {
    AppCompatButton btnLogIn;
    EditText edtMemberShipCode, edtPassword;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        constraintLayout = findViewById(R.id.constraintLayout);
        btnLogIn = findViewById(R.id.btnLogIn);
        edtMemberShipCode = findViewById(R.id.edtMemberShipCode);
        edtPassword = findViewById(R.id.edtPassword);

        handleSSLHandshake();
        SharedPreferences shared = getSharedPreferences("FirstLogin", MODE_PRIVATE);
        if(isNetworkConnected()){
            getMembership();
        }else{
            if(shared.getBoolean("firstLogin",false)==false){
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LogIn.this);
                infoBuilder.setMessage("اتصال به اینترنت برقرار نیست")
                        .setPositiveButton("باشه", (dialog, id) -> finish());
                infoBuilder.create().show();
            }else if(shared.getBoolean("firstLogin",false)==true){

            }
        }
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            //            @RequiresApi(api = Build.VERSION_CODES.P)
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                MemberShipLocalServiceUtil memberShipLocalServiceUtil = new MemberShipLocalServiceUtil(LogIn.this);

                if(!edtPassword.getText().toString().equals("") && !edtMemberShipCode.getText().toString().equals("")){
                    List<Membership> membershipList = memberShipLocalServiceUtil.getMemberShipByCode("membershipcode",Long.parseLong(edtMemberShipCode.getText().toString()));
                    if(membershipList.size() > 0){
                        if(membershipList.get(0).getPhonenumber().equals(edtPassword.getText().toString())) {
                            if (isNetworkConnected()) {
                                SharedPreferences pref = LogIn.this.getSharedPreferences("FirstLogin", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putBoolean("firstLogin", true);
                                editor.commit();

                                Intent intent = new Intent(LogIn.this, LoadData.class);
                                intent.putExtra("userId",String.valueOf(membershipList.get(0).getUserid()));
                                intent.putExtra("groupId",String.valueOf(membershipList.get(0).getGroupid()));
                                startActivity(intent);
                                finish();

                            } else {
                                Intent intent = new Intent(LogIn.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            SharedPreferences pref = LogIn.this.getSharedPreferences("userInfo", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putLong("groupId", membershipList.get(0).getGroupid());
                            editor.putLong("userId", membershipList.get(0).getUserid());
                            editor.putString("userName", membershipList.get(0).getUsername());
                            editor.putString("user", membershipList.get(0).getMembershipcode());
                            editor.putString("pass", edtPassword.getText().toString());
                            editor.commit();

                            Typeface font = Typeface.createFromAsset(LogIn.this.getAssets(), "fonts/IRANSansWeb.ttf");
                            SpannableString efr = new SpannableString("ورود با موفقیت انجام شد");
                            efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else {
                            Typeface font = Typeface.createFromAsset(LogIn.this.getAssets(), "fonts/IRANSansWeb.ttf");
                            SpannableString efr = new SpannableString("رمز عبور صحیح نیست");
                            efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            Toast.makeText(LogIn.this, efr, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Typeface font = Typeface.createFromAsset(LogIn.this.getAssets(), "fonts/IRANSansWeb.ttf");
                        SpannableString efr = new SpannableString("کاربر موجود نیست");
                        efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        Toast.makeText(LogIn.this, efr, Toast.LENGTH_SHORT).show();
                    }
                }else if(edtPassword.getText().toString().equals("") || edtMemberShipCode.getText().toString().equals("")){
                    Typeface font = Typeface.createFromAsset(LogIn.this.getAssets(), "fonts/IRANSansWeb.ttf");
                    SpannableString efr = new SpannableString("نام کاربری و رمز عبور را وارد نمایید");
                    efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Toast.makeText(LogIn.this, efr, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

    private void getMembership() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-memberships-for-app/company-id/"+20155+"/group-id/"+1758958 ,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Hadi" + response);
//                loadDataProgress.setProgress(50);
//                loadDataText.setText("100%");
//
                JsonInsertUtil.insertMemberShipFromJSON(response,LogIn.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LogIn.this);
                infoBuilder.setMessage("خطا در برقراری ارتباط با سرور")
                        .setPositiveButton("باشه", (dialog, id) -> finish());
                infoBuilder.create().show();
                System.out.println("Hadi" + error);
            }
        }) {
            Map params = new HashMap();

            @Override
            public Map getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String credentials = "aseyfodin:Gro693ES147";
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(LogIn.this);
        requestQueue.add(request);

    }

    public static String sha256(final String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.encodeToString(hash, Base64.DEFAULT);
            return encoded.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm != null && cm.getActiveNetworkInfo() != null;
    }
}