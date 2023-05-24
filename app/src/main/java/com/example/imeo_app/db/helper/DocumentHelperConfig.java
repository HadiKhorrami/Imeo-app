package com.example.imeo_app.db.helper;

import com.example.imeo_app.db.tables.Document;
import com.example.imeo_app.db.tables.Producesell;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public interface DocumentHelperConfig {

    Dao<Document, Long> getDocumentDao();

    RuntimeExceptionDao<Document, Long> getDocumentRuntimeExceptionDao();
}
