package com.alsafeer.uis.activity_home.fragments.fragment_bills;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alsafeer.R;
import com.alsafeer.adapters.CurrentPreviousAdapter;
import com.alsafeer.databinding.FragmentDealJointBinding;
import com.alsafeer.models.DealDataModel;
import com.alsafeer.models.ReceiptDataModel;
import com.alsafeer.models.UserModel;
import com.alsafeer.preferences.Preferences;
import com.alsafeer.remote.Api;
import com.alsafeer.tags.Tags;
import com.alsafeer.uis.activity_home.HomeActivity;
import com.alsafeer.uis.activity_pay.PayActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Current extends Fragment  {
    private FragmentDealJointBinding binding;
    private HomeActivity activity;
    private CurrentPreviousAdapter adapter;
    private List<ReceiptDataModel.ReceiptModel> receiptModelList;
    private Preferences preferences;
    private UserModel userModel;




    public static Fragment_Current newInstance() {
        return new Fragment_Current();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_deal_joint, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        receiptModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new CurrentPreviousAdapter(receiptModelList,activity,this);
        binding.recView.setAdapter(adapter);
        binding.swipeRefresh.setOnRefreshListener(() -> getData());
        getData();


    }

    public void getData()
    {

        Api.getService(Tags.base_url)
                .getCurrentPreviousDeals(userModel.getData().getId(),"no")
                .enqueue(new Callback<ReceiptDataModel>() {
                    @Override
                    public void onResponse(Call<ReceiptDataModel> call, Response<ReceiptDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
                        binding.swipeRefresh.setRefreshing(false);
                        if (response.isSuccessful()) {

                            if (response.body().getStatus()==200){
                                if(response.body() != null&&response.body().getData()!=null){
                                    if (response.body().getData().size()>0){
                                        receiptModelList.clear();
                                        receiptModelList.addAll(response.body().getData());
                                        adapter.notifyDataSetChanged();
                                        binding.tvNoData.setVisibility(View.GONE);
                                    }else {
                                        binding.tvNoData.setVisibility(View.VISIBLE);

                                    }
                                }
                            }else {
                                Toast.makeText(activity,getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }


                        } else {
                            binding.progBar.setVisibility(View.GONE);
                            binding.swipeRefresh.setRefreshing(false);

                            switch (response.code()){
                                case 500:
                                    Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;

                                default:
                                    Toast.makeText(activity,getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            try {
                                Log.e("error_code",response.code()+"_"+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<ReceiptDataModel> call, Throwable t) {
                        try {
                            binding.progBar.setVisibility(View.GONE);
                            binding.swipeRefresh.setRefreshing(false);

                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(activity, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                }
                                else if (t.getMessage().toLowerCase().contains("socket")||t.getMessage().toLowerCase().contains("canceled")){ }
                                else {
                                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });
    }

    public void setItemData(ReceiptDataModel.ReceiptModel model) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra("data",model);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode== Activity.RESULT_OK){
            activity.refreshFragmentPreviousDeals();
            Log.e("1","1");
        }
    }
}
