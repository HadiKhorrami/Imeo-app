package com.example.imeo_app.DataModels;

public class FilesLayout {
    private long documentid;
    private long reportid;
    private long mineid;
    private String id;
    public String name;
    public String base64;

    public long getDocumentid() {
        return documentid;
    }

    public void setDocumentid(long documentid) {
        this.documentid = documentid;
    }

    public long getReportid() {
        return reportid;
    }

    public void setReportid(long reportid) {
        this.reportid = reportid;
    }

    public long getMineid() {
        return mineid;
    }

    public void setMineid(long mineid) {
        this.mineid = mineid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getBase64() {
        return base64;
    }

}

