package com.example.gestordegastosfacub.repositories;

import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.User;

public class LoginRepository {

    public void saveUser(User user){
        SessionPersistence.saveUser(user);
    }
}
