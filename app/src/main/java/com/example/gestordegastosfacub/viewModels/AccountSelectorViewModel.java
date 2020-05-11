package com.example.gestordegastosfacub.viewModels;

import com.example.gestordegastosfacub.models.Account;
import com.example.gestordegastosfacub.models.Currency;
import com.example.gestordegastosfacub.repositories.NewExpenseRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccountSelectorViewModel extends ViewModel {
    private NewExpenseRepository newExpenseRepository;
    private MutableLiveData<ArrayList<Account>> accounts = new MutableLiveData<>();
    private MutableLiveData<NewExpenseRepository.OnGetAccountsFail> onGetAccountFail = new MutableLiveData<>();

    public NewExpenseRepository getNewExpenseRepository(){
        if (newExpenseRepository == null){
            newExpenseRepository = new NewExpenseRepository();
            setupObservers();
        }
        return newExpenseRepository;
    }

    private void setupObservers() {
        getNewExpenseRepository().getOnGetAccountsSuccessData().subscribe(onGetAccountsSuccess -> {
            getAccounts().setValue(onGetAccountsSuccess.getAccounts());
        });
        getNewExpenseRepository().getOnGetAccountsFailData().subscribe(onGetAccountsFail -> {
            getOnGetAccountFail().setValue(onGetAccountsFail);
        });
    }

    public MutableLiveData<NewExpenseRepository.OnGetAccountsFail> getOnGetAccountFail() {
        return onGetAccountFail;
    }

    public MutableLiveData<ArrayList<Account>> getAccounts(){
        generateAccounts();
        return accounts;
    }
    public void generateAccounts(){
        accounts.setValue(new ArrayList<Account>());
        accounts.getValue().add(new Account("1","caja chica",new Currency("ARS -Pesos")));
        accounts.getValue().add(new Account("2","Caja grande",new Currency("ARS - Pesos")));
        accounts.getValue().add(new Account("3","Caja fuerte",new Currency("ARS - Pesos")));
        accounts.getValue().add(new Account("4","Caja en dolares",new Currency("USD - DOLARES")));
        accounts.getValue().add(new Account("5","caja de reserva",new Currency("ARS - peso")));
    }

    public void getAccountsFromServer(){
        getNewExpenseRepository().getAccountsFromServer();
    }

}
