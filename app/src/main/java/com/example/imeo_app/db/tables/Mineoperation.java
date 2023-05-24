package com.example.imeo_app.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "mineoperation")
public class Mineoperation {
    @DatabaseField(id = true)
    private long mineoperationid;

    @DatabaseField
    private long mineid;

    @DatabaseField
    private long reportid;

    @DatabaseField
    private int operationtype;

    @DatabaseField
    private int dimension;

    @DatabaseField
    private int volume;

    @DatabaseField
    private int meterage;

    @DatabaseField
    private int cost;

    public long getMineoperationid() {
        return mineoperationid;
    }

    public void setMineoperationid(long mineoperationid) {
        this.mineoperationid = mineoperationid;
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

    public int getOperationtype() {
        return operationtype;
    }

    public void setOperationtype(int operationtype) {
        this.operationtype = operationtype;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getMeterage() {
        return meterage;
    }

    public void setMeterage(int meterage) {
        this.meterage = meterage;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
