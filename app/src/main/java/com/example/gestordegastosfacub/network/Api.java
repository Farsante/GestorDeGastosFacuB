package com.example.gestordegastosfacub.network;

import com.example.gestordegastosfacub.models.Expense;
import com.example.gestordegastosfacub.network.responses.LoginResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("/api/v1/users/login")
    Single<LoginResponse> makeLogin(
            @Field("username") String username,
            @Field("password") String password
    );
    @GET("/api/v1/transactions/myExpenses")
    Single<ArrayList<Expense>> getExpenses();
}
