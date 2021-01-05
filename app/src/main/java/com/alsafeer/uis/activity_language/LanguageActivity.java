package com.alsafeer.uis.activity_language;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alsafeer.R;
import com.alsafeer.databinding.ActivityLanguageBinding;
import com.alsafeer.language.Language;

import io.paperdb.Paper;

public class LanguageActivity extends AppCompatActivity {
    private ActivityLanguageBinding binding;
    private String lang = "";

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_language);
        initView();
    }

    private void initView() {
        binding.tv1.setText(getString(R.string.change_language));
        Paper.init(this);
        lang = Paper.book().read("lang","ar");

        if (lang.equals("ar")){
            binding.flAr.setBackgroundResource(R.drawable.small_stroke_primary);
            binding.flEn.setBackgroundResource(0);

        }else {
            binding.flAr.setBackgroundResource(0);
            binding.flEn.setBackgroundResource(R.drawable.small_stroke_primary);

        }
        binding.btnNext.setVisibility(View.VISIBLE);

        binding.cardAr.setOnClickListener(view -> {
            lang = "ar";
            binding.flAr.setBackgroundResource(R.drawable.small_stroke_primary);
            binding.flEn.setBackgroundResource(0);
            binding.btnNext.setVisibility(View.VISIBLE);

        });

        binding.cardEn.setOnClickListener(view -> {
            lang = "en";
            binding.flAr.setBackgroundResource(0);
            binding.flEn.setBackgroundResource(R.drawable.small_stroke_primary);
            binding.btnNext.setVisibility(View.VISIBLE);
        });

        binding.btnNext.setOnClickListener(view -> {
            Intent intent = getIntent();
            intent.putExtra("lang",lang);
            setResult(RESULT_OK,intent);
            finish();
        });
    }
}