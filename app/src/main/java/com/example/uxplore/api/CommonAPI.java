package com.example.uxplore.api;

import com.example.uxplore.response.CommonResponse;
import com.example.uxplore.response.GenericResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CommonAPI {

    @FormUrlEncoded
    @POST("login")
    Call<CommonResponse> login(@Field("from") String from,
                               @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<GenericResponse> register(@Field("usertypeid") String usertypeid,
                                   @Field("emailaddress") String emailaddress,
                                   @Field("mobilenumber") String mobilenumber,
                                   @Field("password") String password,
                                   @Field("ipaddress")String ipaddress
    );
    @FormUrlEncoded
    @POST("logout")
    Call<GenericResponse> logout(@Field("ID") String ID
    );

    @FormUrlEncoded
    @POST("destinations")
    Call<GenericResponse> destinations(@Field("ip") String ip);

    @FormUrlEncoded
    @POST("packages")
    Call<GenericResponse> packages(@Field("ip") String ip);

    @FormUrlEncoded
    @POST("package/spot")
    Call<GenericResponse> packageDetails (@Field("packageid") String packageid);

    @FormUrlEncoded
    @POST("destination")
    Call<GenericResponse> destination(@Field("spotid") String packageid);

    @FormUrlEncoded
    @POST("transaction")
    Call<GenericResponse> transaction(@Field("paymentid") String paymentid,
                                      @Field("paymentamount") String paymentamount,
                                      @Field("packageid") String  packageid,
                                      @Field("paymenttype") String paymenttype,
                                      @Field("userid") String userid,
                                      @Field("status") String status,
                                      @Field("driverid") String driverid);


}
