package com.example.gestordegastosfacub.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gestordegastosfacub.R;
import com.example.gestordegastosfacub.models.Account;
import com.example.gestordegastosfacub.views.fragments.AccountSelectorFragment;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountSelectorAdapter extends RecyclerView.Adapter<AccountSelectorAdapter.ViewHolder >{
    private ArrayList<Account> accounts;
    private AccountSelectorListener listener;

    public void setListener(AccountSelectorListener listener) {
        this.listener = listener;
    }

    public interface AccountSelectorListener{
        void onAccountClick(Account account);
    }
    public AccountSelectorAdapter(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selector,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Account account = accounts.get(position);
        holder.txtItemSelector.setText(account.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!= null)listener.onAccountClick(account);
            }
        });
    }

    @Override
    public int getItemCount() { return accounts.size() ; }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtItemSelector;

       public ViewHolder(@NonNull View itemView){
           super(itemView);
           txtItemSelector = itemView.getRootView().findViewById(R.id.textViewSelector);
       }
    }

}
