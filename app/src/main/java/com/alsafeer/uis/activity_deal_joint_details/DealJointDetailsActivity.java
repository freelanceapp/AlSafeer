package com.alsafeer.uis.activity_deal_joint_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alsafeer.R;
import com.alsafeer.adapters.ProductAdapter;
import com.alsafeer.adapters.ProductAdapter2;
import com.alsafeer.databinding.ActivityDealDetailsBinding;
import com.alsafeer.databinding.ActivityDealJointDetailsBinding;
import com.alsafeer.language.Language;
import com.alsafeer.models.JointDealDataModel;
import com.alsafeer.models.ProductModel;
import com.alsafeer.uis.activity_receipt.ReceiptActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class DealJointDetailsActivity extends AppCompatActivity {
    private ActivityDealJointDetailsBinding binding;
    private JointDealDataModel.Data model;
    private List<ProductModel> productModelList;
    private ProductAdapter adapter;
    private String lang;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deal_joint_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        model = (JointDealDataModel.Data) intent.getSerializableExtra("data");

    }

    private void initView() {
        productModelList = new ArrayList<>();
        binding.setModel(model);
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);




        if (model.getProducts().size()>0){
            productModelList.addAll(model.getProducts());
            binding.recView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ProductAdapter(productModelList,this);
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