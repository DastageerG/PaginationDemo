package com.example.apitesting.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.apitesting.data.api.TestingApi
import com.example.apitesting.data.model.ApiResponse
import com.example.apitesting.data.model.UserPagingSource
import kotlinx.coroutines.flow.Flow
import org.w3c.dom.CharacterData
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val api: TestingApi)
{

    suspend fun searchUser(limit:Int,offset:Int,searchQuery:String) : Response<ApiResponse> =
        api.searchUser(limit,offset,searchQuery)

    suspend fun getAllUsers(limit:Int,offset:Int) : Response<ApiResponse> =
        api.getAllUsers(limit,offset)


    fun searchResults(query: String)=
        Pager(PagingConfig(pageSize = 20))
        {
            UserPagingSource(api,query)
        }.liveData





} // RepoClosed