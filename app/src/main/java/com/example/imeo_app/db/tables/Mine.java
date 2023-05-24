package com.example.imeo_app.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "mine")
public class Mine {
    @DatabaseField(id = true)
    private long mineid;

    @DatabaseField
    private String minecode;

    @DatabaseField
    private String minename;

    @DatabaseField
    private String minetype;

    @DatabaseField
    private int minestage;

    @DatabaseField
    private String cadastreid;

    @DatabaseField
    private String nationalcode;

    @DatabaseField
    private String beneficiaryname;

    @DatabaseField
    private String beneficiaryaddress;

    @DatabaseField
    private String beneficiaryphone;

    @DatabaseField
    private String mineaddress ;

    @DatabaseField
    private String minephone ;

    @DatabaseField
    private String supervisormembershipcode ;

    @DatabaseField
    private String licenseexpiredate ;

    @DatabaseField
    private String explorelicensename;

    @DatabaseField
    private String explorelicensenumber;

    @DatabaseField
    private String explorelicensedate;

    @DatabaseField
    private String explorelicenseexpiredate;

    @DatabaseField
    private String mineralmaterial;

    @DatabaseField
    private int physicalprogresspercent;

    @DatabaseField
    private String closestcity;

    @DatabaseField
    private String closestvillage;

    @DatabaseField
    private int extractioncapacity;

    @DatabaseField
    private String extractionmethod;

    @DatabaseField
    private String licensenumber;

    @DatabaseField
    private String licensetype;

    @DatabaseField
    private String sazmanmembershipcode;

    @DatabaseField
    private String sazmanmembershipname;

    @DatabaseField
    private String wkt;

    @DatabaseField
    private String wktcenter;

    @DatabaseField
    private String wmsgetmapurl;



    public long getMineid() {
        return mineid;
    }

    public void setMineid(long mineid) {
        this.mineid = mineid;
    }

    public String getMinecode() {
        return minecode;
    }

    public void setMinecode(String minecode) {
        this.minecode = minecode;
    }

    public String getMinename() {
        return minename;
    }

    public void setMinename(String minename) {
        this.minename = minename;
    }

    public String getMinetype() {
        return minetype;
    }

    public void setMinetype(String minetype) {
        this.minetype = minetype;
    }

    public int getMinestage() {
        return minestage;
    }

    public void setMinestage(int minestage) {
        this.minestage = minestage;
    }

    public String getCadastreid() {
        return cadastreid;
    }

    public void setCadastreid(String cadastreid) {
        this.cadastreid = cadastreid;
    }

    public String getNationalcode() {
        return nationalcode;
    }

    public void setNationalcode(String nationalcode) {
        this.nationalcode = nationalcode;
    }

    public String getBeneficiaryname() {
        return beneficiaryname;
    }

    public void setBeneficiaryname(String beneficiaryname) {
        this.beneficiaryname = beneficiaryname;
    }

    public String getBeneficiaryaddress() {
        return beneficiaryaddress;
    }

    public void setBeneficiaryaddress(String beneficiaryaddress) {
        this.beneficiaryaddress = beneficiaryaddress;
    }

    public String getBeneficiaryphone() {
        return beneficiaryphone;
    }

    public void setBeneficiaryphone(String beneficiaryphone) {
        this.beneficiaryphone = beneficiaryphone;
    }

    public String getMineaddress() {
        return mineaddress;
    }

    public void setMineaddress(String mineaddress) {
        this.mineaddress = mineaddress;
    }

    public String getMinephone() {
        return minephone;
    }

    public void setMinephone(String minephone) {
        this.minephone = minephone;
    }

    public String getSupervisormembershipcode() {
        return supervisormembershipcode;
    }

    public void setSupervisormembershipcode(String supervisormembershipcode) {
        this.supervisormembershipcode = supervisormembershipcode;
    }

    public String getLicenseexpiredate() {
        return licenseexpiredate;
    }

    public void setLicenseexpiredate(String licenseexpiredate) {
        this.licenseexpiredate = licenseexpiredate;
    }

    public String getExplorelicensename() {
        return explorelicensename;
    }

    public void setExplorelicensename(String explorelicensename) {
        this.explorelicensename = explorelicensename;
    }

    public String getExplorelicensenumber() {
        return explorelicensenumber;
    }

    public void setExplorelicensenumber(String explorelicensenumber) {
        this.explorelicensenumber = explorelicensenumber;
    }

    public String getExplorelicensedate() {
        return explorelicensedate;
    }

    public void setExplorelicensedate(String explorelicensedate) {
        this.explorelicensedate = explorelicensedate;
    }

    public String getExplorelicenseexpiredate() {
        return explorelicenseexpiredate;
    }

    public void setExplorelicenseexpiredate(String explorelicenseexpiredate) {
        this.explorelicenseexpiredate = explorelicenseexpiredate;
    }

    public String getMineralmaterial() {
        return mineralmaterial;
    }

    public void setMineralmaterial(String mineralmaterial) {
        this.mineralmaterial = mineralmaterial;
    }

    public int getPhysicalprogresspercent() {
        return physicalprogresspercent;
    }

    public void setPhysicalprogresspercent(int physicalprogresspercent) {
        this.physicalprogresspercent = physicalprogresspercent;
    }

    public String getClosestcity() {
        return closestcity;
    }

    public void setClosestcity(String closestcity) {
        this.closestcity = closestcity;
    }

    public String getClosestvillage() {
        return closestvillage;
    }

    public void setClosestvillage(String closestvillage) {
        this.closestvillage = closestvillage;
    }

    public int getExtractioncapacity() {
        return extractioncapacity;
    }

    public void setExtractioncapacity(int extractioncapacity) {
        this.extractioncapacity = extractioncapacity;
    }

    public String getExtractionmethod() {
        return extractionmethod;
    }

    public void setExtractionmethod(String extractionmethod) {
        this.extractionmethod = extractionmethod;
    }

    public String getLicensenumber() {
        return licensenumber;
    }

    public void setLicensenumber(String licensenumber) {
        this.licensenumber = licensenumber;
    }

    public String getLicensetype() {
        return licensetype;
    }

    public void setLicensetype(String licensetype) {
        this.licensetype = licensetype;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public String getSazmanmembershipcode() {
        return sazmanmembershipcode;
    }

    public void setSazmanmembershipcode(String sazmanmembershipcode) {
        this.sazmanmembershipcode = sazmanmembershipcode;
    }

    public String getSazmanmembershipname() {
        return sazmanmembershipname;
    }

    public void setSazmanmembershipname(String sazmanmembershipname) {
        this.sazmanmembershipname = sazmanmembershipname;
    }

    public String getWktcenter() {
        return wktcenter;
    }

    public void setWktcenter(String wktcenter) {
        this.wktcenter = wktcenter;
    }

    public String getWmsgetmapurl() {
        return wmsgetmapurl;
    }

    public void setWmsgetmapurl(String wmsgetmapurl) {
        this.wmsgetmapurl = wmsgetmapurl;
    }
}
