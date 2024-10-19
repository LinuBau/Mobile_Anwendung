package com.example.testapi.apistuff;

import com.example.testapi.dataobjects.Notice;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;


public interface FlaskApiService {
    @GET("/json")
    Call<List<Notice>> getJsonList();
    @POST("/json")
    Call<Notice> addJsonObject(@Body Notice notice);
    @GET("/getUserId")
    Call<Notice> getUserID();
    @POST("/getUserId")
    Call<Integer> addUserID(@Body Integer userid);
}


