package com.alsafeer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alsafeer.R;
import com.alsafeer.databinding.DealRowBinding;
import com.alsafeer.databinding.ReceiptRowBinding;
import com.alsafeer.models.DealDataModel;
import com.alsafeer.models.ReceiptDataModel;
import com.alsafeer.uis.activity_home.fragments.fragment_deals.Fragment_Deal;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ReceiptDataModel.ReceiptModel> list;
    private Context context;
    private LayoutInflater inflater;

    public ReceiptAdapter(List<ReceiptDataModel.ReceiptModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReceiptRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.receipt_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ReceiptDataModel.ReceiptModel model = list.get(position);
        myHolder.binding.setModel(model);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder
    {
        public ReceiptRowBinding binding;

        public MyHolder(@NonNull ReceiptRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }


}
