package com.example.apitesting.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.apitesting.data.model.ApiResponse
import com.example.apitesting.repository.Repository
import com.example.testingapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: Repository,application: Application) : AndroidViewModel(application)
{


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