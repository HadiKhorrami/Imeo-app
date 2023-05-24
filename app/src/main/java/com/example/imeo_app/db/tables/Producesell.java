package com.example.imeo_app.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "producesell")
public class Producesell {
    @DatabaseField(id = true)
    private long producesellid;

    @DatabaseField
    private long mineid;

    @DatabaseField
    private long reportid;

    @DatabaseField
    private long mineralmaterialid;

    @DatabaseField
    private int wasteamount;

    @DatabaseField
    private int actualextraction;

    @DatabaseField
    private int mineraltransport;

    @DatabaseField
    private int salesvalue;

    @DatabaseField
    private int fixedprice;

    @DatabaseField
    private String waybillserial;

    @DatabaseField
    private String otherdetails;

    public long getProducesellid() {
        return producesellid;
    }

    public void setProducesellid(long producesellid) {
        this.producesellid = producesellid;
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

    public long getMineralmaterialid() {
        return mineralmaterialid;
    }

    public void setMineralmaterialid(long mineralmaterialid) {
        this.mineralmaterialid = mineralmaterialid;
    }

    public int getWasteamount() {
        return wasteamount;
    }

    public void setWasteamount(int wasteamount) {
        this.wasteamount = wasteamount;
    }

    public int getActualextraction() {
        return actualextraction;
    }

    public void setActualextraction(int actualextraction) {
        this.actualextraction = actualextraction;
    }

    public int getMineraltransport() {
        return mineraltransport;
    }

    public void setMineraltransport(int mineraltransport) {
        this.mineraltransport = mineraltransport;
    }

    public int getSalesvalue() {
        return salesvalue;
    }

    public void setSalesvalue(int salesvalue) {
        this.salesvalue = salesvalue;
    }

    public int getFixedprice() {
        return fixedprice;
    }

    public void setFixedprice(int fixedprice) {
        this.fixedprice = fixedprice;
    }

    public String getWaybillserial() {
        return waybillserial;
    }

    public void setWaybillserial(String waybillserial) {
        this.waybillserial = waybillserial;
    }

    public String getOtherdetails() {
        return otherdetails;
    }

    public void setOtherdetails(String otherdetails) {
        this.otherdetails = otherdetails;
    }
}
