package com.example.gestordegastosfacub.viewModels;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.gestordegastosfacub.R;
import com.example.gestordegastosfacub.databinding.ActivityNewExpenseBinding;
import com.example.gestordegastosfacub.models.Account;
import com.example.gestordegastosfacub.network.requests.CreateExpenseRequest;
import com.example.gestordegastosfacub.repositories.NewExpenseRepository;
import com.example.gestordegastosfacub.viewModels.NewExpenseViewModel;
import com.example.gestordegastosfacub.views.NewExpenseActivity;
import com.example.gestordegastosfacub.views.fragments.AccountSelectorFragment;
import com.example.gestordegastosfacub.views.fragments.CategorySelectorFragment;
import com.example.gestordegastosfacub.views.fragments.ProviderSelectorFragment;
import com.example.gestordegastosfacub.R;
import com.example.gestordegastosfacub.databinding.ActivityNewExpenseBinding;
import com.example.gestordegastosfacub.models.Account;
import com.example.gestordegastosfacub.models.Expense;
import com.example.gestordegastosfacub.views.fragments.AccountSelectorFragment;

import java.sql.Struct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NewExpenseViewModel extends ViewModel {
    private NewExpenseRepository newExpenseRepository;
    private MutableLiveData<Expense> expense = new MutableLiveData<>();
    private MutableLiveData<Boolean> accountPressed = new MutableLiveData<>();
    private MutableLiveData<Boolean> categoryPressed = new MutableLiveData<>();
    private MutableLiveData<Boolean> providerPressed = new MutableLiveData<>();
    private MutableLiveData<Boolean> newExpensePressed = new MutableLiveData<>();
    private MutableLiveData<String> amount = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();
    private MutableLiveData<String> quantity =  new MutableLiveData<>();
    private MutableLiveData<NewExpenseRepository.OnCreateExpenseSuccess> onCreateExpenseSuccessMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<NewExpenseRepository.OnCreateExpenseFail> onCreateExpenseFailMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean>showOverlay = new MutableLiveData<>();

    public MutableLiveData<Boolean> getShowOverlay() {
        return showOverlay;
    }

    public NewExpenseRepository getNewExpenseRepository(){
        if (newExpenseRepository == null){
            newExpenseRepository =  new NewExpenseRepository();
            setupObservers();
        }
        return newExpenseRepository;
    }

    public MutableLiveData<NewExpenseRepository.OnCreateExpenseSuccess> getOnCreateExpenseSuccessMutableLiveData() {
        return onCreateExpenseSuccessMutableLiveData;
    }

    public MutableLiveData<NewExpenseRepository.OnCreateExpenseFail> getOnCreateExpenseFailMutableLiveData() {
        return onCreateExpenseFailMutableLiveData;
    }

    private void setupObservers() {
        getNewExpenseRepository().getOnCreateExpenseSuccessData().subscribe(onCreateExpenseSuccess -> {
            getOnCreateExpenseSuccessMutableLiveData().setValue(onCreateExpenseSuccess);
        });
        getNewExpenseRepository().getOnCreateExpenseFailData().subscribe(onCreateExpenseFail -> {
            getOnCreateExpenseFailMutableLiveData().setValue(onCreateExpenseFail);
            getShowOverlay().setValue(false);
        });
    }

    public MutableLiveData<String> getAmount() { return amount; }

    public MutableLiveData<String> getDescription() { return description; }

    public MutableLiveData<String> getQuantity() { return quantity; }

    public MutableLiveData<Boolean> getAccountPressed() { return accountPressed; }

    public MutableLiveData<Boolean> getCategoryPressed() { return categoryPressed; }

    public MutableLiveData<Boolean> getProviderPressed() { return providerPressed; }

    public MutableLiveData<Boolean> getNewExpensePressed() {
        return newExpensePressed;
    }

    public MutableLiveData<Expense> getExpense(){
        if (expense.getValue()==null) expense.setValue(new Expense() );
        return expense;
    }

    public void onAccountPressed(){
        getAccountPressed().setValue(true);
    }
    public void onCategoryPressed(){
        getCategoryPressed().setValue(true);
    }
    public void onProviderPressed(){
        getProviderPressed().setValue(true);
    }
    public void onNewExpensePressed(){
        getNewExpensePressed().setValue(true);
    }

    public void saveExpense(){
        getNewExpenseRepository().saveExpenseInDatabase(getExpense().getValue());
    }

    public void createNewExpense(){
       getShowOverlay().setValue(true);
       Double amount = 0.0;
       Integer accountId = 0;
       Integer categoryId = 0;
       Integer providerId = 0;

       try {
           amount = Double.valueOf(getExpense().getValue().getAmount());
           accountId = Integer.parseInt(getExpense().getValue().getAccount().getId());
           categoryId = Integer.parseInt(getExpense().getValue().getCategory().getId());
           providerId = Integer.parseInt(getExpense().getValue().getProvider().getId());
       }catch (Exception e){

       }
        CreateExpenseRequest createExpenseRequest = new CreateExpenseRequest(
                getExpense().getValue().getDescription(),
                amount,
                accountId,
                categoryId,
                providerId
        );
       getNewExpenseRepository().createExpense(createExpenseRequest);
    }

}


