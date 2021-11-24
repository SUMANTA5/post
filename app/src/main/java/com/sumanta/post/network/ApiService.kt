package com.sumanta.post.network

import androidx.room.Delete
import com.sumanta.post.model.Phone
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/v1/phone")
    suspend fun getPhone(): List<Phone>

    @FormUrlEncoded
    @POST("v1/phone")
    suspend fun setPhone(
        @Field("name") name: String,
        @Field("phoneNo") phoneNo: Long
    ): Phone

    @DELETE("/v1/phone/{userId}")
    suspend fun delete(
        @Path("userId") userId: Int
    ): Response<Unit>

}