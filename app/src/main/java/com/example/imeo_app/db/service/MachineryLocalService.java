package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.helper.MachineryHelperConfig;
import com.example.imeo_app.db.tables.Machinery;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

class MachineryLocalService {

    private final Logger logger = LoggerFactory.getLogger(MachineryLocalService.class);
    private Dao<Machinery, Long> machineryDao;

    MachineryLocalService(Context context, Class<? extends OrmLiteSqliteOpenHelper> clz) {

        MachineryHelperConfig dbHelperConfig = (MachineryHelperConfig) OpenHelperManager.getHelper(context, clz);
        machineryDao = dbHelperConfig.getMachineryDao();
    }

    Machinery createMachinery(Machinery machinery) {
        try {
            machineryDao.create(machinery);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return machinery;
    }

    Machinery updateMachinery(Machinery machinery) {
        try {
            machineryDao.update(machinery);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return machinery;
    }

    List<Machinery> getMachinery() {
        try {
            return machineryDao.queryForAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Machinery> getMachineryByReportId(long reportId) {
        try {
            return machineryDao.queryForEq("reportid",reportId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    Machinery getMachineryById(long machineryId) {
        try {
            return machineryDao.queryForId(machineryId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    boolean deleteMachinery(long userId) {
        try {
            machineryDao.deleteById(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
