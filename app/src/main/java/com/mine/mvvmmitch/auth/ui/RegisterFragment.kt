package com.mine.mvvmmitch.auth.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mine.mvvmmitch.R
import com.mine.mvvmmitch.di.ui.BaseAuthFragment
import com.mine.mvvmmitch.utill.ApiEmptyResponse
import com.mine.mvvmmitch.utill.ApiErrorResponse
import com.mine.mvvmmitch.utill.ApiSuccessResponse


class RegisterFragment : BaseAuthFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "RegisterFragment: ${viewModel}")

        viewModel.testRegister().observe(viewLifecycleOwner, Observer {
                response ->{
            when(response){
                is ApiEmptyResponse ->{
                    Log.d(TAG, "ApiEmptyResponse:")
                }
                is ApiErrorResponse -> {
                    Log.d(TAG, "ApiErrorResponse:${response.errorMessage} ")
                }
                is ApiSuccessResponse -> {
                    Log.d(TAG, "ApiSuccessResponse: ${response.body} ")
                }
            }
        }
        })
    }
}