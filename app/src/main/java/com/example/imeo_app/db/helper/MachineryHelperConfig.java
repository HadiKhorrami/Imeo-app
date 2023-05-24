package com.example.imeo_app.db.helper;

import com.example.imeo_app.db.tables.Machinery;
import com.example.imeo_app.db.tables.Mine;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public interface MachineryHelperConfig {

    Dao<Machinery, Long> getMachineryDao();

    RuntimeExceptionDao<Machinery, Long> getMachineryRuntimeExceptionDao();
}
