package com.mycompany.testtask;


import android.sax.RootElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by Slava on 26.02.2018.
 */

public interface ApiService {

    @GET("http://jsonplaceholder.typicode.com/users")
   Call<List<ContactPOJO>> getMyGSON() ;


}
