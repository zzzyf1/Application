package com.example.zyf.application.Db;

import java.util.List;

public class edu_Information {
    //教育机构申请时填写的信息
    private education mEducation;
    private email mEmail;
    private List<phone> mPhone;

    public education getmEducation() {
        return mEducation;
    }

    public void setmEducation(education mEducation) {
        this.mEducation = mEducation;
    }

    public email getmEmail() {
        return mEmail;
    }

    public void setmEmail(email mEmail) {
        this.mEmail = mEmail;
    }

    public List<phone> getmPhone() {
        return mPhone;
    }

    public void setmPhone(List<phone> mPhone) {
        this.mPhone = mPhone;
    }
}
