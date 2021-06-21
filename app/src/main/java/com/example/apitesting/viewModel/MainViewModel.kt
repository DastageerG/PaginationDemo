package com.example.apitesting.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.apitesting.data.model.ApiResponse
import com.example.apitesting.data.model.User
import com.example.apitesting.data.model.UserPagingSource
import com.example.apitesting.repository.Repository
import com.example.testingapp.utils.Constants.TAG
import com.example.testingapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants
import org.w3c.dom.CharacterData
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: Repository,application: Application) : AndroidViewModel(application)
{






    private val currentQuery = MutableLiveData("a")

    val users  = currentQuery.switchMap()
    {

        repository.searchResults().cachedIn(viewModelScope)

    }

    // not Implemented
    fun searchUsers(query:String)
    {
        currentQuery.value = query
    } // searchUsers closed


} // MainViewModel closed