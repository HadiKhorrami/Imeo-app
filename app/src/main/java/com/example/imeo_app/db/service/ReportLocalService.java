package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.helper.ReportHelperConfig;
import com.example.imeo_app.db.tables.Report;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

class ReportLocalService {

    private final Logger logger = LoggerFactory.getLogger(ReportLocalService.class);
    private Dao<Report, Long> reportDao;

    ReportLocalService(Context context, Class<? extends OrmLiteSqliteOpenHelper> clz) {

        ReportHelperConfig dbHelperConfig = (ReportHelperConfig) OpenHelperManager.getHelper(context, clz);
        reportDao = dbHelperConfig.getReportDao();
    }

    Report createReport(Report report) {
        try {
            reportDao.create(report);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return report;
    }

    Report updateReport(Report report) {
        try {
            reportDao.update(report);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return report;
    }

    List<Report> getReport() {
        try {
            return reportDao.queryForAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Report> getReportByMineId(String mineId,long id) {
        try {
            return reportDao.queryForEq(mineId,id);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Report> getReportByReportDate(String reportDate,String date) {
        try {
            return reportDao.queryForEq(reportDate,date);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Report> getReportByMineIdAndReportDate(Map<String, Object> fields) {
        try {
            return reportDao.queryForFieldValuesArgs(fields);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    Report getReportById(long reportId) {
        try {
            return reportDao.queryForId(reportId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    boolean deleteReport(long userId) {
        try {
            reportDao.deleteById(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
