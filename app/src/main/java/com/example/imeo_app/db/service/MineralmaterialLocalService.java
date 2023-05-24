package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.helper.MineralmaterialHelperConfig;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

class MineralmaterialLocalService {

    private final Logger logger = LoggerFactory.getLogger(MineralmaterialLocalService.class);
    private Dao<Mineralmaterial, Long> mineralMaterialDao;

    MineralmaterialLocalService(Context context, Class<? extends OrmLiteSqliteOpenHelper> clz) {

        MineralmaterialHelperConfig dbHelperConfig = (MineralmaterialHelperConfig) OpenHelperManager.getHelper(context, clz);
        mineralMaterialDao = dbHelperConfig.getMineralmaterialDao();
    }

    Mineralmaterial createMineralmaterial(Mineralmaterial mineralMaterial) {
        try {
            mineralMaterialDao.create(mineralMaterial);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return mineralMaterial;
    }

    Mineralmaterial updateMineralmaterial(Mineralmaterial mineralMaterial) {
        try {
            mineralMaterialDao.update(mineralMaterial);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return mineralMaterial;
    }

    List<Mineralmaterial> getMineralmaterial() {
        try {
            return mineralMaterialDao.queryForAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Mineralmaterial> getMineralmaterialByMineId(long mineId) {
        try {
            return mineralMaterialDao.queryForEq("mineid",mineId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Mineralmaterial> getMineralmaterialByMineralId(long mineralMaterialId) {
        try {
            return mineralMaterialDao.queryForEq("mineralmaterialid",mineralMaterialId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    Mineralmaterial getMineralmaterialById(long mineralMaterialId) {
        try {
            return mineralMaterialDao.queryForId(mineralMaterialId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    boolean deleteMineralmaterial(long userId) {
        try {
            mineralMaterialDao.deleteById(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
