package com.mine.mvvmmitch.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mine.mvvmmitch.BaseActivity
import com.mine.mvvmmitch.R
import com.mine.mvvmmitch.di.ViewModelProviderFactory
import com.mine.mvvmmitch.di.ui.auth.AuthViewModel
import com.mine.mvvmmitch.main.MainActivity
import javax.inject.Inject

class AuthActivity : BaseActivity(){
    @Inject
    lateinit var viewProvideFactoryModule: ViewModelProviderFactory
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        authViewModel =  ViewModelProvider(this,viewProvideFactoryModule).get(AuthViewModel::class.java)
        subscribeObserver()

    }

    fun subscribeObserver() {
        authViewModel.viewState.observe(this, Observer {
            it.authToken?.let {
                sessionManager.login(it)
            }

        })

        sessionManager.cacheToken.observe(this, Observer { authToken ->
            if (authToken != null &&  authToken.account_pk != -1 && authToken.token == null) {
                navMainActivity()
            }
            Log.d(TAG, "subscribeObserver: $authToken")
        })
    }


    private fun navMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}