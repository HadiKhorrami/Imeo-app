package com.example.imeo_app.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeo_app.Adapters.ExpMineralMaterialRecyclerAdapter;
import com.example.imeo_app.DataModels.MineralMaterialLayout;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MineralmaterialLocalServiceUtil;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ExplorationMineralMaterial extends Fragment {
    ArrayList<MineralMaterialLayout> mineralMaterialLayoutArrayList;
    ExpMineralMaterialRecyclerAdapter expMineralMaterialRecyclerAdapter;
    RecyclerView recyclerView;
    EditText edtSheklekansar,edtKanihayeasli,edtZonesakhtari,edtSangemizban,edtVahedhayesang;
    AppCompatButton btnEdit,btnDelete,btnSave,btnBack;
    ConstraintLayout mainLayout;
    boolean hasEdit = true;
    long mineId;

    public ExplorationMineralMaterial() {
        // Required empty public constructor
    }

    public static ExplorationMineralMaterial newInstance() {
        ExplorationMineralMaterial fragment = new ExplorationMineralMaterial();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exploration_mineral_material, container, false);
        mineralMaterialLayoutArrayList = new ArrayList<>();
        edtSheklekansar = (EditText) view.findViewById(R.id.edtSheklekansar);
        edtKanihayeasli = (EditText)view.findViewById(R.id.edtKanihayeasli);
        edtZonesakhtari = (EditText)view.findViewById(R.id.edtZonesakhtari);
        edtSangemizban = (EditText)view.findViewById(R.id.edtSangemizban);
        edtVahedhayesang = (EditText)view.findViewById(R.id.edtVahedhayesang);
        btnEdit = (AppCompatButton)view.findViewById(R.id.btnEdit);
        btnSave = (AppCompatButton)view.findViewById(R.id.btnSave);
        btnBack = (AppCompatButton)view.findViewById(R.id.btnBack);
        btnDelete = (AppCompatButton)view.findViewById(R.id.btnDelete);
        recyclerView = view.findViewById(R.id.recyclerView);
        mainLayout = view.findViewById(R.id.mainLayout);

        String mineInfo = getArguments().getString("mineInfo");
        mineId = getArguments().getLong("mineId");
        int mineStage = getArguments().getInt("mineStage");
        String extractionMethod = getArguments().getString("extractionMethod");

        SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
        int status = shared.getInt("status", 0);
        if(status==1){
            for (int i = 0; i < mainLayout.getChildCount(); i++) {
                View child = mainLayout.getChildAt(i);
                child.setEnabled(false);
            }
        }

        long mineralMaterialId = getArguments().getLong("mineralMaterialId");
        getMineralMaterial();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                JSONArray jsonArray = new JSONArray();
                for(int i = 0;i<mineralMaterialLayoutArrayList.size();i++){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("mineralmaterialId",mineralMaterialLayoutArrayList.get(i).getMineralmaterialid());
                        jsonObject.put("mineId",mineralMaterialLayoutArrayList.get(i).getMineid());
                        jsonObject.put("shekleKansar",mineralMaterialLayoutArrayList.get(i).getSheklekansar());
                        jsonObject.put("kanihayeAsli",mineralMaterialLayoutArrayList.get(i).getKanihayeasli());
                        jsonObject.put("zoneSakhtari",mineralMaterialLayoutArrayList.get(i).getZonesakhtari());
                        jsonObject.put("sangeMizban",mineralMaterialLayoutArrayList.get(i).getSangemizban());
                        jsonObject.put("vahedhayeSangshenasieAsli",mineralMaterialLayoutArrayList.get(i).getVahedhayesangshenasieasli());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }
                JsonInsertUtil.insertMineralMaterialFromJSON(jsonArray,getActivity(),"report");
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("ثبت شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                edtSheklekansar.setText("");
                edtZonesakhtari.setText("");
                edtKanihayeasli.setText("");
                edtSangemizban.setText("");
                edtVahedhayesang.setText("");
                Fragment selectedFragment = MineCondition.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putString("mineInfo", mineInfo);
                bundle.putLong("mineId", mineId);
                bundle.putInt("mineStage", mineStage);
                bundle.putString("extractionMethod", extractionMethod);
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm4");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = ExplorationMineInfo.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putString("mineInfo", mineInfo);
                bundle.putLong("mineId", mineId);
                bundle.putInt("mineStage", mineStage);
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm2");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(editReceiver, new IntentFilter("editMineralMaterial"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(deleteReceiver, new IntentFilter("deleteMineralMaterial"));
        return view;
    }
    public BroadcastReceiver editReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hasEdit = true;
            long mineralMaterialId = intent.getLongExtra("mineralMaterialId",0);
            for(int i = 0;i<mineralMaterialLayoutArrayList.size();i++){
                if(mineralMaterialId==mineralMaterialLayoutArrayList.get(i).getMineralmaterialid()){
                    edtSheklekansar.setText(mineralMaterialLayoutArrayList.get(i).getSheklekansar());
                    edtKanihayeasli.setText(mineralMaterialLayoutArrayList.get(i).getKanihayeasli());
                    edtZonesakhtari.setText(mineralMaterialLayoutArrayList.get(i).getZonesakhtari());
                    edtSangemizban.setText(mineralMaterialLayoutArrayList.get(i).getSangemizban());
                    edtVahedhayesang.setText(mineralMaterialLayoutArrayList.get(i).getVahedhayesangshenasieasli());
                }
            }
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onClick(View v) {
                    if (hasEdit == true) {
                        for (int i = 0; i < mineralMaterialLayoutArrayList.size(); i++) {
                            if (mineralMaterialId == mineralMaterialLayoutArrayList.get(i).getMineralmaterialid()) {
                                mineralMaterialLayoutArrayList.get(i).setSheklekansar(edtSheklekansar.getText().toString());
                                mineralMaterialLayoutArrayList.get(i).setKanihayeasli(edtKanihayeasli.getText().toString());
                                mineralMaterialLayoutArrayList.get(i).setZonesakhtari(edtZonesakhtari.getText().toString());
                                mineralMaterialLayoutArrayList.get(i).setSangemizban(edtSangemizban.getText().toString());
                                mineralMaterialLayoutArrayList.get(i).setVahedhayesangshenasieasli(edtVahedhayesang.getText().toString());
                                expMineralMaterialRecyclerAdapter = new ExpMineralMaterialRecyclerAdapter(getActivity(), mineralMaterialLayoutArrayList);
                                recyclerView.setAdapter(expMineralMaterialRecyclerAdapter);
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
                            }
                        }
                        edtSheklekansar.setText("");
                        edtZonesakhtari.setText("");
                        edtKanihayeasli.setText("");
                        edtSangemizban.setText("");
                        edtVahedhayesang.setText("");
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
        @Override
        public void onReceive(Context context, Intent intent) {
            long mineralMaterialId = intent.getLongExtra("expMineralMaterialId",0);
            MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(context);
            mineralmaterialLocalServiceUtil.deleteMineralmaterial(mineralMaterialId);
            for(int i = 0;i<mineralMaterialLayoutArrayList.size();i++){
                if(mineralMaterialId==mineralMaterialLayoutArrayList.get(i).getMineralmaterialid()){
                    System.out.println("hh" + mineralMaterialLayoutArrayList.get(i).getMineralmaterialid());
                    mineralMaterialLayoutArrayList.remove(i);
                }
            }
        }
    };
    public void getMineralMaterial(){
        MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(getActivity());
        List<Mineralmaterial> mineralmaterials = mineralmaterialLocalServiceUtil.getMineralmaterialByMineId(mineId);
        System.out.println(mineralmaterials.size());
        if (mineralmaterials.size() > 0) {
            for (int i = 0; i < mineralmaterials.size(); i++) {
                MineralMaterialLayout mineralMaterialLayout = new MineralMaterialLayout();
                mineralMaterialLayout.setId(i);
                mineralMaterialLayout.setMineralmaterialid(mineralmaterials.get(i).getMineralmaterialid());
                mineralMaterialLayout.setMineid(mineralmaterials.get(i).getMineid());
                mineralMaterialLayout.setSheklekansar(mineralmaterials.get(i).getSheklekansar());
                mineralMaterialLayout.setKanihayeasli(mineralmaterials.get(i).getKanihayeasli());
                mineralMaterialLayout.setZonesakhtari(mineralmaterials.get(i).getZonesakhtari());
                mineralMaterialLayout.setSangemizban(mineralmaterials.get(i).getSangemizban());
                mineralMaterialLayout.setVahedhayesangshenasieasli(mineralmaterials.get(i).getVahedhayesangshenasieasli());
                mineralMaterialLayoutArrayList.add(mineralMaterialLayout);
                expMineralMaterialRecyclerAdapter = new ExpMineralMaterialRecyclerAdapter(getActivity(), mineralMaterialLayoutArrayList);
                recyclerView.setAdapter(expMineralMaterialRecyclerAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
            }
        }
    }


}

