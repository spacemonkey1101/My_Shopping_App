package com.example.MyShoppingApp.interfaces;

import com.example.MyShoppingApp.model.LocationPOJO;
import com.example.MyShoppingApp.model.LoginPOJO;
import com.example.MyShoppingApp.model.SignUpPOJO;
import com.example.MyShoppingApp.model.SubcategoryPOJO;
import com.example.MyShoppingApp.model.CategoryPOJO;
import com.example.MyShoppingApp.model.OtpPOJO;
import com.example.MyShoppingApp.model.QuestionPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolder {

    //POST request
    @POST("signup/")
    Call<SignUpPOJO> submitUser(@Body SignUpPOJO post);

    @POST("login/")
    Call<LoginPOJO> loginUser(@Body LoginPOJO post);

    //POST request
    @POST("activate/{user_id}/")
    Call<OtpPOJO> verifyOtp(@Path("user_id") int user_id, @Body OtpPOJO otp);

    @GET("resend_otp/{user_id}/")
    Call<OtpPOJO> resendOtp(@Path("user_id")  int user_id);


    @POST("user_current_location/")
    Call<LocationPOJO> sendUserLocation(@Header ("Authorization") String token, @Body LocationPOJO locationPOJO);

    @GET("category/")
    Call<List<CategoryPOJO>> getCategories();

    @GET("subcategory/")
    Call<List<SubcategoryPOJO>> getSubCategories(@Query("category") String id);

    @GET("subcategory/{subcategory_id}/attribute_questions/")
    Call<List<QuestionPOJO>> getQuestions(@Path("subcategory_id") String id);

}
