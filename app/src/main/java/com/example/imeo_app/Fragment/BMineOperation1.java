package com.example.imeo_app.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MinefrontLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Minefront;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class BMineOperation1 extends Fragment {
    long repId;
    EditText edtExtractionWorkShopQty,edtAdvancingTunnelQty,edtTotalLengthTunnels,edtTotalVolumeTunnels,
             edtAdvancingDoilesQty,edtTotallengthDoils,edtTotalDrillingDoils,edtExtractedVolume;
    AppCompatButton btnSave,btnBack;
    JSONArray jsonArray;
    ConstraintLayout mainLayout;

    public BMineOperation1() {
        // Required empty public constructor
    }

    public static BMineOperation1 newInstance() {
        BMineOperation1 fragment = new BMineOperation1();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b_mine_operation1, container, false);
        edtExtractionWorkShopQty = view.findViewById(R.id.edtExtractionWorkShopQty);
        edtAdvancingTunnelQty = view.findViewById(R.id.edtAdvancingTunnelQty);
        edtTotalLengthTunnels = view.findViewById(R.id.edtTotalLengthTunnels);
        edtTotalVolumeTunnels = view.findViewById(R.id.edtTotalVolumeTunnels);
        edtAdvancingDoilesQty = view.findViewById(R.id.edtAdvancingDoilesQty);
        edtTotallengthDoils = view.findViewById(R.id.edtTotallengthDoils);
        edtTotalDrillingDoils = view.findViewById(R.id.edtTotalDrillingDoils);
        edtExtractedVolume = view.findViewById(R.id.edtExtractedVolume);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        mainLayout = view.findViewById(R.id.mainLayout);

        jsonArray = new JSONArray();
        SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
        repId = shared.getLong("reportId", 0);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < mainLayout.getChildCount(); i++) {
            View child = mainLayout.getChildAt(i);
            child.setEnabled(false);
        }
        }

        getReportInfo();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("reportId",repId);
                    jsonObject.put("extractionWorkShopQty",Integer.parseInt(edtExtractionWorkShopQty.getText().toString()));
                    jsonObject.put("advancingTunnelQty",Integer.parseInt(edtAdvancingTunnelQty.getText().toString()));
                    jsonObject.put("totalLengthTunnels",Integer.parseInt(edtTotalLengthTunnels.getText().toString()));
                    jsonObject.put("totalVolumeTunnels",Integer.parseInt(edtTotalVolumeTunnels.getText().toString()));
                    jsonObject.put("advancingDoilesQty",Integer.parseInt(edtAdvancingDoilesQty.getText().toString()));
                    jsonObject.put("totalLengthDoiles",Integer.parseInt(edtTotallengthDoils.getText().toString()));
                    jsonObject.put("totalDrillingDoiles",Integer.parseInt(edtTotalDrillingDoils.getText().toString()));
                    jsonObject.put("extractedVolume",Integer.parseInt(edtExtractedVolume.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
                JsonInsertUtil.insertReportsFromJSON(jsonArray,getActivity());
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("ثبت شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                setMineOperation1InReport();

                Fragment selectedFragment = MineOperation2.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm8");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = ProductionSalesStatistics.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm6");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        return view;
    }
    public void getReportInfo(){
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report reports = reportLocalServiceUtil.getReportById(repId);
        if(reports != null){
            edtExtractionWorkShopQty.setText(String.valueOf(reports.getExtractionworkshopqty()));
            edtAdvancingTunnelQty.setText(String.valueOf(reports.getAdvancingdoilesqty()));
            edtTotalLengthTunnels.setText(String.valueOf(reports.getTotallengthtunnels()));
            edtTotalVolumeTunnels.setText(String.valueOf(reports.getTotalvolumetunnels()));
            edtAdvancingDoilesQty.setText(String.valueOf(reports.getAdvancingdoilesqty()));
            edtTotallengthDoils.setText(String.valueOf(reports.getTotallengthdoiles()));
            edtTotalDrillingDoils.setText(String.valueOf(reports.getTotaldrillingdoiles()));
            edtExtractedVolume.setText(String.valueOf(reports.getExtractedvolume()));
        }
    }
    public void setMineOperation1InReport() {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        if (report != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("reportId", repId);
                jsonObject.put("setOperation1", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
        }
    }
}