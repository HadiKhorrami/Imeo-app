package com.example.imeo_app.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "document")
public class Document {
    @DatabaseField(id = true)
    private long documentid;

    @DatabaseField
    private long mineid;

    @DatabaseField
    private long reportid;

    @DatabaseField
    private String filename;

    @DatabaseField
    private String base64;

    public long getDocumentid() {
        return documentid;
    }

    public void setDocumentid(long documentid) {
        this.documentid = documentid;
    }

    public long getMineid() {
        return mineid;
    }

    public void setMineid(long mineid) {
        this.mineid = mineid;
    }

    public long getReportid() {
        return reportid;
    }

    public void setReportid(long reportid) {
        this.reportid = reportid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

}
