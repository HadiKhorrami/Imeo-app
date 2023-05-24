package com.example.imeo_app.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "membership")
public class Membership {
    @DatabaseField(id = true)
    private long membershipid;

    @DatabaseField
    private String membershipcode;

    @DatabaseField
    private long groupid;

    @DatabaseField
    private long userid;

    @DatabaseField
    private String username;

    @DatabaseField
    private String firstname;

    @DatabaseField
    private String lastname;

    @DatabaseField
    private String phonenumber;

    @DatabaseField
    private String fieldstudy;

    @DatabaseField
    private String speciality;

    @DatabaseField
    private String firstfield;

    @DatabaseField
    private String firstbase;

    @DatabaseField
    private String secondfield;

    @DatabaseField
    private String secondbase;

    @DatabaseField
    private long portaluserid;

    public long getMembershipid() {
        return membershipid;
    }

    public void setMembershipid(long membershipid) {
        this.membershipid = membershipid;
    }

    public String getMembershipcode() {
        return membershipcode;
    }

    public void setMembershipcode(String membershipcode) {
        this.membershipcode = membershipcode;
    }

    public long getGroupid() {
        return groupid;
    }

    public void setGroupid(long groupid) {
        this.groupid = groupid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getFieldstudy() {
        return fieldstudy;
    }

    public void setFieldstudy(String fieldstudy) {
        this.fieldstudy = fieldstudy;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getFirstfield() {
        return firstfield;
    }

    public void setFirstfield(String firstfield) {
        this.firstfield = firstfield;
    }

    public String getFirstbase() {
        return firstbase;
    }

    public void setFirstbase(String firstbase) {
        this.firstbase = firstbase;
    }

    public String getSecondfield() {
        return secondfield;
    }

    public void setSecondfield(String secondfield) {
        this.secondfield = secondfield;
    }

    public String getSecondbase() {
        return secondbase;
    }

    public void setSecondbase(String secondbase) {
        this.secondbase = secondbase;
    }

    public long getPortaluserid() {
        return portaluserid;
    }

    public void setPortaluserid(long portaluserid) {
        this.portaluserid = portaluserid;
    }
}
