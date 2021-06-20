package com.example.apitesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apitesting.adapter.UserAdapter
import com.example.apitesting.data.model.ApiResponse
import com.example.apitesting.databinding.ActivityMainBinding
import com.example.apitesting.viewModel.MainViewModel
import com.example.testingapp.utils.Constants
import com.example.testingapp.utils.Constants.TAG
import com.example.testingapp.utils.NetworkResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{

    private var _binding: ActivityMainBinding?=null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val adapter by lazy { UserAdapter() }
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

                binding.apply()
        {

            recycleViewMain.layoutManager = LinearLayoutManager(applicationContext)
            recycleViewMain.adapter = adapter
            recycleViewMain.addItemDecoration(
                    DividerItemDecoration(
                            applicationContext,
                            resources.configuration.orientation
                    )
            )
        }



        viewModel.searchUser(50,1,"a")
        viewModel.userResponse.observe({lifecycle})
        {
            it.let()
            {
                getUserData(it)
            } // it
        } // viewModel


    } // onCreate closed

    private fun getUserData(response: NetworkResponse<ApiResponse>?)
    {
        when(response)
        {
            is NetworkResponse.Success ->
            {
                hideProgressBar()
                response.data?.data?.users.let()
                {
                    adapter.submitList(it)
                } // response closed
            } // is Success closed

            is NetworkResponse.Error ->
            {
                hideProgressBar()
                Toast.makeText(this,"No Internet Connection", Toast.LENGTH_SHORT).show()
                Log.d(Constants.TAG, "requestApiData: "+response.message)
            } /// is Error closed

            is NetworkResponse.Loading ->
            {
                showProgressBar()
            } // isLoading closing

        } // when closed
    } /// getUsersData closed


    fun showProgressBar()
    {
        binding.progressBar.visibility = View.VISIBLE
    } // showProgressBar

    fun hideProgressBar()
    {
        binding.progressBar.visibility = View.GONE
    } // showProgressBar


} // class closed