package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.helper.MemberShipHelperConfig;
import com.example.imeo_app.db.helper.MineHelperConfig;
import com.example.imeo_app.db.tables.Membership;
import com.example.imeo_app.db.tables.Mine;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

class MineLocalService {

    private final Logger logger = LoggerFactory.getLogger(MineLocalService.class);
    private Dao<Mine, Long> mineDao;

    MineLocalService(Context context, Class<? extends OrmLiteSqliteOpenHelper> clz) {

        MineHelperConfig dbHelperConfig = (MineHelperConfig) OpenHelperManager.getHelper(context, clz);
        mineDao = dbHelperConfig.getMineDao();
    }

    Mine createMine(Mine mine) {
        try {
            mineDao.create(mine);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return mine;
    }

    Mine updateMine(Mine mine) {
        try {
            mineDao.update(mine);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return mine;
    }

    List<Mine> getMine() {
        try {
            return mineDao.queryForAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Mine> getMineByCode(String minecode,long mineCode) {
        try {
            return mineDao.queryForEq(minecode,mineCode);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    Mine getMineById(long mineId) {
        try {
            return mineDao.queryForId(mineId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    boolean deleteMine(long userId) {
        try {
            mineDao.deleteById(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
