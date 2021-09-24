package com.mine.mvvmmitch.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.mine.mvvmmitch.BaseActivity
import com.mine.mvvmmitch.R
import com.mine.mvvmmitch.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tool_bar.setOnClickListener {
            sessionManager.logout()
        }
        subscribeObserver()
    }

    fun subscribeObserver() {
        sessionManager.cacheToken.observe(this, Observer { authToken ->
            if (authToken == null || authToken.account_pk == -1 || authToken.token == null) {
                navAuthActivity()
                finish()
            }
            Log.d(TAG, "subscribeObserver: $authToken")
        })
    }


    private fun navAuthActivity(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}