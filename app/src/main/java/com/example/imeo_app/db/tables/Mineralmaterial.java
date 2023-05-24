package com.example.imeo_app.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "mineralmaterial")
public class Mineralmaterial {
    @DatabaseField(id = true)
    private long mineralmaterialid;

    @DatabaseField
    private long mineid;

    @DatabaseField
    private String type_;

    @DatabaseField
    private String color;

    @DatabaseField
    private String mineplace;

    @DatabaseField
    private String usecase;

    @DatabaseField
    private String density;

    @DatabaseField
    private String grading;

    @DatabaseField
    private String degree;

    @DatabaseField
    private String sheklekansar;

    @DatabaseField
    private String kanihayeasli;

    @DatabaseField
    private String zonesakhtari;

    @DatabaseField
    private String sangemizban;

    @DatabaseField
    private String vahedhayesangshenasieasli;


    public long getMineralmaterialid() {
        return mineralmaterialid;
    }

    public void setMineralmaterialid(long mineralmaterialid) {
        this.mineralmaterialid = mineralmaterialid;
    }

    public long getMineid() {
        return mineid;
    }

    public void setMineid(long mineid) {
        this.mineid = mineid;
    }

    public String getType_() {
        return type_;
    }

    public void setType_(String type_) {
        this.type_ = type_;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMineplace() {
        return mineplace;
    }

    public void setMineplace(String mineplace) {
        this.mineplace = mineplace;
    }

    public String getUsecase() {
        return usecase;
    }

    public void setUsecase(String usecase) {
        this.usecase = usecase;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getGrading() {
        return grading;
    }

    public void setGrading(String grading) {
        this.grading = grading;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSheklekansar() {
        return sheklekansar;
    }

    public void setSheklekansar(String sheklekansar) {
        this.sheklekansar = sheklekansar;
    }

    public String getKanihayeasli() {
        return kanihayeasli;
    }

    public void setKanihayeasli(String kanihayeasli) {
        this.kanihayeasli = kanihayeasli;
    }

    public String getZonesakhtari() {
        return zonesakhtari;
    }

    public void setZonesakhtari(String zonesakhtari) {
        this.zonesakhtari = zonesakhtari;
    }

    public String getSangemizban() {
        return sangemizban;
    }

    public void setSangemizban(String sangemizban) {
        this.sangemizban = sangemizban;
    }

    public String getVahedhayesangshenasieasli() {
        return vahedhayesangshenasieasli;
    }

    public void setVahedhayesangshenasieasli(String vahedhayesangshenasieasli) {
        this.vahedhayesangshenasieasli = vahedhayesangshenasieasli;
    }
}
