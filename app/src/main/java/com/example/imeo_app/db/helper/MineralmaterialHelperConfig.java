package com.example.imeo_app.db.helper;

import com.example.imeo_app.db.tables.Mine;
import com.example.imeo_app.db.tables.Mineralmaterial;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public interface MineralmaterialHelperConfig {

    Dao<Mineralmaterial, Long> getMineralmaterialDao();

    RuntimeExceptionDao<Mineralmaterial, Long> getMineralmaterialRuntimeExceptionDao();
}
