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

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MineralmaterialLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MineOperation2 extends Fragment {

    EditText edtMetrazheKolleHaffari,edtQotreChalha,edtTedadeChalha,edtTedadeRadif,edtFaseleyeChalha,edtTedadeChalhaDarRadif,edtFaseleyeRadifi,edtFaseleTaJebhe,edtOmgheChalha,edtMeghdareSang,edtHaffarieVizhe,
            edtAnfo,edtDinamit,edtEmolayt,edtBaroot,edtChashnieElektriki,edtChashnieMamuli,edtKortex,edtNatel,edtPc,edtTakhiri,edtBooster,edtFitile;
    AppCompatRadioButton rdHaffariYes,rdHaffariNo,rdEnfejarYes,rdEnfejarNo;
    ConstraintLayout haffariLayout,enfejarLayout;
    AppCompatButton btnSave,btnBack;
    long repId;
    double specialWeight;
    JSONArray jsonArray;
    int doneGozaresheHaffari,doneGozaresheEnfejari;
    public MineOperation2() {
        // Required empty public constructor
    }

    public static MineOperation2 newInstance() {
        MineOperation2 fragment = new MineOperation2();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_operation2, container, false);
        rdHaffariYes = view.findViewById(R.id.rdHaffariYes);
        rdHaffariNo = view.findViewById(R.id.rdHaffariNo);
        rdEnfejarYes = view.findViewById(R.id.rdEnfejarYes);
        rdEnfejarNo = view.findViewById(R.id.rdEnfejarNo);
        haffariLayout = view.findViewById(R.id.haffariLayout);
        enfejarLayout = view.findViewById(R.id.enfejarLayout);

        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);

        edtMetrazheKolleHaffari = view.findViewById(R.id.edtMetrazheKolleHaffari);
        edtQotreChalha = view.findViewById(R.id.edtQotreChalha);
        edtTedadeChalha = view.findViewById(R.id.edtTedadeChalha);
        edtTedadeRadif = view.findViewById(R.id.edtTedadeRadif);
        edtFaseleyeChalha = view.findViewById(R.id.edtFaseleyeChalha);
        edtTedadeChalhaDarRadif = view.findViewById(R.id.edtTedadeChalhaDarRadif);
        edtFaseleyeRadifi = view.findViewById(R.id.edtFaseleyeRadifi);
        edtFaseleTaJebhe = view.findViewById(R.id.edtFaseleTaJebhe);
        edtOmgheChalha = view.findViewById(R.id.edtOmgheChalha);
        edtMeghdareSang = view.findViewById(R.id.edtMeghdareSang);
        edtHaffarieVizhe = view.findViewById(R.id.edtHaffarieVizhe);
        edtAnfo = view.findViewById(R.id.edtAnfo);
        edtDinamit = view.findViewById(R.id.edtDinamit);
        edtEmolayt = view.findViewById(R.id.edtEmolayt);
        edtBaroot = view.findViewById(R.id.edtBaroot);
        edtChashnieElektriki = view.findViewById(R.id.edtChashnieElektriki);
        edtChashnieMamuli = view.findViewById(R.id.edtChashnieMamuli);
        edtKortex = view.findViewById(R.id.edtKortex);
        edtNatel = view.findViewById(R.id.edtNatel);
        edtPc = view.findViewById(R.id.edtPc);
        edtTakhiri = view.findViewById(R.id.edtTakhiri);
        edtBooster = view.findViewById(R.id.edtBooster);
        edtFitile = view.findViewById(R.id.edtFitile);

        haffariLayout.setVisibility(View.INVISIBLE);
        enfejarLayout.setVisibility(View.INVISIBLE);

        SharedPreferences shared = getActivity().getSharedPreferences("repId", MODE_PRIVATE);
        repId = shared.getLong("reportId", 0);
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        if(report != null){
            MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(getActivity());
            List<Mineralmaterial> mineralmaterial = mineralmaterialLocalServiceUtil.getMineralmaterialByMineId(report.getMineid());
            specialWeight = Double.parseDouble(mineralmaterial.get(0).getDensity());
        }
        getReportInfo();

        rdHaffariYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rdHaffariNo.setChecked(false);
                haffariLayout.setVisibility(View.VISIBLE);
                doneGozaresheHaffari = 1;
            }
        });
        rdHaffariNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rdHaffariYes.setChecked(false);
                haffariLayout.setVisibility(View.INVISIBLE);
                doneGozaresheHaffari = 0;
            }
        });
        rdEnfejarYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rdEnfejarNo.setChecked(false);
                enfejarLayout.setVisibility(View.VISIBLE);
                doneGozaresheEnfejari = 1;
            }
        });
        rdEnfejarNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rdEnfejarYes.setChecked(false);
                enfejarLayout.setVisibility(View.INVISIBLE);
                doneGozaresheEnfejari = 1;
            }
        });

        edtMetrazheKolleHaffari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtTedadeChalha.getText().toString().equals("") && !edtMetrazheKolleHaffari.getText().toString().equals("")){
                    edtOmgheChalha.setText(String.valueOf(Double.parseDouble(edtMetrazheKolleHaffari.getText().toString()) / Double.parseDouble(edtTedadeChalha.getText().toString())));
                }
                if(edtTedadeChalha.getText().toString().equals("") || edtMetrazheKolleHaffari.getText().toString().equals("")){
                    edtOmgheChalha.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTedadeChalha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtTedadeChalha.getText().toString().equals("") && !edtMetrazheKolleHaffari.getText().toString().equals("")){
                    edtOmgheChalha.setText(String.valueOf(Double.parseDouble(edtMetrazheKolleHaffari.getText().toString()) / Double.parseDouble(edtTedadeChalha.getText().toString())));
                }
                if(edtTedadeChalha.getText().toString().equals("") || edtMetrazheKolleHaffari.getText().toString().equals("")){
                    edtOmgheChalha.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtFaseleyeChalha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtFaseleyeChalha.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtFaseleyeRadifi.getText().toString().equals("") && !edtTedadeRadif.getText().toString().equals("") && !edtMetrazheKolleHaffari.getText().toString().equals("") && !edtTedadeChalha.getText().toString().equals("")){
                    edtMeghdareSang.setText(String.valueOf((Double.parseDouble(edtFaseleyeChalha.getText().toString()) * (Double.parseDouble(edtTedadeChalhaDarRadif.getText().toString()) - 1)) * (Double.parseDouble(edtFaseleyeRadifi.getText().toString()) * (Double.parseDouble(edtTedadeRadif.getText().toString()) - 1) * (Double.parseDouble(edtMetrazheKolleHaffari.getText().toString()) / Double.parseDouble(edtTedadeChalha.getText().toString())) * specialWeight)));
                }else if(edtFaseleyeChalha.getText().toString().equals("") || edtTedadeChalhaDarRadif.getText().toString().equals("") || edtFaseleyeRadifi.getText().toString().equals("") || edtTedadeRadif.getText().toString().equals("") || edtMetrazheKolleHaffari.getText().toString().equals("") || edtTedadeChalha.getText().toString().equals("")) {
                    edtMeghdareSang.setText("");
                }
                if(!edtFaseleyeChalha.getText().toString().equals("") && !edtFaseleyeRadifi.getText().toString().equals("")){
                    edtHaffarieVizhe.setText(String.valueOf(1 / (Double.parseDouble(edtFaseleyeChalha.getText().toString()) * Double.parseDouble(edtFaseleyeRadifi.getText().toString()) * specialWeight)));
                }else if(edtFaseleyeChalha.getText().toString().equals("") || edtFaseleyeRadifi.getText().toString().equals("")) {
                    edtHaffarieVizhe.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTedadeChalhaDarRadif.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtFaseleyeChalha.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtFaseleyeRadifi.getText().toString().equals("") && !edtTedadeRadif.getText().toString().equals("") && !edtMetrazheKolleHaffari.getText().toString().equals("") && !edtTedadeChalha.getText().toString().equals("")){
                    edtMeghdareSang.setText(String.valueOf((Double.parseDouble(edtFaseleyeChalha.getText().toString()) * (Double.parseDouble(edtTedadeChalhaDarRadif.getText().toString()) - 1)) * (Double.parseDouble(edtFaseleyeRadifi.getText().toString()) * (Double.parseDouble(edtTedadeRadif.getText().toString()) - 1) * (Double.parseDouble(edtMetrazheKolleHaffari.getText().toString()) / Double.parseDouble(edtTedadeChalha.getText().toString())) * specialWeight)));
                }
                else if(edtFaseleyeChalha.getText().toString().equals("") || edtTedadeChalhaDarRadif.getText().toString().equals("") || edtFaseleyeRadifi.getText().toString().equals("") || edtTedadeRadif.getText().toString().equals("") || edtMetrazheKolleHaffari.getText().toString().equals("") || edtTedadeChalha.getText().toString().equals("")) {
                    edtMeghdareSang.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtFaseleyeRadifi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtFaseleyeChalha.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtFaseleyeRadifi.getText().toString().equals("") && !edtTedadeRadif.getText().toString().equals("") && !edtMetrazheKolleHaffari.getText().toString().equals("") && !edtTedadeChalha.getText().toString().equals("")){
                    edtMeghdareSang.setText(String.valueOf((Double.parseDouble(edtFaseleyeChalha.getText().toString()) * (Double.parseDouble(edtTedadeChalhaDarRadif.getText().toString()) - 1)) * (Double.parseDouble(edtFaseleyeRadifi.getText().toString()) * (Double.parseDouble(edtTedadeRadif.getText().toString()) - 1) * (Double.parseDouble(edtMetrazheKolleHaffari.getText().toString()) / Double.parseDouble(edtTedadeChalha.getText().toString())) * specialWeight)));
                }
                else if(edtFaseleyeChalha.getText().toString().equals("") || edtTedadeChalhaDarRadif.getText().toString().equals("") || edtFaseleyeRadifi.getText().toString().equals("") || edtTedadeRadif.getText().toString().equals("") || edtMetrazheKolleHaffari.getText().toString().equals("") || edtTedadeChalha.getText().toString().equals("")) {
                    edtMeghdareSang.setText("");
                }
                if(!edtFaseleyeChalha.getText().toString().equals("") && !edtFaseleyeRadifi.getText().toString().equals("")){
                    edtHaffarieVizhe.setText(String.valueOf(1 / (Double.parseDouble(edtFaseleyeChalha.getText().toString()) * Double.parseDouble(edtFaseleyeRadifi.getText().toString()) * specialWeight)));
                }else if(edtFaseleyeChalha.getText().toString().equals("") || edtFaseleyeRadifi.getText().toString().equals("")) {
                    edtHaffarieVizhe.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTedadeRadif.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtFaseleyeChalha.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtFaseleyeRadifi.getText().toString().equals("") && !edtTedadeRadif.getText().toString().equals("") && !edtMetrazheKolleHaffari.getText().toString().equals("") && !edtTedadeChalha.getText().toString().equals("")){
                    edtMeghdareSang.setText(String.valueOf((Double.parseDouble(edtFaseleyeChalha.getText().toString()) * (Double.parseDouble(edtTedadeChalhaDarRadif.getText().toString()) - 1)) * (Double.parseDouble(edtFaseleyeRadifi.getText().toString()) * (Double.parseDouble(edtTedadeRadif.getText().toString()) - 1) * (Double.parseDouble(edtMetrazheKolleHaffari.getText().toString()) / Double.parseDouble(edtTedadeChalha.getText().toString())) * specialWeight)));
                }
                else if(edtFaseleyeChalha.getText().toString().equals("") || edtTedadeChalhaDarRadif.getText().toString().equals("") || edtFaseleyeRadifi.getText().toString().equals("") || edtTedadeRadif.getText().toString().equals("") || edtMetrazheKolleHaffari.getText().toString().equals("") || edtTedadeChalha.getText().toString().equals("")) {
                    edtMeghdareSang.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtMetrazheKolleHaffari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtFaseleyeChalha.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtFaseleyeRadifi.getText().toString().equals("") && !edtTedadeRadif.getText().toString().equals("") && !edtMetrazheKolleHaffari.getText().toString().equals("") && !edtTedadeChalha.getText().toString().equals("")){
                    edtMeghdareSang.setText(String.valueOf((Double.parseDouble(edtFaseleyeChalha.getText().toString()) * (Double.parseDouble(edtTedadeChalhaDarRadif.getText().toString()) - 1)) * (Double.parseDouble(edtFaseleyeRadifi.getText().toString()) * (Double.parseDouble(edtTedadeRadif.getText().toString()) - 1) * (Double.parseDouble(edtMetrazheKolleHaffari.getText().toString()) / Double.parseDouble(edtTedadeChalha.getText().toString())) * specialWeight)));
                }
                else if(edtFaseleyeChalha.getText().toString().equals("") || edtTedadeChalhaDarRadif.getText().toString().equals("") || edtFaseleyeRadifi.getText().toString().equals("") || edtTedadeRadif.getText().toString().equals("") || edtMetrazheKolleHaffari.getText().toString().equals("") || edtTedadeChalha.getText().toString().equals("")) {
                    edtMeghdareSang.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTedadeChalha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtFaseleyeChalha.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtTedadeChalhaDarRadif.getText().toString().equals("") && !edtFaseleyeRadifi.getText().toString().equals("") && !edtTedadeRadif.getText().toString().equals("") && !edtMetrazheKolleHaffari.getText().toString().equals("") && !edtTedadeChalha.getText().toString().equals("")){
                    edtMeghdareSang.setText(String.valueOf((Double.parseDouble(edtFaseleyeChalha.getText().toString()) * (Double.parseDouble(edtTedadeChalhaDarRadif.getText().toString()) - 1)) * (Double.parseDouble(edtFaseleyeRadifi.getText().toString()) * (Double.parseDouble(edtTedadeRadif.getText().toString()) - 1) * (Double.parseDouble(edtMetrazheKolleHaffari.getText().toString()) / Double.parseDouble(edtTedadeChalha.getText().toString())) * specialWeight)));
                }
                else if(edtFaseleyeChalha.getText().toString().equals("") || edtTedadeChalhaDarRadif.getText().toString().equals("") || edtFaseleyeRadifi.getText().toString().equals("") || edtTedadeRadif.getText().toString().equals("") || edtMetrazheKolleHaffari.getText().toString().equals("") || edtTedadeChalha.getText().toString().equals("")) {
                    edtMeghdareSang.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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
                    jsonObject.put("DoneGozaresheHaffari",doneGozaresheHaffari);
                    jsonObject.put("metrazheKolleHaffari",edtMetrazheKolleHaffari.getText().toString().equals("") ? 0 : Integer.parseInt(edtMetrazheKolleHaffari.getText().toString()));
                    jsonObject.put("qotreChalha",edtQotreChalha.getText().toString().equals("") ? 0 : Integer.parseInt(edtQotreChalha.getText().toString()));
                    jsonObject.put("tedadeChalha",edtTedadeChalha.getText().toString().equals("") ? 0 : Integer.parseInt(edtTedadeChalha.getText().toString()));
                    jsonObject.put("faseleyeChalha",edtFaseleyeChalha.getText().toString().equals("") ? 0 : Integer.parseInt(edtFaseleyeChalha.getText().toString()));
                    jsonObject.put("tedadeChalhaDarRadif",edtTedadeChalhaDarRadif.getText().toString().equals("") ? 0 : Integer.parseInt(edtTedadeChalhaDarRadif.getText().toString()));
                    jsonObject.put("tedadeRadif",edtTedadeRadif.getText().toString().equals("") ? 0 : Integer.parseInt(edtTedadeRadif.getText().toString()));
                    jsonObject.put("faseleyeRadifi",edtFaseleyeRadifi.getText().toString().equals("") ? 0 : Integer.parseInt(edtFaseleyeRadifi.getText().toString()));
                    jsonObject.put("faseleTaJebheyeKareAzadeChalha",edtFaseleTaJebhe.getText().toString().equals("") ? 0 : Integer.parseInt(edtFaseleTaJebhe.getText().toString()));
                    jsonObject.put("omgheMotavasseteChalha",edtOmgheChalha.getText().toString().equals("") ? 0 : Double.parseDouble(edtOmgheChalha.getText().toString()));
                    jsonObject.put("meghdareSangeGhabeleEstekhraj",edtMeghdareSang.getText().toString().equals("") ? 0 : Double.parseDouble(edtMeghdareSang.getText().toString()));
                    jsonObject.put("haffarieVizhe",edtHaffarieVizhe.getText().toString().equals("") ? 0 : Double.parseDouble(edtHaffarieVizhe.getText().toString()));

                    jsonObject.put("DoneGozaresheEnfejari",doneGozaresheEnfejari);
                    jsonObject.put("anfo",edtAnfo.getText().toString().equals("") ? 0 : Integer.parseInt(edtAnfo.getText().toString()));
                    jsonObject.put("dinamit",edtDinamit.getText().toString().equals("") ? 0 : Integer.parseInt(edtDinamit.getText().toString()));
                    jsonObject.put("emolayt",edtEmolayt.getText().toString().equals("") ? 0 : Integer.parseInt(edtEmolayt.getText().toString()));
                    jsonObject.put("baroot",edtBaroot.getText().toString().equals("") ? 0 : Integer.parseInt(edtBaroot.getText().toString()));
                    jsonObject.put("chashnieElektriki",edtChashnieElektriki.getText().toString().equals("") ? 0 : Integer.parseInt(edtChashnieElektriki.getText().toString()));
                    jsonObject.put("chashnieMamuli",edtChashnieMamuli.getText().toString().equals("") ? 0 : Integer.parseInt(edtChashnieMamuli.getText().toString()));
                    jsonObject.put("kortex",edtKortex.getText().toString().equals("") ? 0 : Integer.parseInt(edtKortex.getText().toString()));
                    jsonObject.put("natel",edtNatel.getText().toString().equals("") ? 0 : Integer.parseInt(edtNatel.getText().toString()));
                    jsonObject.put("pc",edtPc.getText().toString().equals("") ? 0 : Integer.parseInt(edtPc.getText().toString()));
                    jsonObject.put("takhiri",edtTakhiri.getText().toString().equals("") ? 0 : Integer.parseInt(edtTakhiri.getText().toString()));
                    jsonObject.put("booster",edtBooster.getText().toString().equals("") ? 0 : Integer.parseInt(edtBooster.getText().toString()));
                    jsonObject.put("fitile",edtFitile.getText().toString().equals("") ? 0 : Integer.parseInt(edtFitile.getText().toString()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
                JsonInsertUtil.insertReportsFromJSON(jsonArray,getActivity());
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("ثبت شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                setMineOperation2InReport();

                Fragment selectedFragment = Machinery.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm9");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences shared1 = getActivity().getSharedPreferences("mineType", MODE_PRIVATE);
                String mineType = shared1.getString("mineType","");

                if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = BMineOperation1.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm7");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("enfejari")){
                    Fragment selectedFragment = AMineOperation1.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm7");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }

            }
        });


        return view;
    }
    public void getReportInfo(){
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report reports = reportLocalServiceUtil.getReportById(repId);
        if(reports != null){
            if(reports.getDonegozareshehaffari() == 1){
                rdHaffariYes.setChecked(true);
                haffariLayout.setVisibility(View.VISIBLE);
                edtMetrazheKolleHaffari.setText(String.valueOf(reports.getMetrazhekollehaffari()));
                edtQotreChalha.setText(String.valueOf(reports.getQotrechalha()));
                edtTedadeChalha.setText(String.valueOf(reports.getTedadechalha()));
                edtTedadeRadif.setText(String.valueOf(reports.getTedaderadif()));
                edtFaseleyeChalha.setText(String.valueOf(reports.getFaseleyechalha()));
                edtTedadeChalhaDarRadif.setText(String.valueOf(reports.getTedadechalhadarradif()));
                edtFaseleyeRadifi.setText(String.valueOf(reports.getFaseleyeradifi()));
                edtFaseleTaJebhe.setText(String.valueOf(reports.getFaseletajebheyekareazadechalha()));
                edtOmgheChalha.setText(String.valueOf(reports.getOmghemotavassetechalha()));
                edtMeghdareSang.setText(String.valueOf(reports.getMeghdaresangeghabeleestekhraj()));
                edtHaffarieVizhe.setText(String.valueOf(reports.getHaffarievizhe()));
            }
            if(reports.getDonegozaresheenfejari() == 1){
                rdEnfejarYes.setChecked(true);
                enfejarLayout.setVisibility(View.VISIBLE);
                edtAnfo.setText(String.valueOf(reports.getAnfo()));
                edtDinamit.setText(String.valueOf(reports.getDinamit()));
                edtEmolayt.setText(String.valueOf(reports.getEmolayt()));
                edtBaroot.setText(String.valueOf(reports.getBaroot()));
                edtChashnieElektriki.setText(String.valueOf(reports.getChashnieelektriki()));
                edtChashnieMamuli.setText(String.valueOf(reports.getChashniemamuli()));
                edtKortex.setText(String.valueOf(reports.getKortex()));
                edtNatel.setText(String.valueOf(reports.getNatel()));
                edtPc.setText(String.valueOf(reports.getPc()));
                edtTakhiri.setText(String.valueOf(reports.getTakhiri()));
                edtBooster.setText(String.valueOf(reports.getBooster()));
                edtFitile.setText(String.valueOf(reports.getFitile()));
            }
        }
    }
    public void setMineOperation2InReport() {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        if (report != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("reportId", repId);
                jsonObject.put("setOperation2", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
        }
    }
}