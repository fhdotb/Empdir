package com.gmail.otb.fhd.mobileappcoursework.NetworkLayer;



import retrofit2.Retrofit;
//gson=json
import retrofit2.converter.gson.GsonConverterFactory;

//Creating the ApiClient
public class ApiClient {
    //baseUrl=used to set end point
    //addConverterFactory = Used to convert the JSON response into java Objects
    public static final String BASE_URL = "https://androidcoursework-84a4f.firebaseio.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
