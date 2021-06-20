package com.example.apitesting.data.model

import android.app.DownloadManager
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.apitesting.data.api.TestingApi
import com.example.testingapp.utils.Constants
import com.example.testingapp.utils.Constants.TAG
import com.example.testingapp.utils.Constants.USERS_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(private val api:TestingApi,private val query:String?) : PagingSource<Int,User>()
{


//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,User> {
//
//        val position = params.key ?: USERS_PAGE_INDEX
//
//        return try {
//
//            val response = api.searchUser(params.loadSize,position,query)
//            val users = response.body()?.data?.users!!
//
//
//
//            LoadResult.Page(
//                    data = users,
//                    prevKey = if (position == USERS_PAGE_INDEX) null else position - 1,
//                    nextKey = if (position == USERS_PAGE_INDEX) null else position + 1
//            )
//        } catch (exception: IOException) {
//            LoadResult.Error(exception)
//        } catch (e: HttpException) {
//            LoadResult.Error(e)
//        }
//    }
    override fun getRefreshKey(state: PagingState<Int, User>): Int?
    {
        return  state.anchorPosition?.let ()
        {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1

        return try {

            val response = api.searchUser(params.loadSize,page,query!!)

            LoadResult.Page(
                    data = response.body()?.data?.users!!,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.body()?.data?.users?.isEmpty()!!) null else page + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }









} // UserPagingSource