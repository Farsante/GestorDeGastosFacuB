package com.example.gestordegastosfacub.network;

import com.example.gestordegastosfacub.network.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("/api/v1/users/login")
    Call<LoginResponse> makeLogin(
            @Field("username") String username,
            @Field("password") String password
    );
}