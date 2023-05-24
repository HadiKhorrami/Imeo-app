package com.example.imeo_app.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imeo_app.R;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MineSafetyTechnical extends Fragment {

    AppCompatRadioButton rdVaziatRefahiGOOD,rdVaziatRefahiMID,rdVaziatRefahiBAD,rdIsTajhizatYES,rdIsTajhizatNO,rdUseTajhizatGOOD,rdUseTajhizatMID,rdUseTajhizatBAD,rdDriverGovahiYES,rdDriverGovahiNO,rdMachineryImeniYES,
            rdMachineryImeniNO,rdShibeMojazYES,rdShibeMojazNO,rdReayateAyinnamehayeImeniYES,rdReayateAyinnamehayeImeniNO,rdIsAccidentHappenYES,rdIsAccidentHappenNO,rdLaghGiriYES,rdLaghGiriNO,rdNeedGheyreFaalImeniYES,rdNeedGheyreFaalImeniNO;
    EditText edtShibeJaddeyeAsli,edtShibeRamphayeDastrasi,edtOtherDesc;
    AppCompatButton btnSave,btnBack;
    int vaziatRefahi = 1,isTajhizat = 1,useTajhizat = 1,driverGovahi = 1,machineryImeni = 1,shibeMojaz = 1,reayateAyinname = 1,isAccident = 1,laghGiri = 1,needGheyreFaal = 1;
    long repId;
    JSONArray jsonArray;
    ConstraintLayout mainLayout;
    public MineSafetyTechnical() {
        // Required empty public constructor
    }

    public static MineSafetyTechnical newInstance() {
        MineSafetyTechnical fragment = new MineSafetyTechnical();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_safety_technical, container, false);
        rdVaziatRefahiGOOD = view.findViewById(R.id.rdVaziatRefahiGOOD);rdVaziatRefahiMID = view.findViewById(R.id.rdVaziatRefahiMID);rdVaziatRefahiBAD = view.findViewById(R.id.rdVaziatRefahiBAD);rdIsTajhizatYES = view.findViewById(R.id.rdIsTajhizatYES);rdIsTajhizatNO = view.findViewById(R.id.rdIsTajhizatNO);rdUseTajhizatGOOD = view.findViewById(R.id.rdUseTajhizatGOOD);rdUseTajhizatMID = view.findViewById(R.id.rdUseTajhizatMID);rdUseTajhizatBAD = view.findViewById(R.id.rdUseTajhizatBAD);rdDriverGovahiYES = view.findViewById(R.id.rdDriverGovahiYES);rdDriverGovahiNO = view.findViewById(R.id.rdDriverGovahiNO);rdMachineryImeniYES = view.findViewById(R.id.rdMachineryImeniYES);rdMachineryImeniNO = view.findViewById(R.id.rdMachineryImeniNO);rdShibeMojazYES = view.findViewById(R.id.rdShibeMojazYES);rdShibeMojazNO = view.findViewById(R.id.rdShibeMojazNO);rdReayateAyinnamehayeImeniYES = view.findViewById(R.id.rdReayateAyinnamehayeImeniYES);rdReayateAyinnamehayeImeniNO = view.findViewById(R.id.rdReayateAyinnamehayeImeniNO);rdIsAccidentHappenYES = view.findViewById(R.id.rdIsAccidentHappenYES);rdIsAccidentHappenNO = view.findViewById(R.id.rdIsAccidentHappenNO);rdLaghGiriYES = view.findViewById(R.id.rdLaghGiriYES);rdLaghGiriNO = view.findViewById(R.id.rdLaghGiriNO);rdNeedGheyreFaalImeniYES = view.findViewById(R.id.rdNeedGheyreFaalImeniYES);rdNeedGheyreFaalImeniNO = view.findViewById(R.id.rdNeedGheyreFaalImeniNO);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        edtShibeJaddeyeAsli = view.findViewById(R.id.edtShibeJaddeyeAsli);
        edtShibeRamphayeDastrasi = view.findViewById(R.id.edtShibeRamphayeDastrasi);
        edtOtherDesc = view.findViewById(R.id.edtOtherDesc);
        mainLayout = view.findViewById(R.id.mainLayout);

        SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
        repId = shared.getLong("reportId", 0);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < mainLayout.getChildCount(); i++) {
            View child = mainLayout.getChildAt(i);
            child.setEnabled(false);
        }
        }

        SharedPreferences shared1 = getActivity().getSharedPreferences("mineType", MODE_PRIVATE);
        String mineType = shared1.getString("mineType","");

        rdVaziatRefahiGOOD.setChecked(true);rdIsTajhizatYES.setChecked(true);rdUseTajhizatGOOD.setChecked(true);rdDriverGovahiYES.setChecked(true);rdMachineryImeniYES.setChecked(true);rdShibeMojazYES.setChecked(true);rdReayateAyinnamehayeImeniYES.setChecked(true);rdIsAccidentHappenYES.setChecked(true);rdLaghGiriYES.setChecked(true);rdNeedGheyreFaalImeniYES.setChecked(true);
        checkReportExist();

        rdVaziatRefahiGOOD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdVaziatRefahiMID.setChecked(false);
                    rdVaziatRefahiBAD.setChecked(false);
                    vaziatRefahi = 1;
                }
            }
        });
        rdVaziatRefahiMID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdVaziatRefahiGOOD.setChecked(false);
                    rdVaziatRefahiBAD.setChecked(false);
                    vaziatRefahi = 2;
                }
            }
        });
        rdVaziatRefahiBAD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdVaziatRefahiMID.setChecked(false);
                    rdVaziatRefahiGOOD.setChecked(false);
                    vaziatRefahi = 3;
                }
            }
        });

        rdIsTajhizatYES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdIsTajhizatNO.setChecked(false);
                    isTajhizat = 1;
                }
            }
        });
        rdIsTajhizatNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdIsTajhizatYES.setChecked(false);
                    isTajhizat = 2;
                }
            }
        });

        rdUseTajhizatGOOD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdUseTajhizatMID.setChecked(false);
                    rdUseTajhizatBAD.setChecked(false);
                    useTajhizat = 1;
                }
            }
        });
        rdUseTajhizatMID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdUseTajhizatGOOD.setChecked(false);
                    rdUseTajhizatBAD.setChecked(false);
                    useTajhizat = 2;
                }
            }
        });
        rdUseTajhizatBAD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdUseTajhizatMID.setChecked(false);
                    rdUseTajhizatGOOD.setChecked(false);
                    useTajhizat = 3;
                }
            }
        });

        rdDriverGovahiYES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdDriverGovahiNO.setChecked(false);
                    driverGovahi = 1;
                }
            }
        });
        rdDriverGovahiNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdDriverGovahiYES.setChecked(false);
                    driverGovahi = 2;
                }
            }
        });

        rdMachineryImeniYES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdMachineryImeniNO.setChecked(false);
                    machineryImeni = 1;
                }
            }
        });
        rdMachineryImeniNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdMachineryImeniYES.setChecked(false);
                    machineryImeni = 2;
                }
            }
        });

        rdShibeMojazYES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdShibeMojazNO.setChecked(false);
                    shibeMojaz = 1;
                }
            }
        });
        rdShibeMojazNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdShibeMojazYES.setChecked(false);
                    shibeMojaz = 2;
                }
            }
        });

        rdReayateAyinnamehayeImeniYES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdReayateAyinnamehayeImeniNO.setChecked(false);
                    reayateAyinname = 1;
                }
            }
        });
        rdReayateAyinnamehayeImeniNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdReayateAyinnamehayeImeniYES.setChecked(false);
                    reayateAyinname = 2;
                }
            }
        });

        rdIsAccidentHappenYES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdIsAccidentHappenNO.setChecked(false);
                    isAccident = 1;
                }
            }
        });
        rdIsAccidentHappenNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdIsAccidentHappenYES.setChecked(false);
                    isAccident = 2;
                }
            }
        });

        rdLaghGiriYES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdLaghGiriNO.setChecked(false);
                    laghGiri = 1;
                }
            }
        });
        rdLaghGiriNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdLaghGiriYES.setChecked(false);
                    laghGiri = 2;
                }
            }
        });

        rdNeedGheyreFaalImeniYES.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdNeedGheyreFaalImeniNO.setChecked(false);
                    needGheyreFaal = 1;
                }
            }
        });
        rdNeedGheyreFaalImeniNO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdNeedGheyreFaalImeniYES.setChecked(false);
                    needGheyreFaal = 2;
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("reportId",repId);
                        jsonObject.put("vaziateRefahiePersonel",vaziatRefahi);
                        jsonObject.put("isTajhizateImenieFardi",isTajhizat);
                        jsonObject.put("useTajhizateImeni",useTajhizat);
                        jsonObject.put("driverGovahiMotabar",driverGovahi);
                        jsonObject.put("machineryimeni",machineryImeni);
                        jsonObject.put("reayateShibeMojaz",shibeMojaz);
                        if(!edtShibeJaddeyeAsli.getText().toString().equals("")){
                            jsonObject.put("shibeJaddeyeAsli",Integer.parseInt(edtShibeJaddeyeAsli.getText().toString()));
                        }
                        else {
                            jsonObject.put("shibeJaddeyeAsli",0);
                        }
                        if(!edtShibeRamphayeDastrasi.getText().toString().equals("")){
                            jsonObject.put("shibeRamphayeDastrasi",Integer.parseInt(edtShibeRamphayeDastrasi.getText().toString()));
                        }else {
                            jsonObject.put("shibeRamphayeDastrasi",0);
                        }
                        jsonObject.put("isAccidentHappen",isAccident);
                        jsonObject.put("laghGiri",laghGiri);
                        jsonObject.put("needGheyreFaalImeni",needGheyreFaal);
                        if(!edtOtherDesc.getText().toString().equals("")) {
                            jsonObject.put("otherDesc", edtOtherDesc.getText().toString());
                        }else {
                            jsonObject.put("otherDesc", "");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                System.out.println(jsonArray);
                JsonInsertUtil.insertReportsFromJSON(jsonArray,getActivity());
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("ثبت شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                setImeniInReport();

                Fragment selectedFragment = Problems.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                if(mineType.equals("pellekani") || mineType.equals("ekteshafi")){
                    intent.putExtra("button","btnForm14");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("zirzamini")){
                    intent.putExtra("button","btnForm13");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("enfejari")){
                    intent.putExtra("button","btnForm15");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = UseEnergyWater.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                if(mineType.equals("pellekani") || mineType.equals("ekteshafi")){
                    intent.putExtra("button","btnForm12");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("zirzamini")){
                    intent.putExtra("button","btnForm11");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("enfejari")){
                    intent.putExtra("button","btnForm13");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });

        return view;
    }
    public void checkReportExist(){
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report reports = reportLocalServiceUtil.getReportById(repId);
        if(reports != null){
            if(reports.getVaziaterefahiepersonel() == 1){
                rdVaziatRefahiGOOD.setChecked(true);
            }else if(reports.getVaziaterefahiepersonel() == 2){
                rdVaziatRefahiMID.setChecked(true);
                rdVaziatRefahiGOOD.setChecked(false);
            }else if(reports.getVaziaterefahiepersonel() == 3){
                rdVaziatRefahiBAD.setChecked(true);
                rdVaziatRefahiGOOD.setChecked(false);
            }

            if(reports.getIstajhizateimeniefardi() == 1){
                rdIsTajhizatYES.setChecked(true);
            }else if(reports.getIstajhizateimeniefardi() == 2){
                rdIsTajhizatNO.setChecked(true);
                rdIsTajhizatYES.setChecked(false);
            }

            if(reports.getUsetajhizateimeni() == 1){
                rdUseTajhizatGOOD.setChecked(true);
            }else if(reports.getUsetajhizateimeni() == 2){
                rdUseTajhizatMID.setChecked(true);
                rdUseTajhizatGOOD.setChecked(false);
            }else if(reports.getUsetajhizateimeni() == 3){
                rdUseTajhizatBAD.setChecked(true);
                rdUseTajhizatGOOD.setChecked(false);
            }

            if(reports.getDrivergovahimotabar() == 1){
                rdDriverGovahiYES.setChecked(true);
            }else if(reports.getDrivergovahimotabar() == 2){
                rdDriverGovahiNO.setChecked(true);
                rdDriverGovahiYES.setChecked(false);
            }

            if(reports.getMachineryimeni() == 1){
                rdMachineryImeniYES.setChecked(true);
            }else if(reports.getMachineryimeni() == 2){
                rdMachineryImeniNO.setChecked(true);
                rdMachineryImeniYES.setChecked(false);
            }

            if(reports.getReayateshibemojaz() == 1){
                rdShibeMojazYES.setChecked(true);
            }else if(reports.getReayateshibemojaz() == 2){
                rdShibeMojazNO.setChecked(true);
                rdShibeMojazYES.setChecked(false);
            }

            if(reports.getReayateayinnamehayeimeni() == 1){
                rdReayateAyinnamehayeImeniYES.setChecked(true);
            }else if(reports.getReayateayinnamehayeimeni() == 2){
                rdReayateAyinnamehayeImeniNO.setChecked(true);
                rdReayateAyinnamehayeImeniYES.setChecked(false);
            }

            if(reports.getIsaccidenthappen() == 1){
                rdIsAccidentHappenYES.setChecked(true);
            }else if(reports.getIsaccidenthappen() == 2){
                rdIsAccidentHappenNO.setChecked(true);
                rdIsAccidentHappenYES.setChecked(false);
            }

            if(reports.getLaghgiri() == 1){
                rdLaghGiriYES.setChecked(true);
            }else if(reports.getLaghgiri() == 2){
                rdLaghGiriNO.setChecked(true);
                rdLaghGiriYES.setChecked(false);
            }

            if(reports.getNeedgheyrefaalimeni() == 1){
                rdNeedGheyreFaalImeniYES.setChecked(true);
            }else if(reports.getNeedgheyrefaalimeni() == 2){
                rdNeedGheyreFaalImeniNO.setChecked(true);
                rdNeedGheyreFaalImeniYES.setChecked(false);
            }
            edtShibeJaddeyeAsli.setText(String.valueOf(reports.getShibejaddeyeasli()));
            edtShibeRamphayeDastrasi.setText(String.valueOf(reports.getShiberamphayedastrasi()));
            edtOtherDesc.setText(String.valueOf(reports.getOtherdesc()));
        }
    }
    public void setImeniInReport() {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        if (report != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("reportId", repId);
                jsonObject.put("setImeni", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
        }
    }
}