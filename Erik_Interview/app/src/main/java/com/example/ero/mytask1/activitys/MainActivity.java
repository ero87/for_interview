package com.example.ero.mytask1.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ero.mytask1.R;
import com.example.ero.mytask1.adapters.UserAdapter;
import com.example.ero.mytask1.models.ModelForUser;
import com.example.ero.mytask1.models.Result;
import com.example.ero.mytask1.services.ApiServices;
import com.example.ero.mytask1.services.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String Tag = "Main";
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listLoader();
        retrofitClient();
    }

    private void listLoader() {
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        adapter = new UserAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void retrofitClient() {
        ApiServices client = Retrofit.getClient().create(ApiServices.class);
        client.randomUsers(7).enqueue(new Callback<ModelForUser>() {
            @Override
            public void onResponse(@NonNull Call<ModelForUser> call,
                                   @NonNull Response<ModelForUser> response) {
                List<Result> list = response.body().getResults();
                adapter.setData(list);
            }

            @Override
            public void onFailure(@NonNull Call<ModelForUser> call, @NonNull Throwable t) {
                Log.e(Tag, t.toString());
            }
        });
    }


}
