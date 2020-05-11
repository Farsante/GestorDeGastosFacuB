package com.example.gestordegastosfacub.viewModels;

import com.example.gestordegastosfacub.models.Provider;
import com.example.gestordegastosfacub.repositories.NewExpenseRepository;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProviderSelectorViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Provider>> providers = new MutableLiveData<>();
    private MutableLiveData<NewExpenseRepository.OnGetProvidersFail> onGetProvidersFail = new MutableLiveData<>();
    private NewExpenseRepository newExpenseRepository;

    public MutableLiveData<NewExpenseRepository.OnGetProvidersFail> getOnGetProvidersFail() {
        return onGetProvidersFail;
    }

    public MutableLiveData<ArrayList<Provider>> getProviders() {
        return providers;
    }

    public NewExpenseRepository getNewExpenseRepository() {
        if (newExpenseRepository ==  null){
            newExpenseRepository = new NewExpenseRepository();
            setupObservers();
        }
        return newExpenseRepository;
    }

    private void setupObservers() {
        getNewExpenseRepository().getOnGetProvidersSuccessData().subscribe(onGetProvidersSuccess -> {
            getProviders().setValue(onGetProvidersSuccess.getProviders());
        });
        getNewExpenseRepository().getOnGetProvidersFailData().subscribe(onGetProvidersFail -> {
            getOnGetProvidersFail().setValue(onGetProvidersFail);
        });
    }

    public void getProvidersFromServer(String categoryId) {
        getNewExpenseRepository().getProviderFromServer(categoryId);
    }
}
