package com.example.gestordegastosfacub.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestordegastosfacub.R;
import com.example.gestordegastosfacub.adapters.CategorySelectorAdapter;
import com.example.gestordegastosfacub.databinding.FragmentSelectorBinding;
import com.example.gestordegastosfacub.models.Category;
import com.example.gestordegastosfacub.viewModels.CategorySelectorViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategorySelectorFragment extends Fragment {
    private FragmentSelectorBinding binding;
    private CategorySelectorAdapter adapter;
    private CategorySelectorViewModel viewModel;
    private CategorySelectorFragmentListener listener;


    public CategorySelectorFragment() {
        // Required empty public constructor
    }
    public interface CategorySelectorFragmentListener{
        void onCategorySelected(Category category);
    }

    public static CategorySelectorFragment newInstance() {
        return new CategorySelectorFragment()    ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selector, container, false);*/
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_selector,container,false);
        viewModel = new ViewModelProvider(this).get(CategorySelectorViewModel.class);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycler();
    }

    private void setupRecycler() {
        adapter = new CategorySelectorAdapter(viewModel.getCategories().getValue());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.recyclerSelector.setLayoutManager(linearLayoutManager);
        binding.recyclerSelector.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        binding.recyclerSelector.setAdapter(adapter);
        adapter.setListener(new CategorySelectorAdapter.CategorySelectorListener() {
            @Override
            public void onCategoryClick(Category category) {
                if (listener !=null) listener.onCategorySelected(category);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener=(CategorySelectorFragmentListener) context;
        }catch (ClassCastException ex){
            throw new ClassCastException(context.toString()
                    +"must implement CategorySelectorFragmentListener");

        }
    }
}
