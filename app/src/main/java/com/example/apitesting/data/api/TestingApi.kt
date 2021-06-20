package com.example.apitesting.data.api

import com.example.apitesting.data.model.ApiResponse
import com.example.testingapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TestingApi
{


    @GET("user/all")
    suspend fun searchUser
    (
        @Query("limit")limit:Int,
        @Query("offset")offset:Int,
        @Query("searchQuery") query:String
    )
    : Response<ApiResponse>


    @GET("user/all")
    suspend fun getAllUsers
                (
        @Query("limit")limit:Int,
        @Query("offset")offset:Int,
    )
            : Response<ApiResponse>


}