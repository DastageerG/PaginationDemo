package com.example.apitesting.data.model

import android.app.DownloadManager
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.apitesting.data.api.TestingApi
import com.example.testingapp.utils.Constants
import com.example.testingapp.utils.Constants.TAG
import com.google.android.material.snackbar.Snackbar
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class UserPagingSource(private val api:TestingApi,private val query:String?=null) : PagingSource<Int,User>() , ActionMode.Callback
{


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

            var  response:Response<ApiResponse>
            // here response will be assigned based on if we have query or not

            if(query!=null)
            {
                response = api.searchUser(params.loadSize,page,query)
            }
            else
            {
                response = api.getAllUsers(params.loadSize,page)
            }


            LoadResult.Page(
                    data = response.body()?.data?.users!!,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.body()?.data?.users?.isEmpty()!!) null else page + 1
            )


        } catch (e: Exception)
        {
            LoadResult.Error(e)
        } // catch closed


    } // load closed

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean
    {
        TODO("Not yet implemented")
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean
    {
        TODO("Not yet implemented")
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean
    {
        TODO("Not yet implemented")
    }

    override fun onDestroyActionMode(mode: ActionMode?)
    {
        TODO("Not yet implemented")
    }


} // UserPagingSource