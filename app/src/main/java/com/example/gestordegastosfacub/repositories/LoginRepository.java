package com.example.gestordegastosfacub.repositories;

import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.User;
import com.example.gestordegastosfacub.network.RestClient;
import com.example.gestordegastosfacub.network.responses.LoginResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.reactivex.Emitter;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
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
                if (e instanceof HttpException){
                    ResponseBody body =  ((HttpException)e).response().errorBody();
                    try {
                        JSONObject jObjError = new JSONObject(body.string());
                        LoginResponse loginResponse = new Gson().fromJson(jObjError.toString(),LoginResponse.class);
                        getOnLoginFail().onNext(new OnLoginFail(loginResponse.getError()));
                    }catch (Exception ex){
                        getOnLoginFail().onNext(new OnLoginFail(ex.getMessage()));
                    }
                }
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
