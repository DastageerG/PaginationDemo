package com.example.apitesting.data.api

import com.example.apitesting.data.model.ApiResponse
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


    // we show all the users in a paginatedResponse
    @GET("user/all")
    suspend fun getAllUsers
                (
        @Query("limit")limit:Int,
        @Query("offset")offset:Int,
    )
            : Response<ApiResponse>


} // TestingApi