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

    public HomeRepository getHomeRepository(){
        if (homeRepository == null) homeRepository = new HomeRepository();
        return homeRepository;
    }

    public MutableLiveData<ArrayList<Expense>> getExpenses() { return expenses; }

    public MutableLiveData<String> getUserName() { return userName; }

    public MutableLiveData<Boolean> getButonNewExpense() { return butonNewExpense; }

    public void onButonNewExpensePressed(){
        getButonNewExpense().setValue(true);
    }

    public  ArrayList<Expense> generateExpenses(){
        expenses.setValue(new ArrayList<Expense>());
        expenses.getValue().add(new Expense("$1200","Caja grande","comida","Miercoles 15 de Abril de 2020 ","Comida", "ARS - PESOS", "5", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$139", "Caja chica", "Impuestos", "Martes 15 de Abril de 2020 ","Comida", "ARS - PESOS", "5", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$289", "Caja chica", "Libreria", "Martes 15 de Abril de 2020 ","Comida", "ARS - PESOS", "5", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$589", "Caja grande", "Comida", "Martes 15 de Abril de 2020 ","Comida", "ARS - PESOS", "5", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$589", "Caja grande", "Comida", "Martes 15 de Abril de 2020 ","libreria", "ARS - PESOS", "5", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$139", "Caja chica", "Impuestos", "Martes 15 de Abril de 2020 ","Comida", "ARS - PESOS", "5", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$289", "Caja chica", "Libreria", "Martes 15 de Abril de 2020 ","Comida", "ARS - PESOS", "6", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$589", "Caja grande", "Comida", "Martes 15 de Abril de 2020 ","libreria", "ARS - PESOS", "6", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$589", "Caja grande", "Comida", "Martes 15 de Abril de 2020 ","Comida", "ARS - PESOS", "6", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$139", "Caja chica", "Impuestos", "Martes 15 de Abril de 2020 ","Comida", "ARS - PESOS", "6", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$289", "Caja chica", "Libreria", "Martes 15 de Abril de 2020 ","libreria", "ARS - PESOS", "6", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$589", "Caja grande", "Comida", "Martes 15 de Abril de 2020 ","Comida", "ARS - PESOS", "6", "Esto es una descripción"));
        expenses.getValue().add(new Expense("$589", "Caja grande", "Comida", "Martes 15 de Abril de 2020 ","Comida", "ARS - PESOS", "6", "Esto es una descripción"));
        expenses.getValue().add(new Expense("1","$489", "Caja grande", "Constantino", "Martes 15 de Abril de 2020", "Comida", "ARS - PESOS", "5", "Esto es una descripción"));
        expenses.getValue().add(new Expense("2","$139", "Caja chica", "Copitec", "Martes 15 de Abril de 2020", "Libreria", "ARS - PESOS", "0", "Esto es una descripción"));
        expenses.getValue().add(new Expense("3","$289", "Caja chica", "Super", "Martes 15 de Abril de 2020", "Articulos de limpieza", "ARS - PESOS", "5", "Esto es una descripción 2"));
        expenses.getValue().add(new Expense("4","$589", "Caja grande", "Constantino", "Martes 15 de Abril de 2020", "Comida", "ARS - PESOS", "6", "Esto es una descripción"));
        expenses.getValue().add(new Expense("5","$589", "Caja grande", "Constantino", "Martes 15 de Abril de 2020", "Comida", "ARS - PESOS", "1", "Esto es una descripción"));
        return expenses.getValue();

    }

    public void setupUserInfo(){
        User user = getHomeRepository().getUser();
        getUserName().setValue(user.getUsername());
    }

}
