package com.example.gestordegastosfacub.viewModels;

import com.example.gestordegastosfacub.models.User;
import com.example.gestordegastosfacub.views.LoginActivity;

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

    public LoginRepository getLoginRepository() {
        if (loginRepository == null) loginRepository = new LoginRepository();
        return loginRepository;
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
}
