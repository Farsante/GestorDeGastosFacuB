package com.example.gestordegastosfacub.viewModels;

import com.example.gestordegastosfacub.models.Provider;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProviderSelectorViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Provider>> providers = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Provider>> getProviders() {
        generateProviders();
        return providers;
    }

    private void generateProviders() {
        providers.setValue(new ArrayList<Provider>());
        providers.getValue().add(new Provider("1","Proveedor 1"));
        providers.getValue().add(new Provider("2","Proveedor 2"));
        providers.getValue().add(new Provider("3","Proveedor 3"));
        providers.getValue().add(new Provider("4","Proveedor 4"));
    }
}
