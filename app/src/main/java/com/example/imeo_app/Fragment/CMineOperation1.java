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
import com.example.imeo_app.db.service.MineoperationLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Mineoperation;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;


public class CMineOperation1 extends Fragment {

    EditText edtDimensionType1,edtVolumeType1,edtMeterageType1,edtCostType1,edtDimensionType2,edtVolumeType2,edtMeterageType2,edtCostType2,
            edtDimensionType3,edtVolumeType3,edtMeterageType3,edtCostType3,edtDimensionType4,edtVolumeType4,edtMeterageType4,edtCostType4;
    AppCompatButton btnSave,btnBack;
    long repId,mineId;
    JSONArray jsonArray;
    ConstraintLayout mainLayout;
    public CMineOperation1() {
        // Required empty public constructor
    }

    public static CMineOperation1 newInstance() {
        CMineOperation1 fragment = new CMineOperation1();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c_mine_operation1, container, false);
        edtDimensionType1 = view.findViewById(R.id.edtDimensionType1);
        edtVolumeType1 = view.findViewById(R.id.edtVolumeType1);
        edtMeterageType1 = view.findViewById(R.id.edtMeterageType1);
        edtCostType1 = view.findViewById(R.id.edtCostType1);
        edtDimensionType2 = view.findViewById(R.id.edtDimensionType2);
        edtVolumeType2 = view.findViewById(R.id.edtVolumeType2);
        edtMeterageType2 = view.findViewById(R.id.edtMeterageType2);
        edtCostType2 = view.findViewById(R.id.edtCostType2);
        edtDimensionType3 = view.findViewById(R.id.edtDimensionType3);
        edtVolumeType3 = view.findViewById(R.id.edtVolumeType3);
        edtMeterageType3 = view.findViewById(R.id.edtMeterageType3);
        edtCostType3 = view.findViewById(R.id.edtCostType3);
        edtDimensionType4 = view.findViewById(R.id.edtDimensionType4);
        edtVolumeType4 = view.findViewById(R.id.edtVolumeType4);
        edtMeterageType4 = view.findViewById(R.id.edtMeterageType4);
        edtCostType4 = view.findViewById(R.id.edtCostType4);
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
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        mineId = report.getMineid();
        getMineOperation();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                JSONObject jsonObject1 = new JSONObject();
                try {
                    Random rn = new Random();
                    long mineOperationId = rn.nextLong() * -1;
                    jsonObject1.put("reportId",repId);
                    jsonObject1.put("mineOperationId",mineOperationId);
                    jsonObject1.put("mineId",mineId);
                    jsonObject1.put("operationType",1);
                    if(!edtDimensionType1.getText().toString().equals(""))
                        jsonObject1.put("dimension",Integer.parseInt(edtDimensionType1.getText().toString()));
                    else {
                        jsonObject1.put("dimension",0);
                    }if(!edtVolumeType1.getText().toString().equals(""))
                        jsonObject1.put("volume",Integer.parseInt(edtVolumeType1.getText().toString()));
                    else {
                        jsonObject1.put("volume",0);
                    }if(!edtMeterageType1.getText().toString().equals(""))
                        jsonObject1.put("meterage",Integer.parseInt(edtMeterageType1.getText().toString()));
                    else {
                        jsonObject1.put("meterage",0);
                    }if(!edtCostType1.getText().toString().equals(""))
                        jsonObject1.put("cost",Integer.parseInt(edtCostType1.getText().toString()));
                    else {
                        jsonObject1.put("cost",0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject2 = new JSONObject();
                try {
                    Random rn = new Random();
                    long mineOperationId = rn.nextLong() * -1;
                    jsonObject2.put("reportId",repId);
                    jsonObject2.put("mineOperationId",mineOperationId);
                    jsonObject2.put("mineId",mineId);
                    jsonObject2.put("operationType",2);
                    if(!edtDimensionType2.getText().toString().equals(""))
                        jsonObject2.put("dimension",Integer.parseInt(edtDimensionType2.getText().toString()));
                    else {
                        jsonObject2.put("dimension",0);
                    }if(!edtVolumeType2.getText().toString().equals(""))
                        jsonObject2.put("volume",Integer.parseInt(edtVolumeType2.getText().toString()));
                    else {
                        jsonObject2.put("volume",0);
                    }if(!edtMeterageType2.getText().toString().equals(""))
                        jsonObject2.put("meterage",Integer.parseInt(edtMeterageType2.getText().toString()));
                    else {
                        jsonObject2.put("meterage",0);
                    }if(!edtCostType2.getText().toString().equals(""))
                        jsonObject2.put("cost",Integer.parseInt(edtCostType2.getText().toString()));
                    else {
                        jsonObject2.put("cost",0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject3 = new JSONObject();
                try {
                    Random rn = new Random();
                    long mineOperationId = rn.nextLong() * -1;
                    jsonObject3.put("reportId",repId);
                    jsonObject3.put("mineOperationId",mineOperationId);
                    jsonObject3.put("mineId",mineId);
                    jsonObject3.put("operationType",3);
                    if(!edtDimensionType3.getText().toString().equals(""))
                        jsonObject3.put("dimension",Integer.parseInt(edtDimensionType3.getText().toString()));
                    else {
                        jsonObject3.put("dimension",0);
                    }if(!edtVolumeType3.getText().toString().equals(""))
                        jsonObject3.put("volume",Integer.parseInt(edtVolumeType3.getText().toString()));
                    else {
                        jsonObject3.put("volume",0);
                    }if(!edtMeterageType3.getText().toString().equals(""))
                        jsonObject3.put("meterage",Integer.parseInt(edtMeterageType3.getText().toString()));
                    else {
                        jsonObject3.put("meterage",0);
                    }if(!edtCostType3.getText().toString().equals(""))
                        jsonObject3.put("cost",Integer.parseInt(edtCostType3.getText().toString()));
                    else {
                        jsonObject3.put("cost",0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject4= new JSONObject();
                try {
                    Random rn = new Random();
                    long mineOperationId = rn.nextLong() * -1;
                    jsonObject4.put("reportId",repId);
                    jsonObject4.put("mineOperationId",mineOperationId);
                    jsonObject4.put("mineId",mineId);
                    jsonObject4.put("operationType",4);
                    if(!edtDimensionType4.getText().toString().equals(""))
                        jsonObject4.put("dimension",Integer.parseInt(edtDimensionType4.getText().toString()));
                    else {
                        jsonObject4.put("dimension",0);
                    }if(!edtVolumeType4.getText().toString().equals(""))
                        jsonObject4.put("volume",Integer.parseInt(edtVolumeType4.getText().toString()));
                    else {
                        jsonObject4.put("volume",0);
                    }if(!edtMeterageType4.getText().toString().equals(""))
                        jsonObject4.put("meterage",Integer.parseInt(edtMeterageType4.getText().toString()));
                    else {
                        jsonObject4.put("meterage",0);
                    }if(!edtCostType4.getText().toString().equals(""))
                        jsonObject4.put("cost",Integer.parseInt(edtCostType4.getText().toString()));
                    else {
                        jsonObject4.put("cost",0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject1);
                jsonArray.put(jsonObject2);
                jsonArray.put(jsonObject3);
                jsonArray.put(jsonObject4);
                System.out.println(jsonArray);
                JsonInsertUtil.insertMineOperationFromJSON(jsonArray,getActivity());
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("ثبت شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                setMineOperation1InReport();

                Fragment selectedFragment = Machinery.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm10");
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
                intent.putExtra("button","btnForm8");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        return view;
    }
    public void getMineOperation(){
        MineoperationLocalServiceUtil mineoperationLocalServiceUtil = new MineoperationLocalServiceUtil(getActivity());
        List<Mineoperation> mineoperation = mineoperationLocalServiceUtil.getMineoperationByReportId(repId);
        if(mineoperation != null){
            for(int i = 0;i<mineoperation.size();i++){
                if(mineoperation.get(i).getOperationtype()==1){
                    edtDimensionType1.setText(String.valueOf(mineoperation.get(i).getDimension()));
                    edtVolumeType1.setText(String.valueOf(mineoperation.get(i).getVolume()));
                    edtMeterageType1.setText(String.valueOf(mineoperation.get(i).getMeterage()));
                    edtCostType1.setText(String.valueOf(mineoperation.get(i).getCost()));
                }else if(mineoperation.get(i).getOperationtype()==2){
                    edtDimensionType2.setText(String.valueOf(mineoperation.get(i).getDimension()));
                    edtVolumeType2.setText(String.valueOf(mineoperation.get(i).getVolume()));
                    edtMeterageType2.setText(String.valueOf(mineoperation.get(i).getMeterage()));
                    edtCostType2.setText(String.valueOf(mineoperation.get(i).getCost()));
                }else if(mineoperation.get(i).getOperationtype()==3){
                    edtDimensionType3.setText(String.valueOf(mineoperation.get(i).getDimension()));
                    edtVolumeType3.setText(String.valueOf(mineoperation.get(i).getVolume()));
                    edtMeterageType3.setText(String.valueOf(mineoperation.get(i).getMeterage()));
                    edtCostType3.setText(String.valueOf(mineoperation.get(i).getCost()));
                }else if(mineoperation.get(i).getOperationtype()==4){
                    edtDimensionType4.setText(String.valueOf(mineoperation.get(i).getDimension()));
                    edtVolumeType4.setText(String.valueOf(mineoperation.get(i).getVolume()));
                    edtMeterageType4.setText(String.valueOf(mineoperation.get(i).getMeterage()));
                    edtCostType4.setText(String.valueOf(mineoperation.get(i).getCost()));
                }

            }

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