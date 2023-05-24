package com.example.imeo_app.db.helper;

import com.example.imeo_app.db.tables.Machinery;
import com.example.imeo_app.db.tables.Report;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public interface ReportHelperConfig {

    Dao<Report, Long> getReportDao();

    RuntimeExceptionDao<Report, Long> getReportRuntimeExceptionDao();
}
