package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.tables.Report;

import java.util.List;
import java.util.Map;

public class ReportLocalServiceUtil {

    private ReportLocalService reportLocalService;

    public ReportLocalServiceUtil(Context context) {
        reportLocalService = new ReportLocalService(context, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);
    }

    public Report insertReport(Report user) {
        return reportLocalService.createReport(user);
    }

    public Report updateReport(Report report) {
        return reportLocalService.updateReport(report);
    }

    public Report getReportById(long reportId) {
        return reportLocalService.getReportById(reportId);
    }

    public List<Report> getReport() {
        return reportLocalService.getReport();
    }

    public List<Report> getReportByMineId(String mineId,long id) {
        return reportLocalService.getReportByMineId(mineId,id);
    }

    public List<Report> getReportByReportDate(String reportDate,String date) {
        return reportLocalService.getReportByReportDate(reportDate,date);
    }

    public List<Report> getReportByMineIdAndReportDate(Map<String, Object> fields) {
        return reportLocalService.getReportByMineIdAndReportDate(fields);
    }

    public boolean deleteReport(long userId) {
     return reportLocalService.deleteReport(userId);
   }

}
