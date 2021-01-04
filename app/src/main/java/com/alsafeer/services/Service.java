package com.alsafeer.services;

import com.alsafeer.models.DealDataModel;
import com.alsafeer.models.JointDealDataModel;
import com.alsafeer.models.PlaceGeocodeData;
import com.alsafeer.models.PlaceMapDetailsData;
import com.alsafeer.models.ReceiptDataModel;
import com.alsafeer.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("email") String email,
                          @Field("password") String password);


    @GET("api/GetDeals")
    Call<JointDealDataModel> getJointDealData(@Query(value = "user_id") int user_id);

    @GET("api/GetSales")
    Call<DealDataModel> getDealData(@Query(value = "user_id") int user_id);

    @GET("api/GetReset")
    Call<ReceiptDataModel> getReceiptData(@Query(value = "sale_id") int sale_id);
}