package com.example.imeo_app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imeo_app.R;

import org.json.JSONException;
import org.json.JSONObject;


public class ExplorationMineInfo extends Fragment {

    TextView txtLicenseNumber,txtCadastreId,txtExploreLicenseName,txtExploreLicenseNumber,txtExploreLicenseDate,txtExploreLicenseExpireDate,txtMineralMaterial,txtPhysicalProgressPercent,txtClosestCity,txtClosestVillage;
    AppCompatButton btnSave,btnBack;
    public ExplorationMineInfo() {
        // Required empty public constructor
    }

    public static ExplorationMineInfo newInstance() {
        ExplorationMineInfo fragment = new ExplorationMineInfo();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exploration_mine_info, container, false);
        txtLicenseNumber = view.findViewById(R.id.txtLicenseNumber);
        txtCadastreId = view.findViewById(R.id.txtCadastreId);
        txtExploreLicenseName = view.findViewById(R.id.txtSheklekansar);
        txtExploreLicenseNumber = view.findViewById(R.id.txtSupervisorMembershipCode);
        txtExploreLicenseDate = view.findViewById(R.id.txtLicenseExpireDate);
        txtExploreLicenseExpireDate = view.findViewById(R.id.txtBeneficiaryAddress);
        txtMineralMaterial = view.findViewById(R.id.txtBeneficiaryPhone);
        txtPhysicalProgressPercent = view.findViewById(R.id.txtMineAddress);
        txtClosestCity = view.findViewById(R.id.txtMinePhone);
        txtClosestVillage = view.findViewById(R.id.txtLicenseType);
        btnBack = view.findViewById(R.id.btnBack);
        btnSave = view.findViewById(R.id.btnSave);

        String mineInfo = getArguments().getString("mineInfo");
        long mineId = getArguments().getLong("mineId");
        int mineStage = getArguments().getInt("mineStage");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = MineLocation.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putString("mineInfo", mineInfo);
                bundle.putLong("mineId", mineId);
                bundle.putInt("mineStage", mineStage);
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm1");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = ExplorationMineralMaterial.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putString("mineInfo", mineInfo);
                bundle.putLong("mineId", mineId);
                bundle.putInt("mineStage", mineStage);
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                intent.putExtra("button","btnForm3");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });

        try {
            JSONObject mineInfoJsonObject = new JSONObject(mineInfo);
            txtLicenseNumber.setText(mineInfoJsonObject.getString("licenseNumber"));
            txtCadastreId.setText(mineInfoJsonObject.getString("cadastreId"));
            txtExploreLicenseName.setText(mineInfoJsonObject.getString("exploreLicenseName"));
            txtExploreLicenseNumber.setText(mineInfoJsonObject.getString("exploreLicenseNumber"));
            txtExploreLicenseDate.setText(mineInfoJsonObject.getString("exploreLicenseDate"));
            txtExploreLicenseExpireDate.setText(mineInfoJsonObject.getString("exploreLicenseExpireDate"));
            txtMineralMaterial.setText(mineInfoJsonObject.getString("mineralMaterial"));
            txtPhysicalProgressPercent.setText(mineInfoJsonObject.getString("physicalProgressPercent"));
            txtClosestCity.setText(mineInfoJsonObject.getString("closestCity"));
            txtClosestVillage.setText(mineInfoJsonObject.getString("closestVillage"));
            System.out.println(mineInfoJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}