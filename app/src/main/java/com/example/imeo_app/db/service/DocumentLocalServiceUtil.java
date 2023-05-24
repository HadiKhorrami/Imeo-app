package com.example.imeo_app.db.service;

import android.content.Context;

import com.example.imeo_app.db.tables.Document;

import java.util.List;

public class DocumentLocalServiceUtil {

    private DocumentLocalService documentLocalService;

    public DocumentLocalServiceUtil(Context context) {
        documentLocalService = new DocumentLocalService(context, com.example.imeo_app.db.helper.ImeoDBHelperConfig.class);
    }

    public Document insertDocument(Document user) {
        return documentLocalService.createDocument(user);
    }

    public Document updateDocument(Document document) {
        return documentLocalService.updateDocument(document);
    }

    public Document getDocumentById(long documentId) {
        return documentLocalService.getDocumentById(documentId);
    }

    public List<Document> getDocument() {

        return documentLocalService.getDocument();
    }

    public List<Document> getDocumentByReportId(long reportId) {
        return documentLocalService.getDocumentByReportId(reportId);
    }

    public List<Document> getDocumentByMineralMaterialId(long mineId) {
        return documentLocalService.getDocumentByReportId(mineId);
    }

    public boolean deleteDocument(long userId) {
     return documentLocalService.deleteDocument(userId);
   }

}
