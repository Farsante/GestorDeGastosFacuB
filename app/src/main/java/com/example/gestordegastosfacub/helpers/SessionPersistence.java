package com.example.gestordegastosfacub.helpers;

import android.service.autofill.AutofillService;

import com.example.gestordegastosfacub.models.User;

import io.paperdb.Paper;

public class SessionPersistence {
    public static void saveUser(User users){
        Paper.book().write("user",users);
    }
    public static User getUser(){
        return Paper.book().read("user");
    }
    public static void deleteUser(){
        Paper.book().delete("user");
    }
}
