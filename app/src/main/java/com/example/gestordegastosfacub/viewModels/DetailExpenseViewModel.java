package com.example.gestordegastosfacub.viewModels;

import android.view.View;

import com.example.gestordegastosfacub.models.Expense;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailExpenseViewModel extends ViewModel {
    private MutableLiveData<Expense> expense = new MutableLiveData<>();

    public MutableLiveData<Expense> getExpense() { return expense; }

    public void setExpense(Expense expense) { getExpense().setValue(expense); }
}
