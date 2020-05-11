package com.example.gestordegastosfacub.viewModels;

import com.example.gestordegastosfacub.models.Category;
import com.example.gestordegastosfacub.repositories.NewExpenseRepository;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategorySelectorViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Category>> categories =  new MutableLiveData<>();
    private MutableLiveData<NewExpenseRepository.OnGetCategoryFail> onGetCategoryFail = new MutableLiveData<>();
    private NewExpenseRepository newExpenseRepository;

    public NewExpenseRepository getNewExpenseRepository(){
        if (newExpenseRepository == null){
            newExpenseRepository = new NewExpenseRepository();
            setupObservers();
        }
        return newExpenseRepository;
    }

    public MutableLiveData<NewExpenseRepository.OnGetCategoryFail> getOnGetCategoryFail(){
        return onGetCategoryFail;
    }

    private void setupObservers() {
        getNewExpenseRepository().getOnGetCategoriesSuccessData().subscribe(onGetCategoriesSuccess -> {
            getCategories().setValue(onGetCategoriesSuccess.getCategories());
        });
        getNewExpenseRepository().getOnGetCategoriesFailData().subscribe(onGetCategoryFail -> {
            getOnGetCategoryFail().setValue(onGetCategoryFail);
        });
    }

    public MutableLiveData<ArrayList<Category>> getCategories(){
        return categories;
    }

    public void getCategoriesFromServer(){
        getNewExpenseRepository().getCategoriesFromServer();
    }

}
