package com.alsafeer.uis.activity_deal_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.alsafeer.R;
import com.alsafeer.adapters.ProductAdapter2;
import com.alsafeer.databinding.ActivityDealDetailsBinding;
import com.alsafeer.databinding.ActivitySplashBinding;
import com.alsafeer.language.Language;
import com.alsafeer.models.DealDataModel;
import com.alsafeer.models.ProductModel2;
import com.alsafeer.uis.activity_login.LoginActivity;
import com.alsafeer.uis.activity_receipt.ReceiptActivity;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class DealDetailsActivity extends AppCompatActivity {
    private ActivityDealDetailsBinding binding;
    private DealDataModel.Data model;
    private String lang;
    private List<ProductModel2> productModelList;
    private ProductAdapter2 adapter;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deal_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (DealDataModel.Data) intent.getSerializableExtra("data");

    }

    private void initView() {
        productModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.setModel(model);


        if (model.getDetails().size()>0){
            productModelList.addAll(model.getDetails());
            binding.recView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ProductAdapter2(productModelList,this);
            binding.recView.setAdapter(adapter);
            binding.tvNoData.setVisibility(View.GONE);
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }

        binding.llProducts.setOnClickListener(v -> {
            if (binding.expandedLayout.isExpanded()){
                binding.arrow.clearAnimation();
                binding.arrow.animate().rotation(0).setDuration(300).start();
                binding.expandedLayout.collapse(true);

            }else {
                binding.arrow.clearAnimation();
                binding.arrow.animate().rotation(180).setDuration(300).start();
                binding.expandedLayout.expand(true);

            }
        });

        binding.btnBills.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReceiptActivity.class);
            intent.putExtra("id",model.getId());
            startActivity(intent);
        });
        binding.llBack.setOnClickListener(v -> finish());

    }
}