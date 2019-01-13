package me.susieson.receiptsafe;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FirebaseInterface {

    @GET(".json")
    Call<User> getAllInfo();

}
