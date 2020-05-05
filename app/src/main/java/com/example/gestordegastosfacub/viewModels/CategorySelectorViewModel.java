package com.example.gestordegastosfacub.viewModels;

import com.example.gestordegastosfacub.models.Category;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CategorySelectorViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Category>> categories =  new MutableLiveData<>();

    public MutableLiveData<ArrayList<Category>> getCategories(){
        generateCategories();
        return categories;
    }

    public void generateCategories(){
        categories.setValue(new ArrayList<Category>());
        categories.getValue().add(new Category("1","comida"));
        categories.getValue().add(new Category("2","Limpieza"));
        categories.getValue().add(new Category("3","Libreria"));
        categories.getValue().add(new Category("4","Limpieza"));
        categories.getValue().add(new Category("5","servicios"));
    }

}
