package com.example.imeo_app.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.imeo_app.Activity.Report;
import com.example.imeo_app.DataModels.MineExpansionLayout;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.util.JsonInsertUtil;
import com.github.florent37.expansionpanel.ExpansionHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class MineExpansionRecyclerAdapter extends RecyclerView.Adapter<MineExpansionRecyclerAdapter.MineExpansionViewHolder> {
    ArrayList<MineExpansionLayout> mineExpansionLayoutsArrayList;
    RecyclerView recyclerView;
    Dialog dialog;
    JSONObject mineInfo;
    private Context context;
    private int lastPosition = -1;
    private int maxYear = 0;
    private int maxMonth = 0;
    String[] centerNewPoints;

    public MineExpansionRecyclerAdapter(Context context, ArrayList<MineExpansionLayout> arrayList) {
        mineExpansionLayoutsArrayList = new ArrayList<MineExpansionLayout>();
        mineExpansionLayoutsArrayList = arrayList;
    }


    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    @NonNull
    @Override
    public MineExpansionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_expansion_layout, parent, false);
        context = parent.getContext();
        return new MineExpansionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MineExpansionViewHolder holder, final int position) {
        final MineExpansionLayout dataModel = mineExpansionLayoutsArrayList.get(position);
        mineInfo = new JSONObject();
        holder.txtMineCode.setText(dataModel.getMinecode());
        holder.txtMineName.setText(dataModel.getMinename());
        holder.txtTitle.setText(dataModel.getMinename());
        holder.txtExploreLicenseName.setText(dataModel.getExplorelicensename());
        holder.txtCadastreId.setText(dataModel.getCadastreid());
        holder.txtLicenseNumber.setText(dataModel.getLicensenumber());
        holder.txtExtractionCapacity.setText(String.valueOf(dataModel.getExtractioncapacity()));
        holder.txtSazmanMembershipName.setText(dataModel.getSazmanmembershipname());
        holder.txtLicenseType.setText(dataModel.getLicensetype());
        holder.txtMineType.setText(dataModel.getMinetype());
        holder.txtExtractionMethod.setText(dataModel.getExtractionmethod());
        holder.btnReport.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(context);
                List<com.example.imeo_app.db.tables.Report> reports = reportLocalServiceUtil.getReportByMineId("mineId",dataModel.getMineid());

                if(reports.size()>0){
                    long latestReportId = 0;
                    PersianDateFormat pdformater1 = new PersianDateFormat("yyyy/MM/dd");
                    for(int i = 0;i<reports.size();i++){
                        try {
                            PersianDate maxPersianDate = pdformater1.parse(reports.get(0).getReportdate()+"/01");
                            maxYear = maxPersianDate.getShYear();
                            maxMonth = maxPersianDate.getShMonth();
                            PersianDate persianDate = pdformater1.parse(reports.get(i).getReportdate()+"/01");
                            if(persianDate.getShYear()>maxYear){
                                maxYear = persianDate.getShYear();
                                maxMonth = persianDate.getShMonth();
                            }
                            if(persianDate.getShYear() == maxYear && persianDate.getShMonth()>maxMonth){
                                maxMonth = persianDate.getShMonth();
                            }
                            System.out.println(maxYear + " " + maxMonth);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(0<maxMonth && maxMonth<10){
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("reportdate",maxYear + "/" + "0" + maxMonth);
                        params.put("mineid",dataModel.getMineid());
                        List<com.example.imeo_app.db.tables.Report> reportsByMaxDate = reportLocalServiceUtil.getReportByMineIdAndReportDate(params);
                        if(reportsByMaxDate.size()>0){
                            latestReportId = reportsByMaxDate.get(0).getReportid();
                            System.out.println("adad" + latestReportId);
                        }
                    }else {
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("reportdate",maxYear + "/" + maxMonth);
                        params.put("mineid",dataModel.getMineid());
                        List<com.example.imeo_app.db.tables.Report> reportsByMaxDate = reportLocalServiceUtil.getReportByMineIdAndReportDate(params);
                        if(reportsByMaxDate.size()>0){
                            latestReportId = reportsByMaxDate.get(0).getReportid();
                            System.out.println("adad" + latestReportId);
                        }
                    }
                    Random rn = new Random();
                    long randomReportId = rn.nextLong() * -1;
                    PersianDate pdate = new PersianDate();
                    PersianDateFormat pdformater = new PersianDateFormat("Y/m");
                    String date = String.valueOf(pdformater.format(pdate));
                    System.out.println("date = " + date);
                    if(maxYear <= Integer.parseInt(date.substring(0,4)) && maxMonth < Integer.parseInt(date.substring(5,date.length()))-1){
                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("reportId", randomReportId);
                            jsonObject.put("mineId", dataModel.getMineid());
                            jsonObject.put("mineName", dataModel.getMinename());
                            jsonObject.put("reportDate", date.substring(0,5) + String.valueOf(Integer.parseInt(date.substring(5,date.length()))-1));
                            jsonObject.put("createDate", new Date().getTime());
                            jsonObject.put("persianCreateDate", pdformater.format(pdate));
                            jsonObject.put("modifiedDate", new Date().getTime());
                            jsonObject.put("mineActive", 2);
                            jsonObject.put("status", 0);
                            jsonObject.put("statusDate", new Date().getTime());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(jsonObject);
                        System.out.println(jsonObject);
                        JsonInsertUtil.insertReportsFromJSON(jsonArray, context);
                    }

                    try {
                        mineInfo.put("mineId",dataModel.getMineid());
                        mineInfo.put("mineCode",dataModel.getMinecode());
                        mineInfo.put("mineName",dataModel.getMinename());
                        mineInfo.put("mineType",dataModel.getMinetype());
                        mineInfo.put("mineStage",dataModel.getMinestage());
                        mineInfo.put("cadastreId",dataModel.getCadastreid());
                        mineInfo.put("nationalCode",dataModel.getNationalcode());
                        mineInfo.put("beneficiaryName",dataModel.getBeneficiaryname());
                        mineInfo.put("beneficiaryAddress",dataModel.getBeneficiaryaddress());
                        mineInfo.put("beneficiaryPhone",dataModel.getBeneficiaryphone());
                        mineInfo.put("mineAddress",dataModel.getMineaddress());
                        mineInfo.put("minePhone",dataModel.getMinephone());
                        mineInfo.put("supervisorMembershipCode",dataModel.getSupervisormembershipcode());
                        mineInfo.put("exploreLicenseName",dataModel.getExplorelicensename());
                        mineInfo.put("exploreLicenseNumber",dataModel.getExplorelicensenumber());
                        mineInfo.put("exploreLicenseDate",dataModel.getExplorelicensedate());
                        mineInfo.put("exploreLicenseExpireDate",dataModel.getMinename());
                        mineInfo.put("mineralMaterial",dataModel.getMineralmaterial());
                        mineInfo.put("physicalProgressPercent",dataModel.getPhysicalprogresspercent());
                        mineInfo.put("closestCity",dataModel.getClosestcity());
                        mineInfo.put("closestVillage",dataModel.getClosestvillage());
                        mineInfo.put("extractionCapacity",dataModel.getExtractioncapacity());
                        mineInfo.put("extractionMethod",dataModel.getExtractionmethod());
                        mineInfo.put("licenseNumber",dataModel.getLicensenumber());
                        mineInfo.put("licenseType",dataModel.getLicensetype());
                        mineInfo.put("licenseExpireDate",dataModel.getLicenseexpiredate());
                        mineInfo.put("reportId",dataModel.getReportid());
                        mineInfo.put("reportStatus",dataModel.getReportstatus());
                        mineInfo.put("sazmanMembershipCode",dataModel.getSazmanmembershipcode());
                        mineInfo.put("sazmanMembershipName",dataModel.getSazmanmembershipname());
                        mineInfo.put("wkt",dataModel.getWkt());
                        mineInfo.put("wktCenter",dataModel.getWktcenter());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(context, Report.class);
                    intent.putExtra("reportDate", maxYear + "/" + maxMonth);
                    intent.putExtra("reportId", latestReportId);
                    intent.putExtra("mineInfo", String.valueOf(mineInfo));
                    intent.putExtra("mineId",dataModel.getMineid());
                    intent.putExtra("mineStage",dataModel.getMinestage());
                    intent.putExtra("extractionMethod",dataModel.getExtractionmethod());
                    intent.putExtra("from","");
                    context.startActivity(intent);

                }else {
                    System.out.println("NoHas");
                    Random rn = new Random();
                    long randomReportId = rn.nextLong() * -1;
                    /////////////////////////////////////////////////////////////////
                    PersianDate pdate = new PersianDate();
                    PersianDateFormat pdformater = new PersianDateFormat("Y/m");
                    PersianDateFormat fullPdformater = new PersianDateFormat("Y/m/d");
                    String date = String.valueOf(pdformater.format(pdate));

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("reportdate",date.substring(0,5) + "0" + String.valueOf(Integer.parseInt(date.substring(5,date.length()))-1));
                    params.put("mineid",dataModel.getMineid());

                        List<com.example.imeo_app.db.tables.Report> reportsByMaxDate = reportLocalServiceUtil.getReportByMineIdAndReportDate(params);
                        if(reportsByMaxDate.size()==0) {
                            JSONArray jsonArray = new JSONArray();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("reportId", randomReportId);
                                jsonObject.put("mineId", dataModel.getMineid());
                                jsonObject.put("mineName", dataModel.getMinename());
                                jsonObject.put("reportDate", date.substring(0,5) + String.valueOf(Integer.parseInt(date.substring(5,date.length()))-1));
                                jsonObject.put("createDate", new Date().getTime());
                                jsonObject.put("persianCreateDate", fullPdformater.format(pdate));
                                jsonObject.put("modifiedDate", new Date().getTime());
                                jsonObject.put("mineActive", 2);
                                jsonObject.put("status", 0);
                                jsonObject.put("statusDate", new Date().getTime());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            jsonArray.put(jsonObject);
                            System.out.println(jsonObject);
                            JsonInsertUtil.insertReportsFromJSON(jsonArray, context);
                        }

                        try {
                        mineInfo.put("mineId",dataModel.getMineid());
                        mineInfo.put("mineCode",dataModel.getMinecode());
                        mineInfo.put("mineName",dataModel.getMinename());
                        mineInfo.put("mineType",dataModel.getMinetype());
                        mineInfo.put("mineStage",dataModel.getMinestage());
                        mineInfo.put("cadastreId",dataModel.getCadastreid());
                        mineInfo.put("nationalCode",dataModel.getNationalcode());
                        mineInfo.put("beneficiaryName",dataModel.getBeneficiaryname());
                        mineInfo.put("beneficiaryAddress",dataModel.getBeneficiaryaddress());
                        mineInfo.put("beneficiaryPhone",dataModel.getBeneficiaryphone());
                        mineInfo.put("mineAddress",dataModel.getMineaddress());
                        mineInfo.put("minePhone",dataModel.getMinephone());
                        mineInfo.put("supervisorMembershipCode",dataModel.getSupervisormembershipcode());
                        mineInfo.put("exploreLicenseName",dataModel.getExplorelicensename());
                        mineInfo.put("exploreLicenseNumber",dataModel.getExplorelicensenumber());
                        mineInfo.put("exploreLicenseDate",dataModel.getExplorelicensedate());
                        mineInfo.put("exploreLicenseExpireDate",dataModel.getMinename());
                        mineInfo.put("mineralMaterial",dataModel.getMineralmaterial());
                        mineInfo.put("physicalProgressPercent",dataModel.getPhysicalprogresspercent());
                        mineInfo.put("closestCity",dataModel.getClosestcity());
                        mineInfo.put("closestVillage",dataModel.getClosestvillage());
                        mineInfo.put("extractionCapacity",dataModel.getExtractioncapacity());
                        mineInfo.put("extractionMethod",dataModel.getExtractionmethod());
                        mineInfo.put("licenseNumber",dataModel.getLicensenumber());
                        mineInfo.put("licenseType",dataModel.getLicensetype());
                        mineInfo.put("licenseExpireDate",dataModel.getLicenseexpiredate());
                        mineInfo.put("reportId",dataModel.getReportid());
                        mineInfo.put("reportStatus",dataModel.getReportstatus());
                        mineInfo.put("sazmanMembershipCode",dataModel.getSazmanmembershipcode());
                        mineInfo.put("sazmanMembershipName",dataModel.getSazmanmembershipname());
                        mineInfo.put("wkt",dataModel.getWkt());
                        mineInfo.put("wktCenter",dataModel.getWktcenter());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(context, Report.class);
                    intent.putExtra("reportId", randomReportId);
                    intent.putExtra("reportDate", date);
                    intent.putExtra("mineInfo", String.valueOf(mineInfo));
                    intent.putExtra("mineId",dataModel.getMineid());
                    intent.putExtra("mineStage",dataModel.getMinestage());
                    intent.putExtra("extractionMethod",dataModel.getExtractionmethod());
                    intent.putExtra("from","");
                    context.startActivity(intent);
                }
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm4");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });
        holder.btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("ShowOnMap");
                if(!dataModel.getWktcenter().equals("")) {
                    ArrayList<String> centerPoints = new ArrayList();
                    String s, s1, s2, s3, s4;
                    s = dataModel.getWktcenter();
                    s1 = s.replaceAll("POLYGON ", "").replaceAll("POINT ", "");
                    s2 = s1.replaceAll("[()]", "");
                    s3 = s2.replaceAll(", ", "#");
                    s4 = s3.replaceAll(" ", ",");
                    String[] centerSplitString = s4.split("#");
                    for (int i = 0; i < centerSplitString.length; i++) {
                        centerPoints.add(centerSplitString[i]);
                    }
                    for (int i = 0; i < centerPoints.size(); i++) {
                        centerNewPoints = centerPoints.get(i).split(",");
                        intent.putExtra("centerLat" , Double.parseDouble(centerNewPoints[1]));
                        intent.putExtra("centerLng" , Double.parseDouble(centerNewPoints[0]));
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                }
            }
        });
        holder.btnRouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dataModel.getWktcenter().equals("")) {
                    ArrayList<String> centerPoints = new ArrayList();
                    String s, s1, s2, s3, s4;
                    s = dataModel.getWktcenter();
                    s1 = s.replaceAll("POLYGON ", "").replaceAll("POINT ", "");
                    s2 = s1.replaceAll("[()]", "");
                    s3 = s2.replaceAll(", ", "#");
                    s4 = s3.replaceAll(" ", ",");
                    String[] centerSplitString = s4.split("#");
                    for (int i = 0; i < centerSplitString.length; i++) {
                        centerPoints.add(centerSplitString[i]);
                    }
                    for (int i = 0; i < centerPoints.size(); i++) {
                        centerNewPoints = centerPoints.get(i).split(",");
                    }
                    String uri = "google.navigation:q="+centerNewPoints[1]+","+centerNewPoints[0]+ "&mode=d";
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mineExpansionLayoutsArrayList.size();
    }


    public class MineExpansionViewHolder extends RecyclerView.ViewHolder {
        public AppCompatButton btnReviewList,btnShowOnMap,btnRouting;
        public ExpansionHeader expansionHeader;
        TextView txtTitle,txtMineCode,txtMineName,txtExploreLicenseName,txtCadastreId,txtLicenseNumber,txtExtractionCapacity,txtSazmanMembershipName,txtLicenseType,txtMineType,txtExtractionMethod;
        AppCompatButton btnReport;
        public int id;
        public MineExpansionViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
            txtMineCode = (TextView)itemView.findViewById(R.id.txtSheklekansar);
            txtMineName = (TextView)itemView.findViewById(R.id.txtMineName);
            txtExploreLicenseName = (TextView)itemView.findViewById(R.id.txtSheklekansar);
            txtCadastreId = (TextView)itemView.findViewById(R.id.txtCadastreId);
            txtLicenseNumber = (TextView)itemView.findViewById(R.id.txtLicenseNumber);
            txtExtractionCapacity = (TextView)itemView.findViewById(R.id.txtExtractionCapacity);
            txtSazmanMembershipName = (TextView)itemView.findViewById(R.id.txtSazmanMembershipName);
            txtLicenseType = (TextView)itemView.findViewById(R.id.txtLicenseType);
            txtMineType = (TextView)itemView.findViewById(R.id.txtMineType);
            txtExtractionMethod = (TextView)itemView.findViewById(R.id.txtExtractionMethod);
            btnReport = (AppCompatButton)itemView.findViewById(R.id.btnReport);
            btnShowOnMap = (AppCompatButton)itemView.findViewById(R.id.btnShowOnMap);
            btnRouting = (AppCompatButton)itemView.findViewById(R.id.btnRouting);
        }
    }


}
