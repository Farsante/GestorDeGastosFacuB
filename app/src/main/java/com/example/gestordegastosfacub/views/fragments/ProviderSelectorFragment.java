package com.example.gestordegastosfacub.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestordegastosfacub.R;
import com.example.gestordegastosfacub.databinding.FragmentSelectorBinding;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class ProviderSelectorFragment extends Fragment {
    private FragmentSelectorBinding binding;


    public ProviderSelectorFragment() {
        // Required empty public constructor
    }

    public static ProviderSelectorFragment newInstance() {
        return new ProviderSelectorFragment()    ;
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
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_selector, container, false);
    }
}
