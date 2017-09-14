package com.melas.javadevelopers_lagos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.melas.javadevelopers_lagos.models.ApiResponse;
import com.melas.javadevelopers_lagos.models.Developer;
import com.melas.javadevelopers_lagos.rest.ApiClient;
import com.melas.javadevelopers_lagos.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DeveloperAdapter.ListItemClickListener {

    private ArrayList<Developer> developersList;
    private DeveloperAdapter adapter;
    private RecyclerView developersRecyclerView;
    private ApiInterface apiInterface;
    private TextView errorMessage;
    private ProgressBar loader;
    private final String query = "language:java+location:lagos";
    public static final String DATA_DEVELOPER = "developer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        developersRecyclerView = (RecyclerView) findViewById(R.id.developers_recycler_view);
        errorMessage = (TextView) findViewById(R.id.error_message);
        loader = (ProgressBar) findViewById(R.id.loader);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        developersList = new ArrayList<>();
        if (!isNetworkAvailable()) {
            errorMessage.setText(R.string.network_error);
            errorMessage.setVisibility(View.VISIBLE);
            loader.setVisibility(View.INVISIBLE);
            return;
        }
        getDevelopers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onListItemClick(int itemClickedIndex) {
        Developer developer = developersList.get(itemClickedIndex);
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(DATA_DEVELOPER, developer);
        startActivity(intent);
    }

    private void getDevelopers() {
        loader.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
        Call<ApiResponse> call = apiInterface.getDevelopers(query);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    for(Developer developer : response.body().getDevelopers()) {
                        developersList.add(developer);
                    }
                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    developersRecyclerView.setLayoutManager(manager);
                    developersRecyclerView.setHasFixedSize(true);
                    adapter = new DeveloperAdapter(developersList, MainActivity.this);
                    developersRecyclerView.setAdapter(adapter);
                    loader.setVisibility(View.INVISIBLE);
                    errorMessage.setVisibility(View.INVISIBLE);
                } else {
                    loader.setVisibility(View.INVISIBLE);
                    errorMessage.setText(R.string.error_message);
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                loader.setVisibility(View.INVISIBLE);
                errorMessage.setText(t.getMessage());
                errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}
