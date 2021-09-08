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
import com.mine.mvvmmitch.di.ui.auth.state.RegistrationFields
import com.mine.mvvmmitch.utill.ApiEmptyResponse
import com.mine.mvvmmitch.utill.ApiErrorResponse
import com.mine.mvvmmitch.utill.ApiSuccessResponse
import kotlinx.android.synthetic.main.fragment_register.*


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
        subscribeObserver()
        /* viewModel.testRegister().observe(viewLifecycleOwner, Observer {
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
    }

    override fun subscribeObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { authViewState ->

                authViewState.registrationFields?.let { registerationFields ->

                        registerationFields.registrationEmail?.let { email ->
                            input_email.setText(email)

                        }
                        registerationFields.registrationPassword?.let { password ->
                            input_password.setText(password)

                        }
                        registerationFields.registrationUserName?.let { userName ->
                            input_username.setText(userName)

                        }
                        registerationFields.registrationConfirmPassword?.let { confirmPassword ->
                            input_email.setText(confirmPassword)

                        }
                    }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: Registration")
        viewModel.setRegistrationFields(
            RegistrationFields(
                registrationEmail = input_email.text.toString(),
                registrationPassword = input_password.text.toString(),
                registrationUserName = input_username.text.toString(),
                registrationConfirmPassword = input_password_confirm.text.toString(),
            )
        )
    }
}