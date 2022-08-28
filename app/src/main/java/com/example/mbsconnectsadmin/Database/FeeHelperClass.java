package com.example.mbsconnectsadmin.Database;

public class FeeHelperClass {
    String course,year,type,updater_name,fee_term1,fee_term2;

    public FeeHelperClass()
    {

    }

    public FeeHelperClass(String course, String year, String type, String updater_name, String fee_term1, String fee_term2) {
        this.course = course;
        this.year = year;
        this.type = type;
        this.updater_name = updater_name;
        this.fee_term1 = fee_term1;
        this.fee_term2 = fee_term2;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdater_name() {
        return updater_name;
    }

    public void setUpdater_name(String updater_name) {
        this.updater_name = updater_name;
    }

    public String getFee_term1() {
        return fee_term1;
    }

    public void setFee_term1(String fee_term1) {
        this.fee_term1 = fee_term1;
    }

    public String getFee_term2() {
        return fee_term2;
    }

    public void setFee_term2(String fee_term2) {
        this.fee_term2 = fee_term2;
    }
}
