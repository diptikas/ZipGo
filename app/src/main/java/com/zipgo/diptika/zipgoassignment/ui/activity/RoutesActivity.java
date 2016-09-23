package com.zipgo.diptika.zipgoassignment.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.zipgo.diptika.zipgoassignment.R;
import com.zipgo.diptika.zipgoassignment.ui.adapter.RoutesListAdapter;
import com.zipgo.diptika.zipgoassignment.ui.api.retrofit.ApiClient;
import com.zipgo.diptika.zipgoassignment.ui.api.retrofit.ApiInterface;
import com.zipgo.diptika.zipgoassignment.ui.viewmodel.RouteModel;
import com.zipgo.diptika.zipgoassignment.ui.viewmodel.StopModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Diptika on 19/09/16.
 */

public class RoutesActivity extends AppCompatActivity implements Callback<List<StopModel>> {

    private Spinner mPickUpSpinner;
    private Spinner mDestinationSpinner;
    private List<String> mStopList;
    private RecyclerView mRoutesRecyclerView;
    private RoutesListAdapter mRoutesListAdapter;
    private List<RouteModel> mRouteModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        initView();
        getStopList();
        getRoutes();
    }

    private void initView() {
        mStopList=new ArrayList<>();
        mRouteModelList = new ArrayList<>();

        mPickUpSpinner=(Spinner)findViewById(R.id.spinner_pickup);
        mDestinationSpinner=(Spinner)findViewById(R.id.spinner_destination);
        mRoutesRecyclerView = (RecyclerView) findViewById(R.id.route_list);

    }

    private void getRoutes() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<RouteModel>> call = apiService.getRoutesDetail();
        call.enqueue(new Callback<List<RouteModel>>() {
            @Override
            public void onResponse(Call<List<RouteModel>>call, Response<List<RouteModel>> response) {
                for (RouteModel routemodel : response.body()) {
                    mRouteModelList.add(routemodel);
                }
                mRoutesRecyclerView.setLayoutManager(new LinearLayoutManager(RoutesActivity.this));
                mRoutesListAdapter = new RoutesListAdapter(RoutesActivity.this,mRouteModelList);
                mRoutesRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRoutesRecyclerView.setAdapter(mRoutesListAdapter);

            }
            @Override
            public void onFailure(Call<List<RouteModel>>call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something went wrong.Please Try Again",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getStopList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StopModel>> call = apiService.getStopList();
        Log.d("url", String.valueOf(call.request().url()));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<StopModel>> call, Response<List<StopModel>> response) {
        if(response!=null) {

            for (StopModel stopmodel : response.body()) {
                mStopList.add(stopmodel.getName());
            }

            ArrayAdapter<String> stopAdapter=new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item,mStopList);
            mPickUpSpinner.setAdapter(stopAdapter);
            mDestinationSpinner.setAdapter(stopAdapter);

        }
    }

    @Override
    public void onFailure(Call<List<StopModel>> call, Throwable t) {
        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
    }
}
