package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.helper.ProducesellHelperConfig;
import com.example.imeo_app.db.tables.Producesell;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

class ProducesellLocalService {

    private final Logger logger = LoggerFactory.getLogger(ProducesellLocalService.class);
    private Dao<Producesell, Long> produceSellDao;

    ProducesellLocalService(Context context, Class<? extends OrmLiteSqliteOpenHelper> clz) {

        ProducesellHelperConfig dbHelperConfig = (ProducesellHelperConfig) OpenHelperManager.getHelper(context, clz);
        produceSellDao = dbHelperConfig.getProducesellDao();
    }

    Producesell createProducesell(Producesell produceSell) {
        try {
            produceSellDao.create(produceSell);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return produceSell;
    }

    Producesell updateProducesell(Producesell produceSell) {
        try {
            produceSellDao.update(produceSell);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return produceSell;
    }

    List<Producesell> getProducesell() {
        try {
            return produceSellDao.queryForAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Producesell> getProducesellByReportId(long reportId) {
        try {
            return produceSellDao.queryForEq("reportId",reportId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Producesell> getProducesellByMineId(long mineId) {
        try {
            return produceSellDao.queryForEq("mineid",mineId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    Producesell getProducesellById(long produceSellId) {
        try {
            return produceSellDao.queryForId(produceSellId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    boolean deleteProducesell(long userId) {
        try {
            produceSellDao.deleteById(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
