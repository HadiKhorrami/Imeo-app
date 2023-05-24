package com.example.imeo_app.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imeo_app.Adapters.AMineOperationRecyclerAdapter;
import com.example.imeo_app.Adapters.ExpMineralMaterialRecyclerAdapter;
import com.example.imeo_app.DataModels.AMineOperationLayout;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MinefrontLocalServiceUtil;
import com.example.imeo_app.db.service.MineralmaterialLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Minefront;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AMineOperation1 extends Fragment {

     long repId,mineId;
    AppCompatRadioButton rdKansang,rdBatele;
    EditText edtWorkFrontWide,edtWorkFrontHeight,edtWorkFrontLength,edtWorkFrontSLope;
    AppCompatButton btnAdd,btnEdit,btnSave,btnBack;
    int stoneType;
    JSONArray jsonArray;
    JSONObject jsonObject;
    AMineOperationRecyclerAdapter aMineOperationRecyclerAdapter;
    ArrayList<AMineOperationLayout> aMineOperationLayouts;
    RecyclerView recyclerView;
    ConstraintLayout mainLayout;
    boolean hasEdit = true;
    
    public AMineOperation1() {
        // Required empty public constructor
    }

    public static AMineOperation1 newInstance() {
        AMineOperation1 fragment = new AMineOperation1();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_mine_operation1, container, false);
        rdKansang = view.findViewById(R.id.rdKansang);
        rdBatele = view.findViewById(R.id.rdBatele);
        edtWorkFrontWide = view.findViewById(R.id.edtWorkFrontWide);
        edtWorkFrontHeight = view.findViewById(R.id.edtWorkFrontHeight);
        edtWorkFrontLength = view.findViewById(R.id.edtWorkFrontLength);
        edtWorkFrontSLope = view.findViewById(R.id.edtWorkFrontSLope);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnEdit = view.findViewById(R.id.btnEdit);
        recyclerView = view.findViewById(R.id.mineFrontRecyclerView);
        mainLayout = view.findViewById(R.id.mainLayout);
        rdKansang.setChecked(true);

        aMineOperationLayouts = new ArrayList<>();

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

        getReportMineFront();

        rdKansang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdBatele.setChecked(false);
                    stoneType = 1;
                }
            }
        });
        rdBatele.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    rdKansang.setChecked(false);
                    stoneType = 2;
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                Random rn = new Random();
                long randomReportId = rn.nextLong() * -1;

                AMineOperationLayout aMineOperationLayout = new AMineOperationLayout();
                aMineOperationLayout.setId(aMineOperationLayouts.size());
                aMineOperationLayout.setMinefrontid(randomReportId);
                aMineOperationLayout.setMineid(mineId);
                aMineOperationLayout.setReportid(repId);
                aMineOperationLayout.setStonetype(stoneType);
                if(!edtWorkFrontWide.getText().toString().equals(""))
                aMineOperationLayout.setWorkfrontwide(Integer.parseInt(edtWorkFrontWide.getText().toString()));
                else {
                    aMineOperationLayout.setWorkfrontwide(0);
                }
                if(!edtWorkFrontHeight.getText().toString().equals(""))
                    aMineOperationLayout.setWorkfrontheight(Integer.parseInt(edtWorkFrontHeight.getText().toString()));
                else {
                    aMineOperationLayout.setWorkfrontheight(0);
                }
                if(!edtWorkFrontLength.getText().toString().equals(""))
                    aMineOperationLayout.setWorkfrontlength(Integer.parseInt(edtWorkFrontLength.getText().toString()));
                else {
                    aMineOperationLayout.setWorkfrontlength(0);
                }
                if(!edtWorkFrontSLope.getText().toString().equals(""))
                    aMineOperationLayout.setWorkfrontslope(Integer.parseInt(edtWorkFrontSLope.getText().toString()));
                else {
                    aMineOperationLayout.setWorkfrontslope(0);
                }
                aMineOperationLayouts.add(aMineOperationLayout);
                aMineOperationRecyclerAdapter = new AMineOperationRecyclerAdapter(getActivity(), aMineOperationLayouts);
                recyclerView.setAdapter(aMineOperationRecyclerAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("اضافه شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                edtWorkFrontHeight.setText("");
                edtWorkFrontWide.setText("");
                edtWorkFrontLength.setText("");
                edtWorkFrontSLope.setText("");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                jsonArray = new JSONArray();
                for(int i = 0;i<aMineOperationLayouts.size();i++){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("mineFrontId",aMineOperationLayouts.get(i).getMinefrontid());
                        jsonObject.put("mineId",aMineOperationLayouts.get(i).getMineid());
                        jsonObject.put("reportId",aMineOperationLayouts.get(i).getReportid());
                        jsonObject.put("stoneTypeCode",aMineOperationLayouts.get(i).getStonetype());
                        jsonObject.put("workFrontWide",aMineOperationLayouts.get(i).getWorkfrontwide());
                        jsonObject.put("workFrontHeight",aMineOperationLayouts.get(i).getWorkfrontheight());
                        jsonObject.put("workFrontLength",aMineOperationLayouts.get(i).getWorkfrontlength());
                        jsonObject.put("workFrontSlope",aMineOperationLayouts.get(i).getWorkfrontslope());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }
                System.out.println(jsonArray);
                JsonInsertUtil.insertMineFrontFromJSON(jsonArray,getActivity());
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("ثبت شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                setMineOperation1InReport();

                SharedPreferences shared1 = getActivity().getSharedPreferences("mineType", MODE_PRIVATE);
                String mineType = shared1.getString("mineType","");

                if(mineType.equals("zirzamini") || mineType.equals("enfejari")){
                    Fragment selectedFragment = MineOperation2.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm10");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("pellekani")){
                    Fragment selectedFragment = Machinery.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm10");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = Machinery.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm10");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(editReceiver, new IntentFilter("editMineFront"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(deleteReceiver, new IntentFilter("deleteMineFront"));
        return view;
    }
    public BroadcastReceiver editReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hasEdit = true;
            long mineFrontId = intent.getLongExtra("mineFrontId",0);
            for(int i = 0;i<aMineOperationLayouts.size();i++){
                if(mineFrontId==aMineOperationLayouts.get(i).getMinefrontid()){
                    if(aMineOperationLayouts.get(i).getStonetype()==1){
                        rdKansang.setChecked(true);
                    }else if(aMineOperationLayouts.get(i).getStonetype()==2){
                        rdBatele.setChecked(true);
                    }
                    edtWorkFrontHeight.setText(String.valueOf(aMineOperationLayouts.get(i).getWorkfrontheight()));
                    edtWorkFrontWide.setText(String.valueOf(aMineOperationLayouts.get(i).getWorkfrontwide()));
                    edtWorkFrontLength.setText(String.valueOf(aMineOperationLayouts.get(i).getWorkfrontlength()));
                    edtWorkFrontSLope.setText(String.valueOf(aMineOperationLayouts.get(i).getWorkfrontslope()));
                }
            }
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onClick(View v) {
                    if (hasEdit == true) {
                        for (int i = 0; i < aMineOperationLayouts.size(); i++) {
                            if (mineFrontId == aMineOperationLayouts.get(i).getMinefrontid()) {
                                aMineOperationLayouts.get(i).setStonetype(stoneType);
                                aMineOperationLayouts.get(i).setWorkfrontwide(Integer.parseInt(edtWorkFrontWide.getText().toString()));
                                aMineOperationLayouts.get(i).setWorkfrontlength(Integer.parseInt(edtWorkFrontLength.getText().toString()));
                                aMineOperationLayouts.get(i).setWorkfrontheight(Integer.parseInt(edtWorkFrontHeight.getText().toString()));
                                aMineOperationLayouts.get(i).setWorkfrontslope(Integer.parseInt(edtWorkFrontSLope.getText().toString()));
                                aMineOperationRecyclerAdapter = new AMineOperationRecyclerAdapter(getActivity(), aMineOperationLayouts);
                                recyclerView.setAdapter(aMineOperationRecyclerAdapter);
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
                            }
                        }
                        edtWorkFrontWide.setText("");
                        edtWorkFrontLength.setText("");
                        edtWorkFrontHeight.setText("");
                        edtWorkFrontSLope.setText("");
                        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                        SpannableString efr = new SpannableString("ویرایش شد");
                        efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                        hasEdit = false;
                    }
                }
            });

        }
    };
    public BroadcastReceiver deleteReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onReceive(Context context, Intent intent) {
            long mineFrontId = intent.getLongExtra("mineFrontId",0);
            MinefrontLocalServiceUtil minefrontLocalServiceUtil = new MinefrontLocalServiceUtil(context);
            minefrontLocalServiceUtil.deleteMinefront(mineFrontId);
            System.out.println(mineFrontId);
            for(int i = 0;i<aMineOperationLayouts.size();i++){
                if(mineFrontId==aMineOperationLayouts.get(i).getMinefrontid()){
                    aMineOperationLayouts.remove(i);
                }
            }
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansWeb.ttf");
            SpannableString efr = new SpannableString("حذف شد");
            efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Toast.makeText(context, efr, Toast.LENGTH_SHORT).show();
        }
    };
    public void getReportMineFront(){
        aMineOperationLayouts = new ArrayList<>();
        MinefrontLocalServiceUtil minefrontLocalServiceUtil = new MinefrontLocalServiceUtil(getActivity());

        List<Minefront> minefront = minefrontLocalServiceUtil.getMinefrontByReportId(repId);
        if(minefront.size()>0){
            for (int i = 0; i < minefront.size(); i++) {
                AMineOperationLayout aMineOperationLayout = new AMineOperationLayout();
                aMineOperationLayout.setId(i);
                aMineOperationLayout.setMinefrontid(minefront.get(i).getMinefrontid());
                aMineOperationLayout.setMineid(minefront.get(i).getMineid());
                aMineOperationLayout.setReportid(minefront.get(i).getReportid());
                aMineOperationLayout.setStonetype(minefront.get(i).getStonetype());
                aMineOperationLayout.setWorkfrontwide(minefront.get(i).getWorkfrontwide());
                aMineOperationLayout.setWorkfrontheight(minefront.get(i).getWorkfrontheight());
                aMineOperationLayout.setWorkfrontlength(minefront.get(i).getWorkfrontlength());
                aMineOperationLayout.setWorkfrontslope(minefront.get(i).getWorkfrontslope());
                aMineOperationLayouts.add(aMineOperationLayout);
                aMineOperationRecyclerAdapter = new AMineOperationRecyclerAdapter(getActivity(), aMineOperationLayouts);
                recyclerView.setAdapter(aMineOperationRecyclerAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
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