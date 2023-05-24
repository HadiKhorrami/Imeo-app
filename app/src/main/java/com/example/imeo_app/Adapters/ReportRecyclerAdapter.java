package com.example.imeo_app.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imeo_app.Activity.Report;
import com.example.imeo_app.DataModels.ReportLayout;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MineLocalServiceUtil;
import com.example.imeo_app.db.tables.Mine;
import com.example.imeo_app.db.util.JsonInsertUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class ReportRecyclerAdapter extends RecyclerView.Adapter<ReportRecyclerAdapter.ReportViewHolder> {
    ArrayList<ReportLayout> reportLayoutsArrayList;
    RecyclerView recyclerView;
    Dialog dialog;
    JSONObject mineInfo;
    JSONArray jsonArray;
    private Context context;
    private int lastPosition = -1;
    private int maxYear = 0;
    private int maxMonth = 0;

    public ReportRecyclerAdapter(Context context, ArrayList<ReportLayout> arrayList) {
        reportLayoutsArrayList = new ArrayList<ReportLayout>();
        reportLayoutsArrayList = arrayList;
    }


    @Override
    public int getItemViewType(int position) {
        return position % 2 * 2;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_layout, parent, false);
        context = parent.getContext();
        return new ReportViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ReportViewHolder holder, final int position) {
        mineInfo = new JSONObject();
        final ReportLayout dataModel = reportLayoutsArrayList.get(position);
        if(!dataModel.getMinename().equals("")){
            holder.txtMineName.setText(dataModel.getMinename());
        }else {
            holder.txtMineName.setText("");
        }

        if(!dataModel.getExplorelicensename().equals("")){
            holder.txtExploreLicenseName.setText(dataModel.getExplorelicensename());
        }else {
            holder.txtExploreLicenseName.setText("");
        }

        if(dataModel.getStatus()==0){
            holder.txtStatus.setText("پیش نویس");
        }else if(dataModel.getStatus()==1){
            holder.txtStatus.setText("تایید شده");
        }else if(dataModel.getStatus()==2){
            holder.txtStatus.setText("رد شده");
        }

        holder.txtReportDate.setText(dataModel.getReportdate());
        holder.txtCreateDate.setText(String.valueOf(dataModel.getPersiancreatedate()));
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineLocalServiceUtil mineLocalServiceUtil = new MineLocalServiceUtil(context);
                Mine mines = mineLocalServiceUtil.getMineById(dataModel.getMineid());
                if(mines != null){
                    try {
                        mineInfo.put("mineId",mines.getMineid());
                        mineInfo.put("mineCode",mines.getMinecode());
                        mineInfo.put("mineName",mines.getMinename());
                        mineInfo.put("mineType",mines.getMinetype());
                        mineInfo.put("mineStage",mines.getMinestage());
                        mineInfo.put("cadastreId",mines.getCadastreid());
                        mineInfo.put("nationalCode",mines.getNationalcode());
                        mineInfo.put("beneficiaryName",mines.getBeneficiaryname());
                        mineInfo.put("beneficiaryAddress",mines.getBeneficiaryaddress());
                        mineInfo.put("beneficiaryPhone",mines.getBeneficiaryphone());
                        mineInfo.put("mineAddress",mines.getMineaddress());
                        mineInfo.put("minePhone",mines.getMinephone());
                        mineInfo.put("supervisorMembershipCode",mines.getSupervisormembershipcode());
                        mineInfo.put("exploreLicenseName",mines.getExplorelicensename());
                        mineInfo.put("exploreLicenseNumber",mines.getExplorelicensenumber());
                        mineInfo.put("exploreLicenseDate",mines.getExplorelicensedate());
                        mineInfo.put("exploreLicenseExpireDate",mines.getMinename());
                        mineInfo.put("mineralMaterial",mines.getMineralmaterial());
                        mineInfo.put("physicalProgressPercent",mines.getPhysicalprogresspercent());
                        mineInfo.put("closestCity",mines.getClosestcity());
                        mineInfo.put("closestVillage",mines.getClosestvillage());
                        mineInfo.put("extractionCapacity",mines.getExtractioncapacity());
                        mineInfo.put("extractionMethod",mines.getExtractionmethod());
                        mineInfo.put("licenseNumber",mines.getLicensenumber());
                        mineInfo.put("licenseType",mines.getLicensetype());
                        mineInfo.put("licenseExpireDate",mines.getLicenseexpiredate());
                        mineInfo.put("sazmanMembershipCode",mines.getSazmanmembershipcode());
                        mineInfo.put("sazmanMembershipName",mines.getSazmanmembershipname());
                        mineInfo.put("wkt",mines.getWkt());
                        mineInfo.put("wktCenter",mines.getWktcenter());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                Intent intent = new Intent(context, Report.class);
                intent.putExtra("reportDate", dataModel.getReportdate());
                intent.putExtra("reportId", dataModel.getReportid());
                intent.putExtra("mineInfo", String.valueOf(mineInfo));
                intent.putExtra("mineId",dataModel.getMineid());
                intent.putExtra("mineStage",dataModel.getMinestage());
                intent.putExtra("extractionMethod",dataModel.getExtractionmethod());
                intent.putExtra("from","");
                context.startActivity(intent);
            }
        });
        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder infoBuilder = new AlertDialog.Builder(context);
                infoBuilder.setMessage("میخواهید گزارش را تایید کنید؟")
                        .setPositiveButton("بله", (dialog, id) -> setMineConfirm(holder,dataModel)).
                        setNegativeButton("خیر", (dialog, id) -> infoBuilder.create().dismiss());
                infoBuilder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportLayoutsArrayList.size();
    }


    public class ReportViewHolder extends RecyclerView.ViewHolder {
        public AppCompatButton btnEdit,btnPreview,btnConfirm;
        TextView txtMineName,txtExploreLicenseName,txtStatus,txtReportDate,txtCreateDate;
        public int id;
        public ReportViewHolder(View itemView) {
            super(itemView);
            txtMineName = (TextView)itemView.findViewById(R.id.txtMineName);
            txtExploreLicenseName = (TextView)itemView.findViewById(R.id.txtExploreLicenseName);
            txtStatus = (TextView)itemView.findViewById(R.id.txtStatus);
            txtReportDate = (TextView)itemView.findViewById(R.id.txtReportDate);
            txtCreateDate = (TextView)itemView.findViewById(R.id.txtCreateDate);
            btnEdit = (AppCompatButton)itemView.findViewById(R.id.btnEdit);
            btnPreview = (AppCompatButton)itemView.findViewById(R.id.btnPreview);
            btnConfirm = (AppCompatButton)itemView.findViewById(R.id.btnConfirm);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void setMineConfirm(ReportViewHolder holder, ReportLayout dataModel){
        PersianDate pdate = new PersianDate();
        PersianDateFormat pdformater = new PersianDateFormat("Y/m/d");
        jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("reportId",dataModel.getReportid());
            jsonObject.put("status",1);
            jsonObject.put("statusDate",pdformater.format(pdate));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        System.out.println(jsonArray);
        JsonInsertUtil.insertReportsFromJSON(jsonArray,context);
        holder.txtStatus.setText("تایید شده");
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansWeb.ttf");
        SpannableString efr = new SpannableString("تایید شد");
        efr.setSpan(new TypefaceSpan(font), 0, efr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Toast.makeText(context, efr, Toast.LENGTH_SHORT).show();
    }

}
