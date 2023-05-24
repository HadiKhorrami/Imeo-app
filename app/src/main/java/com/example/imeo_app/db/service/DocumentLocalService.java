package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.helper.DocumentHelperConfig;
import com.example.imeo_app.db.tables.Document;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

class DocumentLocalService {

    private final Logger logger = LoggerFactory.getLogger(DocumentLocalService.class);
    private Dao<Document, Long> documentDao;

    DocumentLocalService(Context context, Class<? extends OrmLiteSqliteOpenHelper> clz) {

        DocumentHelperConfig dbHelperConfig = (DocumentHelperConfig) OpenHelperManager.getHelper(context, clz);
        documentDao = dbHelperConfig.getDocumentDao();
    }

    Document createDocument(Document document) {
        try {
            documentDao.create(document);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return document;
    }

    Document updateDocument(Document document) {
        try {
            documentDao.update(document);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return document;
    }

    List<Document> getDocument() {
        try {
            return documentDao.queryForAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Document> getDocumentByReportId(long reportId) {
        try {
            return documentDao.queryForEq("reportId",reportId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    List<Document> getDocumentByMineId(long mineId) {
        try {
            return documentDao.queryForEq("mineid",mineId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    Document getDocumentById(long documentId) {
        try {
            return documentDao.queryForId(documentId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    boolean deleteDocument(long userId) {
        try {
            documentDao.deleteById(userId);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


}
