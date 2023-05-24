package com.example.imeo_app.DataModels;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class ReportLayout {
    private long reportid;
    private Date createdate;
    private String persiancreatedate;
    private int status;
    private String reportdate;
    private String explorelicensename;
    private String minename;
    private long mineid;
    private int minestage;
    private String extractionmethod;

    public long getReportid() {
        return reportid;
    }

    public void setReportid(long reportid) {
        this.reportid = reportid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getPersiancreatedate() {
        return persiancreatedate;
    }

    public void setPersiancreatedate(String persiancreatedate) {
        this.persiancreatedate = persiancreatedate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getExplorelicensename() {
        return explorelicensename;
    }

    public void setExplorelicensename(String explorelicensename) {
        this.explorelicensename = explorelicensename;
    }

    public String getMinename() {
        return minename;
    }

    public void setMinename(String minename) {
        this.minename = minename;
    }

    public long getMineid() {
        return mineid;
    }

    public void setMineid(long mineid) {
        this.mineid = mineid;
    }

    public int getMinestage() {
        return minestage;
    }

    public void setMinestage(int minestage) {
        this.minestage = minestage;
    }

    public String getExtractionmethod() {
        return extractionmethod;
    }

    public void setExtractionmethod(String extractionmethod) {
        this.extractionmethod = extractionmethod;
    }
}

