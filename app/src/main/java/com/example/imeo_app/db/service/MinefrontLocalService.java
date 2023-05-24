package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.helper.MinefrontHelperConfig;
import com.example.imeo_app.db.tables.Minefront;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

class MinefrontLocalService {

    private final Logger logger = LoggerFactory.getLogger(MinefrontLocalService.class);
    private Dao<Minefront, Long>  mineFrontDao;

    MinefrontLocalService(Context context, Class<? extends OrmLiteSqliteOpenHelper> clz) {

        MinefrontHelperConfig dbHelperConfig = (MinefrontHelperConfig) OpenHelperManager.getHelper(context, clz);
         mineFrontDao = dbHelperConfig.getMinefrontDao();
    }

    Minefront createMinefront(Minefront  mineFront) {
        try {
             mineFrontDao.create( mineFront);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return  mineFront;
    }

    Minefront updateMinefront(Minefront  mineFront) {
        try {
             mineFrontDao.update( mineFront);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return  mineFront;
    }

    List<Minefront> getMinefront() {
        try {
            return  mineFrontDao.queryForAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Minefront> getMinefrontByReportId(long reportId) {
        try {
            return  mineFrontDao.queryForEq( "reportid", reportId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    Minefront getMinefrontById(long  mineFrontId) {
        try {
            return  mineFrontDao.queryForId( mineFrontId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    boolean deleteMinefront(long userId) {
        try {
             mineFrontDao.deleteById(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
