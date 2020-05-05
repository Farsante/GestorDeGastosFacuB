package com.example.gestordegastosfacub.repositories;

import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.Expense;
import com.example.gestordegastosfacub.models.User;

import java.util.ArrayList;

public class HomeRepository {

    public User getUser(){return SessionPersistence.getUser(); }

    public ArrayList<Expense> getExpensesFromDatabase() {
        return SessionPersistence.getSavedExpenses();
    }
}
