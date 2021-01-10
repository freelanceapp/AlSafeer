package com.alsafeer.services;

import com.alsafeer.models.DealDataModel;
import com.alsafeer.models.JointDealDataModel;
import com.alsafeer.models.NotificationDataModel;
import com.alsafeer.models.PlaceGeocodeData;
import com.alsafeer.models.PlaceMapDetailsData;
import com.alsafeer.models.ReceiptDataModel;
import com.alsafeer.models.ResponseModel;
import com.alsafeer.models.UserModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("api/GetAllResets")
    Call<ReceiptDataModel> getCurrentPreviousDeals(@Query(value = "user_id") int user_id,
                                                   @Query(value = "type") String type
    );

    @Multipart
    @POST("api/PayReset")
    Call<ResponseModel> pay(@Part("user_id") RequestBody user_id,
                            @Part("notes") RequestBody notes,
                            @Part("receipt_id") RequestBody receipt_id,
                            @Part MultipartBody.Part image);

    @GET("api/GetNotification")
    Call<NotificationDataModel> getNotifications(@Query(value = "user_id") int user_id
    );

    @GET("api/logout")
    Call<ResponseModel> logout(@Query(value = "user_id") int user_id
    );


    @FormUrlEncoded
    @POST("api/UpdateProfile")
    Call<UserModel> updateProfileWithoutImage(@Field("user_id") int user_id,
                                              @Field("name") String name,
                                              @Field("email") String email,
                                              @Field("phone") String phone


    );

    @Multipart
    @POST("api/UpdateProfile")
    Call<UserModel> updateProfileWithImage(@Part("user_id") RequestBody user_id,
                                           @Part("name") RequestBody name,
                                           @Part("email") RequestBody email,
                                           @Part("phone") RequestBody phone,
                                           @Part MultipartBody.Part logo


    );

    @FormUrlEncoded
    @POST("api/UploadToken")
    Call<ResponseModel> updateFirebaseToken(@Field("user_id") int user_id,
                                            @Field("token") String token,
                                            @Field("type") String type

    );

    @GET("api/GetProfile")
    Call<UserModel> getUserById(@Query(value = "user_id") int user_id
    );

}