package com.example.apitesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apitesting.adapter.LoadingStateAdapter
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
            recycleViewMain.adapter = adapter.withLoadStateFooter(LoadingStateAdapter())
            recycleViewMain.addItemDecoration(
                    DividerItemDecoration(
                            applicationContext,
                            resources.configuration.orientation
                    )  // DividerItemDecoration closed
            ) // addItemDecoration closed
        } // binding.apply closed



        viewModel.users.observe({lifecycle})
        {
            adapter.submitData(lifecycle,it)
        } // userObserver closed

    } // onCreate closed





} // class closed