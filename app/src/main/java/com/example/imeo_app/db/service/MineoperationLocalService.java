package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.helper.MineoperationHelperConfig;
import com.example.imeo_app.db.tables.Mineoperation;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

class MineoperationLocalService {

    private final Logger logger = LoggerFactory.getLogger(MineoperationLocalService.class);
    private Dao<Mineoperation, Long> mineOperationDao;

    MineoperationLocalService(Context context, Class<? extends OrmLiteSqliteOpenHelper> clz) {

        MineoperationHelperConfig dbHelperConfig = (MineoperationHelperConfig) OpenHelperManager.getHelper(context, clz);
        mineOperationDao = dbHelperConfig.getMineoperationDao();
    }

    Mineoperation createMineoperation(Mineoperation mineOperation) {
        try {
            mineOperationDao.create(mineOperation);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return mineOperation;
    }

    Mineoperation updateMineoperation(Mineoperation mineOperation) {
        try {
            mineOperationDao.update(mineOperation);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return mineOperation;
    }

    List<Mineoperation> getMineoperation() {
        try {
            return mineOperationDao.queryForAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Mineoperation> getMineoperationByReportId(long reportId) {
        try {
            return mineOperationDao.queryForEq("reportid",reportId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    Mineoperation getMineoperationById(long mineOperationId) {
        try {
            return mineOperationDao.queryForId(mineOperationId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    boolean deleteMineoperation(long userId) {
        try {
            mineOperationDao.deleteById(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
