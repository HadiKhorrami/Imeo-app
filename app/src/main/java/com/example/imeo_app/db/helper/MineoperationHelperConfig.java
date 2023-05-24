package com.example.imeo_app.db.helper;

import com.example.imeo_app.db.tables.Mine;
import com.example.imeo_app.db.tables.Mineoperation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public interface MineoperationHelperConfig {

    Dao<Mineoperation, Long> getMineoperationDao();

    RuntimeExceptionDao<Mineoperation, Long> getMineoperationRuntimeExceptionDao();
}
