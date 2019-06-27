package com.example.zyf.application.Utils;
import com.example.zyf.application.Administration.eduApplication;
import com.example.zyf.application.Db.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class resolveJson {
    //将json按list解析
    public static List<edu_Information> getList(String json){
        return new Gson().fromJson(json,new TypeToken<List<edu_Information>>(){}.getType());

    }
    public static List<comment> getCommentList(String json){
        return new Gson().fromJson(json,new TypeToken<List<comment>>(){}.getType());

    }

}
