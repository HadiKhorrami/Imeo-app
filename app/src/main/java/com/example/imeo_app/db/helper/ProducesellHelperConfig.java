package com.example.imeo_app.db.helper;

import com.example.imeo_app.db.tables.Machinery;
import com.example.imeo_app.db.tables.Producesell;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public interface ProducesellHelperConfig {

    Dao<Producesell, Long> getProducesellDao();

    RuntimeExceptionDao<Producesell, Long> getProducesellRuntimeExceptionDao();
}
