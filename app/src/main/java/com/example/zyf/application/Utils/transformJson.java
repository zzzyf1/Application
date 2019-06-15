package com.example.zyf.application.Utils;
import com.example.zyf.application.Db.*;
import com.google.gson.Gson;
public class transformJson {
    //把edu_Information对象转换成json
    public static String edu_InformationToJson(edu_Information edu_information){
        return new Gson().toJson(edu_information);
    }
}
