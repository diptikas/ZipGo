package com.zipgo.diptika.zipgoassignment.ui.api.retrofit;

import com.zipgo.diptika.zipgoassignment.ui.viewmodel.RouteModel;
import com.zipgo.diptika.zipgoassignment.ui.viewmodel.StopModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by Diptika on 19/09/16.
 */

public interface  ApiInterface {

    @GET("57dd85c71100000302a2def5")
    Call<List<StopModel>> getStopList();

    @GET("57dd88221100005402a2def7")
    Call<List<RouteModel>> getRoutesDetail();

}