package com.example.gestordegastosfacub.network;

import com.example.gestordegastosfacub.models.Account;
import com.example.gestordegastosfacub.models.Category;
import com.example.gestordegastosfacub.models.Expense;
import com.example.gestordegastosfacub.models.Provider;
import com.example.gestordegastosfacub.network.responses.LoginResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("/api/v1/users/login")
    Single<LoginResponse> makeLogin(
            @Field("username") String username,
            @Field("password") String password
    );
    @GET("/api/v1/transactions/myExpenses")
    Single<ArrayList<Expense>> getExpenses();

    @GET("/api/v1/accounts")
    Single<ArrayList<Account>>  getAccounts();

    @GET("/api/v1/expenseCategories")
    Single<ArrayList<Category>> getCategories();

    @GET("/api/v1/providers")
    Single<ArrayList<Provider>> getProviders(
            @Query("categoId")  int categoryId
    );

}
