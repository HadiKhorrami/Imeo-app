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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExtractiveMineInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExtractiveMineInfo extends Fragment {

    TextView txtName,txtBeneficiaryName,txtCadastreId,txtNationalCode,txtMineCode,txtLicenseNumber,txtSupervisorMembershipCode,
            txtLicenseExpireDate,txtBeneficiaryPhone,txtBeneficiaryAddress,txtMineAddress,txtMinePhone,txtLicenseType,txtMineType,txtMethod,txtExtractionCapacity;
    AppCompatButton btnSave,btnBack;

    public ExtractiveMineInfo() {
        // Required empty public constructor
    }

    public static ExtractiveMineInfo newInstance() {
        ExtractiveMineInfo fragment = new ExtractiveMineInfo();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extractive_mine_info, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtBeneficiaryName = view.findViewById(R.id.txtBeneficiaryName);
        txtCadastreId = view.findViewById(R.id.txtCadastreId);
        txtNationalCode = view.findViewById(R.id.txtNationalCode);
        txtMineCode = view.findViewById(R.id.txtSheklekansar);
        txtLicenseNumber = view.findViewById(R.id.txtLicenseNumber);
        txtSupervisorMembershipCode = view.findViewById(R.id.txtSupervisorMembershipCode);
        txtLicenseExpireDate = view.findViewById(R.id.txtLicenseExpireDate);
        txtBeneficiaryPhone = view.findViewById(R.id.txtBeneficiaryPhone);
        txtBeneficiaryAddress = view.findViewById(R.id.txtBeneficiaryAddress);
        txtMineAddress = view.findViewById(R.id.txtMineAddress);
        txtMinePhone = view.findViewById(R.id.txtMinePhone);
        txtLicenseType = view.findViewById(R.id.txtLicenseType);
        txtMineType = view.findViewById(R.id.txtMineType);
        txtMethod = view.findViewById(R.id.txtMethod);
        txtExtractionCapacity = view.findViewById(R.id.txtExtractionCapacity);
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
                Fragment selectedFragment = ExtractiveMineralMaterial.newInstance();
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

        System.out.println(mineInfo);
        try {
            JSONObject mineInfoJsonObject = new JSONObject(mineInfo);
            txtName.setText(mineInfoJsonObject.getString("mineName"));
            txtNationalCode.setText(mineInfoJsonObject.getString("nationalCode"));
            txtBeneficiaryName.setText(mineInfoJsonObject.getString("beneficiaryName"));
            txtCadastreId.setText(mineInfoJsonObject.getString("cadastreId"));
            txtMineCode.setText(mineInfoJsonObject.getString("mineCode"));
            txtLicenseNumber.setText(mineInfoJsonObject.getString("licenseNumber"));
            txtSupervisorMembershipCode.setText(mineInfoJsonObject.getString("supervisorMembershipCode"));
            txtLicenseExpireDate.setText(mineInfoJsonObject.getString("licenseExpireDate"));
            txtBeneficiaryPhone.setText(mineInfoJsonObject.getString("beneficiaryPhone"));
            txtBeneficiaryAddress.setText(mineInfoJsonObject.getString("beneficiaryAddress"));
            txtMineAddress.setText(mineInfoJsonObject.getString("mineAddress"));
            txtMinePhone.setText(mineInfoJsonObject.getString("minePhone"));
            txtLicenseType.setText(mineInfoJsonObject.getString("licenseType"));
            txtMineType.setText(mineInfoJsonObject.getString("mineType"));
            txtMethod.setText(mineInfoJsonObject.getString("extractionMethod"));
            txtExtractionCapacity.setText(mineInfoJsonObject.getString("extractionCapacity"));
            System.out.println(mineInfoJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}