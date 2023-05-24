package com.example.imeo_app.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.imeo_app.R;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class MineCondition extends Fragment {

    AppCompatButton btnSave,btnBack;
    EditText edtYear,edtMonth;
    RadioGroup radioGroup;
    AppCompatRadioButton rdInActive,rdActive;
    int activeORInActive = 2;
    String mineName;
    JSONObject mineInfoJsonObject;
    public MineCondition() {
        // Required empty public constructor
    }

    public static MineCondition newInstance() {
        MineCondition fragment = new MineCondition();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_condition, container, false);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        edtYear = view.findViewById(R.id.edtYear);
        edtMonth = view.findViewById(R.id.edtMonth);
        rdActive = view.findViewById(R.id.rdActive);
        rdInActive = view.findViewById(R.id.rdInActive);
        radioGroup = view.findViewById(R.id.radioGroup);


        PersianDate pdate = new PersianDate();
        PersianDateFormat pdformater = new PersianDateFormat("Y/m");
        PersianDateFormat fullPdformater = new PersianDateFormat("Y/m/d");
        String fullDate = String.valueOf(fullPdformater.format(pdate));
        String date = String.valueOf(pdformater.format(pdate));
        String currYear,currMonth;
        currYear = date.substring(0,4);
        currMonth = String.valueOf(Integer.parseInt(date.substring(5,date.length()))-1);
        edtYear.setText(currYear);
        edtMonth.setText(currMonth);

        long mineId = getArguments().getLong("mineId");
        int mineStage = getArguments().getInt("mineStage");
        String mineInfo = getArguments().getString("mineInfo");
        String extractionMethod = getArguments().getString("extractionMethod");
//        String year = reportDate.substring(0,4);
//        String month = reportDate.substring(5,reportDate.length());
        try {
            mineInfoJsonObject = new JSONObject(mineInfo);
            mineName = mineInfoJsonObject.getString("mineName");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rdActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdInActive.setChecked(false);
                    activeORInActive = 1;
                }
            }
        });
        rdInActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdActive.setChecked(false);
                    activeORInActive = 2;
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtYear.getText().toString().equals("0") && !edtMonth.getText().toString().equals("0") && Integer.parseInt(edtYear.getText().toString()) <= Integer.parseInt(currYear)) {
                    Random rn = new Random();
                    long randomReportId = rn.nextLong() * -1;
                    if (Integer.parseInt(edtYear.getText().toString()) < (Integer.parseInt(currYear))) {
                        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
                        if(Integer.parseInt(edtMonth.getText().toString())<10){
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("reportdate",edtYear.getText().toString() + "/" + "0" + edtMonth.getText().toString());
                            params.put("mineid",mineId);
                            List<Report> reportsByMaxDate = reportLocalServiceUtil.getReportByMineIdAndReportDate(params);
                            if(reportsByMaxDate.size()>0){
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("reportId", reportsByMaxDate.get(0).getReportid());
                                    jsonObject.put("mineId", reportsByMaxDate.get(0).getMineid());
                                    jsonObject.put("reportDate", reportsByMaxDate.get(0).getReportdate());
                                    jsonObject.put("mineName", mineName);
                                    jsonObject.put("createDate", new Date().getTime());
                                    jsonObject.put("persianCreateDate",fullDate);
                                    jsonObject.put("modifiedDate", new Date().getTime());;
                                    jsonObject.put("mineActive", activeORInActive);
                                    jsonObject.put("status", 0);
                                    jsonObject.put("statusDate",new Date().getTime());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);
                                System.out.println(jsonObject);
                                JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
                                SharedPreferences pref = getActivity().getSharedPreferences("repId", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putLong("reportId", reportsByMaxDate.get(0).getReportid());
                                editor.commit();
                                Intent intent = new Intent(getActivity(), com.example.imeo_app.Activity.Report.class);
                                intent.putExtra("reportDate", reportsByMaxDate.get(0).getReportdate());
                                intent.putExtra("reportId", reportsByMaxDate.get(0).getReportid());
                                intent.putExtra("mineInfo", mineInfo);
                                intent.putExtra("mineId",mineId);
                                intent.putExtra("mineStage",mineStage);
                                intent.putExtra("extractionMethod",extractionMethod);
                                intent.putExtra("from","MineCondition");
                                getActivity().startActivity(intent);
                            }else{
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("reportId", randomReportId);
                                    jsonObject.put("mineId", mineId);
                                    jsonObject.put("reportDate", edtYear.getText().toString() + "/0" + edtMonth.getText().toString());
                                    jsonObject.put("mineName", mineName);
                                    jsonObject.put("createDate", new Date().getTime());
                                    jsonObject.put("persianCreateDate",fullDate);
                                    jsonObject.put("modifiedDate", new Date().getTime());;
                                    jsonObject.put("mineActive", activeORInActive);
                                    jsonObject.put("status", 0);
                                    jsonObject.put("statusDate",new Date().getTime());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);
                                System.out.println(jsonObject);
                                JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
                                SharedPreferences pref = getActivity().getSharedPreferences("repId", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putLong("reportId", randomReportId);
                                editor.commit();
                                Intent intent = new Intent(getActivity(), com.example.imeo_app.Activity.Report.class);
                                intent.putExtra("reportDate", edtYear.getText().toString() + "/0" + edtMonth.getText().toString());
                                intent.putExtra("reportId", randomReportId);
                                intent.putExtra("mineInfo", mineInfo);
                                intent.putExtra("mineId",mineId);
                                intent.putExtra("mineStage",mineStage);
                                intent.putExtra("extractionMethod",extractionMethod);
                                intent.putExtra("from","MineCondition");
                                getActivity().startActivity(intent);
                            }
                        }else{
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("reportdate",edtYear.getText().toString() + "/" + edtMonth.getText().toString());
                            params.put("mineid",mineId);
                            List<Report> reportsByMaxDate = reportLocalServiceUtil.getReportByMineIdAndReportDate(params);
                            if(reportsByMaxDate.size()>0){
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("reportId", reportsByMaxDate.get(0).getReportid());
                                    jsonObject.put("mineId", reportsByMaxDate.get(0).getMineid());
                                    jsonObject.put("reportDate", reportsByMaxDate.get(0).getReportdate());
                                    jsonObject.put("mineName", mineName);
                                    jsonObject.put("createDate", new Date().getTime());
                                    jsonObject.put("persianCreateDate",fullDate);
                                    jsonObject.put("modifiedDate", new Date().getTime());;
                                    jsonObject.put("mineActive", activeORInActive);
                                    jsonObject.put("status", 0);
                                    jsonObject.put("statusDate",new Date().getTime());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);
                                System.out.println(jsonObject);
                                JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
                                SharedPreferences pref = getActivity().getSharedPreferences("repId", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putLong("reportId", reportsByMaxDate.get(0).getReportid());
                                editor.commit();
                                Intent intent = new Intent(getActivity(), com.example.imeo_app.Activity.Report.class);
                                intent.putExtra("reportDate", reportsByMaxDate.get(0).getReportdate());
                                intent.putExtra("reportId", reportsByMaxDate.get(0).getReportid());
                                intent.putExtra("mineInfo", mineInfo);
                                intent.putExtra("mineId",mineId);
                                intent.putExtra("mineStage",mineStage);
                                intent.putExtra("extractionMethod",extractionMethod);
                                intent.putExtra("from","MineCondition");
                                getActivity().startActivity(intent);
                            }else {
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("reportId", randomReportId);
                                    jsonObject.put("mineId", mineId);
                                    jsonObject.put("reportDate", edtYear.getText().toString() + "/" + edtMonth.getText().toString());
                                    jsonObject.put("mineName", mineName);
                                    jsonObject.put("createDate", new Date().getTime());
                                    jsonObject.put("persianCreateDate",fullDate);
                                    jsonObject.put("modifiedDate", new Date().getTime());;
                                    jsonObject.put("mineActive", activeORInActive);
                                    jsonObject.put("status", 0);
                                    jsonObject.put("statusDate",new Date().getTime());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);
                                System.out.println(jsonObject);
                                JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
                                SharedPreferences pref = getActivity().getSharedPreferences("repId", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putLong("reportId", randomReportId);
                                editor.commit();
                                Intent intent = new Intent(getActivity(), com.example.imeo_app.Activity.Report.class);
                                intent.putExtra("reportDate", edtYear.getText().toString() + "/" + edtMonth.getText().toString());
                                intent.putExtra("reportId", randomReportId);
                                intent.putExtra("mineInfo", mineInfo);
                                intent.putExtra("mineId",mineId);
                                intent.putExtra("mineStage",mineStage);
                                intent.putExtra("extractionMethod",extractionMethod);
                                intent.putExtra("from","MineCondition");
                                getActivity().startActivity(intent);
                            }
                        }
                    }else if (Integer.parseInt(edtYear.getText().toString()) == (Integer.parseInt(currYear)) && Integer.parseInt(edtMonth.getText().toString()) <= (Integer.parseInt(currMonth))){
                        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
                        if(Integer.parseInt(edtMonth.getText().toString())<10){
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("reportdate",edtYear.getText().toString() + "/" + "0" + edtMonth.getText().toString());
                            params.put("mineid",mineId);
                            List<Report> reportsByMaxDate = reportLocalServiceUtil.getReportByMineIdAndReportDate(params);
                            if(reportsByMaxDate.size()>0){
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("reportId", reportsByMaxDate.get(0).getReportid());
                                    jsonObject.put("mineId", reportsByMaxDate.get(0).getMineid());
                                    jsonObject.put("reportDate", reportsByMaxDate.get(0).getReportdate());
                                    jsonObject.put("mineName", mineName);
                                    jsonObject.put("createDate", new Date().getTime());
                                    jsonObject.put("persianCreateDate",fullDate);
                                    jsonObject.put("modifiedDate", new Date().getTime());;
                                    jsonObject.put("mineActive", activeORInActive);
                                    jsonObject.put("status", 0);
                                    jsonObject.put("statusDate",new Date().getTime());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);
                                System.out.println(jsonObject);
                                JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
                                SharedPreferences pref = getActivity().getSharedPreferences("repId", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putLong("reportId", reportsByMaxDate.get(0).getReportid());
                                editor.commit();
                                Intent intent = new Intent(getActivity(), com.example.imeo_app.Activity.Report.class);
                                intent.putExtra("reportDate", reportsByMaxDate.get(0).getReportdate());
                                intent.putExtra("reportId",  reportsByMaxDate.get(0).getReportid());
                                intent.putExtra("mineInfo", mineInfo);
                                intent.putExtra("mineId",mineId);
                                intent.putExtra("mineStage",mineStage);
                                intent.putExtra("extractionMethod",extractionMethod);
                                intent.putExtra("from","MineCondition");
                                getActivity().startActivity(intent);
                            } else {
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("reportId", randomReportId);
                                    jsonObject.put("mineId", mineId);
                                    jsonObject.put("reportDate", edtYear.getText().toString() + "/0" + edtMonth.getText().toString());
                                    jsonObject.put("mineName", mineName);
                                    jsonObject.put("createDate", new Date().getTime());
                                    jsonObject.put("persianCreateDate",fullDate);
                                    jsonObject.put("modifiedDate", new Date().getTime());;
                                    jsonObject.put("mineActive", activeORInActive);
                                    jsonObject.put("status", 0);
                                    jsonObject.put("statusDate",new Date().getTime());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);
                                System.out.println(jsonObject);
                                JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
                                SharedPreferences pref = getActivity().getSharedPreferences("repId", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putLong("reportId", randomReportId);
                                editor.commit();
                                Intent intent = new Intent(getActivity(), com.example.imeo_app.Activity.Report.class);
                                intent.putExtra("reportDate", edtYear.getText().toString() + "/0" + edtMonth.getText().toString());
                                intent.putExtra("reportId",  randomReportId);
                                intent.putExtra("mineInfo", mineInfo);
                                intent.putExtra("mineId",mineId);
                                intent.putExtra("mineStage",mineStage);
                                intent.putExtra("extractionMethod",extractionMethod);
                                intent.putExtra("from","MineCondition");
                                getActivity().startActivity(intent);
                            }
                        }else {
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("reportdate",edtYear.getText().toString() + "/" + edtMonth.getText().toString());
                            params.put("mineid",mineId);
                            List<Report> reportsByMaxDate = reportLocalServiceUtil.getReportByMineIdAndReportDate(params);
                            if (reportsByMaxDate.size() > 0) {
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("reportId", reportsByMaxDate.get(0).getReportid());
                                    jsonObject.put("mineId", reportsByMaxDate.get(0).getMineid());
                                    jsonObject.put("reportDate", reportsByMaxDate.get(0).getReportdate());
                                    jsonObject.put("mineName", mineName);
                                    jsonObject.put("createDate", new Date().getTime());
                                    jsonObject.put("persianCreateDate",fullDate);
                                    jsonObject.put("modifiedDate", new Date().getTime());;
                                    jsonObject.put("mineActive", activeORInActive);
                                    jsonObject.put("status", 0);
                                    jsonObject.put("statusDate",new Date().getTime());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);
                                System.out.println(jsonObject);
                                JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
                                SharedPreferences pref = getActivity().getSharedPreferences("repId", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putLong("reportId", reportsByMaxDate.get(0).getReportid());
                                editor.commit();
                                Intent intent = new Intent(getActivity(), com.example.imeo_app.Activity.Report.class);
                                intent.putExtra("reportDate", reportsByMaxDate.get(0).getReportdate());
                                intent.putExtra("reportId",  reportsByMaxDate.get(0).getReportid());
                                intent.putExtra("mineInfo", mineInfo);
                                intent.putExtra("mineId",mineId);
                                intent.putExtra("mineStage",mineStage);
                                intent.putExtra("extractionMethod",extractionMethod);
                                intent.putExtra("from","MineCondition");
                                getActivity().startActivity(intent);
                            }else {
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("reportId", randomReportId);
                                    jsonObject.put("mineId", mineId);
                                    jsonObject.put("reportDate", edtYear.getText().toString() + "/" + edtMonth.getText().toString());
                                    jsonObject.put("mineName", mineName);
                                    jsonObject.put("createDate", new Date().getTime());
                                    jsonObject.put("persianCreateDate",fullDate);
                                    jsonObject.put("modifiedDate", new Date().getTime());
                                    jsonObject.put("mineActive", activeORInActive);
                                    jsonObject.put("status", 0);
                                    jsonObject.put("statusDate",new Date().getTime());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(jsonObject);
                                System.out.println(jsonObject);
                                JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
                                SharedPreferences pref = getActivity().getSharedPreferences("repId", 0);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putLong("reportId", randomReportId);
                                editor.commit();
                                Intent intent = new Intent(getActivity(), com.example.imeo_app.Activity.Report.class);
                                intent.putExtra("reportDate", edtYear.getText().toString() + "/" + edtMonth.getText().toString());
                                intent.putExtra("reportId",  randomReportId);
                                intent.putExtra("mineInfo", mineInfo);
                                intent.putExtra("mineId",mineId);
                                intent.putExtra("mineStage",mineStage);
                                intent.putExtra("extractionMethod",extractionMethod);
                                intent.putExtra("from","MineCondition");
                                getActivity().startActivity(intent);
                            }
                        }
                    }

                    SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
                    long repId = shared.getLong("reportId", 0);

                    ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
                    Report report = reportLocalServiceUtil.getReportById(repId);
                    if(report != null){
                        if(mineStage == 2 && extractionMethod.equals("روباز پله کانی") && report.getMineactive() == 1 || extractionMethod.equals("روباز انفجاری") && report.getMineactive() == 1){
                            Fragment selectedFragment = GeometryJebheKar.newInstance();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                            Bundle bundle = new Bundle();
                            bundle.putString("mineInfo", mineInfo);
                            bundle.putLong("mineId", mineId);
                            bundle.putInt("mineStage", mineStage);
                            bundle.putString("extractionMethod", extractionMethod);
                            selectedFragment.setArguments(bundle);
                        }else if(mineStage == 2 && extractionMethod.equals("زیرزمینی") && report.getMineactive() == 1){
                            Fragment selectedFragment = GeometryTunnel.newInstance();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                            Bundle bundle = new Bundle();
                            bundle.putString("mineInfo", mineInfo);
                            bundle.putLong("mineId", mineId);
                            bundle.putInt("mineStage", mineStage);
                            bundle.putString("extractionMethod", extractionMethod);
                            selectedFragment.setArguments(bundle);
                        }else if(report.getMineactive() == 2){
                            Fragment selectedFragment = Problems.newInstance();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                            Bundle bundle = new Bundle();
                            bundle.putString("mineInfo", mineInfo);
                            bundle.putLong("mineId", mineId);
                            bundle.putInt("mineStage", mineStage);
                            bundle.putInt("mineActive", 2);
                            bundle.putString("extractionMethod", extractionMethod);
                            selectedFragment.setArguments(bundle);
                        }
                        else if(mineStage == 1 && report.getMineactive() == 1){
                            Fragment selectedFragment = GeometrySineKar.newInstance();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                            Bundle bundle = new Bundle();
                            bundle.putString("mineInfo", mineInfo);
                            bundle.putLong("mineId", mineId);
                            bundle.putInt("mineStage", mineStage);
                            bundle.putString("extractionMethod", extractionMethod);
                            selectedFragment.setArguments(bundle);
                        }
                    }

                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm5");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            } else{
                    Toast.makeText(getActivity(), "مقدار برای تاریخ مجاز نیست", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
                long repId = shared.getLong("reportId", 0);

                if(mineStage==1){
                    Fragment selectedFragment = ExplorationMineralMaterial.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("mineInfo", mineInfo);
                    bundle.putLong("mineId", mineId);
                    bundle.putInt("mineStage", mineStage);
                    bundle.putString("extractionMethod", extractionMethod);
                    selectedFragment.setArguments(bundle);
                }else if(mineStage==2){
                    Fragment selectedFragment = ExtractiveMineralMaterial.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("mineInfo", mineInfo);
                    bundle.putLong("mineId", mineId);
                    bundle.putInt("mineStage", mineStage);
                    bundle.putString("extractionMethod", extractionMethod);
                    selectedFragment.setArguments(bundle);
                }
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm3");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });

        return view;
    }
}