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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeo_app.Adapters.AMineOperationRecyclerAdapter;
import com.example.imeo_app.Adapters.MachineryRecyclerAdapter;
import com.example.imeo_app.DataModels.AMineOperationLayout;
import com.example.imeo_app.DataModels.MachineryLayout;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MachineryLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Report;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Machinery extends Fragment {

    Spinner spnNameMashin,spnEquipment;
    AppCompatButton btnAdd,btnEdit,btnSave,btnBack;
    EditText edtModeleMashin,edtRoozeKari,edtMasrafeSookht;
    ArrayList<MachineryLayout> machineryLayoutArrayList;
    MachineryRecyclerAdapter machineryRecyclerAdapter;
    RecyclerView recyclerView;
    JSONArray jsonArray;
    long repId,mineId;
    int nameMashinCode,equipmentCode;
    String nameMashin,equipment;
    ConstraintLayout mainLayout;
    boolean hasEdit = true;
    public Machinery() {
    }

    public static Machinery newInstance() {
        Machinery fragment = new Machinery();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machinery, container, false);
        spnNameMashin = view.findViewById(R.id.spnNameMashin);
        spnEquipment = view.findViewById(R.id.spnEquipment);
        edtModeleMashin = view.findViewById(R.id.edtModeleMashin);
        edtRoozeKari = view.findViewById(R.id.edtRoozeKari);
        edtMasrafeSookht = view.findViewById(R.id.edtMasrafeSookht);
        recyclerView = view.findViewById(R.id.machineryRecyclerView);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        mainLayout = view.findViewById(R.id.mainLayout);
        machineryLayoutArrayList = new ArrayList<>();
        setSpinners();

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
        getReportMachinery();

        SharedPreferences shared1 = getActivity().getSharedPreferences("mineType", MODE_PRIVATE);
        String mineType = shared1.getString("mineType","");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
                public void onClick(View v) {
                    Random rn = new Random();
                    long randomMachineryId = rn.nextLong() * -1;

                    MachineryLayout machineryLayout = new MachineryLayout();
                    machineryLayout.setId(machineryLayoutArrayList.size());
                    machineryLayout.setMachineryid(randomMachineryId);
                    machineryLayout.setReportid(repId);
                    machineryLayout.setMineid(mineId);
                    machineryLayout.setNamemashinCode(nameMashinCode);
                    machineryLayout.setEquipmentCode(equipmentCode);
                    machineryLayout.setNamemashin(nameMashin);
                    machineryLayout.setEquipment(equipment);
                    if(!edtMasrafeSookht.getText().toString().equals(""))
                        machineryLayout.setMasrafesookht(Integer.parseInt(edtMasrafeSookht.getText().toString()));
                    else {
                        machineryLayout.setMasrafesookht(0);
                    }
                    if(!edtModeleMashin.getText().toString().equals(""))
                        machineryLayout.setModelemashin(edtModeleMashin.getText().toString());
                    else {
                        machineryLayout.setModelemashin("");
                    }
                    if(!edtRoozeKari.getText().toString().equals(""))
                        machineryLayout.setRuzekari(Integer.parseInt(edtRoozeKari.getText().toString()));
                    else {
                        machineryLayout.setRuzekari(0);
                    }

                    machineryLayoutArrayList.add(machineryLayout);
                    machineryRecyclerAdapter = new MachineryRecyclerAdapter(getActivity(), machineryLayoutArrayList);
                    recyclerView.setAdapter(machineryRecyclerAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
                    Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                    SpannableString efr = new SpannableString("اضافه شد");
                    efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                    edtMasrafeSookht.setText("");
                    edtModeleMashin.setText("");
                    edtRoozeKari.setText("");
                }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                jsonArray = new JSONArray();
                for(int i = 0;i<machineryLayoutArrayList.size();i++){
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("machineryId",machineryLayoutArrayList.get(i).getMachineryid());
                        jsonObject.put("reportId",machineryLayoutArrayList.get(i).getReportid());
                        jsonObject.put("mineId",machineryLayoutArrayList.get(i).getMineid());
                        jsonObject.put("equipmentCode",machineryLayoutArrayList.get(i).getEquipmentCode());
                        jsonObject.put("masrafeSookht",machineryLayoutArrayList.get(i).getMasrafesookht());
                        jsonObject.put("nameMashinCode",machineryLayoutArrayList.get(i).getNamemashinCode());
                        jsonObject.put("modeleMashin",machineryLayoutArrayList.get(i).getModelemashin());
                        jsonObject.put("ruzeKari",machineryLayoutArrayList.get(i).getRuzekari());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(jsonObject);
                }
                System.out.println(jsonArray);
                JsonInsertUtil.insertMachineryFromJSON(jsonArray,getActivity());
                Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                SpannableString efr = new SpannableString("ثبت شد");
                efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Toast.makeText(getActivity(), efr, Toast.LENGTH_SHORT).show();
                setMachineryInReport();

                Fragment selectedFragment = ManPower.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                selectedFragment.setArguments(bundle);
                Intent intent = new Intent("setColor");
                if(mineType.equals("pellekani") || mineType.equals("ekteshafi")){
                    intent.putExtra("button","btnForm11");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("zirzamini")){
                    intent.putExtra("button","btnForm10");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("enfejari")){
                    intent.putExtra("button","btnForm12");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }


            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                if(mineType.equals("zirzamini") || mineType.equals("enfejari")){
                    Fragment selectedFragment = MineOperation2.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm8");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else if(mineType.equals("pellekani")){
                    Fragment selectedFragment = AMineOperation1.newInstance();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    selectedFragment.setArguments(bundle);
                    Intent intent = new Intent("setColor");
                    intent.putExtra("button","btnForm9");
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(editReceiver, new IntentFilter("editMachinery"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(deleteReceiver, new IntentFilter("deleteMineFront"));
        return view;
    }
    public void setSpinners(){
        String[] nameMashinArray = getResources().getStringArray(R.array.spnNameMashin);
        if(nameMashinArray != null && nameMashinArray.length > 0) {
            String[] names = new String[nameMashinArray.length];
            String[] values = new String[nameMashinArray.length];
            // Now we will parse the records and split them into name and value
            for(int i = 0; i < nameMashinArray.length; i++) {
                    String nameMashin = nameMashinArray[i];
                    if (nameMashin == null || nameMashin.isEmpty() || nameMashin.equals("")) {
                        continue;
                    }
                    // Split the record by "," seperator for example for choice "Choice,1"
                    String[] nameValue = nameMashin.split("-");
                    if (nameValue.length < 2) {
                        continue;
                    }
                    names[i] = nameValue[0]; // first index will have the names
                    values[i] = nameValue[1]; // second will have its value
            }
//            Log.d(TAG, "onViewCreated: names and values: "+ Arrays.toString(names)+" - "+Arrays.toString(values));
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnNameMashin.setAdapter(adapter);
            spnNameMashin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                    ((TextView)parent.getChildAt(0)).setTypeface(font);
                    int val;
                    String name="";
                        val = Integer.parseInt(values[position]); // Here you have value as numeric type
                        name = names[position]; // Here you have value as numeric type
                        nameMashinCode = val;
                        nameMashin = name;
                        System.out.println(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else {}
        ////////////////////////////////////////////////////////////////////////////////////////
        String[] equipmentArray = getResources().getStringArray(R.array.spnEquipment);
        if(equipmentArray != null && equipmentArray.length > 0) {
            String[] names = new String[equipmentArray.length];
            String[] values = new String[equipmentArray.length];
            // Now we will parse the records and split them into name and value
            for(int i = 0; i < equipmentArray.length; i++) {
                    String equipment = equipmentArray[i];
                    if (equipment == null || equipment.isEmpty() || equipment.equals("")) {
                        continue;
                    }
                    // Split the record by "," seperator for example for choice "Choice,1"
                    String[] nameValue = equipment.split("-");
                    if (nameValue.length < 2) {
                        continue;
                    }
                    names[i] = nameValue[0]; // first index will have the names
                    values[i] = nameValue[1]; // second will have its value
            }
//            Log.d(TAG, "onViewCreated: names and values: "+ Arrays.toString(names)+" - "+Arrays.toString(values));
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, names);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnEquipment.setAdapter(adapter);
            spnEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansWeb.ttf");
                    ((TextView)parent.getChildAt(0)).setTypeface(font);
                    int val;
                    String name="";
                        val = Integer.parseInt(values[position]); // Here you have value as numeric type
                        name = names[position]; // Here you have value as numeric type
                        equipmentCode = val;
                        equipment = name;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else {}
    }
    public BroadcastReceiver editReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hasEdit = true;
            long machineryId = intent.getLongExtra("machineryId",0);
            for(int i = 0;i<machineryLayoutArrayList.size();i++){
                if(machineryId==machineryLayoutArrayList.get(i).getMachineryid()){
                    spnNameMashin.setSelection(machineryLayoutArrayList.get(i).getNamemashinCode()-1);
                    spnEquipment.setSelection(machineryLayoutArrayList.get(i).getEquipmentCode()-1);
                    edtModeleMashin.setText(String.valueOf(machineryLayoutArrayList.get(i).getModelemashin()));
                    edtRoozeKari.setText(String.valueOf(machineryLayoutArrayList.get(i).getRuzekari()));
                    edtMasrafeSookht.setText(String.valueOf(machineryLayoutArrayList.get(i).getMasrafesookht()));
                }
            }
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onClick(View v) {
                    if (hasEdit == true) {
                        for (int i = 0; i < machineryLayoutArrayList.size(); i++) {
                            if (machineryId == machineryLayoutArrayList.get(i).getMachineryid()) {
                                machineryLayoutArrayList.get(i).setNamemashinCode(nameMashinCode);
                                machineryLayoutArrayList.get(i).setNamemashin(nameMashin);
                                machineryLayoutArrayList.get(i).setEquipmentCode(equipmentCode);
                                machineryLayoutArrayList.get(i).setEquipment(equipment);
                                machineryLayoutArrayList.get(i).setModelemashin(edtModeleMashin.getText().toString());
                                machineryLayoutArrayList.get(i).setRuzekari(Integer.parseInt(edtRoozeKari.getText().toString()));
                                machineryLayoutArrayList.get(i).setMasrafesookht(Integer.parseInt(edtMasrafeSookht.getText().toString()));
                                machineryRecyclerAdapter = new MachineryRecyclerAdapter(getActivity(), machineryLayoutArrayList);
                                recyclerView.setAdapter(machineryRecyclerAdapter);
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
                            }
                        }
                        edtModeleMashin.setText("");
                        edtRoozeKari.setText("");
                        edtMasrafeSookht.setText("");
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
            long machineryId = intent.getLongExtra("machineryId",0);
            MachineryLocalServiceUtil machineryLocalServiceUtil = new MachineryLocalServiceUtil(context);
            machineryLocalServiceUtil.deleteMachinery(machineryId);
            for(int i = 0;i<machineryLayoutArrayList.size();i++){
                if(machineryId==machineryLayoutArrayList.get(i).getMachineryid()){
                    machineryLayoutArrayList.remove(i);
                }
            }
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansWeb.ttf");
            SpannableString efr = new SpannableString("حذف شد");
            efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            Toast.makeText(context, efr, Toast.LENGTH_SHORT).show();
        }
    };
    public void getReportMachinery(){
        machineryLayoutArrayList = new ArrayList<>();
        MachineryLocalServiceUtil machineryLocalServiceUtil = new MachineryLocalServiceUtil(getActivity());

        List<com.example.imeo_app.db.tables.Machinery> machinery = machineryLocalServiceUtil.getMachineryByReportId(repId);
        if(machinery.size()>0){
            for (int i = 0; i < machinery.size(); i++) {
                MachineryLayout machineryLayout = new MachineryLayout();
                machineryLayout.setId(i);
                machineryLayout.setMachineryid(machinery.get(i).getMachineryid());
                machineryLayout.setMineid(machinery.get(i).getMineid());
                machineryLayout.setReportid(machinery.get(i).getReportid());
                machineryLayout.setNamemashinCode(machinery.get(i).getNamemashin());
                if(machinery.get(i).getNamemashin()==1){machineryLayout.setNamemashin("بولدوزر");}
                if(machinery.get(i).getNamemashin()==2){machineryLayout.setNamemashin("بیل مکانیکی");}
                if(machinery.get(i).getNamemashin()==3){machineryLayout.setNamemashin("لودر");}
                if(machinery.get(i).getNamemashin()==4){machineryLayout.setNamemashin("کامیون");}
                if(machinery.get(i).getNamemashin()==5){machineryLayout.setNamemashin("تراکتور");}
                if(machinery.get(i).getNamemashin()==6){machineryLayout.setNamemashin("دام تراک");}
                if(machinery.get(i).getNamemashin()==7){machineryLayout.setNamemashin("دریل واگن");}
                if(machinery.get(i).getNamemashin()==8){machineryLayout.setNamemashin("راسل");}
                if(machinery.get(i).getNamemashin()==9){machineryLayout.setNamemashin("کمپرسور");}
                if(machinery.get(i).getNamemashin()==10){machineryLayout.setNamemashin("وانت");}
                if(machinery.get(i).getNamemashin()==11){machineryLayout.setNamemashin("سواری");}
                machineryLayout.setEquipmentCode(machinery.get(i).getEquipment());
                if(machinery.get(i).getEquipment()==1){machineryLayout.setEquipment("ژنراتور");}
                if(machinery.get(i).getEquipment()==2){machineryLayout.setEquipment("سیم برش");}
                if(machinery.get(i).getEquipment()==3){machineryLayout.setEquipment("پیکور دستی");}
                if(machinery.get(i).getEquipment()==4){machineryLayout.setEquipment("کابل");}
                machineryLayout.setModelemashin(machinery.get(i).getModelemashin());
                machineryLayout.setRuzekari(machinery.get(i).getRuzekari());
                machineryLayout.setMasrafesookht(machinery.get(i).getMasrafesookht());
                machineryLayoutArrayList.add(machineryLayout);
                machineryRecyclerAdapter = new MachineryRecyclerAdapter(getActivity(), machineryLayoutArrayList);
                recyclerView.setAdapter(machineryRecyclerAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
            }
        }
    }
    public void setMachineryInReport() {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        Report report = reportLocalServiceUtil.getReportById(repId);
        if (report != null) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("reportId", repId);
                jsonObject.put("setMachinery", true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
            JsonInsertUtil.insertReportsFromJSON(jsonArray, getActivity());
        }
    }
}