package com.example.gestordegastosfacub.viewModels;

import com.example.gestordegastosfacub.models.User;
import com.example.gestordegastosfacub.views.LoginActivity;

import java.net.MalformedURLException;
import java.sql.Struct;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.gestordegastosfacub.repositories.LoginRepository;

public class LoginViewModel extends ViewModel {
    private User user;
    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<Boolean> loginButton = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private MutableLiveData<LoginRepository.OnLoginSuccess> onLoginSuccessData = new MutableLiveData<>();
    private MutableLiveData<LoginRepository.OnLoginFail> onLoginFailData =new MutableLiveData<>();
    private MutableLiveData<Boolean> showOverlay = new MutableLiveData<>();

    public MutableLiveData<Boolean> getShowOverlay() { return showOverlay; }

    public LoginRepository getLoginRepository() {
        if (loginRepository == null) {
            loginRepository = new LoginRepository();
            setupObserver();
        }
        return loginRepository;
    }

    public MutableLiveData<LoginRepository.OnLoginSuccess> getOnLoginSuccessData() {
        return onLoginSuccessData;
    }

    public MutableLiveData<LoginRepository.OnLoginFail> getOnLoginFailData() {
        return onLoginFailData;
    }

    private void setupObserver() {
     getLoginRepository().getOnLoginSuccess().subscribe(onLoginSuccess -> {
         getOnLoginSuccessData().setValue(onLoginSuccess);
         getShowOverlay().setValue(false);
     });

        getLoginRepository().getOnLoginFail().subscribe(onLoginFail -> {
            getOnLoginFailData().setValue(onLoginFail);
          getShowOverlay().setValue(false);
        });
    }

    public User getUser(){
        if(user == null) user = new User();
        return user;
    }

    public MutableLiveData<String> getUsername() { return username; }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void onLoginPressed(){
        getLoginButton().setValue(true);
    }

    public MutableLiveData <Boolean> getLoginButton() {
        return loginButton;
    }

    public void saveUser(){ getLoginRepository().saveUser(getUser()); }

    public void makeLogin() {
        getShowOverlay().setValue(true);
        getLoginRepository().makeLoginToServer(getUser().getUsername(),getUser().getPassword());
    }
}
