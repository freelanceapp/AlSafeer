package com.alsafeer.uis.activity_receipt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alsafeer.R;
import com.alsafeer.adapters.ProductAdapter2;
import com.alsafeer.adapters.ReceiptAdapter;
import com.alsafeer.databinding.ActivityDealDetailsBinding;
import com.alsafeer.databinding.ActivityReceiptBinding;
import com.alsafeer.language.Language;
import com.alsafeer.models.DealDataModel;
import com.alsafeer.models.ProductModel2;
import com.alsafeer.models.ReceiptDataModel;
import com.alsafeer.remote.Api;
import com.alsafeer.tags.Tags;
import com.alsafeer.uis.activity_pay.PayActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptActivity extends AppCompatActivity {
    private ActivityReceiptBinding binding;
    private int id;
    private String lang;
    private List<ReceiptDataModel.ReceiptModel> receiptModelList;
    private ReceiptAdapter adapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_receipt);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        id = (int) intent.getSerializableExtra("id");

    }

    private void initView() {
        receiptModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReceiptAdapter(receiptModelList,this);
        binding.recView.setAdapter(adapter);
        binding.llBack.setOnClickListener(v -> finish());

        getData();
    }

    private void getData()
    {
        binding.progBar.setVisibility(View.VISIBLE);
        receiptModelList.clear();
        adapter.notifyDataSetChanged();
        binding.tvNoData.setVisibility(View.GONE);

        Api.getService(Tags.base_url)
                .getReceiptData(id)
                .enqueue(new Callback<ReceiptDataModel>() {
                    @Override
                    public void onResponse(Call<ReceiptDataModel> call, Response<ReceiptDataModel> response) {
                        binding.progBar.setVisibility(View.GONE);
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
                                }else {
                                    binding.tvNoData.setVisibility(View.VISIBLE);

                                }
                            }else {
                                Toast.makeText(ReceiptActivity.this,getString(R.string.failed), Toast.LENGTH_SHORT).show();

                            }


                        } else {
                            binding.progBar.setVisibility(View.GONE);

                            switch (response.code()){
                                case 500:
                                    Toast.makeText(ReceiptActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                                    break;

                                default:
                                    Toast.makeText(ReceiptActivity.this,getString(R.string.failed), Toast.LENGTH_SHORT).show();
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
                            if (t.getMessage() != null) {
                                Log.e("error", t.getMessage());
                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(ReceiptActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                }
                                else if (t.getMessage().toLowerCase().contains("socket")||t.getMessage().toLowerCase().contains("canceled")){ }
                                else {
                                    Toast.makeText(ReceiptActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                });
    }

    public void pay(ReceiptDataModel.ReceiptModel model) {
        Intent intent = new Intent(this, PayActivity.class);
        intent.putExtra("data",model);
        startActivityForResult(intent,100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            getData();
        }
    }
}