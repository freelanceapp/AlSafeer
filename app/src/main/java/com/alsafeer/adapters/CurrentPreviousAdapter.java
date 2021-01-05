package com.alsafeer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alsafeer.R;
import com.alsafeer.databinding.CurrentPreviousReceiptRowBinding;
import com.alsafeer.databinding.ReceiptRowBinding;
import com.alsafeer.models.ReceiptDataModel;
import com.alsafeer.uis.activity_home.fragments.fragment_bills.Fragment_Current;

import java.util.List;

public class CurrentPreviousAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ReceiptDataModel.ReceiptModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public CurrentPreviousAdapter(List<ReceiptDataModel.ReceiptModel> list, Context context,Fragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CurrentPreviousReceiptRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.current_previous_receipt_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ReceiptDataModel.ReceiptModel model = list.get(position);
        myHolder.binding.setModel(model);
        myHolder.binding.btnPay.setOnClickListener(v -> {
            ReceiptDataModel.ReceiptModel model2 = list.get(myHolder.getAdapterPosition());
            if (fragment instanceof Fragment_Current){
                Fragment_Current fragment_current = (Fragment_Current) fragment;
                fragment_current.setItemData(model2);
            }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        public CurrentPreviousReceiptRowBinding binding;

        public MyHolder(@NonNull CurrentPreviousReceiptRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
