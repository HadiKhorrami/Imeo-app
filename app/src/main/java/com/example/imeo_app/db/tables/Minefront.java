package com.example.imeo_app.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "minefront")
public class Minefront {
    @DatabaseField(id = true)
    private long minefrontid;

    @DatabaseField
    private long mineid;

    @DatabaseField
    private long reportid;

    @DatabaseField
    private int stonetype;

    @DatabaseField
    private int workfrontwide;

    @DatabaseField
    private int workfrontheight;

    @DatabaseField
    private int workfrontlength;

    @DatabaseField
    private int workfrontslope;

    public long getMinefrontid() {
        return minefrontid;
    }

    public void setMinefrontid(long minefrontid) {
        this.minefrontid = minefrontid;
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

    public int getStonetype() {
        return stonetype;
    }

    public void setStonetype(int stonetype) {
        this.stonetype = stonetype;
    }

    public int getWorkfrontwide() {
        return workfrontwide;
    }

    public void setWorkfrontwide(int workfrontwide) {
        this.workfrontwide = workfrontwide;
    }

    public int getWorkfrontheight() {
        return workfrontheight;
    }

    public void setWorkfrontheight(int workfrontheight) {
        this.workfrontheight = workfrontheight;
    }

    public int getWorkfrontlength() {
        return workfrontlength;
    }

    public void setWorkfrontlength(int workfrontlength) {
        this.workfrontlength = workfrontlength;
    }

    public int getWorkfrontslope() {
        return workfrontslope;
    }

    public void setWorkfrontslope(int workfrontslope) {
        this.workfrontslope = workfrontslope;
    }
}
