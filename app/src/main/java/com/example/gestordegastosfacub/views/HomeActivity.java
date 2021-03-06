package com.example.gestordegastosfacub.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gestordegastosfacub.R;
import com.example.gestordegastosfacub.adapters.ExpensesAdapter;
import com.example.gestordegastosfacub.databinding.ActivityHomeBinding;
import com.example.gestordegastosfacub.helpers.SessionPersistence;
import com.example.gestordegastosfacub.models.Expense;
import com.example.gestordegastosfacub.models.User;
import com.example.gestordegastosfacub.repositories.HomeRepository;
import com.example.gestordegastosfacub.viewModels.HomeViewModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private ExpensesAdapter adapter;
    private HomeViewModel viewModel;
    //private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setHomeViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.setupUserInfo();
        setupObservers();

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getExpensesFromServer();
    }

    private void setupObservers() {
        viewModel.getButonNewExpense().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //Toast.makeText(HomeActivity.this, "go to new expense", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this,NewExpenseActivity.class));
            }
        });
        viewModel.getExpenses().observe(this, new Observer<ArrayList<Expense>>() {
            @Override
            public void onChanged(ArrayList<Expense> expenses) {
                setupRecyclerView(expenses);
            }
        });
        viewModel.getOnGetExpensesFailData().observe(this, new Observer<HomeRepository.OnGetExpensesFail>() {
            @Override
            public void onChanged(HomeRepository.OnGetExpensesFail onGetExpensesFail) {
                Toast.makeText(HomeActivity.this, onGetExpensesFail.getError(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupRecyclerView(ArrayList<Expense> expenses) {
        adapter = new ExpensesAdapter(viewModel.generateExpenses());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recycleExpenses.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.recycleExpenses.setLayoutManager(linearLayoutManager);
        binding.recycleExpenses.setAdapter(adapter);
        adapter.setListener(new ExpensesAdapter.ExpensesAdapterInterface() {
            @Override
            public void onItemClick(Expense expense) {
                //Toast.makeText(HomeActivity.this, expense.getAccount(), Toast.LENGTH_SHORT).show();
               goToDetailExpenseActivity(expense);
            }
        });
    }

    private void goToDetailExpenseActivity(Expense expense) {
        Intent intent = new Intent(this,DetailExpenseActivity.class);
        intent.putExtra("expense",expense);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_action_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id == R.id.buttonLogout){
            logout();
        }
        return super.onOptionsItemSelected(item);

    }

    private void logout() {
        SessionPersistence.deleteUser();
        SessionPersistence.deleteExpense();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
