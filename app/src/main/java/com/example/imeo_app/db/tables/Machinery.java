package com.example.imeo_app.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "machinery")
public class Machinery {
    @DatabaseField(id = true)
    private long machineryid;

    @DatabaseField
    private long reportid;

    @DatabaseField
    private long mineid;

    @DatabaseField
    private int namemashin;

    @DatabaseField
    private String modelemashin;

    @DatabaseField
    private int ruzekari;

    @DatabaseField
    private int masrafesookht;

    @DatabaseField
    private int equipment;

    public long getMachineryid() {
        return machineryid;
    }

    public void setMachineryid(long machineryid) {
        this.machineryid = machineryid;
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

    public int getNamemashin() {
        return namemashin;
    }

    public void setNamemashin(int namemashin) {
        this.namemashin = namemashin;
    }

    public String getModelemashin() {
        return modelemashin;
    }

    public void setModelemashin(String modelemashin) {
        this.modelemashin = modelemashin;
    }

    public int getRuzekari() {
        return ruzekari;
    }

    public void setRuzekari(int ruzekari) {
        this.ruzekari = ruzekari;
    }

    public int getMasrafesookht() {
        return masrafesookht;
    }

    public void setMasrafesookht(int masrafesookht) {
        this.masrafesookht = masrafesookht;
    }

    public int getEquipment() {
        return equipment;
    }

    public void setEquipment(int equipment) {
        this.equipment = equipment;
    }
}
