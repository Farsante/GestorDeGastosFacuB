package com.example.gestordegastosfacub.repositories;

import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.Expense;
import com.example.gestordegastosfacub.models.User;
import com.example.gestordegastosfacub.network.RestClient;

import java.lang.reflect.Executable;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class HomeRepository {
    private PublishSubject<OnGetExpensesSuccess> onGetExpensesSuccess = PublishSubject.create();
    private PublishSubject<OnGetExpensesFail> onGetExpensesFail = PublishSubject.create();

    public User getUser(){return SessionPersistence.getUser(); }

    public ArrayList<Expense> getExpensesFromDatabase() {
        return SessionPersistence.getSavedExpenses();
    }

    public void getExpensesFromServer(){
        RestClient.getApiService().getExpenses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getExpensesObserver());
    }

    public DisposableSingleObserver<ArrayList<Expense>> getExpensesObserver(){
        return new DisposableSingleObserver<ArrayList<Expense>>() {
            @Override
            public void onSuccess(ArrayList<Expense> expenses) {
               getOnGetExpensesSuccess().onNext(new OnGetExpensesSuccess(expenses));
            }

            @Override
            public void onError(Throwable e) {
                getOnGetExpensesFail().onNext(new OnGetExpensesFail("Ha ocurrido un error! "));
            }
        };
    }

    public PublishSubject<OnGetExpensesSuccess> getOnGetExpensesSuccess() {
        return onGetExpensesSuccess;
    }

    public PublishSubject<OnGetExpensesFail> getOnGetExpensesFail() {
        return onGetExpensesFail;
    }

    public class OnGetExpensesSuccess{
        private ArrayList<Expense> expenses;

        public OnGetExpensesSuccess(ArrayList<Expense> expenses) {
            this.expenses = expenses;
        }

        public ArrayList<Expense> getExpenses() {
            return expenses;
        }
    }
    public class OnGetExpensesFail{
        private String error;

        public OnGetExpensesFail(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }

}
