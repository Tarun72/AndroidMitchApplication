package com.mine.mvvmmitch.di.ui.auth
import androidx.lifecycle.ViewModel
import com.mine.mvvmmitch.respository.auth.AuthRepository
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
): ViewModel(){



}
