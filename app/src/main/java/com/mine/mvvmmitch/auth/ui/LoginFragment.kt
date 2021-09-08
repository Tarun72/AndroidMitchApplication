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
import com.mine.mvvmmitch.di.ui.auth.state.LoginFields
import com.mine.mvvmmitch.utill.ApiEmptyResponse
import com.mine.mvvmmitch.utill.ApiErrorResponse
import com.mine.mvvmmitch.utill.ApiSuccessResponse
import com.mine.mvvmmitch.utill.GenericApiResponse
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseAuthFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "LoginFragment: ${viewModel}")
/*
        viewModel.testLogin().observe(viewLifecycleOwner, Observer {
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
        })*/
        subscribeObserver()
    }

    override fun subscribeObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { authViewState ->
            authViewState.loginFields?.let { loginFields: LoginFields ->

                loginFields.login_email?.let { email ->
                    input_email.setText(email)
                }
                loginFields.login_password?.let { password ->
                    input_password.setText(password)
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoginFields(
            LoginFields(
                login_email = input_email.toString(),
                login_password = input_password.toString()
            )
        )
    }
}