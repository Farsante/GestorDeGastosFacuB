package com.example.gestordegastosfacub.viewModels;

import com.example.gestordegastosfacub.models.Expense;
import com.example.gestordegastosfacub.models.User;
import com.example.gestordegastosfacub.repositories.HomeRepository;
import com.example.gestordegastosfacub.views.HomeActivity;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Expense>> expenses = new MutableLiveData<>();
    private MutableLiveData<String> userName = new MutableLiveData<>();
    private MutableLiveData<Boolean> butonNewExpense = new MutableLiveData<>();
    private HomeRepository homeRepository;
    private MutableLiveData<Boolean> showOverlay = new MutableLiveData<>();
    private MutableLiveData<HomeRepository.OnGetExpensesFail>onGetExpensesFailData = new MutableLiveData<>();


   public MutableLiveData<Boolean> getShowOverlay(){
       return showOverlay;
   }

    public MutableLiveData<HomeRepository.OnGetExpensesFail> getOnGetExpensesFailData() {
        return onGetExpensesFailData;
    }

    public HomeRepository getHomeRepository(){
        if (homeRepository == null) {
            homeRepository = new HomeRepository();
            setupObservers();
        }
        return homeRepository;
    }

    private void setupObservers() {
        getHomeRepository().getOnGetExpensesSuccess().subscribe(onGetExpensesSuccess ->{
                getExpenses().setValue(onGetExpensesSuccess.getExpenses());
                getShowOverlay().setValue(false);
        });
        getHomeRepository().getOnGetExpensesFail().subscribe(onGetExpensesFail -> {
            getOnGetExpensesFailData().setValue(onGetExpensesFail);
            getShowOverlay().setValue(false);
        });
    }


    public MutableLiveData<ArrayList<Expense>> getExpenses() { return expenses; }

    public MutableLiveData<String> getUserName() { return userName; }

    public MutableLiveData<Boolean> getButonNewExpense() { return butonNewExpense; }

    public void onButonNewExpensePressed(){
        getButonNewExpense().setValue(true);
    }

    public  ArrayList<Expense> generateExpenses(){
    return getHomeRepository().getExpensesFromDatabase();
    }

    public void setupUserInfo(){
        User user = getHomeRepository().getUser();
        getUserName().setValue("¡"+user.getName()+"!");    }

    public void getExpensesFromServer() {
        getShowOverlay().setValue(true);
        getHomeRepository().getExpensesFromServer();
    }
}
