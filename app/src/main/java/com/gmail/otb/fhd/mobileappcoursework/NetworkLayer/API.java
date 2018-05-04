package com.gmail.otb.fhd.mobileappcoursework.NetworkLayer;

import com.gmail.otb.fhd.mobileappcoursework.model.Employee;
import com.gmail.otb.fhd.mobileappcoursework.model.JsonResponse;

/**
 * Created by fahadalms3odi on 4/19/18.
 */


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

//The interface defines the possible HTTP operations and manages all URL calls
public interface API {
    @GET("/.json")
    Call<JsonResponse> getDetails();

    @POST("/offices/0/{employees}.json")
    Call<Employee> addEmployeeFirstOffice(@Body Employee employee);

    @POST("/offices/1/{employees}.json")
    Call<Employee> addEmployeeSecondOffice(@Body Employee employee);


//    @PUT("/offices/0/{employees}.json")
//    Call<Employee> addEmployeeFirstOffice(
//            @Path("employees") String title,
//            @Body Employee employee);



}
