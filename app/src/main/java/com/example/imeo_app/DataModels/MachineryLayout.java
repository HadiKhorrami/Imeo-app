package com.example.imeo_app.DataModels;


import com.j256.ormlite.field.DatabaseField;

public class MachineryLayout {
    private int id;
    private long machineryid;
    private long reportid;
    private long mineid;
    private int namemashincode;
    private String namemashin;
    private String modelemashin;
    private int ruzekari;
    private int masrafesookht;
    private int equipmentcode;
    private String equipment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getNamemashin() {
        return namemashin;
    }

    public void setNamemashin(String namemashin) {
        this.namemashin = namemashin;
    }

    public int getNamemashinCode() {
        return namemashincode;
    }

    public void setNamemashinCode(int namemashincode) {
        this.namemashincode = namemashincode;
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

    public int getEquipmentCode() {
        return equipmentcode;
    }

    public void setEquipmentCode(int equipmentcode) {
        this.equipmentcode = equipmentcode;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}

