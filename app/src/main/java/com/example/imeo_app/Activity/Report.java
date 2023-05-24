package com.example.imeo_app.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imeo_app.Fragment.AMineOperation1;
import com.example.imeo_app.Fragment.BMineOperation1;
import com.example.imeo_app.Fragment.CMineOperation1;
import com.example.imeo_app.Fragment.CommentOffer;
import com.example.imeo_app.Fragment.Documents;
import com.example.imeo_app.Fragment.ExpertOpinion;
import com.example.imeo_app.Fragment.ExplorationMineInfo;
import com.example.imeo_app.Fragment.ExplorationMineralMaterial;
import com.example.imeo_app.Fragment.GeometryChahak;
import com.example.imeo_app.Fragment.GeometryMineralDeposit;
import com.example.imeo_app.Fragment.GeometrySineKar;
import com.example.imeo_app.Fragment.GeometryTrench;
import com.example.imeo_app.Fragment.GeometryTunnel;
import com.example.imeo_app.Fragment.GeometryWaste;
import com.example.imeo_app.Fragment.GeometryJebheKar;
import com.example.imeo_app.Fragment.Machinery;
import com.example.imeo_app.Fragment.ManPower;
import com.example.imeo_app.Fragment.MineCondition;
import com.example.imeo_app.Fragment.MineLocation;
import com.example.imeo_app.Fragment.ExtractiveMineInfo;
import com.example.imeo_app.Fragment.ExtractiveMineralMaterial;
import com.example.imeo_app.Fragment.MineOperation2;
import com.example.imeo_app.Fragment.MineSafetyTechnical;
import com.example.imeo_app.Fragment.Problems;
import com.example.imeo_app.Fragment.ProductionSalesStatistics;
import com.example.imeo_app.Fragment.UseEnergyWater;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.DocumentLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Report extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnMain,btnForm1,btnForm2,btnForm3,btnForm4,btnForm5,btnForm6,btnForm7,btnForm8,btnForm9,btnForm10,btnForm11,btnForm12,btnForm13,btnForm14,btnForm15,btnForm16,btnForm17,btnForm18;
    TextView textView5,textView6,textView7,textView8,textView9,textView10,textView11,textView12,textView13,textView14,textView15,textView16,textView17,textView18;
    String mineType;
    ImageView imageView7,imageView8,imageView9,imageView10,imageView11,imageView12,imageView13,imageView14,imageView15,imageView16,imageView17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);
        textView5 = findViewById(R.id.textView5);textView6 = findViewById(R.id.textView6);textView7 = findViewById(R.id.textView7);textView8 = findViewById(R.id.textView8);textView9 = findViewById(R.id.textView9);textView10 = findViewById(R.id.textView10);textView11 = findViewById(R.id.textView11);textView12 = findViewById(R.id.textView12);textView13 = findViewById(R.id.textView13);textView14 = findViewById(R.id.textView14);textView15 = findViewById(R.id.textView15);textView16 = findViewById(R.id.textView16);textView17 = findViewById(R.id.textView17);textView18 = findViewById(R.id.textView18);

        btnMain = findViewById(R.id.btnMain);btnForm1 = findViewById(R.id.btnForm1);btnForm2 = findViewById(R.id.btnForm2);btnForm3 = findViewById(R.id.btnForm3);btnForm4 = findViewById(R.id.btnForm4);btnForm5 = findViewById(R.id.btnForm5);btnForm6 = findViewById(R.id.btnForm6);btnForm7 = findViewById(R.id.btnForm7);btnForm8 = findViewById(R.id.btnForm8);btnForm9 = findViewById(R.id.btnForm9);btnForm10 = findViewById(R.id.btnForm10);btnForm11 = findViewById(R.id.btnForm11);btnForm12 = findViewById(R.id.btnForm12);btnForm13 = findViewById(R.id.btnForm13);btnForm14 = findViewById(R.id.btnForm14);btnForm15 = findViewById(R.id.btnForm15);btnForm16 = findViewById(R.id.btnForm16);btnForm17 = findViewById(R.id.btnForm17);btnForm18 = findViewById(R.id.btnForm18);

        imageView7 = findViewById(R.id.imageView7);imageView8 = findViewById(R.id.imageView8);imageView9 = findViewById(R.id.imageView9);imageView10 = findViewById(R.id.imageView10);imageView11 = findViewById(R.id.imageView11);imageView12 = findViewById(R.id.imageView12);imageView13 = findViewById(R.id.imageView13);imageView14 = findViewById(R.id.imageView14);imageView15 = findViewById(R.id.imageView15);imageView16 = findViewById(R.id.imageView16);imageView17 = findViewById(R.id.imageView17);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        long reportId = intent.getLongExtra("reportId",0);
        String reportDate = intent.getStringExtra("reportDate");
        int mineStage = intent.getIntExtra("mineStage",0);
        long mineId = intent.getLongExtra("mineId",0);
        String mineInfo = intent.getStringExtra("mineInfo");
        String extractionMethod = intent.getStringExtra("extractionMethod");
        String from = intent.getStringExtra("from");

        ReportLocalServiceUtil reportLocalServiceUtil1 = new ReportLocalServiceUtil(Report.this);
        com.example.imeo_app.db.tables.Report report1 = reportLocalServiceUtil1.getReportById(reportId);
        SharedPreferences pref = Report.this.getSharedPreferences("repId", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("reportId", reportId);
        editor.putInt("status", report1.getStatus());
        editor.commit();

        SharedPreferences shared = Report.this.getSharedPreferences("repId", MODE_PRIVATE);
        long repId = shared.getLong("reportId", 0);

        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(Report.this);
        com.example.imeo_app.db.tables.Report report = reportLocalServiceUtil.getReportById(repId);

        Fragment selectedFragment = MineCondition.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
        Bundle bundle = new Bundle();
        bundle.putLong("reportId", reportId);
        bundle.putString("reportDate", reportDate);
        bundle.putLong("mineId", mineId);
        bundle.putInt("mineStage", mineStage);
        bundle.putString("mineInfo", mineInfo);
        bundle.putString("extractionMethod", extractionMethod);
        selectedFragment.setArguments(bundle);

        SharedPreferences pref1 = Report.this.getSharedPreferences("mineType", 0);
        SharedPreferences.Editor editor1 = pref1.edit();
        if(mineStage == 2 && extractionMethod.equals("روباز پله کانی") && report.getMineactive() == 1 || extractionMethod.equals("روباز انفجاری") && report.getMineactive() == 1){
            textView5.setText("مختصات جبهه کار فعال");
            textView6.setText("مختصات محل دپوی ماده معدنی");
            textView7.setText("مختصات محل دپوی باطله");
            textView8.setText("آمار تولید و فروش");
            textView9.setText("آمار عملیات معدن 1");
            if(mineStage == 2 && extractionMethod.equals("روباز انفجاری")){
                mineType = "enfejari";
                editor1.putString("mineType", mineType);
                editor1.commit();
                textView10.setText("آمار عملیات معدن 2");
                textView11.setText("مشخصات ماشین آلات");
                textView12.setText("نیروی انسانی");
                textView13.setText("مصرف انرژی و آب");
                textView14.setText("اصول ایمنی و فنی");
                textView15.setText("مشکلات و موانع موجود");
                textView16.setText("پیشنهادات و اظهار نظر کلی");
                textView17.setText("مستندات گزارش");
                textView18.setText("نظر نهایی کارشناس");
            }
            if(mineStage == 2 && extractionMethod.equals("روباز پله کانی")){
                mineType = "pellekani";
                editor1.putString("mineType", mineType);
                editor1.commit();
                textView10.setText("مشخصات ماشین آلات");
                textView11.setText("نیروی انسانی");
                textView12.setText("مصرف انرژی و آب");
                textView13.setText("اصول ایمنی و فنی");
                textView14.setText("مشکلات و موانع موجود");
                textView15.setText("پیشنهادات و اظهار نظر کلی");
                textView16.setText("مستندات گزارش");
                textView17.setText("نظر نهایی کارشناس");

                imageView17.setVisibility(View.GONE);
                btnForm18.setVisibility(View.GONE);
                textView18.setVisibility(View.GONE);
            }
        }else if(mineStage == 2 && extractionMethod.equals("روباز پله کانی") || extractionMethod.equals("روباز انفجاری") && report.getMineactive() == 2){
            mineType = "enfejari";
            editor1.putString("mineType", mineType);
            editor1.commit();
            
            textView5.setText("مشکلات و موانع موجود");
            textView6.setText("پیشنهادات و اظهارنظر کلی");
            textView7.setText("نظر نهایی کارشناس");
            imageView7.setVisibility(View.GONE);
            btnForm8.setVisibility(View.GONE);
            textView8.setVisibility(View.GONE);
            imageView8.setVisibility(View.GONE);
            btnForm9.setVisibility(View.GONE);
            textView9.setVisibility(View.GONE);
            imageView9.setVisibility(View.GONE);
            btnForm10.setVisibility(View.GONE);
            textView10.setVisibility(View.GONE);
            imageView10.setVisibility(View.GONE);
            btnForm11.setVisibility(View.GONE);
            textView11.setVisibility(View.GONE);
            imageView11.setVisibility(View.GONE);
            btnForm12.setVisibility(View.GONE);
            textView12.setVisibility(View.GONE);
            imageView12.setVisibility(View.GONE);
            btnForm13.setVisibility(View.GONE);
            textView13.setVisibility(View.GONE);
            imageView13.setVisibility(View.GONE);
            btnForm14.setVisibility(View.GONE);
            textView14.setVisibility(View.GONE);
            imageView14.setVisibility(View.GONE);
            btnForm15.setVisibility(View.GONE);
            textView15.setVisibility(View.GONE);
            imageView15.setVisibility(View.GONE);
            btnForm16.setVisibility(View.GONE);
            textView16.setVisibility(View.GONE);
            imageView16.setVisibility(View.GONE);
            btnForm17.setVisibility(View.GONE);
            textView17.setVisibility(View.GONE);
            imageView17.setVisibility(View.GONE);
            btnForm18.setVisibility(View.GONE);
            textView18.setVisibility(View.GONE);
         } else if(mineStage == 2 && extractionMethod.equals("زیرزمینی") && report.getMineactive() == 1){
            mineType = "zirzamini";
            editor1.putString("mineType", mineType);
            editor1.commit();
            textView5.setText("مختصات دهانه تونل");
            textView6.setText("آمار تولید و فروش");
            textView7.setText("آمار عملیات معدن 1");
            textView8.setText("آمار عملیات معدن 2");
            textView9.setText("مشخصات ماشین آلات");
            textView10.setText("نیروی انسانی");
            textView11.setText("مصرف انرژی و آب");
            textView12.setText("اصول ایمنی و فنی");
            textView13.setText("مشکلات و موانع موجود");
            textView14.setText("پیشنهادات و اظهار نظر کلی");
            textView15.setText("مستندات گزارش");
            textView16.setText("نظر نهایی کارشناس");

            imageView16.setVisibility(View.GONE);
            btnForm17.setVisibility(View.GONE);
            textView17.setVisibility(View.GONE);
            imageView17.setVisibility(View.GONE);
            btnForm18.setVisibility(View.GONE);
            textView18.setVisibility(View.GONE);
        }else if(mineStage == 2 && extractionMethod.equals("زیرزمینی") && report.getMineactive() == 2){
            mineType = "zirzamini";
            editor1.putString("mineType", mineType);
            editor1.commit();
            
            textView5.setText("مشکلات و موانع موجود");
            textView6.setText("پیشنهادات و اظهارنظر کلی");
            textView7.setText("نظر نهایی کارشناس");
            imageView7.setVisibility(View.GONE);
            btnForm8.setVisibility(View.GONE);
            textView8.setVisibility(View.GONE);
            imageView8.setVisibility(View.GONE);
            btnForm9.setVisibility(View.GONE);
            textView9.setVisibility(View.GONE);
            imageView9.setVisibility(View.GONE);
            btnForm10.setVisibility(View.GONE);
            textView10.setVisibility(View.GONE);
            imageView10.setVisibility(View.GONE);
            btnForm11.setVisibility(View.GONE);
            textView11.setVisibility(View.GONE);
            imageView11.setVisibility(View.GONE);
            btnForm12.setVisibility(View.GONE);
            textView12.setVisibility(View.GONE);
            imageView12.setVisibility(View.GONE);
            btnForm13.setVisibility(View.GONE);
            textView13.setVisibility(View.GONE);
            imageView13.setVisibility(View.GONE);
            btnForm14.setVisibility(View.GONE);
            textView14.setVisibility(View.GONE);
            imageView14.setVisibility(View.GONE);
            btnForm15.setVisibility(View.GONE);
            textView15.setVisibility(View.GONE);
            imageView15.setVisibility(View.GONE);
            btnForm16.setVisibility(View.GONE);
            textView16.setVisibility(View.GONE);
            imageView16.setVisibility(View.GONE);
            btnForm17.setVisibility(View.GONE);
            textView17.setVisibility(View.GONE);
            imageView17.setVisibility(View.GONE);
            btnForm18.setVisibility(View.GONE);
            textView18.setVisibility(View.GONE);
        } else
            if(mineStage == 1 && report.getMineactive() == 1){
            mineType = "ekteshafi";
            editor1.putString("mineType", mineType);
            editor1.commit();
            textView5.setText("مختصات محل سینه کار");
            textView6.setText("مختصات محل ترانشه");
            textView7.setText("مختصات محل چاهک");
            textView8.setText("آمار تولید و فروش");
            textView9.setText("آمار عملیات معدن 1");
            textView10.setText("مشخصات ماشین آلات");
            textView11.setText("نیروی انسانی");
            textView12.setText("مصرف انرژی و آب");
            textView13.setText("مشکلات و موانع موجود");
            textView14.setText("پیشنهادات و اظهار نظر کلی");
            textView15.setText("مستندات گزارش");
            textView16.setText("نظر نهایی کارشناس");

            imageView16.setVisibility(View.GONE);
            btnForm17.setVisibility(View.GONE);
            textView17.setVisibility(View.GONE);
            imageView17.setVisibility(View.GONE);
            btnForm18.setVisibility(View.GONE);
            textView18.setVisibility(View.GONE);
        }else if(mineStage == 1 && report.getMineactive() == 2){
            mineType = "ekteshafi";
            editor1.putString("mineType", mineType);
            editor1.commit();

            textView5.setText("مشکلات و موانع موجود");
            textView6.setText("پیشنهادات و اظهارنظر کلی");
            textView7.setText("نظر نهایی کارشناس");
            imageView7.setVisibility(View.GONE);
            btnForm8.setVisibility(View.GONE);
            textView8.setVisibility(View.GONE);
            imageView8.setVisibility(View.GONE);
            btnForm9.setVisibility(View.GONE);
            textView9.setVisibility(View.GONE);
            imageView9.setVisibility(View.GONE);
            btnForm10.setVisibility(View.GONE);
            textView10.setVisibility(View.GONE);
            imageView10.setVisibility(View.GONE);
            btnForm11.setVisibility(View.GONE);
            textView11.setVisibility(View.GONE);
            imageView11.setVisibility(View.GONE);
            btnForm12.setVisibility(View.GONE);
            textView12.setVisibility(View.GONE);
            imageView12.setVisibility(View.GONE);
            btnForm13.setVisibility(View.GONE);
            textView13.setVisibility(View.GONE);
            imageView13.setVisibility(View.GONE);
            btnForm14.setVisibility(View.GONE);
            textView14.setVisibility(View.GONE);
            imageView14.setVisibility(View.GONE);
            btnForm15.setVisibility(View.GONE);
            textView15.setVisibility(View.GONE);
            imageView15.setVisibility(View.GONE);
            btnForm16.setVisibility(View.GONE);
            textView16.setVisibility(View.GONE);
            imageView16.setVisibility(View.GONE);
            btnForm17.setVisibility(View.GONE);
            textView17.setVisibility(View.GONE);
            imageView17.setVisibility(View.GONE);
            btnForm18.setVisibility(View.GONE);
            textView18.setVisibility(View.GONE);
        }

        if(from.equals("MineCondition") && report.getMineactive()==1){
            System.out.println(mineType);
            if(mineType.equals("enfejari") || mineType.equals("pellekani")){
                Fragment fragment = GeometryJebheKar.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                Bundle bundle1 = new Bundle();
                bundle1.putString("mineInfo", mineInfo);
                bundle1.putInt("mineStage", mineStage);
                fragment.setArguments(bundle1);
            }else if(mineType.equals("zirzamini")){
                Fragment fragment = GeometryTunnel.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                Bundle bundle1 = new Bundle();
                bundle1.putString("mineInfo", mineInfo);
                bundle1.putInt("mineStage", mineStage);
                fragment.setArguments(bundle1);
            }else if(mineType.equals("ekteshafi")){
                Fragment fragment = GeometrySineKar.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                Bundle bundle1 = new Bundle();
                bundle1.putString("mineInfo", mineInfo);
                bundle1.putInt("mineStage", mineStage);
                fragment.setArguments(bundle1);
            }
            btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
            btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
        }else if(from.equals("MineCondition") && report.getMineactive()==2){
            Fragment fragment = Problems.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
            Bundle bundle1 = new Bundle();
            bundle1.putString("mineInfo", mineInfo);
            bundle1.putInt("mineActive", 2);
            bundle.putLong("mineId", mineId);
            bundle.putInt("mineStage", mineStage);
            bundle.putString("extractionMethod", extractionMethod);
            fragment.setArguments(bundle1);
            btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
            btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
        }

        btnForm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = MineLocation.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putString("mineInfo", mineInfo);
                selectedFragment.setArguments(bundle);
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineStage==1){
                    Fragment selectedFragment = ExplorationMineInfo.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("mineInfo", mineInfo);
                    selectedFragment.setArguments(bundle);
                }else if(mineStage==2){
                    Fragment selectedFragment = ExtractiveMineInfo.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("mineInfo", mineInfo);
                    selectedFragment.setArguments(bundle);
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineStage==1){
                    Fragment selectedFragment = ExplorationMineralMaterial.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putLong("mineId", mineId);
                    bundle.putInt("mineStage", mineStage);
                    selectedFragment.setArguments(bundle);
                }else if(mineStage==2){
                    Fragment selectedFragment = ExtractiveMineralMaterial.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putLong("mineId", mineId);
                    bundle.putInt("mineStage", mineStage);
                    selectedFragment.setArguments(bundle);
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = MineCondition.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putLong("reportId", reportId);
                bundle.putString("reportDate", reportDate);
                bundle.putLong("mineId", mineId);
                bundle.putInt("mineStage", mineStage);
                bundle.putInt("mineType", mineStage);
                bundle.putString("mineInfo", mineInfo);
                bundle.putString("extractionMethod", extractionMethod);
                selectedFragment.setArguments(bundle);
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }

        });
        btnForm5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(report.getMineactive() == 1) {
                    if (mineType.equals("enfejari") || mineType.equals("pellekani")) {
                        Fragment selectedFragment = GeometryJebheKar.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("mineInfo", mineInfo);
                        selectedFragment.setArguments(bundle);
                    } else if (mineType.equals("zirzamini")) {
                        Fragment selectedFragment = GeometryTunnel.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("mineInfo", mineInfo);
                        selectedFragment.setArguments(bundle);
                    } else if (mineType.equals("ekteshafi")) {
                        Fragment selectedFragment = GeometrySineKar.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("mineInfo", mineInfo);
                        selectedFragment.setArguments(bundle);
                    }
                }else {
                    Fragment selectedFragment = Problems.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("mineInfo", mineInfo);
                    bundle1.putInt("mineActive", 2);
                    bundle1.putLong("mineId", mineId);
                    bundle1.putInt("mineStage", mineStage);
                    bundle1.putString("extractionMethod", extractionMethod);
                    selectedFragment.setArguments(bundle1);
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(report.getMineactive() == 1) {
                    if (mineType.equals("enfejari") || mineType.equals("pellekani")) {
                        Fragment selectedFragment = GeometryMineralDeposit.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("mineInfo", mineInfo);
                        selectedFragment.setArguments(bundle);
                    } else if (mineType.equals("zirzamini")) {
                        Fragment selectedFragment = ProductionSalesStatistics.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("mineInfo", mineInfo);
                        selectedFragment.setArguments(bundle1);
                    } else if (mineType.equals("ekteshafi")) {
                        Fragment selectedFragment = GeometryTrench.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("mineInfo", mineInfo);
                        selectedFragment.setArguments(bundle);
                    }
                }
                else {
                    Fragment selectedFragment = CommentOffer.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (report.getMineactive() == 1) {
                    if (mineType.equals("enfejari") || mineType.equals("pellekani")) {
                        Fragment selectedFragment = GeometryWaste.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("mineInfo", mineInfo);
                        selectedFragment.setArguments(bundle);
                    } else if (mineType.equals("zirzamini")) {
                        Fragment selectedFragment = BMineOperation1.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    } else if (mineType.equals("ekteshafi")) {
                        Fragment selectedFragment = GeometryChahak.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        Bundle bundle = new Bundle();
                        bundle.putString("mineInfo", mineInfo);
                        selectedFragment.setArguments(bundle);
                    }
                    btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                    btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                    btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                }
            }
        });
        btnForm8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari") || mineType.equals("pellekani")){
                    Fragment selectedFragment = ProductionSalesStatistics.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("mineInfo", mineInfo);
                    selectedFragment.setArguments(bundle1);
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = MineOperation2.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = ProductionSalesStatistics.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("mineInfo", mineInfo);
                    selectedFragment.setArguments(bundle1);
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari") || mineType.equals("pellekani")){
                    Fragment selectedFragment = AMineOperation1.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = Machinery.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = CMineOperation1.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari")){
                    Fragment selectedFragment = MineOperation2.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("pellekani")){
                    Fragment selectedFragment = Machinery.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = ManPower.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = Machinery.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari")){
                    Fragment selectedFragment = Machinery.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("pellekani")){
                    Fragment selectedFragment = ManPower.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = UseEnergyWater.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = ManPower.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari")){
                    Fragment selectedFragment = ManPower.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("pellekani")){
                    Fragment selectedFragment = UseEnergyWater.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = MineSafetyTechnical.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = UseEnergyWater.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari")){
                    Fragment selectedFragment = UseEnergyWater.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("pellekani")){
                    Fragment selectedFragment = MineSafetyTechnical.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = Problems.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("mineInfo", mineInfo);
                    bundle1.putInt("mineActive", 2);
                    bundle1.putLong("mineId", mineId);
                    bundle1.putInt("mineStage", mineStage);
                    bundle1.putString("extractionMethod", extractionMethod);
                    selectedFragment.setArguments(bundle1);
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = Problems.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("mineInfo", mineInfo);
                    bundle1.putInt("mineActive", 2);
                    bundle1.putLong("mineId", mineId);
                    bundle1.putInt("mineStage", mineStage);
                    bundle1.putString("extractionMethod", extractionMethod);
                    selectedFragment.setArguments(bundle1);
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari")){
                    Fragment selectedFragment = MineSafetyTechnical.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("pellekani")){
                    Fragment selectedFragment = Problems.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("mineInfo", mineInfo);
                    bundle1.putInt("mineActive", 2);
                    bundle1.putLong("mineId", mineId);
                    bundle1.putInt("mineStage", mineStage);
                    bundle1.putString("extractionMethod", extractionMethod);
                    selectedFragment.setArguments(bundle1);
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = CommentOffer.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("mineActive", report1.getMineactive());
                    selectedFragment.setArguments(bundle1);
                }else if(mineType.equals("ekteshafi")){
                    Fragment selectedFragment = Documents.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari")){
                    Fragment selectedFragment = Problems.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("mineInfo", mineInfo);
                    bundle1.putInt("mineActive", 2);
                    bundle1.putLong("mineId", mineId);
                    bundle1.putInt("mineStage", mineStage);
                    bundle1.putString("extractionMethod", extractionMethod);
                    selectedFragment.setArguments(bundle1);
                }else if(mineType.equals("pellekani")){
                    Fragment selectedFragment = CommentOffer.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("mineActive", report1.getMineactive());
                    selectedFragment.setArguments(bundle1);
                }else if(mineType.equals("zirzamini")){
                    Fragment selectedFragment = Documents.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("ekteshafi")){
//                    Fragment selectedFragment = ExpertOpinion.newInstance();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));

            }
        });
        btnForm16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari")){
                    Fragment selectedFragment = CommentOffer.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("mineActive", report1.getMineactive());
                    selectedFragment.setArguments(bundle1);
                }else if(mineType.equals("pellekani")){
                    Fragment selectedFragment = Documents.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("zirzamini")){
//                    Fragment selectedFragment = ExpertOpinion.newInstance();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        });
        btnForm17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mineType.equals("enfejari")){
                    Fragment selectedFragment = Documents.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }else if(mineType.equals("pellekani")){
//                    Fragment selectedFragment = ExpertOpinion.newInstance();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                }
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));

            }

        });
//        btnForm18.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(mineType.equals("enfejari")){
//                    Fragment selectedFragment = ExpertOpinion.newInstance();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
//                }
//                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
//                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
//            }
//        });

        LocalBroadcastManager.getInstance(Report.this).registerReceiver(mMessageReceiver, new IntentFilter("setColor"));
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void onReceive(Context context, Intent intent) {
            String button = intent.getStringExtra("button");
            if(button.equals("btnForm1")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm2")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm3")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm4")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm5")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm6")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm7")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm8")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm9")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm10")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm11")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm12")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm13")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm14")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm15")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
            if(button.equals("btnForm16")){
                btnForm1.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm2.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm3.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm4.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm5.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm6.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm7.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm8.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm9.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm10.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm11.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm12.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm13.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm14.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm15.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm16.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.add_button));
                btnForm17.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
                btnForm18.setBackgroundDrawable(ContextCompat.getDrawable(Report.this, R.drawable.all_projects_button));
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Report.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

    }
}