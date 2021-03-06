package com.example.gestordegastosfacub.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestordegastosfacub.R;
import com.example.gestordegastosfacub.adapters.ProviderSelectorAdapter;
import com.example.gestordegastosfacub.databinding.FragmentSelectorBinding;
import com.example.gestordegastosfacub.models.Provider;
import com.example.gestordegastosfacub.repositories.NewExpenseRepository;
import com.example.gestordegastosfacub.viewModels.ProviderSelectorViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ProviderSelectorFragment extends Fragment {
    private FragmentSelectorBinding binding;
    private ProviderSelectorAdapter adapter;
    private ProviderSelectorViewModel viewModel;
    private ProviderSelectorFragmentListener listener;
    public static final String CATEGORY_ID  = "CATEGORY_ID";
    private String categoryId;


    public ProviderSelectorFragment() {
        // Required empty public constructor
    }
    public interface ProviderSelectorFragmentListener{
        void onProviderSelected(Provider provider);
    }

    public static ProviderSelectorFragment newInstance(String categoryId) {
        ProviderSelectorFragment fragment = new ProviderSelectorFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY_ID,categoryId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            categoryId = getArguments().getString(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_selector,container,false);
        viewModel = new ViewModelProvider(this).get(ProviderSelectorViewModel.class);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getProvidersFromServer(categoryId);
        setupObservers();
    }
    private void setupObservers() {
        viewModel.getProviders().observe(this, new Observer<ArrayList<Provider>>() {
            @Override
            public void onChanged(ArrayList<Provider> providers) {
                setupRecycler(providers);
                if (providers.isEmpty()){
                    binding.emptyListText.setText("No se encontraron proveedores");
                }else {
                    binding.emptyListText.setText("");
                }
                binding.layoutOverlay.setVisibility(View.GONE);
            }
        });
        viewModel.getOnGetProvidersFail().observe(this, new Observer<NewExpenseRepository.OnGetProvidersFail>() {
            @Override
            public void onChanged(NewExpenseRepository.OnGetProvidersFail onGetProvidersFail) {
                binding.layoutOverlay.setVisibility(View.GONE);
            }
        });
    }


    private void setupRecycler(ArrayList<Provider> providers) {
        adapter = new ProviderSelectorAdapter(providers);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerSelector.setLayoutManager(linearLayoutManager);
        binding.recyclerSelector.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        binding.recyclerSelector.setAdapter(adapter);
        adapter.setListener(new ProviderSelectorAdapter.ProviderSelectorListener() {
            @Override
            public void onAccountClick(Provider provider) {
                if (listener!= null) listener.onProviderSelected(provider);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener=(ProviderSelectorFragmentListener) context;
        }catch (ClassCastException ex){
            throw new ClassCastException(context.toString()
            +"must implement ProviderSelectorFragmentListener");
        }
    }
}
