package com.example.imeo_app.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imeo_app.Adapters.ReportRecyclerAdapter;
import com.example.imeo_app.DataModels.ReportLayout;
import com.example.imeo_app.R;
import com.example.imeo_app.db.service.MineLocalServiceUtil;
import com.example.imeo_app.db.service.ReportLocalServiceUtil;
import com.example.imeo_app.db.tables.Mine;
import com.example.imeo_app.db.tables.Report;

import java.util.ArrayList;
import java.util.List;

public class  Reports extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ReportLayout> reportLayoutArrayList;
    ReportRecyclerAdapter reportRecyclerAdapter;
    EditText edtSearch;
    public  Reports() {
        // Required empty public constructor
    }

    public static  Reports newInstance(String param1, String param2) {

        return null;
    }

    public static Fragment newInstance() {
         Reports reports = new  Reports();
        return reports;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        edtSearch = view.findViewById(R.id.edtSearch);
        reportLayoutArrayList = new ArrayList<>();
        getAllReports();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int k, int i1, int i2) {
                MineLocalServiceUtil mineLocalServiceUtil = new MineLocalServiceUtil(getActivity());
                if (edtSearch.getText().toString().length() > 0) {
                    reportLayoutArrayList.clear();
                    reportRecyclerAdapter = new ReportRecyclerAdapter(getActivity(), reportLayoutArrayList);
                    recyclerView.setAdapter(reportRecyclerAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));

                    ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
                    List<Report> reports = reportLocalServiceUtil.getReport();

                    for (int i = 0; i < reports.size(); i++) {
                        if (reports.size() > 0 && !reports.get(i).getMinename().equals("") && String.valueOf(reports.get(i).getMinename()).startsWith(charSequence.toString())) {
                            ReportLayout reportLayout = new ReportLayout();
                            Mine mines = mineLocalServiceUtil.getMineById(reports.get(i).getMineid());
                            reportLayout.setReportid(reports.get(i).getReportid());
                            reportLayout.setReportdate(reports.get(i).getReportdate());
                            reportLayout.setMinename(reports.get(i).getMinename());
                            reportLayout.setMineid(reports.get(i).getMineid());
                            if(mines != null){
                                reportLayout.setMinestage(mines.getMinestage());
                                reportLayout.setExtractionmethod(mines.getExtractionmethod());
                            }
                            reportLayout.setStatus(reports.get(i).getStatus());
                            reportLayout.setExplorelicensename(reports.get(i).getExplorelicensename());
                            reportLayout.setCreatedate(reports.get(i).getCreatedate());
                            reportLayoutArrayList.add(reportLayout);
                            reportRecyclerAdapter = new ReportRecyclerAdapter(getActivity(), reportLayoutArrayList);
                            recyclerView.setAdapter(reportRecyclerAdapter);
                            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
                        }
                    }
                } else {
                    reportLayoutArrayList.clear();
                    getAllReports();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return view;
    }
    private void getAllReports() {
        ReportLocalServiceUtil reportLocalServiceUtil = new ReportLocalServiceUtil(getActivity());
        List<Report> reports = reportLocalServiceUtil.getReport();

        MineLocalServiceUtil mineLocalServiceUtil = new MineLocalServiceUtil(getActivity());

        if (reports.size() > 0) {
            for (int i = 0; i < reports.size(); i++) {
                ReportLayout reportLayout = new ReportLayout();
                Mine mines = mineLocalServiceUtil.getMineById(reports.get(i).getMineid());
                reportLayout.setReportid(reports.get(i).getReportid());
                reportLayout.setReportdate(reports.get(i).getReportdate());
                reportLayout.setMinename(reports.get(i).getMinename());
                reportLayout.setMineid(reports.get(i).getMineid());
                if(mines != null){
                    reportLayout.setMinestage(mines.getMinestage());
                    reportLayout.setExtractionmethod(mines.getExtractionmethod());
                }
                reportLayout.setStatus(reports.get(i).getStatus());
                reportLayout.setExplorelicensename(reports.get(i).getExplorelicensename());
                reportLayout.setPersiancreatedate(reports.get(i).getPersiancreatedate());
                reportLayoutArrayList.add(reportLayout);
                reportRecyclerAdapter = new ReportRecyclerAdapter(getActivity(), reportLayoutArrayList);
                recyclerView.setAdapter(reportRecyclerAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false));
            }
        }
    }

}