package com.example.gestordegastosfacub.views.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gestordegastosfacub.R;
import com.example.gestordegastosfacub.adapters.AccountSelectorAdapter;
import com.example.gestordegastosfacub.databinding.FragmentSelectorBinding;
import com.example.gestordegastosfacub.models.Account;
import com.example.gestordegastosfacub.viewModels.AccountSelectorViewModel;

import java.util.ArrayList;

public class AccountSelectorFragment extends Fragment {
    private FragmentSelectorBinding binding;
    private AccountSelectorViewModel viewModel;
    private AccountSelectorAdapter adapter;

    private AccountSelectorFragmentListener listener;


    public interface AccountSelectorFragmentListener{
        void onAccountSelected(Account account);
    }
    public AccountSelectorFragment() {
        // Required empty public constructor
    }

    public static AccountSelectorFragment newInstance() {
       /* AccountSelectorFragment fragment = new AccountSelectorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);*/
        return new AccountSelectorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_selector,container,false);
        viewModel = new ViewModelProvider(this).get(AccountSelectorViewModel.class);
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_selector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getAccountsFromServer();
        binding.layoutOverlay.setVisibility(View.VISIBLE);
        setupObservers();
    }

    private void setupObservers() {
        viewModel.getAccounts().observe(this, accounts -> {
            setupRecycler(accounts);
            if (accounts.isEmpty()){
                binding.emptyListText.setText("No se encontraron cuentas disponibles");
            }else {
                binding.emptyListText.setText("");

            }
            binding.layoutOverlay.setVisibility(View.GONE);
        });
        viewModel.getOnGetAccountFail().observe(this,onGetAccountsFail -> {
            binding.layoutOverlay.setVisibility(View.GONE);
        });
    }

    private void setupRecycler(ArrayList<Account> accounts) {
        adapter = new AccountSelectorAdapter(accounts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerSelector.setLayoutManager(linearLayoutManager);
        binding.recyclerSelector.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        binding.recyclerSelector.setAdapter(adapter);
        adapter.setListener(new AccountSelectorAdapter.AccountSelectorListener() {
            @Override
            public void onAccountClick(Account account) {
                if (listener != null) listener.onAccountSelected(account);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AccountSelectorFragmentListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
            +"must implement AccountSelectorFragmentListener");
        }
    }
}
