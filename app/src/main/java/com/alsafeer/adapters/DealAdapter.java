package com.alsafeer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alsafeer.R;
import com.alsafeer.databinding.DealJointRowBinding;
import com.alsafeer.databinding.DealRowBinding;
import com.alsafeer.models.DealDataModel;
import com.alsafeer.models.JointDealDataModel;
import com.alsafeer.uis.activity_home.fragments.fragment_deals.Fragment_Deal;
import com.alsafeer.uis.activity_home.fragments.fragment_deals.Fragment_Joint_Deal;

import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DealDataModel.Data> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment_Deal fragment_deal;
    public DealAdapter(List<DealDataModel.Data> list, Context context, Fragment_Deal fragment_deal) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment_deal =fragment_deal;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        DealRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.deal_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        DealDataModel.Data model = list.get(position);
        myHolder.binding.setModel(model);
        myHolder.itemView.setOnClickListener(v -> {
            DealDataModel.Data model2 = list.get(myHolder.getAdapterPosition());
            fragment_deal.setItemData(model2);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public DealRowBinding binding;

        public MyHolder(@NonNull DealRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
