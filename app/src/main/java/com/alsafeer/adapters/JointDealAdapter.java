package com.alsafeer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alsafeer.R;
import com.alsafeer.databinding.DealJointRowBinding;
import com.alsafeer.models.JointDealDataModel;
import com.alsafeer.uis.activity_home.fragments.fragment_deals.Fragment_Joint_Deal;

import java.util.List;

public class JointDealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<JointDealDataModel.Data> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment_Joint_Deal fragment_joint_deal;
    public JointDealAdapter(List<JointDealDataModel.Data> list, Context context,Fragment_Joint_Deal fragment_joint_deal) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment_joint_deal =fragment_joint_deal;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        DealJointRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.deal_joint_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        JointDealDataModel.Data model = list.get(position);
        myHolder.binding.setModel(model);
        myHolder.itemView.setOnClickListener(v -> {
            JointDealDataModel.Data model2 = list.get(myHolder.getAdapterPosition());
            fragment_joint_deal.setItemData(model2);
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public DealJointRowBinding binding;

        public MyHolder(@NonNull DealJointRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
