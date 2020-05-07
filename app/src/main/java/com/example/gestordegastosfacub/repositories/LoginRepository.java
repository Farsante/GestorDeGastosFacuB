package com.example.gestordegastosfacub.repositories;

import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.User;
import com.example.gestordegastosfacub.network.RestClient;
import com.example.gestordegastosfacub.network.responses.LoginResponse;

import io.reactivex.Emitter;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private PublishSubject<OnLoginSuccess> onLoginSuccess = PublishSubject.create();
    private PublishSubject<OnLoginFail> onLoginFail = PublishSubject.create();

    public PublishSubject<OnLoginSuccess> getOnLoginSuccess() {
        return onLoginSuccess;
    }

    public PublishSubject<OnLoginFail> getOnLoginFail() {
        return onLoginFail;
    }

    public void saveUser(User user){ SessionPersistence.saveUser(user); }

    public void makeLoginToServer(String username,String password){
        RestClient.getApiService().makeLogin(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(makeLoginObserver());
                /*.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    saveUser(response.body().getUser());
                    getOnLoginSuccess().onNext(new OnLoginSuccess(response.body()));
                }else {
                    getOnLoginFail().onNext(new OnLoginFail("ha ocurrido un error "));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                getOnLoginFail().onNext(new OnLoginFail("ha ocurrido un error, lo sentimos "));
            }
        });*/
    }

    public DisposableSingleObserver<LoginResponse> makeLoginObserver(){
        return new DisposableSingleObserver<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                saveUser(loginResponse.getUser());
                getOnLoginSuccess().onNext(new OnLoginSuccess(loginResponse));
            }

            @Override
            public void onError(Throwable e) {
                getOnLoginFail().onNext(new OnLoginFail("Ha ocurrido un error"));
            }
        };
    }

    public class OnLoginSuccess {
        private LoginResponse loginResponse;
        public OnLoginSuccess (LoginResponse loginResponse){
            this.loginResponse= loginResponse;
        }
        public LoginResponse getLoginResponse(){
            return loginResponse;
        }
    }

    public class OnLoginFail {
       private String error;

       public OnLoginFail(String error){ this.error = error; }

       public String getError(){ return error; }
    }

}
