package com.example.imeo_app.db.helper;

import com.example.imeo_app.db.tables.Machinery;
import com.example.imeo_app.db.tables.Minefront;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public interface MinefrontHelperConfig {

    Dao<Minefront, Long> getMinefrontDao();

    RuntimeExceptionDao<Minefront, Long> getMinefrontRuntimeExceptionDao();
}
