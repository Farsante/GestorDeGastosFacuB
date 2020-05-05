package com.example.gestordegastosfacub.repositories;

import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.Expense;

public class NewExpenseRepository {

    public void saveExpenseInDatabase(Expense expense){
        SessionPersistence.saveExpenses(expense);
    }
}
