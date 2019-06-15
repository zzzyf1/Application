package com.example.zyf.application.Db;

public class education {
    private String education_id; //系统自动生成
    private String domain;
    private String address;
    private int maxAge;  //适合年龄最大值
    private int minAge;
    private String describe;
    private int star;   //星级指数 ,50代表好评50%



    public String getEducation_id() {
        return education_id;
    }

    public void setEducation_id(String  education_id) {
        this.education_id = education_id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
