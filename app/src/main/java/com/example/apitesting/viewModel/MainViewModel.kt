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






    private val currentQuery = MutableLiveData("b")

    val users  = currentQuery.switchMap()
    {
        repository.searchResults(it).cachedIn(viewModelScope)

    }

    fun searchPhotos(query:String)
    {
        currentQuery.value = query
    }






















    var userResponse: MutableLiveData<NetworkResponse<ApiResponse>> = MutableLiveData()
    // private var searchUserResponse:MutableLiveData<NetworkResponse<User>> = MutableLiveData()

    fun searchUser(limit:Int,offset:Int,searchQuery:String) = viewModelScope.launch()
    {
        searchUserSafeCall(limit,offset,searchQuery)
    } // searchUser closed

    private suspend fun searchUserSafeCall(limit: Int, offset: Int, searchQuery: String)
    {
        userResponse.value = NetworkResponse.Loading()
        if(hasInternetConnection())
        {
            try
            {
                val response = repository.searchUser(limit,offset,searchQuery)
                userResponse.value = handleUserResponse(response)

            }catch (e: Exception)
            {
                userResponse.value = NetworkResponse.Error("No Users Found.")
            } // catch closed
        } /// if closed
        else
        {
            userResponse.value = NetworkResponse.Error("No Internet Connection")
        } // else closed
    } // searchUsersSafeCall closed


    private fun handleUserResponse(response: Response<ApiResponse>): NetworkResponse<ApiResponse>
    {
        return when
        {
            response.message().toString().contains("timeout") ->  NetworkResponse.Error("Time Out.")
            response.code() == 402                          -> NetworkResponse.Error("APi Key Limited.")
            response.body()?.data?.users.isNullOrEmpty() -> NetworkResponse.Error("Recipes Not Fount.")
            response.isSuccessful                           -> NetworkResponse.Success(response.body()!!)
            else -> NetworkResponse.Error(response.message().toString())
        } // return when closed
    } // handle User Response






    // check Network Availability
    private fun hasInternetConnection() : Boolean
    {
        val connectivityManager = getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when
        {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)-> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        } // return When closed
    } // hasInternetConnection



}