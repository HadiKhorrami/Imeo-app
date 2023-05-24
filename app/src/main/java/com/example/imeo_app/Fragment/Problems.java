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
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Problems extends Fragment {

    EditText edtMoshkelat;
    AppCompatButton btnSave,btnBack;
    JSONArray jsonArray;
    long repId;
    ConstraintLayout mainLayout;
    public Problems() {
        // Required empty public constructor
    }

    public static Problems newInstance() {
        Problems fragment = new Problems();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_problems, container, false);

        edtMoshkelat = view.findViewById(R.id.edtMoshkelat);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
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

        long mineId = getArguments().getLong("mineId");
        int mineStage = getArguments().getInt("mineStage");
        String mineInfo = getArguments().getString("mineInfo");
        String extractionMethod = getArguments().getString("extractionMethod");

        checkReportExist();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("reportId",repId);
                    if(!edtMoshkelat.getText().toString().equals("")){
                        jsonObject.put("moshkelatVaMavane",edtMoshkelat.getText().toString());
                    }else {
                        jsonObject.put("moshkelatVaMavane","");
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
                setProblemsInReport();

                Intent intent = new Intent("setColor");
                if(getArguments().containsKey("mineActive")){
                    Fragment selectedFragment = CommentOffer.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putInt("mineActive", 2);
                    selectedFragment.setArguments(bundle);
                    intent.putExtra("button","btnForm6");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else {
                    Fragment selectedFragment = CommentOffer.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    if (mineType.equals("pellekani") || mineType.equals("ekteshafi")) {
                        intent.putExtra("button", "btnForm15");
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    } else if (mineType.equals("zirzamini")) {
                        intent.putExtra("button", "btnForm14");
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    } else if (mineType.equals("enfejari")) {
                        intent.putExtra("button", "btnForm16");
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    }
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments().containsKey("mineActive")){
                    Fragment selectedFragment = MineCondition.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putLong("mineId", mineId);
                    bundle.putInt("mineStage", mineStage);
                    bundle.putString("mineInfo", mineInfo);
                    bundle.putString("extractionMethod", extractionMethod);
                    selectedFragment.setArguments(bundle);
                }else {
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

            }
        });
        return view;
    }
    public void checkReportExist(){
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report reports = reportLocalServiceUtil.getReportById(repId);
        if(reports != null){
            edtMoshkelat.setText(String.valueOf(reports.getMoshkelatvamavane()));
        }
    }
    public void setProblemsInReport() {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        if (report != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("reportId", repId);
                jsonObject.put("setProblems", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
        }
    }
}