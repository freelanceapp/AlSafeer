package com.alsafeer.uis.activity_notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alsafeer.R;
import com.alsafeer.adapters.NotificationAdapter;
import com.alsafeer.databinding.ActivityNotificationBinding;
import com.alsafeer.language.Language;
import com.alsafeer.models.NotificationDataModel;
import com.alsafeer.models.UserModel;
import com.alsafeer.preferences.Preferences;
import com.alsafeer.remote.Api;
import com.alsafeer.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity  {

    private ActivityNotificationBinding binding;
    private String lang;
    private List<NotificationDataModel.NotificationModel> notificationModelList;
    private NotificationAdapter adapter;
    private Preferences preferences;
    private UserModel userModel;
    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        initView();

    }

    private void initView() {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager!=null){
            manager.cancel(Tags.not_tag,Tags.not_id);
        }

        notificationModelList = new ArrayList<>();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(notificationModelList,this);
        binding.recView.setAdapter(adapter);
        binding.llBack.setOnClickListener(view -> finish());

        getNotifications();
    }

    private void getNotifications() {

        Call<NotificationDataModel> call = Api.getService(Tags.base_url).
                getNotifications( userModel.getData().getId());

        call.enqueue(new Callback<NotificationDataModel>() {
            @Override
            public void onResponse(Call<NotificationDataModel> call, Response<NotificationDataModel> response) {
                binding.progBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().status==200){
                        if (response.body().data!=null&&response.body().getData().size()>0){
                            binding.tvNoData.setVisibility(View.GONE);
                            notificationModelList.clear();
                            notificationModelList.addAll(response.body().getData());
                            adapter.notifyDataSetChanged();
                        }else {
                            binding.tvNoData.setVisibility(View.VISIBLE);

                        }
                    }else {
                        Toast.makeText(NotificationActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    binding.progBar.setVisibility(View.GONE);

                    try {
                        Toast.makeText(NotificationActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        Log.e("error", response.code() + "_" + response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationDataModel> call, Throwable t) {
                try {
                    binding.progBar.setVisibility(View.GONE);
                    if (t.getMessage() != null) {
                        Log.e("error", t.getMessage() + "__");

                        if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                            Toast.makeText(NotificationActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();


                        } else if (t.getMessage().toLowerCase().equals("Canceled".toLowerCase()) || t.getMessage().toLowerCase().contains("Socket closed".toLowerCase())) {

                        } else {
                            Toast.makeText(NotificationActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();

                        }
                    }


                } catch (Exception e) {
                }

            }
        });
    }


}