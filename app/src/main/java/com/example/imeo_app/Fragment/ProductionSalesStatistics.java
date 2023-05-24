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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MineralmaterialLocalServiceUtil;
import com.example.imeo_app.db.service.ProducesellLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.example.imeo_app.db.tables.Producesell;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class ProductionSalesStatistics extends Fragment {

    EditText edtWorkDayInMonth,edtShiftInDay,edtWorkHourInShift,edtWorkShiftInMonth,edtWorkHourInMonth,
            edtWasteAmount,edtActualExtraction,edtMineralTransport,edtSalesValue,edtFixedPrice,edtWaybillSerial,edtOtherDetails;
    AppCompatButton btnSave,btnBack,btnAdd;
    ConstraintLayout mainLayout;
    Spinner spnMineralMaterial;
    long repId,mineId,mineralMaterialId;
    Boolean existedProduceSellId = false;
    String mineType,mineName,reporDate;
    Date statusDate;
    int mineActive,status;
    ConstraintLayout mmConstraintLayout;
    ArrayList<String> mineralMaterialsArray;
    ArrayList<String> produceSellMinerals;
    JSONArray mmJsonArray;
    JSONObject mmJsonObject;
    String[] names;
    String[] values;
    public ProductionSalesStatistics() {
    }

    public static ProductionSalesStatistics newInstance() {
        ProductionSalesStatistics fragment = new ProductionSalesStatistics();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_production_sales_statistics, container, false);
        mainLayout = view.findViewById(R.id.mainLayout);
        edtWorkDayInMonth = view.findViewById(R.id.edtWorkDayInMonth);
        edtShiftInDay = view.findViewById(R.id.edtShiftInDay);
        edtWorkHourInShift = view.findViewById(R.id.edtWorkHourInShift);
        edtWorkShiftInMonth = view.findViewById(R.id.edtWorkShiftInMonth);
        edtWorkHourInMonth = view.findViewById(R.id.edtWorkHourInMonth);
        edtWorkDayInMonth = view.findViewById(R.id.edtWorkDayInMonth);

        edtWasteAmount = view.findViewById(R.id.edtWasteAmount);
        edtActualExtraction = view.findViewById(R.id.edtActualExtraction);
        edtMineralTransport = view.findViewById(R.id.edtMineralTransport);
        edtSalesValue = view.findViewById(R.id.edtSalesValue);
        edtFixedPrice = view.findViewById(R.id.edtFixedPrice);
        edtWaybillSerial = view.findViewById(R.id.edtWaybillSerial);
        edtOtherDetails = view.findViewById(R.id.edtOtherDetails);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        mmConstraintLayout = view.findViewById(R.id.mmConstraintLayout);
        spnMineralMaterial = view.findViewById(R.id.spnMineralMaterial);
        mineralMaterialsArray = new ArrayList<>();
        mmJsonArray = new JSONArray();

        String mineInfo = getArguments().getString("mineInfo");

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
        mineName = report.getMinename();
        reporDate = report.getReportdate();
        mineActive = report.getMineactive();
        status = report.getStatus();
        statusDate = report.getStatusdate();

        SharedPreferences shared1 = getActivity().getSharedPreferences("mineType", MODE_PRIVATE);
        mineType = shared1.getString("mineType","");


//        for (int i = 0; i < mainLayout.getChildCount(); i++) {
//            View child = mainLayout.getChildAt(i);
//            child.setEnabled(false);
//        }

        checkReportExist();
        checkProduceExist();
//        getMineMineralMaterial();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                Random rn = new Random();
                long randomReportId = rn.nextLong() * -1;

                try {
                    System.out.println(existedProduceSellId);
                    if(existedProduceSellId==false){
                        mmJsonObject = new JSONObject();
                        mmJsonObject.put("produceSellId",randomReportId);
                        mmJsonObject.put("reportId",repId);
                        mmJsonObject.put("mineralMaterialId",mineralMaterialId);
                        mmJsonObject.put("mineId",mineId);
                    }

                    if(!edtWasteAmount.getText().toString().equals(""))
                        mmJsonObject.put("wasteAmount",edtWasteAmount.getText().toString());
                    else{
                        mmJsonObject.put("wasteAmount",0);
                    }

                    if(!edtActualExtraction.getText().toString().equals(""))
                        mmJsonObject.put("actualExtraction",edtActualExtraction.getText().toString());
                    else{
                        mmJsonObject.put("actualExtraction",0);
                    }

                    if(!edtMineralTransport.getText().toString().equals(""))
                        mmJsonObject.put("mineralTransport",edtMineralTransport.getText().toString());
                    else{
                        mmJsonObject.put("mineralTransport",0);
                    }

                    if(!edtSalesValue.getText().toString().equals(""))
                        mmJsonObject.put("salesValue",edtSalesValue.getText().toString());
                    else{
                        mmJsonObject.put("salesValue",0);
                    }

                    if(!edtFixedPrice.getText().toString().equals(""))
                        mmJsonObject.put("fixedPrice",edtFixedPrice.getText().toString());
                    else{
                        mmJsonObject.put("fixedPrice",0);
                    }

                    if(!edtWaybillSerial.getText().toString().equals(""))
                        mmJsonObject.put("waybillSerial",edtWaybillSerial.getText().toString());
                    else{
                        mmJsonObject.put("waybillSerial","");
                    }

                    if(!edtOtherDetails.getText().toString().equals(""))
                        mmJsonObject.put("otherDetails",edtOtherDetails.getText().toString());
                    else{
                        mmJsonObject.put("otherDetails","");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mmJsonArray.put(mmJsonObject);
                System.out.println(mmJsonArray);
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("اضافه شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                edtWasteAmount.setText("");
                edtActualExtraction.setText("");
                edtMineralTransport.setText("");
                edtSalesValue.setText("");
                edtFixedPrice.setText("");
                edtWaybillSerial.setText("");
                edtOtherDetails.setText("");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("reportId",repId);

                    if(!edtWorkDayInMonth.getText().toString().equals(""))
                    jsonObject.put("workDayInMonth",edtWorkDayInMonth.getText().toString());
                    else{
                        jsonObject.put("workDayInMonth","");
                    }
                    if(!edtShiftInDay.getText().toString().equals(""))
                    jsonObject.put("shiftInDay",edtShiftInDay.getText().toString());
                    else{
                        jsonObject.put("shiftInDay","");
                    }
                    if(!edtWorkHourInShift.getText().toString().equals(""))
                    jsonObject.put("workHourInShift",edtWorkHourInShift.getText().toString());
                    else{
                        jsonObject.put("workHourInShift","");
                    }
                    if(!edtWorkShiftInMonth.getText().toString().equals(""))
                    jsonObject.put("workShiftInMonth",edtWorkShiftInMonth.getText().toString());
                    else{
                        jsonObject.put("workShiftInMonth","");
                    }
                    if(!edtWorkHourInMonth.getText().toString().equals(""))
                    jsonObject.put("workHourInMonth",edtWorkHourInMonth.getText().toString());
                    else{
                        jsonObject.put("workHourInMonth","");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(jsonObject);
                System.out.println(jsonArray);
                System.out.println(mmJsonArray);
                JsonInsertUtil.insertReportsFromJSON(jsonArray,getActivity());
                JsonInsertUtil.insertProduceSellFromJSON(mmJsonArray,getActivity());
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("ثبت شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                edtWorkDayInMonth.setText("");
                edtShiftInDay.setText("");
                edtWorkHourInShift.setText("");
                edtWorkShiftInMonth.setText("");
                edtWorkHourInMonth.setText("");
                setProduceSellInReport();

                if(mineType.equals("pellekani") || mineType.equals("enfejari")){
                    Fragment selectedFragment = AMineOperation1.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm9");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = BMineOperation1.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm7");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = CMineOperation1.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm9");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("pellekani") || mineType.equals("enfejari")){
                    Fragment selectedFragment = GeometryWaste.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("mineInfo", mineInfo);
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm7");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = GeometryTunnel.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("mineInfo", mineInfo);
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm5");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = GeometryChahak.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("mineInfo", mineInfo);
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm7");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });
        edtShiftInDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtWorkDayInMonth.getText().toString().equals("") || edtShiftInDay.getText().toString().equals("")){
                    edtWorkShiftInMonth.setText("0");
                }else {
                    edtWorkShiftInMonth.setText(String.valueOf(Integer.parseInt(edtShiftInDay.getText().toString())*Integer.parseInt(edtWorkDayInMonth.getText().toString())));
                }

                if(edtWorkDayInMonth.getText().toString().equals("") || edtShiftInDay.getText().toString().equals("") || edtWorkHourInShift.getText().toString().equals("")){
                    edtWorkHourInMonth.setText("0");
                }else {
                    edtWorkHourInMonth.setText(String.valueOf(Integer.parseInt(edtShiftInDay.getText().toString())*Integer.parseInt(edtWorkDayInMonth.getText().toString())*Integer.parseInt(edtWorkHourInShift.getText().toString())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtWorkDayInMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtShiftInDay.getText().toString().equals("") || edtWorkDayInMonth.getText().toString().equals("")){
                    edtWorkShiftInMonth.setText("0");
                }else {
                    edtWorkShiftInMonth.setText(String.valueOf(Integer.parseInt(edtShiftInDay.getText().toString())*Integer.parseInt(edtWorkDayInMonth.getText().toString())));
                }

                if(edtWorkDayInMonth.getText().toString().equals("") || edtShiftInDay.getText().toString().equals("") || edtWorkHourInShift.getText().toString().equals("")){
                    edtWorkHourInMonth.setText("0");
                }else {
                    edtWorkHourInMonth.setText(String.valueOf(Integer.parseInt(edtShiftInDay.getText().toString())*Integer.parseInt(edtWorkDayInMonth.getText().toString())*Integer.parseInt(edtWorkHourInShift.getText().toString())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtWorkHourInShift.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(edtShiftInDay.getText().toString().equals("") || edtWorkDayInMonth.getText().toString().equals("") || edtWorkHourInShift.getText().toString().equals("")){
                    edtWorkHourInMonth.setText("0");
                }else {
                    edtWorkHourInMonth.setText(String.valueOf(Integer.parseInt(edtShiftInDay.getText().toString())*Integer.parseInt(edtWorkDayInMonth.getText().toString())*Integer.parseInt(edtWorkHourInShift.getText().toString())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    public void getMineMineralMaterial(){
        MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(getActivity());
        List<Mineralmaterial> mineralmaterial = mineralmaterialLocalServiceUtil.getMineralmaterialByMineId(mineId);
        if(mineralmaterial.size()==0){
            mmConstraintLayout.setVisibility(View.INVISIBLE);
        }else {
            for(int i = 0;i<mineralmaterial.size();i++){
                mineralMaterialsArray.add(mineralmaterial.get(i).getType_() + "-" + mineralmaterial.get(i).getMineralmaterialid());
            }
            String[] myArray = new String[mineralMaterialsArray.size()];
            mineralMaterialsArray.toArray(myArray);
            if(myArray != null && myArray.length > 0) {
                String[] names = new String[myArray.length];
                String[] values = new String[myArray.length];
                // Now we will parse the records and split them into name and value
                for(int i = 0; i < myArray.length; i++) {
                    String mineralMaterial = myArray[i];
                    if(mineralMaterial == null || mineralMaterial.isEmpty()) {
                        continue;
                    }
                    // Split the record by "," seperator for example for choice "Choice,1"
                    String[] nameValue = mineralMaterial.split("-");
                    if(nameValue.length < 2) {
                        continue;
                    }
                    names[i] = nameValue[0]; // first index will have the names
                    values[i] = nameValue[1]; // second will have its value
                }
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, names);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnMineralMaterial.setAdapter(adapter);
                spnMineralMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        mmJsonObject = new JSONObject();
                        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                        ((TextView)parent.getChildAt(0)).setTypeface(font);
                        long val = 0;
                        String name="";
                            val = Long.parseLong(values[position]); // Here you have value as numeric type
                            name = names[position]; // Here you have value as numeric type
                            mineralMaterialId = val; // Here you have value as numeric type
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }
    public void checkReportExist(){
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report reports = reportLocalServiceUtil.getReportById(repId);
        if(reports != null){
            edtWorkHourInMonth.setText(String.valueOf(reports.getWorkhourinmonth()));
            edtWorkDayInMonth.setText(String.valueOf(reports.getWorkdayinmonth()));
            edtShiftInDay.setText(String.valueOf(reports.getShiftinday()));
            edtWorkHourInShift.setText(String.valueOf(reports.getWorkhourinshift()));
            edtWorkShiftInMonth.setText(String.valueOf(reports.getWorkshiftinmonth()));
        }
    }
    public void checkProduceExist(){
        ProducesellLocalServiceUtil producesellLocalServiceUtil = new ProducesellLocalServiceUtil(getActivity());
        MineralmaterialLocalServiceUtil mineralmaterialLocalServiceUtil = new MineralmaterialLocalServiceUtil(getActivity());
        List<Producesell> producesell = producesellLocalServiceUtil.getProducesellByReportId(repId);
        if(producesell.size()>0){
            System.out.println("has");
            for(int i = 0; i<producesell.size(); i++){
                List<Mineralmaterial> mineralmaterials = mineralmaterialLocalServiceUtil.getMineralmaterialByMineralId(producesell.get(i).getMineralmaterialid());

                    if(!mineralmaterials.get(0).getType_().equals(""))
                        mineralMaterialsArray.add(mineralmaterials.get(0).getType_() + "-" + mineralmaterials.get(0).getMineralmaterialid());
                    else if(!mineralmaterials.get(0).getSheklekansar().equals("")){
                        mineralMaterialsArray.add(mineralmaterials.get(0).getSheklekansar() + "-" + mineralmaterials.get(0).getMineralmaterialid());
                    }

                String[] myArray = new String[mineralMaterialsArray.size()];
                mineralMaterialsArray.toArray(myArray);
                if(myArray != null && myArray.length > 0) {
                    names = new String[myArray.length];
                    values = new String[myArray.length];
                    // Now we will parse the records and split them into name and value
                    for(int j = 0; j < myArray.length; j++) {
                        String mineralMaterial = myArray[j];
                        if(mineralMaterial == null || mineralMaterial.isEmpty()) {
                            continue;
                        }
                        // Split the record by "," seperator for example for choice "Choice,1"
                        String[] nameValue = mineralMaterial.split("-");
                        if(nameValue.length < 2) {
                            continue;
                        }
                        names[j] = nameValue[0]; // first index will have the names
                        values[j] = nameValue[1]; // second will have its value
                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, names);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnMineralMaterial.setAdapter(adapter);
                    int finalI = i;

                }
            }
            spnMineralMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mmJsonObject = new JSONObject();
                    Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                    ((TextView) parent.getChildAt(0)).setTypeface(font);
                    long val = 0;
                    String name = "";
                    val = Long.parseLong(values[position]); // Here you have value as numeric type
                    name = names[position]; // Here you have value as numeric type
                    mineralMaterialId = val; // Here you have value as numeric type
                    mmJsonObject = new JSONObject();
                    List<Producesell> producesell = producesellLocalServiceUtil.getProducesellByReportId(repId);
                    if (producesell.size() > 0)
                    {
                        existedProduceSellId = true;
                        for (int j = 0; j < producesell.size(); j++) {
                            if (producesell.get(j).getMineralmaterialid() == val) {
                                System.out.println("yess");
                                System.out.println(producesell.get(j).getMineralmaterialid());
                                edtWasteAmount.setText(String.valueOf(producesell.get(j).getWasteamount()));
                                edtActualExtraction.setText(String.valueOf(producesell.get(j).getActualextraction()));
                                edtMineralTransport.setText(String.valueOf(producesell.get(j).getMineraltransport()));
                                edtSalesValue.setText(String.valueOf(producesell.get(j).getSalesvalue()));
                                edtFixedPrice.setText(String.valueOf(producesell.get(j).getFixedprice()));
                                edtWaybillSerial.setText(String.valueOf(producesell.get(j).getWaybillserial()));
                                edtOtherDetails.setText(String.valueOf(producesell.get(j).getOtherdetails()));
                                try {
                                    mmJsonObject.put("produceSellId", producesell.get(j).getProducesellid());
                                    mmJsonObject.put("reportId", producesell.get(j).getReportid());
                                    mmJsonObject.put("mineralMaterialId", producesell.get(j).getMineralmaterialid());
                                    mmJsonObject.put("mineId", producesell.get(j).getMineid());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(mmJsonObject);
                            }
                        }
                }
                    else {
                        existedProduceSellId = false;
                    }
//
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else {
            System.out.println("Nohas");
            getMineMineralMaterial();
        }
    }
    public void setProduceSellInReport() {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        if (report != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("reportId", repId);
                jsonObject.put("setProduceSell", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
        }
    }
}