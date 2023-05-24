package com.example.imeo_app.Service;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

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
import com.example.imeo_app.Activity.LoadData;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MachineryLocalServiceUtil;
import com.example.imeo_app.db.service.MinefrontLocalServiceUtil;
import com.example.imeo_app.db.service.MineoperationLocalServiceUtil;
import com.example.imeo_app.db.service.MineralmaterialLocalServiceUtil;
import com.example.imeo_app.db.service.ProducesellLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Machinery;
import com.example.imeo_app.db.tables.Minefront;
import com.example.imeo_app.db.tables.Mineoperation;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.example.imeo_app.db.tables.Producesell;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.validation.Validator;

public class LoadDataService extends IntentService {
    public Context context;
    public String username = "";
    public String password = "";
    public long groupId;
    ArrayList<Long> localInspectionsId;
    JSONArray reportList,machineryList,mineFrontList,mineOperationsList,mineralMaterialList,produceSellList;
    public LoadDataService() {
        super("LoadDataService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            while(true)
            {
                Thread.sleep(15000);//update every minutes
                if(isNetworkConnected()){
                    SharedPreferences shared = getSharedPreferences("userInfo", MODE_PRIVATE);
//                     username = shared.getString("user","");
//                     password = shared.getString("pass","");
                     groupId = shared.getLong("groupId",0);
                     System.out.println("user" + username + "pass" + password);
                    handleSSLHandshake();
                    sendMachineryToServer();
                    sendMineFrontToServer();
                    sendMineOperationToServer();
                    sendMineralMaterialToServer();
                    sendProduceSellToServer();
                    sendReportsToServer();

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

                    Log.d(TAG, "Service Running...");
                }
                else
                    Log.d(TAG,"");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm != null && cm.getActiveNetworkInfo() != null;
    }

    private void deleteMineralMaterialTable(){
        JsonInsertUtil.deleteMineralMaterialTable(getApplicationContext());
    }
    private void deleteMachineryTable(){
        JsonInsertUtil.deleteMachinerylTable(getApplicationContext());
    }
    private void deleteMineFrontTable(){
        JsonInsertUtil.deleteMineFrontlTable(getApplicationContext());
    }
    private void deleteMineOperationTable(){
        JsonInsertUtil.deleteMineOperationTable(getApplicationContext());
    }
    private void deleteReportsTable(){
        JsonInsertUtil.deleteReportsTable(getApplicationContext());
    }
    private void deleteProduceSellTable(){
        JsonInsertUtil.deleteProduceSellTable(getApplicationContext());
    }

    private void getMineralMaterialFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-mineral-materials-for-app/company-id/"+20155+"/group-id/"+String.valueOf(groupId) ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);
                
                try {
                    JsonInsertUtil.insertMineralMaterialFromJSON(response.getJSONArray("data"),getApplicationContext(),"LoadData");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
    private void getMachineryFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-machineries-for-app" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);

                try {
                    JsonInsertUtil.insertMachineryFromJSON(response.getJSONArray("data"),getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
    private void getMineOperationFrontFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-mine-operation-for-app" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);

                try {
                    JsonInsertUtil.insertMineOperationFromJSON(response.getJSONArray("data"),getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
    private void getReportsFromServer(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-reports-for-app/companyId/"+20155 ,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("Hadi" + response);

                JsonInsertUtil.insertReportsFromJSON(response,getApplicationContext());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
    private void getProduceSellFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-produce-sell-for-app" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);

                try {
                    JsonInsertUtil.insertProduceSellFromJSON(response.getJSONArray("data"),getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
    private void getMineFrontFromServer(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/get-mine-fronts-for-app" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Hadi" + response);

                try {
                    JsonInsertUtil.insertMineFrontFromJSON(response.getJSONArray("data"),getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }

    private void sendMachineryToServer()  {
        MachineryLocalServiceUtil machineryLocalServiceUtil = new MachineryLocalServiceUtil(getApplicationContext());
        List<Machinery> machinery = machineryLocalServiceUtil.getMachinery();
        machineryList = new JSONArray();
        if (machinery.size() > 0 ) {
            for (int i = 0; i < machinery.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("machineryId" , machinery.get(i).getMachineryid());
                        jsonObject.put("reportId" , machinery.get(i).getReportid());
                        jsonObject.put("mineId", machinery.get(i).getMineid());
                        jsonObject.put("equipmentCode", machinery.get(i).getEquipment());
                        jsonObject.put("masrafeSookht", machinery.get(i).getMasrafesookht());
                        jsonObject.put("nameMashinCode" , machinery.get(i).getNamemashin());
                        jsonObject.put("modeleMashin" , machinery.get(i).getModelemashin());
                        jsonObject.put("ruzeKari" , machinery.get(i).getRuzekari());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                machineryList.put(jsonObject);
            }
                StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/update-machinery", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if(response.contains("success")) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }) {
                    Map<String, Object> params = new HashMap();

                    @Override
                    public Map getParams() throws AuthFailureError {
                        params.put("machineryList", machineryList.toString());
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        String credentials = "aseyfodin" + ":" + "Gro693ES147";
                        ;
                        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                            params.put("Content-Type", "application/json");
                        params.put("Authorization", auth);
                        return params;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
        }
    }
    private void sendMineFrontToServer()  {
        MinefrontLocalServiceUtil minefrontLocalServiceUtil = new MinefrontLocalServiceUtil(getApplicationContext());
        List<Minefront> minefronts = minefrontLocalServiceUtil.getMinefront();
        mineFrontList = new JSONArray();
        if (minefronts.size() > 0 ) {
            for (int i = 0; i < minefronts.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("machineryId" , minefronts.get(i).getMinefrontid());
                    jsonObject.put("reportId" , minefronts.get(i).getReportid());
                    jsonObject.put("mineId", minefronts.get(i).getMineid());
                    jsonObject.put("stoneTypeCode", minefronts.get(i).getStonetype());
                    jsonObject.put("workFrontWide", minefronts.get(i).getWorkfrontwide());
                    jsonObject.put("workFrontHeight", minefronts.get(i).getWorkfrontheight());
                    jsonObject.put("workFrontLength", minefronts.get(i).getWorkfrontlength());
                    jsonObject.put("workFrontSlope", minefronts.get(i).getWorkfrontslope());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mineFrontList.put(jsonObject);
            }
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/update-mine-front", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    if(response.contains("success")) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            }) {
                Map<String, Object> params = new HashMap();

                @Override
                public Map getParams() throws AuthFailureError {
                    params.put("mineFrontList", mineFrontList.toString());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    String credentials = "aseyfodin" + ":" + "Gro693ES147";
                    ;
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                            params.put("Content-Type", "application/json");
                    params.put("Authorization", auth);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }
    private void sendMineOperationToServer()  {
        MineoperationLocalServiceUtil mineoperationLocalServiceUtil = new MineoperationLocalServiceUtil(getApplicationContext());
        List<Mineoperation> mineoperations = mineoperationLocalServiceUtil.getMineoperation();
        mineOperationsList = new JSONArray();
        if (mineoperations.size() > 0 ) {
            for (int i = 0; i < mineoperations.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mineOperationId" , mineoperations.get(i).getMineoperationid());
                    jsonObject.put("reportId" , mineoperations.get(i).getReportid());
                    jsonObject.put("mineId", mineoperations.get(i).getMineid());
                    jsonObject.put("operationType", mineoperations.get(i).getOperationtype());
                    jsonObject.put("dimension", mineoperations.get(i).getDimension());
                    jsonObject.put("volume", mineoperations.get(i).getVolume());
                    jsonObject.put("meterage", mineoperations.get(i).getMeterage());
                    jsonObject.put("cost", mineoperations.get(i).getCost());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mineOperationsList.put(jsonObject);
            }
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/update-mine-operation", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    if(response.contains("success")) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            }) {
                Map<String, Object> params = new HashMap();

                @Override
                public Map getParams() throws AuthFailureError {
                    params.put("mineOperationList", mineOperationsList.toString());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    String credentials = "aseyfodin" + ":" + "Gro693ES147";
                    ;
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                            params.put("Content-Type", "application/json");
                    params.put("Authorization", auth);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }
    private void sendMineralMaterialToServer()  {
        MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(getApplicationContext());
        List<Mineralmaterial> mineralmaterials = mineralmaterialLocalServiceUtil.getMineralmaterial();
        mineralMaterialList = new JSONArray();
        if (mineralmaterials.size() > 0 ) {
            for (int i = 0; i < mineralmaterials.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("mineralmaterialId", mineralmaterials.get(i).getMineralmaterialid());
                    jsonObject.put("mineId", mineralmaterials.get(i).getMineid());
                    jsonObject.put("minePlace", mineralmaterials.get(i).getMineplace());
                    jsonObject.put("color", mineralmaterials.get(i).getColor());
                    jsonObject.put("density", mineralmaterials.get(i).getDensity());
                    jsonObject.put("kanihayeAsli", mineralmaterials.get(i).getKanihayeasli());
                    jsonObject.put("sangeMizban", mineralmaterials.get(i).getSangemizban());
                    jsonObject.put("shekleKansar", mineralmaterials.get(i).getSheklekansar());
                    jsonObject.put("type", mineralmaterials.get(i).getType_());
                    jsonObject.put("usecase", mineralmaterials.get(i).getUsecase());
                    jsonObject.put("vahedhayeSangshenasieAsli", mineralmaterials.get(i).getVahedhayesangshenasieasli());
                    jsonObject.put("zoneSakhtari", mineralmaterials.get(i).getZonesakhtari());
                    jsonObject.put("grading", mineralmaterials.get(i).getGrading());
                    jsonObject.put("degree", mineralmaterials.get(i).getDegree());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mineralMaterialList.put(jsonObject);
            }
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/update-mineral-material", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    if(response.contains("success")) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            }) {
                Map<String, Object> params = new HashMap();

                @Override
                public Map getParams() throws AuthFailureError {
                    params.put("mineralMaterialList", mineralMaterialList.toString());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    String credentials = "aseyfodin" + ":" + "Gro693ES147";
                    ;
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                            params.put("Content-Type", "application/json");
                    params.put("Authorization", auth);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }
    private void sendProduceSellToServer()  {
        ProducesellLocalServiceUtil producesellLocalServiceUtil = new ProducesellLocalServiceUtil(getApplicationContext());
        List<Producesell> producesells = producesellLocalServiceUtil.getProducesell();
        produceSellList = new JSONArray();
        if (producesells.size() > 0 ) {
            for (int i = 0; i < producesells.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("produceSellId" , producesells.get(i).getProducesellid());
                    jsonObject.put("reportId" , producesells.get(i).getReportid());
                    jsonObject.put("mineId", producesells.get(i).getMineid());
                    jsonObject.put("salesValue", producesells.get(i).getSalesvalue());
                    jsonObject.put("mineralTransport", producesells.get(i).getMineraltransport());
                    jsonObject.put("otherDetails", producesells.get(i).getOtherdetails());
                    jsonObject.put("fixedPrice", producesells.get(i).getFixedprice());
                    jsonObject.put("mineralMaterialId", producesells.get(i).getMineralmaterialid());
                    jsonObject.put("actualExtraction", producesells.get(i).getActualextraction());
                    jsonObject.put("wasteAmount", producesells.get(i).getWasteamount());
                    jsonObject.put("waybillSerial", producesells.get(i).getWaybillserial());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                produceSellList.put(jsonObject);
            }
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/update-produce-sell", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    if(response.contains("success")) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.getMessage());
                }
            }) {
                Map<String, Object> params = new HashMap();

                @Override
                public Map getParams() throws AuthFailureError {
                    params.put("produceSellList", produceSellList.toString());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    String credentials = "aseyfodin" + ":" + "Gro693ES147";
                    ;
                    String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                            params.put("Content-Type", "application/json");
                    params.put("Authorization", auth);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }
    private void sendReportsToServer()  {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getApplicationContext());
        List<Report> reports = reportLocalServiceUtil.getReport();
        reportList = new JSONArray();
        if (reports.size() > 0 ) {
            for (int i = 0; i < reports.size(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("reportId" , reports.get(i).getReportid());
                        jsonObject.put("reportDate" , reports.get(i).getReportdate());
                        jsonObject.put("mineId", reports.get(i).getMineid());
                        jsonObject.put("mineName", reports.get(i).getMinename());
                        jsonObject.put("mineActive", reports.get(i).getMineactive());

                        jsonObject.put("createDate" , reports.get(i).getCreatedate() == null ? -1 : reports.get(i).getCreatedate().getTime());
                        jsonObject.put("modifiedDate" , reports.get(i).getModifieddate() == null ? -1 : reports.get(i).getModifieddate().getTime());
                        jsonObject.put("statusDate" , reports.get(i).getStatusdate() == null ? -1 : reports.get(i).getStatusdate().getTime());

                        jsonObject.put("statusUserId", reports.get(i).getStatususerid());
                        jsonObject.put("exploreLicenseName", reports.get(i).getExplorelicensename());
                        jsonObject.put("geometrySinekarJebhe", reports.get(i).getGeometrysinekarjebhe());
                        jsonObject.put("geometryDepoMadani", reports.get(i).getGeometrydepomadani());
                        jsonObject.put("geometryDepoWaste", reports.get(i).getGeometrydepowaste());
                        jsonObject.put("geometryDahaneTunnel", reports.get(i).getGeometrydahanetunnel());
                        jsonObject.put("geometrySinekar", reports.get(i).getGeometrysinekar());
                        jsonObject.put("geometryTrench", reports.get(i).getGeometrytrench());
                        jsonObject.put("geometryWell", reports.get(i).getGeometrywell());
                        jsonObject.put("workDayInMonth", reports.get(i).getWorkdayinmonth());
                        jsonObject.put("shiftInDay", reports.get(i).getShiftinday());
                        jsonObject.put("workHourInShift", reports.get(i).getWorkhourinshift());
                        jsonObject.put("workShiftInMonth", reports.get(i).getWorkshiftinmonth());
                        jsonObject.put("workHourInMonth", reports.get(i).getWorkhourinmonth());
                        jsonObject.put("extractionWorkShopQty", reports.get(i).getExtractionworkshopqty());
                        jsonObject.put("advancingTunnelQty", reports.get(i).getAdvancingtunnelqty());
                        jsonObject.put("totalLengthTunnels", reports.get(i).getTotallengthtunnels());
                        jsonObject.put("totalVolumeTunnels", reports.get(i).getTotalvolumetunnels());
                        jsonObject.put("advancingDoilesQty", reports.get(i).getAdvancingdoilesqty());
                        jsonObject.put("totalLengthDoiles", reports.get(i).getTotallengthdoiles());
                        jsonObject.put("totalDrillingDoiles", reports.get(i).getTotaldrillingdoiles());
                        jsonObject.put("extractedVolume", reports.get(i).getExtractedvolume());
                        jsonObject.put("DoneGozaresheHaffari", reports.get(i).getDonegozareshehaffari());
                        jsonObject.put("metrazheKolleHaffari", reports.get(i).getMetrazhekollehaffari());
                        jsonObject.put("qotreChalha", reports.get(i).getQotrechalha());
                        jsonObject.put("tedadeChalha", reports.get(i).getTedadechalha());
                        jsonObject.put("faseleyeChalha", reports.get(i).getFaseleyechalha());
                        jsonObject.put("tedadeChalhaDarRadif", reports.get(i).getTedadechalhadarradif());
                        jsonObject.put("tedadeRadif", reports.get(i).getTedaderadif());
                        jsonObject.put("faseleyeRadifi", reports.get(i).getFaseleyeradifi());
                        jsonObject.put("faseleTaJebheyeKareAzadeChalha", reports.get(i).getFaseletajebheyekareazadechalha());
                        jsonObject.put("omgheMotavasseteChalha", reports.get(i).getOmghemotavassetechalha());
                        jsonObject.put("meghdareSangeGhabeleEstekhraj", reports.get(i).getMeghdaresangeghabeleestekhraj());
                        jsonObject.put("haffarieVizhe", reports.get(i).getHaffarievizhe());
                        jsonObject.put("DoneGozaresheEnfejari", reports.get(i).getDonegozaresheenfejari());
                        jsonObject.put("anfo", reports.get(i).getAnfo());
                        jsonObject.put("dinamit", reports.get(i).getDinamit());
                        jsonObject.put("emolayt", reports.get(i).getEmolayt());
                        jsonObject.put("baroot", reports.get(i).getBaroot());
                        jsonObject.put("chashnieElektriki",reports.get(i).getChashnieelektriki());
                        jsonObject.put("chashnieMamuli",reports.get(i).getChashniemamuli());
                        jsonObject.put("kortex", reports.get(i).getKortex());
                        jsonObject.put("natel", reports.get(i).getNatel());
                        jsonObject.put("pc", reports.get(i).getPc());
                        jsonObject.put("takhiri", reports.get(i).getTakhiri());
                        jsonObject.put("booster", reports.get(i).getBooster());
                        jsonObject.put("fitile", reports.get(i).getFitile());
                        jsonObject.put("mohandesiAmani", reports.get(i).getMohandesiamani());
                        jsonObject.put("mohandesiPeymani", reports.get(i).getMohandesipeymani());
                        jsonObject.put("mohandesisum", reports.get(i).getMohandesisum());
                        jsonObject.put("kargaranAmani", reports.get(i).getKargaranamani());
                        jsonObject.put("kargaranPeymani", reports.get(i).getKargaranpeymani());
                        jsonObject.put("kargaranSum", reports.get(i).getKargaransum());
                        jsonObject.put("edariAmani", reports.get(i).getEdariamani());
                        jsonObject.put("edariPeymani", reports.get(i).getEdaripeymani());
                        jsonObject.put("edariSum", reports.get(i).getEdarisum());
                        jsonObject.put("amaniSum", reports.get(i).getAmanisum());
                        jsonObject.put("peymaniSum", reports.get(i).getPeymanisum());
                        jsonObject.put("totalSum", reports.get(i).getTotalsum());
                        jsonObject.put("unInsuredWorker", reports.get(i).getUninsuredworker());
                        jsonObject.put("doreAmoozeshiBimeWorker", reports.get(i).getDoreamoozeshibimeworker());
                        jsonObject.put("averageEfficiencyToneNafar", reports.get(i).getAverageefficiencytonenafar());
                        jsonObject.put("averageEfficiencyKarkonaneTolidi", reports.get(i).getAverageefficiencykarkonanetolidi());
                        jsonObject.put("averageEfficiencyKarkonanSum", reports.get(i).getAverageefficiencykarkonansum());
                        jsonObject.put("bargh", reports.get(i).getBargh());
                        jsonObject.put("gazeTabiyi", reports.get(i).getGazetabiyi());
                        jsonObject.put("abeSanati", reports.get(i).getAbesanati());
                        jsonObject.put("benzin", reports.get(i).getBenzin());
                        jsonObject.put("gazoyil", reports.get(i).getGazoyil());
                        jsonObject.put("abeShorb", reports.get(i).getAbeshorb());
                        jsonObject.put("sayer", reports.get(i).getSayer());
                        jsonObject.put("useBargh", reports.get(i).getUsebargh());
                        jsonObject.put("tedadeGenerator", reports.get(i).getTedadegenerator());
                        jsonObject.put("tavaneGenerator", reports.get(i).getTavanegenerator());
                        jsonObject.put("masrafeGenerator", reports.get(i).getMasrafegenerator());
                        jsonObject.put("vaziateRefahiePersonel", reports.get(i).getVaziaterefahiepersonel());
                        jsonObject.put("isTajhizateImenieFardi", reports.get(i).getIstajhizateimeniefardi());
                        jsonObject.put("useTajhizateImeni", reports.get(i).getUsetajhizateimeni());
                        jsonObject.put("driverGovahiMotabar", reports.get(i).getDrivergovahimotabar());
                        jsonObject.put("machineryimeni", reports.get(i).getMachineryimeni());
                        jsonObject.put("reayateShibeMojaz", reports.get(i).getReayateshibemojaz());
                        jsonObject.put("shibeJaddeyeAsli", reports.get(i).getShibejaddeyeasli());
                        jsonObject.put("shibeRamphayeDastrasi", reports.get(i).getShiberamphayedastrasi());
                        jsonObject.put("reayateAyinnamehayeImeni", reports.get(i).getReayateayinnamehayeimeni());
                        jsonObject.put("isAccidentHappen", reports.get(i).getIsaccidenthappen());
                        jsonObject.put("laghGiri", reports.get(i).getLaghgiri());
                        jsonObject.put("needGheyreFaalImeni", reports.get(i).getNeedgheyrefaalimeni());
                        jsonObject.put("otherDesc", reports.get(i).getOtherdesc());
                        jsonObject.put("moshkelatVaMavane", reports.get(i).getMoshkelatvamavane());
                        jsonObject.put("pishnahadatVaEzhareNazar", reports.get(i).getPishnahadatvaezharenazar());
                        jsonObject.put("opinion", reports.get(i).getOpinion());
                        jsonObject.put("setEnergy", reports.get(i).getSetenergy());
                        jsonObject.put("setImeni", reports.get(i).getSetimeni());
                        jsonObject.put("setMachinery", reports.get(i).getSetmachinery());
                        jsonObject.put("setOperation1", reports.get(i).getSetoperation1());
                        jsonObject.put("setOperation2", reports.get(i).getSetoperation2());
                        jsonObject.put("setPeople", reports.get(i).getSetpeople());
                        jsonObject.put("setProblems", reports.get(i).getSetproblems());
                        jsonObject.put("setProduceSell", reports.get(i).getSetproducesell());
                        jsonObject.put("setGeom1", reports.get(i).getSetgeom1());
                        jsonObject.put("setGeom2", reports.get(i).getSetgeom2());
                        jsonObject.put("setGeom3", reports.get(i).getSetgeom3());
                        jsonObject.put("setSuggest", reports.get(i).getSetsuggest());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                reportList.put(jsonObject);
            }
                StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.url) + "/imeo-portlet.appremote/update-reports", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if(response.contains("success")) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }) {
                    Map<String, Object> params = new HashMap();

                    @Override
                    public Map getParams() throws AuthFailureError {
                        params.put("reportList", reportList.toString());
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        String credentials = "aseyfodin" + ":" + "Gro693ES147";
                        ;
                        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//                            params.put("Content-Type", "application/json");
                        params.put("Authorization", auth);
                        return params;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
        }
    }
}
