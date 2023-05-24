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

import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imeo_app.DataModels.AMineOperationLayout;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.ProducesellLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Producesell;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManPower#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManPower extends Fragment {

    EditText edtMohandesiAmani,edtMohandesiPeymani,edtMohandesiSum,edtKargaranAmani,edtKargaranPeymani,edtKargaranSum,edtEdariAmani,edtEdariPeymani,edtEdariSum,edtAmaniSum,edtPeymaniSum,edtSum,
            edtUnInsuredWorker,edtDoreAmoozeshiBimeWorker,edtAverageEfficiencyKarkonaneTolidi,edtAverageEfficiencyKarkonanSum;
    AppCompatButton btnSave,btnBack;
    long repId;
    int actualExtraction = 0;
    JSONArray jsonArray;
    ArrayList<AMineOperationLayout> aMineOperationLayouts;
    ConstraintLayout mainLayout;
    public ManPower() {
        // Required empty public constructor
    }

    public static ManPower newInstance() {
        ManPower fragment = new ManPower();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_man_power, container, false);
        edtMohandesiAmani = view.findViewById(R.id.edtMohandesiAmani);
        edtMohandesiPeymani = view.findViewById(R.id.edtMohandesiPeymani);
        edtMohandesiSum = view.findViewById(R.id.edtMohandesiSum);
        edtKargaranAmani = view.findViewById(R.id.edtKargaranAmani);
        edtKargaranPeymani = view.findViewById(R.id.edtKargaranPeymani);
        edtKargaranSum = view.findViewById(R.id.edtKargaranSum);
        edtEdariAmani = view.findViewById(R.id.edtEdariAmani);
        edtEdariPeymani = view.findViewById(R.id.edtEdariPeymani);
        edtEdariSum = view.findViewById(R.id.edtEdariSum);
        edtAmaniSum = view.findViewById(R.id.edtAmaniSum);
        edtPeymaniSum = view.findViewById(R.id.edtPeymaniSum);
        edtSum = view.findViewById(R.id.edtSum);
        edtUnInsuredWorker = view.findViewById(R.id.edtUnInsuredWorker);
        edtDoreAmoozeshiBimeWorker = view.findViewById(R.id.edtDoreAmoozeshiBimeWorker);
        edtAverageEfficiencyKarkonaneTolidi = view.findViewById(R.id.edtAverageEfficiencyKarkonaneTolidi);
        edtAverageEfficiencyKarkonanSum = view.findViewById(R.id.edtAverageEfficiencyKarkonanSum);
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
        setActualExtraction();
        checkReportExist();

        SharedPreferences shared1 = getActivity().getSharedPreferences("mineType", MODE_PRIVATE);
        String mineType = shared1.getString("mineType","");

        edtMohandesiAmani.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtMohandesiAmani.getText().toString().equals("") && !edtMohandesiPeymani.getText().toString().equals("")) {
                    edtMohandesiSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + Integer.parseInt(edtMohandesiPeymani.getText().toString())));
                } else if (!edtMohandesiAmani.getText().toString().equals("") && edtMohandesiPeymani.getText().toString().equals("")) {
                    edtMohandesiSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + 0));
                } else if (edtMohandesiAmani.getText().toString().equals("") && !edtMohandesiPeymani.getText().toString().equals("")) {
                    edtMohandesiSum.setText(String.valueOf(0 + Integer.parseInt(edtMohandesiPeymani.getText().toString())));
                } else if (edtMohandesiAmani.getText().toString().equals("") && edtMohandesiPeymani.getText().toString().equals("")) {
                    edtMohandesiSum.setText("0");
                }

                if (!edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")) {
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + Integer.parseInt(edtKargaranAmani.getText().toString()) + Integer.parseInt(edtEdariAmani.getText().toString())));
                } else if (edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")) {
                    edtAmaniSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranAmani.getText().toString()) + Integer.parseInt(edtEdariAmani.getText().toString())));
                } else if (!edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")) {
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + 0 + Integer.parseInt(edtEdariAmani.getText().toString())));
                } else if (!edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")) {
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + Integer.parseInt(edtKargaranAmani.getText().toString()) + 0));
                } else if (!edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")) {
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + 0 + 0));
                } else if (edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")) {
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(0 + edtKargaranAmani.getText().toString()) + 0));
                } else if (edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")) {
                    edtAmaniSum.setText(String.valueOf(0 + 0 + Integer.parseInt(edtEdariAmani.getText().toString())));
                } else if (edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")) {
                    edtAmaniSum.setText("0");
                }

                if (!edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals(""))
                    edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + Integer.parseInt(edtPeymaniSum.getText().toString())));
                else if (edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals("")) {
                    edtSum.setText(String.valueOf(0 + Integer.parseInt(edtPeymaniSum.getText().toString())));
                } else if (!edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")) {
                    edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + 0));
                } else if (edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")) {
                    edtSum.setText("0");
                }

                    if (edtKargaranSum.getText().toString().equals("") && Integer.parseInt(edtMohandesiSum.getText().toString())!=0){
                        edtAverageEfficiencyKarkonaneTolidi.setText(String.valueOf(actualExtraction / (Integer.parseInt(edtMohandesiSum.getText().toString()) + 0)));
                } else {
                        if(Integer.parseInt(edtMohandesiSum.getText().toString())!=0)
                           edtAverageEfficiencyKarkonaneTolidi.setText(String.valueOf(actualExtraction / (Integer.parseInt(edtMohandesiSum.getText().toString()) + Integer.parseInt(edtKargaranSum.getText().toString()))));
                } if(Integer.parseInt(edtMohandesiSum.getText().toString())==0 && Integer.parseInt(edtKargaranSum.getText().toString())==0 || edtKargaranSum.getText().toString().equals("")){
                    edtAverageEfficiencyKarkonaneTolidi.setText("0");
                }

                if(Integer.parseInt(edtSum.getText().toString()) != 0)
                    edtAverageEfficiencyKarkonanSum.setText(String.valueOf(actualExtraction / Integer.parseInt(edtSum.getText().toString())));
                else {
                    edtAverageEfficiencyKarkonanSum.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtMohandesiPeymani.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtMohandesiPeymani.getText().toString().equals("") && !edtMohandesiAmani.getText().toString().equals("")) {
                    edtMohandesiSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + Integer.parseInt(edtMohandesiAmani.getText().toString())));
                } else if (!edtMohandesiAmani.getText().toString().equals("") && edtMohandesiPeymani.getText().toString().equals("")) {
                    edtMohandesiSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + 0));
                } else if (edtMohandesiAmani.getText().toString().equals("") && !edtMohandesiPeymani.getText().toString().equals("")) {
                    edtMohandesiSum.setText(String.valueOf(0 + Integer.parseInt(edtMohandesiPeymani.getText().toString())));
                } else if (edtMohandesiAmani.getText().toString().equals("") && edtMohandesiPeymani.getText().toString().equals("")) {
                    edtMohandesiSum.setText("0");
                }

                if (!edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")) {
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + Integer.parseInt(edtKargaranPeymani.getText().toString()) + Integer.parseInt(edtEdariPeymani.getText().toString())));
                } else if (edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")) {
                    edtPeymaniSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranPeymani.getText().toString()) + Integer.parseInt(edtEdariPeymani.getText().toString())));
                } else if (!edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")) {
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + 0 + Integer.parseInt(edtEdariPeymani.getText().toString())));
                } else if (!edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")) {
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + Integer.parseInt(edtKargaranPeymani.getText().toString()) + 0));
                } else if (!edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")) {
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + 0 + 0));
                } else if (edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")) {
                    edtPeymaniSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranPeymani.getText().toString()) + 0));
                } else if (edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")) {
                    edtPeymaniSum.setText(String.valueOf(0 + 0 + Integer.parseInt(edtEdariPeymani.getText().toString())));
                } else if (edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")) {
                    edtPeymaniSum.setText("0");
                }

                if (!edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals(""))
                    edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + Integer.parseInt(edtPeymaniSum.getText().toString())));
                else if (edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals("")) {
                    edtSum.setText(String.valueOf(0 + Integer.parseInt(edtPeymaniSum.getText().toString())));
                } else if (!edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")) {
                    edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + 0));
                } else if (edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")) {
                    edtSum.setText("0");
                }

                if (edtKargaranSum.getText().toString().equals("") && Integer.parseInt(edtMohandesiSum.getText().toString()) != 0) {
                    edtAverageEfficiencyKarkonaneTolidi.setText(String.valueOf(actualExtraction / (Integer.parseInt(edtMohandesiSum.getText().toString()) + 0)));
                } else {
                    if (Integer.parseInt(edtMohandesiSum.getText().toString()) != 0)
                        edtAverageEfficiencyKarkonaneTolidi.setText(String.valueOf(actualExtraction / (Integer.parseInt(edtMohandesiSum.getText().toString()) + Integer.parseInt(edtKargaranSum.getText().toString()))));
                }if(Integer.parseInt(edtMohandesiSum.getText().toString())==0 && Integer.parseInt(edtKargaranSum.getText().toString())==0 || edtKargaranSum.getText().toString().equals("")){
                    edtAverageEfficiencyKarkonaneTolidi.setText("0");
                }

                if (Integer.parseInt(edtSum.getText().toString()) != 0){
                    edtAverageEfficiencyKarkonanSum.setText(String.valueOf(actualExtraction / Integer.parseInt(edtSum.getText().toString())));
            }else {
                    edtAverageEfficiencyKarkonanSum.setText("0");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtKargaranAmani.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtKargaranAmani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("")){
                    edtKargaranSum.setText(String.valueOf(Integer.parseInt(edtKargaranAmani.getText().toString()) + Integer.parseInt(edtKargaranPeymani.getText().toString())));
                }
                else if(!edtKargaranAmani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("")){
                    edtKargaranSum.setText(String.valueOf(Integer.parseInt(edtKargaranAmani.getText().toString()) + 0));
                }else if(edtKargaranAmani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("")){
                    edtKargaranSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranPeymani.getText().toString())));
                }else if(edtKargaranAmani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("")){
                    edtKargaranSum.setText("0");
                }

                if(!edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + Integer.parseInt(edtKargaranAmani.getText().toString()) +  Integer.parseInt(edtEdariAmani.getText().toString())));
                }else if(edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranAmani.getText().toString()) +  Integer.parseInt(edtEdariAmani.getText().toString())));
                }else if(!edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + 0 +  Integer.parseInt(edtEdariAmani.getText().toString())));
                }else if(!edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + Integer.parseInt(edtKargaranAmani.getText().toString()) +  0));
                }
                else if(!edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + 0 +  0));
                }else if(edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(0 + edtKargaranAmani.getText().toString()) + 0));
                }else if(edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(0 + 0 + Integer.parseInt(edtEdariAmani.getText().toString())));
                }else if(edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText("0");
                }

                if(!edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals(""))
                edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + Integer.parseInt(edtPeymaniSum.getText().toString())));
                else if(edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText(String.valueOf(0 + Integer.parseInt(edtPeymaniSum.getText().toString())));
                }else if(!edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + 0));
                }else if(edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText("0");
                }


                    if (edtMohandesiSum.getText().toString().equals("") && Integer.parseInt(edtKargaranSum.getText().toString())!=0) {
                        edtAverageEfficiencyKarkonaneTolidi.setText(String.valueOf(actualExtraction / (0 + Integer.parseInt(edtKargaranSum.getText().toString()))));
                    } else {
                        if(Integer.parseInt(edtKargaranSum.getText().toString())!=0)
                           edtAverageEfficiencyKarkonaneTolidi.setText(String.valueOf(actualExtraction / (Integer.parseInt(edtMohandesiSum.getText().toString()) + Integer.parseInt(edtKargaranSum.getText().toString()))));
                    }if(Integer.parseInt(edtMohandesiSum.getText().toString())==0 && Integer.parseInt(edtKargaranSum.getText().toString())==0 || edtMohandesiSum.getText().toString().equals("")){
                    edtAverageEfficiencyKarkonaneTolidi.setText("0");
                }

                if(Integer.parseInt(edtSum.getText().toString()) != 0) {
                    edtAverageEfficiencyKarkonanSum.setText(String.valueOf(actualExtraction / Integer.parseInt(edtSum.getText().toString())));
                }else {
                    edtAverageEfficiencyKarkonanSum.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtKargaranPeymani.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtKargaranAmani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("")){
                    edtKargaranSum.setText(String.valueOf(Integer.parseInt(edtKargaranAmani.getText().toString()) + Integer.parseInt(edtKargaranPeymani.getText().toString())));
                }
                else if(!edtKargaranAmani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("")){
                    edtKargaranSum.setText(String.valueOf(Integer.parseInt(edtKargaranAmani.getText().toString()) + 0));
                }else if(edtKargaranAmani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("")){
                    edtKargaranSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranPeymani.getText().toString())));
                }else if(edtKargaranAmani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("")){
                    edtKargaranSum.setText("0");
                }

                if(!edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + Integer.parseInt(edtKargaranPeymani.getText().toString()) +  Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranPeymani.getText().toString()) +  Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(!edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + 0 +  Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(!edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + Integer.parseInt(edtKargaranPeymani.getText().toString()) +  0));
                }
                else if(!edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + 0 +  0));
                }else if(edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranPeymani.getText().toString()) + 0));
                }else if(edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(0 + 0 + Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText("0");
                }

                if(!edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals(""))
                edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + Integer.parseInt(edtPeymaniSum.getText().toString())));
                else if(edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText(String.valueOf(0 + Integer.parseInt(edtPeymaniSum.getText().toString())));
                }else if(!edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + 0));
                }else if(edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText("0");
                }

                    if (edtMohandesiSum.getText().toString().equals("") && Integer.parseInt(edtKargaranSum.getText().toString())!=0) {
                        edtAverageEfficiencyKarkonaneTolidi.setText(String.valueOf(actualExtraction / (0 + Integer.parseInt(edtKargaranSum.getText().toString()))));
                    } else {
                        if(Integer.parseInt(edtKargaranSum.getText().toString())!=0)
                            edtAverageEfficiencyKarkonaneTolidi.setText(String.valueOf(actualExtraction / (Integer.parseInt(edtMohandesiSum.getText().toString()) + Integer.parseInt(edtKargaranSum.getText().toString()))));
                    }if(Integer.parseInt(edtMohandesiSum.getText().toString())==0 && Integer.parseInt(edtKargaranSum.getText().toString())==0 || edtMohandesiSum.getText().toString().equals("")){
                    edtAverageEfficiencyKarkonaneTolidi.setText("0");
                }

                if(Integer.parseInt(edtSum.getText().toString()) != 0) {
                    edtAverageEfficiencyKarkonanSum.setText(String.valueOf(actualExtraction / Integer.parseInt(edtSum.getText().toString())));
                }else {
                    edtAverageEfficiencyKarkonanSum.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtEdariAmani.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtEdariAmani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtEdariSum.setText(String.valueOf(Integer.parseInt(edtEdariAmani.getText().toString()) + Integer.parseInt(edtEdariPeymani.getText().toString())));
                }
                else if(!edtEdariAmani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtEdariSum.setText(String.valueOf(Integer.parseInt(edtEdariAmani.getText().toString()) + 0));
                }else if(edtEdariAmani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtEdariSum.setText(String.valueOf(0 + Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(edtEdariAmani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtEdariSum.setText("0");
                }

                if(!edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + Integer.parseInt(edtKargaranAmani.getText().toString()) +  Integer.parseInt(edtEdariAmani.getText().toString())));
                }else if(edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranAmani.getText().toString()) +  Integer.parseInt(edtEdariAmani.getText().toString())));
                }else if(!edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + 0 +  Integer.parseInt(edtEdariAmani.getText().toString())));
                }else if(!edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + Integer.parseInt(edtKargaranAmani.getText().toString()) +  0));
                }
                else if(!edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiAmani.getText().toString()) + 0 +  0));
                }else if(edtMohandesiAmani.getText().toString().equals("") && !edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(Integer.parseInt(0 + edtKargaranAmani.getText().toString()) + 0));
                }else if(edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && !edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText(String.valueOf(0 + 0 + Integer.parseInt(edtEdariAmani.getText().toString())));
                }else if(edtMohandesiAmani.getText().toString().equals("") && edtKargaranAmani.getText().toString().equals("") && edtEdariAmani.getText().toString().equals("")){
                    edtAmaniSum.setText("0");
                }

                if(!edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals(""))
                edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + Integer.parseInt(edtPeymaniSum.getText().toString())));
                else if(edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText(String.valueOf(0 + Integer.parseInt(edtPeymaniSum.getText().toString())));
                }else if(!edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + 0));
                }else if(edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText("0");
                }

                if(Integer.parseInt(edtSum.getText().toString()) != 0) {
                    edtAverageEfficiencyKarkonanSum.setText(String.valueOf(actualExtraction / Integer.parseInt(edtSum.getText().toString())));
                }else {
                    edtAverageEfficiencyKarkonanSum.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtEdariPeymani.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!edtEdariAmani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtEdariSum.setText(String.valueOf(Integer.parseInt(edtEdariAmani.getText().toString()) + Integer.parseInt(edtEdariPeymani.getText().toString())));
                }
                else if(!edtEdariAmani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtEdariSum.setText(String.valueOf(Integer.parseInt(edtEdariAmani.getText().toString()) + 0));
                }else if(edtEdariAmani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtEdariSum.setText(String.valueOf(0 + Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(edtEdariAmani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtEdariSum.setText("0");
                }

                if(!edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + Integer.parseInt(edtKargaranPeymani.getText().toString()) +  Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranPeymani.getText().toString()) +  Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(!edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + 0 +  Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(!edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + Integer.parseInt(edtKargaranPeymani.getText().toString()) +  0));
                }
                else if(!edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(Integer.parseInt(edtMohandesiPeymani.getText().toString()) + 0 +  0));
                }else if(edtMohandesiPeymani.getText().toString().equals("") && !edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(0 + Integer.parseInt(edtKargaranPeymani.getText().toString()) + 0));
                }else if(edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && !edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText(String.valueOf(0 + 0 + Integer.parseInt(edtEdariPeymani.getText().toString())));
                }else if(edtMohandesiPeymani.getText().toString().equals("") && edtKargaranPeymani.getText().toString().equals("") && edtEdariPeymani.getText().toString().equals("")){
                    edtPeymaniSum.setText("0");
                }

                if(!edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals(""))
                edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + Integer.parseInt(edtPeymaniSum.getText().toString())));
                else if(edtAmaniSum.getText().toString().equals("") && !edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText(String.valueOf(0 + Integer.parseInt(edtPeymaniSum.getText().toString())));
                }else if(!edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText(String.valueOf(Integer.parseInt(edtAmaniSum.getText().toString()) + 0));
                }else if(edtAmaniSum.getText().toString().equals("") && edtPeymaniSum.getText().toString().equals("")){
                    edtSum.setText("0");
                }

                if(Integer.parseInt(edtSum.getText().toString()) != 0) {
                    edtAverageEfficiencyKarkonanSum.setText(String.valueOf(actualExtraction / Integer.parseInt(edtSum.getText().toString())));
                }else {
                    edtAverageEfficiencyKarkonanSum.setText("0");
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
                        if(!edtMohandesiAmani.getText().toString().equals(""))
                           jsonObject.put("mohandesiAmani",Integer.parseInt(edtMohandesiAmani.getText().toString()));
                        else {
                            jsonObject.put("mohandesiAmani",0);
                        }
                        if(!edtMohandesiPeymani.getText().toString().equals(""))
                        jsonObject.put("mohandesiPeymani",Integer.parseInt(edtMohandesiPeymani.getText().toString()));
                        else {
                            jsonObject.put("mohandesiPeymani",0);
                        }
                        if(!edtMohandesiSum.getText().toString().equals(""))
                            jsonObject.put("mohandesisum",Integer.parseInt(edtMohandesiSum.getText().toString()));
                        else {
                            jsonObject.put("mohandesisum",0);
                        }
                        if(!edtKargaranAmani.getText().toString().equals(""))
                            jsonObject.put("kargaranAmani",Integer.parseInt(edtKargaranAmani.getText().toString()));
                        else {
                            jsonObject.put("kargaranAmani",0);
                        }
                        if(!edtKargaranPeymani.getText().toString().equals(""))
                            jsonObject.put("kargaranPeymani",Integer.parseInt(edtKargaranPeymani.getText().toString()));
                        else {
                            jsonObject.put("kargaranPeymani",0);
                        }
                        if(!edtKargaranSum.getText().toString().equals(""))
                            jsonObject.put("kargaranSum",Integer.parseInt(edtKargaranSum.getText().toString()));
                        else {
                            jsonObject.put("kargaranSum",0);
                        }
                        if(!edtEdariAmani.getText().toString().equals(""))
                            jsonObject.put("edariAmani",Integer.parseInt(edtEdariAmani.getText().toString()));
                        else {
                            jsonObject.put("edariAmani",0);
                        }
                        if(!edtEdariPeymani.getText().toString().equals(""))
                            jsonObject.put("edariPeymani",Integer.parseInt(edtEdariPeymani.getText().toString()));
                        else {
                            jsonObject.put("edariPeymani",0);
                        }
                        if(!edtEdariSum.getText().toString().equals(""))
                            jsonObject.put("edariSum",Integer.parseInt(edtEdariSum.getText().toString()));
                        else {
                            jsonObject.put("edariSum",0);
                        }
                        if(!edtAmaniSum.getText().toString().equals(""))
                            jsonObject.put("amaniSum",Integer.parseInt(edtAmaniSum.getText().toString()));
                        else {
                            jsonObject.put("amaniSum",0);
                        }
                        if(!edtPeymaniSum.getText().toString().equals(""))
                            jsonObject.put("peymaniSum",Integer.parseInt(edtPeymaniSum.getText().toString()));
                        else {
                            jsonObject.put("peymaniSum",0);
                        }
                        if(!edtSum.getText().toString().equals(""))
                            jsonObject.put("totalSum",Integer.parseInt(edtSum.getText().toString()));
                        else {
                            jsonObject.put("totalSum",0);
                        }
                        if(!edtUnInsuredWorker.getText().toString().equals(""))
                            jsonObject.put("unInsuredWorker",Integer.parseInt(edtUnInsuredWorker.getText().toString()));
                        else {
                            jsonObject.put("unInsuredWorker",0);
                        }
                        if(!edtDoreAmoozeshiBimeWorker.getText().toString().equals(""))
                            jsonObject.put("doreAmoozeshiBimeWorker",Integer.parseInt(edtDoreAmoozeshiBimeWorker.getText().toString()));
                        else {
                            jsonObject.put("doreAmoozeshiBimeWorker",0);
                        }
                        if(!edtAverageEfficiencyKarkonaneTolidi.getText().toString().equals(""))
                            jsonObject.put("averageEfficiencyKarkonaneTolidi",Double.parseDouble(edtAverageEfficiencyKarkonaneTolidi.getText().toString()));
                        else {
                            jsonObject.put("averageEfficiencyKarkonaneTolidi",0);
                        }
                        if(!edtAverageEfficiencyKarkonanSum.getText().toString().equals(""))
                            jsonObject.put("averageEfficiencyKarkonanSum",Double.parseDouble(edtAverageEfficiencyKarkonanSum.getText().toString()));
                        else {
                            jsonObject.put("averageEfficiencyKarkonanSum",0);
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
                setManPowerInReport();

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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = Machinery.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                if(mineType.equals("pellekani") || mineType.equals("ekteshafi")){
                    intent.putExtra("button","btnForm10");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("zirzamini")){
                    intent.putExtra("button","btnForm9");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("enfejari")){
                    intent.putExtra("button","btnForm11");
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
            edtMohandesiAmani.setText(String.valueOf(reports.getMohandesiamani()));
            edtMohandesiPeymani.setText(String.valueOf(reports.getMohandesipeymani()));
            edtMohandesiSum.setText(String.valueOf(reports.getMohandesisum()));
            edtKargaranAmani.setText(String.valueOf(reports.getKargaranamani()));
            edtKargaranPeymani.setText(String.valueOf(reports.getKargaranpeymani()));
            edtKargaranSum.setText(String.valueOf(reports.getKargaransum()));
            edtEdariAmani.setText(String.valueOf(reports.getEdariamani()));
            edtEdariPeymani.setText(String.valueOf(reports.getEdaripeymani()));
            edtEdariSum.setText(String.valueOf(reports.getEdarisum()));
            edtAmaniSum.setText(String.valueOf(reports.getAmanisum()));
            edtPeymaniSum.setText(String.valueOf(reports.getPeymanisum()));
            edtSum.setText(String.valueOf(reports.getTotalsum()));
            edtUnInsuredWorker.setText(String.valueOf(reports.getUninsuredworker()));
            edtDoreAmoozeshiBimeWorker.setText(String.valueOf(reports.getDoreamoozeshibimeworker()));
            edtAverageEfficiencyKarkonaneTolidi.setText(String.valueOf(reports.getAverageefficiencykarkonanetolidi()));
            edtAverageEfficiencyKarkonanSum.setText(String.valueOf(reports.getAverageefficiencykarkonansum()));
        }
    }
    public Boolean setActualExtraction(){
        ProducesellLocalServiceUtil producesellLocalServiceUtil = new ProducesellLocalServiceUtil(getActivity());
        List<Producesell> producesell = producesellLocalServiceUtil.getProducesellByReportId(repId);
        if(producesell.size()>0){
            int i = 0;        // Create a separate integer to serve as your array indexer.
            while(i < producesell.size()) {   // The indexer needs to be less than 10, not A itself.
                actualExtraction += producesell.get(i).getActualextraction();   // either sum = sum + ... or sum += ..., but not both
                i++;           // You need to increment the index at the end of the loop.
            }
            return true;
        }
        return false;
    }
    public void setManPowerInReport() {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        if (report != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("reportId", repId);
                jsonObject.put("setPeople", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
        }
    }
}