package com.example.apitesting.data.api

import com.example.apitesting.data.model.ApiResponse
import com.example.testingapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TestingApi
{

    // search a user with query
    @GET("user/all")
    suspend fun searchUser
    (
        @Query("limit")limit:Int,
        @Query("offset")offset:Int,
        @Query("searchQuery") query:String
    )
    : Response<ApiResponse>


    // we will use it to show all users without query
    @GET("user/all")
    suspend fun getAllUsers
                (
        @Query("limit")limit:Int,
        @Query("offset")offset:Int,
    )
            : Response<ApiResponse>


} // TestingApi