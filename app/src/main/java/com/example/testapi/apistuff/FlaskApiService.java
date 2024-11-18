package com.example.testapi.apistuff;

import com.example.testapi.activitys.MainActivity;
import com.example.testapi.dataobjects.Message;
import com.example.testapi.dataobjects.Notice;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    @GET("/getkeysWith/{userid}")
    Call<List<Integer>> getChatKeys(@Path("userid") int userid);
    @GET("/get_messages/{this_userid}/{another_userid}")
    Call<List<Message>> getChat(@Path("this_userid") int this_userid,@Path("another_userid") int another_userid);
    @POST("/send_message")
    Call<Message> sendMessage(@Body Message message);

}


