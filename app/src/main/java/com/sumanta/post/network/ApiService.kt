package com.sumanta.post.network

import com.sumanta.post.model.Phone
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("/v1/phone")
    suspend fun getPhone(): List<Phone>

    @FormUrlEncoded
    @POST("v1/phone")
    suspend fun setPhone(
        @Field("name") name: String,
        @Field("phoneNo") phoneNo: Long
    ): Phone

}