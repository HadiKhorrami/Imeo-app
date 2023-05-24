package com.example.imeo_app.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.imeo_app.R;
import com.example.imeo_app.db.util.JsonInsertUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LoadData extends AppCompatActivity {
    private ProgressBar loadDataProgress;
    TextView loadDataText;
    String groupId,userId;
    JSONArray inspectionList;
    ArrayList<Long> localInspectionsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
        loadDataProgress = findViewById(R.id.loadDataProgress);
        loadDataText = findViewById(R.id.loadDataText);
        inspectionList = new JSONArray();
        localInspectionsId = new ArrayList<>();

//        SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
//        userId = shared.getLong("userId",0);

        handleSSLHandshake();
        if(isNetworkConnected()){
            Intent intent = getIntent();
            userId = intent.getStringExtra("userId");
            groupId = intent.getStringExtra("groupId");

            deleteDocumentTable();
            deleteMineTable();
            deleteMineralMaterialTable();
            deleteMachineryTable();
            deleteMineFrontTable();
            deleteMineOperationTable();
            deleteReportsTable();
            deleteProduceSellTable();
            getMineralMaterialFromServer();
            getMachineryFromServer();
            getMineFrontFromServer();
            getMineOperationFrontFromServer();
            getReportsFromServer();
            getProduceSellFromServer();
            getMinesFromServer();
        }else {
            AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LoadData.this);
            infoBuilder.setMessage("اتصال به اینترنت برقرار نیست")
                    .setPositiveButton("باشه", (dialog, id) -> finish());
            infoBuilder.create().show();
        }
    }
    @SuppressLint("TrulyRandom")
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
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm != null && cm.getActiveNetworkInfo() != null;
    }
    private void deleteDocumentTable(){
        JsonInsertUtil.deleteDocumentTable(LoadData.this);
    }
    private void deleteMineTable(){
        JsonInsertUtil.deleteMineTable(LoadData.this);
    }
    private void deleteMineralMaterialTable(){
        JsonInsertUtil.deleteMineralMaterialTable(LoadData.this);
    }
    private void deleteMachineryTable(){
        JsonInsertUtil.deleteMachinerylTable(LoadData.this);
    }
    private void deleteMineFrontTable(){
        JsonInsertUtil.deleteMineFrontlTable(LoadData.this);
    }
    private void deleteMineOperationTable(){
        JsonInsertUtil.deleteMineOperationTable(LoadData.this);
    }
    private void deleteReportsTable(){
        JsonInsertUtil.deleteReportsTable(LoadData.this);
    }
    private void deleteProduceSellTable(){
        JsonInsertUtil.deleteProduceSellTable(LoadData.this);
    }
    /////////////////////////////////////////////////
    private void getMinesFromServer(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-mines-for-app/company-id/"+20155+"/user-id/"+userId+"/group-id/"+groupId ,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("getMines : " + response);
                JSONArray result = new JSONArray();
                loadDataProgress.setProgress(90);
                loadDataText.setText("90%");
                for(int i = 0;i<response.length();i++){
                    try {
                        result.put(response.getJSONObject(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JsonInsertUtil.insertMineFromJSON(result,LoadData.this);
                Intent intent = new Intent(LoadData.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LoadData.this);
                infoBuilder.setMessage("خطا در برقراری ارتباط با سرور")
                        .setPositiveButton("باشه", (dialog, id) -> finish());
                infoBuilder.create().show();
                System.out.println("Hadi" + error);
            }
        }) {
            Map params = new HashMap();

            @Override
            public Map getParams() throws AuthFailureError {
//                params.put("companyId", 20155);
//                params.put("userId", userId);
//                params.put("groupId", 20435);
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
        RequestQueue requestQueue = Volley.newRequestQueue(LoadData.this);
        requestQueue.add(request);

    }
    private void getMineralMaterialFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-mineral-materials-for-app/company-id/"+20155+"/group-id/"+groupId ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);
                loadDataProgress.setProgress(25);
                loadDataText.setText("25%");
                try {
                    JsonInsertUtil.insertMineralMaterialFromJSON(response.getJSONArray("data"),LoadData.this,"LoadData");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LoadData.this);
                infoBuilder.setMessage("خطا در برقراری ارتباط با سرور")
                        .setPositiveButton("باشه", (dialog, id) -> finish());
                infoBuilder.create().show();
                System.out.println("Hadi" + error);
            }
        }) {
            Map params = new HashMap();

            @Override
            public Map getParams() throws AuthFailureError {
//                params.put("companyId", 20155);
//                params.put("userId", userId);
//                params.put("groupId", 20435);
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
        RequestQueue requestQueue = Volley.newRequestQueue(LoadData.this);
        requestQueue.add(request);

    }
    private void getMachineryFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-machineries-for-app" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);
                loadDataProgress.setProgress(60);
                loadDataText.setText("60%");
                try {
                    JsonInsertUtil.insertMachineryFromJSON(response.getJSONArray("data"),LoadData.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LoadData.this);
                infoBuilder.setMessage("خطا در برقراری ارتباط با سرور")
                        .setPositiveButton("باشه", (dialog, id) -> finish());
                infoBuilder.create().show();
                System.out.println("Hadi" + error);
            }
        }) {
            Map params = new HashMap();

            @Override
            public Map getParams() throws AuthFailureError {
//                params.put("companyId", 20155);
//                params.put("userId", userId);
//                params.put("groupId", 20435);
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
        RequestQueue requestQueue = Volley.newRequestQueue(LoadData.this);
        requestQueue.add(request);

    }
    private void getMineOperationFrontFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-mine-operation-for-app" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);
                loadDataProgress.setProgress(85);
                loadDataText.setText("85%");
                try {
                    JsonInsertUtil.insertMineOperationFromJSON(response.getJSONArray("data"),LoadData.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LoadData.this);
                infoBuilder.setMessage("خطا در برقراری ارتباط با سرور")
                        .setPositiveButton("باشه", (dialog, id) -> finish());
                infoBuilder.create().show();
                System.out.println("Hadi" + error);
            }
        }) {
            Map params = new HashMap();

            @Override
            public Map getParams() throws AuthFailureError {
//                params.put("companyId", 20155);
//                params.put("userId", userId);
//                params.put("groupId", 20435);
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
        RequestQueue requestQueue = Volley.newRequestQueue(LoadData.this);
        requestQueue.add(request);

    }
    private void getReportsFromServer(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-reports-for-app/companyId/"+20155 ,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Hadi" + response);
                loadDataProgress.setProgress(87);
                loadDataText.setText("87%");
                    JsonInsertUtil.insertReportsFromJSON(response,LoadData.this);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LoadData.this);
                infoBuilder.setMessage("خطا در برقراری ارتباط با سرور")
                        .setPositiveButton("باشه", (dialog, id) -> finish());
                infoBuilder.create().show();
                System.out.println("Hadi" + error);
            }
        }) {
            Map params = new HashMap();

            @Override
            public Map getParams() throws AuthFailureError {
//                params.put("companyId", 20155);
//                params.put("userId", userId);
//                params.put("groupId", 20435);
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
        RequestQueue requestQueue = Volley.newRequestQueue(LoadData.this);
        requestQueue.add(request);

    }
    private void getProduceSellFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-produce-sell-for-app" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);
                loadDataProgress.setProgress(89);
                loadDataText.setText("89%");
                try {
                    JsonInsertUtil.insertProduceSellFromJSON(response.getJSONArray("data"),LoadData.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LoadData.this);
                infoBuilder.setMessage("خطا در برقراری ارتباط با سرور")
                        .setPositiveButton("باشه", (dialog, id) -> finish());
                infoBuilder.create().show();
                System.out.println("Hadi" + error);
            }
        }) {
            Map params = new HashMap();

            @Override
            public Map getParams() throws AuthFailureError {
//                params.put("companyId", 20155);
//                params.put("userId", userId);
//                params.put("groupId", 20435);
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
        RequestQueue requestQueue = Volley.newRequestQueue(LoadData.this);
        requestQueue.add(request);

    }
    private void getMineFrontFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-mine-fronts-for-app" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);
                loadDataProgress.setProgress(75);
                loadDataText.setText("75%");
                try {
                    JsonInsertUtil.insertMineFrontFromJSON(response.getJSONArray("data"),LoadData.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(LoadData.this);
                infoBuilder.setMessage("خطا در برقراری ارتباط با سرور")
                        .setPositiveButton("باشه", (dialog, id) -> finish());
                infoBuilder.create().show();
                System.out.println("Hadi" + error);
            }
        }) {
            Map params = new HashMap();

            @Override
            public Map getParams() throws AuthFailureError {
//                params.put("companyId", 20155);
//                params.put("userId", userId);
//                params.put("groupId", 20435);
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
        RequestQueue requestQueue = Volley.newRequestQueue(LoadData.this);
        requestQueue.add(request);

    }


}