package com.gmail.otb.fhd.mobileappcoursework.NetworkLayer;

import com.gmail.otb.fhd.mobileappcoursework.model.JsonResponse;

/**
 * Created by fahadalms3odi on 4/19/18.
 */


import retrofit2.Call;
import retrofit2.http.GET;

//The interface defines the possible HTTP operations and manages all URL calls
public interface API {
    @GET("/.json")
    Call<JsonResponse> getDetails();

}
