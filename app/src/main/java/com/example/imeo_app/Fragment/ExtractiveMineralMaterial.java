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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
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
import android.widget.Toast;

import com.example.imeo_app.Activity.Report;
import com.example.imeo_app.Adapters.ExpMineralMaterialRecyclerAdapter;
import com.example.imeo_app.Adapters.ExtMineralMaterialRecyclerAdapter;
import com.example.imeo_app.DataModels.ExtMineralMaterialLayout;
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

public class ExtractiveMineralMaterial extends Fragment {
    ArrayList<MineralMaterialLayout> mineralMaterialLayoutArrayList;
    ExtMineralMaterialRecyclerAdapter extMineralMaterialRecyclerAdapter;
    RecyclerView recyclerView;
    EditText edtType,edtColor,edtMinePlace,edtUseCase,edtDensity;
    AppCompatButton btnEdit,btnSave,btnBack;
    ConstraintLayout mainLayout;
    NestedScrollView nestedScrollView;
    boolean hasEdit = true;
    public ExtractiveMineralMaterial() {
        // Required empty public constructor
    }

    public static ExtractiveMineralMaterial newInstance() {
        ExtractiveMineralMaterial fragment = new ExtractiveMineralMaterial();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extractive_mineral_material, container, false);
        mineralMaterialLayoutArrayList = new ArrayList<>();
        edtType = view.findViewById(R.id.edtType);
        edtColor = view.findViewById(R.id.edtColor);
        edtMinePlace = view.findViewById(R.id.edtMinePlace);
        edtUseCase = view.findViewById(R.id.edtUseCase);
        edtDensity = view.findViewById(R.id.edtDensity);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        recyclerView = view.findViewById(R.id.recyclerView);
        mainLayout = view.findViewById(R.id.mainLayout);
        nestedScrollView = view.findViewById(R.id.nestedScrollView);

        String mineInfo = getArguments().getString("mineInfo");
        long mineId = getArguments().getLong("mineId");
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

        MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(getActivity());
        List<Mineralmaterial> mineralmaterials = mineralmaterialLocalServiceUtil.getMineralmaterialByMineId(mineId);
        System.out.println(mineralmaterials.size());
        if (mineralmaterials.size() > 0) {
            for (int i = 0; i < mineralmaterials.size(); i++) {
                MineralMaterialLayout mineralMaterialLayout = new MineralMaterialLayout();
                mineralMaterialLayout.setId(i);
                mineralMaterialLayout.setMineralmaterialid(mineralmaterials.get(i).getMineralmaterialid());
                mineralMaterialLayout.setMineid(mineralmaterials.get(i).getMineid());
                mineralMaterialLayout.setType_(mineralmaterials.get(i).getType_());
                mineralMaterialLayout.setColor(mineralmaterials.get(i).getColor());
                mineralMaterialLayout.setMineplace(mineralmaterials.get(i).getMineplace());
                mineralMaterialLayout.setUsecase(mineralmaterials.get(i).getUsecase());
                mineralMaterialLayout.setDensity(mineralmaterials.get(i).getDensity());
                mineralMaterialLayoutArrayList.add(mineralMaterialLayout);
                extMineralMaterialRecyclerAdapter = new ExtMineralMaterialRecyclerAdapter(getActivity(), mineralMaterialLayoutArrayList);
                recyclerView.setAdapter(extMineralMaterialRecyclerAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
            }
        }
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(editReceiver, new IntentFilter("editMineralMaterial"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(deleteReceiver, new IntentFilter("deleteMineralMaterial"));
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
                        jsonObject.put("minePlace",mineralMaterialLayoutArrayList.get(i).getMineplace());
                        jsonObject.put("type",mineralMaterialLayoutArrayList.get(i).getType_());
                        jsonObject.put("color",mineralMaterialLayoutArrayList.get(i).getColor());
                        jsonObject.put("usecase",mineralMaterialLayoutArrayList.get(i).getUsecase());
                        jsonObject.put("density",mineralMaterialLayoutArrayList.get(i).getDensity());
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
                edtType.setText("");
                edtColor.setText("");
                edtMinePlace.setText("");
                edtUseCase.setText("");
                edtDensity.setText("");

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
                Fragment selectedFragment = ExtractiveMineInfo.newInstance();
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
        return view;
    }
    public BroadcastReceiver editReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hasEdit = true;
            long mineralMaterialId = intent.getLongExtra("mineralMaterialId",0);
            System.out.println(mineralMaterialId);
            for(int i = 0;i<mineralMaterialLayoutArrayList.size();i++){
                if(mineralMaterialId==mineralMaterialLayoutArrayList.get(i).getMineralmaterialid()){
                    edtType.setText(mineralMaterialLayoutArrayList.get(i).getType_());
                    edtColor.setText(mineralMaterialLayoutArrayList.get(i).getColor());
                    edtMinePlace.setText(mineralMaterialLayoutArrayList.get(i).getMineplace());
                    edtUseCase.setText(mineralMaterialLayoutArrayList.get(i).getUsecase());
                    edtDensity.setText(mineralMaterialLayoutArrayList.get(i).getDensity());
                }
            }
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onClick(View v) {
                    if (hasEdit == true) {
                        for (int i = 0; i < mineralMaterialLayoutArrayList.size(); i++) {
                            if (mineralMaterialId == mineralMaterialLayoutArrayList.get(i).getMineralmaterialid()) {
                                mineralMaterialLayoutArrayList.get(i).setType_(edtType.getText().toString());
                                mineralMaterialLayoutArrayList.get(i).setColor(edtColor.getText().toString());
                                mineralMaterialLayoutArrayList.get(i).setMineplace(edtMinePlace.getText().toString());
                                mineralMaterialLayoutArrayList.get(i).setUsecase(edtUseCase.getText().toString());
                                mineralMaterialLayoutArrayList.get(i).setDensity(edtDensity.getText().toString());
                                extMineralMaterialRecyclerAdapter = new ExtMineralMaterialRecyclerAdapter(getActivity(), mineralMaterialLayoutArrayList);
                                recyclerView.setAdapter(extMineralMaterialRecyclerAdapter);
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
                            }
                        }
                        edtType.setText("");
                        edtColor.setText("");
                        edtMinePlace.setText("");
                        edtUseCase.setText("");
                        edtDensity.setText("");
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
            long mineralMaterialId = intent.getLongExtra("mineralMaterialId",0);
            MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(context);
              mineralmaterialLocalServiceUtil.deleteMineralmaterial(mineralMaterialId);
            for(int i = 0;i<mineralMaterialLayoutArrayList.size();i++){
                if(mineralMaterialId==mineralMaterialLayoutArrayList.get(i).getMineralmaterialid()){
                    mineralMaterialLayoutArrayList.remove(i);
                }
            }
        }
    };

}