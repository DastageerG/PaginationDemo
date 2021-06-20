package com.example.apitesting.repository

import com.example.apitesting.data.api.TestingApi
import com.example.apitesting.data.model.ApiResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val api: TestingApi)
{

    suspend fun searchUser(limit:Int,offset:Int,searchQuery:String) : Response<ApiResponse> =
        api.searchUser(limit,offset,searchQuery)

    suspend fun getAllUsers(limit:Int,offset:Int) : Response<ApiResponse> =
        api.getAllUsers(limit,offset)

}