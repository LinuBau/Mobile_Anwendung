package com.example.testapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;


public interface FlaskApiService {
    @GET("/json")
    Call<List<SchwazesBrettAnzeige>> getJsonList();
    @POST("/json")
    Call<SchwazesBrettAnzeige> addJsonObject(@Body SchwazesBrettAnzeige schwazesBrettAnzeige);
}
