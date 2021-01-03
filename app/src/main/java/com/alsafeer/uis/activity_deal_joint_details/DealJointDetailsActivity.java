package com.alsafeer.uis.activity_deal_joint_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.alsafeer.R;
import com.alsafeer.databinding.ActivityDealDetailsBinding;
import com.alsafeer.databinding.ActivityDealJointDetailsBinding;
import com.alsafeer.language.Language;

import io.paperdb.Paper;

public class DealJointDetailsActivity extends AppCompatActivity {
    private ActivityDealJointDetailsBinding binding;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deal_joint_details);
        initView();
    }

    private void initView() {
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
        binding.llBack.setOnClickListener(v -> finish());

    }
}