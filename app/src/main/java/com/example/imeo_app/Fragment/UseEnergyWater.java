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


public class UseEnergyWater extends Fragment {

    EditText edtBargh,edtGazeTabiyi,edtAbeSanati,edtBenzin,edtGazoyil,edtAbeShorb,edtOtherDetails,edtTedadeGenerator,edtTavaneGenerator,edtMasrafeGenerator;
    AppCompatRadioButton rdUseBargYes,rdUseBargNo;
    ConstraintLayout useBarghLayout;
    AppCompatButton btnSave,btnBack;
    long repId;
    int useBargh = 2;
    JSONArray jsonArray;
    ConstraintLayout mainLayout;
    public UseEnergyWater() {
        // Required empty public constructor
    }

    public static UseEnergyWater newInstance() {
        UseEnergyWater fragment = new UseEnergyWater();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use_energy_water, container, false);
        rdUseBargYes = view.findViewById(R.id.rdUseBargYes);
        rdUseBargNo = view.findViewById(R.id.rdUseBargNo);
        useBarghLayout = view.findViewById(R.id.useBarghLayout);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        edtBargh = view.findViewById(R.id.edtBargh);
        edtGazeTabiyi = view.findViewById(R.id.edtGazeTabiyi);
        edtAbeSanati = view.findViewById(R.id.edtAbeSanati);
        edtBenzin = view.findViewById(R.id.edtBenzin);
        edtGazoyil = view.findViewById(R.id.edtGazoyil);
        edtAbeShorb = view.findViewById(R.id.edtAbeShorb);
        edtOtherDetails = view.findViewById(R.id.edtOtherDetails);
        edtTedadeGenerator = view.findViewById(R.id.edtTedadeGenerator);
        edtTavaneGenerator = view.findViewById(R.id.edtTavaneGenerator);
        edtMasrafeGenerator = view.findViewById(R.id.edtMasrafeGenerator);
        mainLayout = view.findViewById(R.id.mainLayout);
        rdUseBargNo.setChecked(true);
        useBarghLayout.setVisibility(View.INVISIBLE);

        SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
        repId = shared.getLong("reportId", 0);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < mainLayout.getChildCount(); i++) {
            View child = mainLayout.getChildAt(i);
            child.setEnabled(false);
        }
        }
        checkReportExist();

        SharedPreferences shared1 = getActivity().getSharedPreferences("mineType", MODE_PRIVATE);
        String mineType = shared1.getString("mineType","");

        rdUseBargYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true) {
                    rdUseBargNo.setChecked(false);
                    useBarghLayout.setVisibility(View.VISIBLE);
                    useBargh = 1;
                }
            }
        });
        rdUseBargNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdUseBargYes.setChecked(false);
                    useBarghLayout.setVisibility(View.INVISIBLE);
                    useBargh = 2;
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
                    if(!edtBargh.getText().toString().equals(""))
                        jsonObject.put("bargh",Integer.parseInt(edtBargh.getText().toString()));
                    else {
                        jsonObject.put("bargh",0);
                    }
                    if(!edtGazeTabiyi.getText().toString().equals(""))
                        jsonObject.put("gazeTabiyi",Integer.parseInt(edtGazeTabiyi.getText().toString()));
                    else {
                        jsonObject.put("gazeTabiyi",0);
                    }
                    if(!edtAbeSanati.getText().toString().equals(""))
                        jsonObject.put("abeSanati",Integer.parseInt(edtAbeSanati.getText().toString()));
                    else {
                        jsonObject.put("abeSanati",0);
                    }
                    if(!edtBenzin.getText().toString().equals(""))
                        jsonObject.put("benzin",Integer.parseInt(edtBenzin.getText().toString()));
                    else {
                        jsonObject.put("benzin",0);
                    }
                    if(!edtGazoyil.getText().toString().equals(""))
                        jsonObject.put("gazoyil",Integer.parseInt(edtGazoyil.getText().toString()));
                    else {
                        jsonObject.put("gazoyil",0);
                    }
                    if(!edtAbeShorb.getText().toString().equals(""))
                        jsonObject.put("abeShorb",Integer.parseInt(edtAbeShorb.getText().toString()));
                    else {
                        jsonObject.put("abeShorb",0);
                    }
                    if(!edtOtherDetails.getText().toString().equals(""))
                        jsonObject.put("sayer",edtOtherDetails.getText().toString());
                    else {
                        jsonObject.put("sayer",0);
                    }

                        jsonObject.put("useBargh",useBargh);

                    if(!edtTavaneGenerator.getText().toString().equals(""))
                        jsonObject.put("tedadeGenerator",Integer.parseInt(edtTavaneGenerator.getText().toString()));
                    else {
                        jsonObject.put("tedadeGenerator",0);
                    }
                    if(!edtTavaneGenerator.getText().toString().equals(""))
                        jsonObject.put("tavaneGenerator",Integer.parseInt(edtTavaneGenerator.getText().toString()));
                    else {
                        jsonObject.put("tavaneGenerator",0);
                    }
                    if(!edtMasrafeGenerator.getText().toString().equals(""))
                        jsonObject.put("masrafeGenerator",Integer.parseInt(edtMasrafeGenerator.getText().toString()));
                    else {
                        jsonObject.put("masrafeGenerator",0);
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
                setEnergyInReport();

                Fragment selectedFragment = MineSafetyTechnical.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                if(mineType.equals("pellekani") || mineType.equals("ekteshafi")){
                    intent.putExtra("button","btnForm13");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("zirzamini")){
                    intent.putExtra("button","btnForm12");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("enfejari")){
                    intent.putExtra("button","btnForm14");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = ManPower.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                if(mineType.equals("pellekani") || mineType.equals("ekteshafi")){
                    intent.putExtra("button","btnForm11");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("zirzamini")){
                    intent.putExtra("button","btnForm10");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("enfejari")){
                    intent.putExtra("button","btnForm12");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });
        return  view;
    }
    public void checkReportExist(){
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report reports = reportLocalServiceUtil.getReportById(repId);
        if(reports != null){
            edtBargh.setText(String.valueOf(reports.getBargh()));
            edtGazeTabiyi.setText(String.valueOf(reports.getGazetabiyi()));
            edtAbeSanati.setText(String.valueOf(reports.getAbesanati()));
            edtBenzin.setText(String.valueOf(reports.getBenzin()));
            edtGazoyil.setText(String.valueOf(reports.getGazoyil()));
            edtAbeShorb.setText(String.valueOf(reports.getAbeshorb()));
            edtOtherDetails.setText(String.valueOf(reports.getSayer()));
            edtTedadeGenerator.setText(String.valueOf(reports.getTedadegenerator()));
            edtTavaneGenerator.setText(String.valueOf(reports.getTavanegenerator()));
            edtMasrafeGenerator.setText(String.valueOf(reports.getMasrafegenerator()));

            if(reports.getUsebargh()==1){
                rdUseBargYes.setChecked(true);
                rdUseBargNo.setChecked(false);
                useBarghLayout.setVisibility(View.VISIBLE);
            }else if(reports.getUsebargh()==2){
                rdUseBargNo.setChecked(true);
                rdUseBargYes.setChecked(false);
            }
        }
    }
    public void setEnergyInReport() {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        if (report != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("reportId", repId);
                jsonObject.put("setEnergy", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
        }
    }
}