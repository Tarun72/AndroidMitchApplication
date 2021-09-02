package com.mine.mvvmmitch.di.ui.auth
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mine.mvvmmitch.auth.network_response.LoginResponse
import com.mine.mvvmmitch.auth.network_response.RegistrationResponse
import com.mine.mvvmmitch.respository.auth.AuthRepository
import com.mine.mvvmmitch.utill.GenericApiResponse
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
): ViewModel(){


    fun testLogin(): LiveData<GenericApiResponse<LoginResponse>> {
        return authRepository.testLogin("tarunshrm395@gmail.com","123456789")
    }

    fun testRegister(): LiveData<GenericApiResponse<RegistrationResponse>> {
        return authRepository.testRegister("tarunshrm395@gmail.com",
        username = "tarun719",password = "123456789",password2 = "123456789")
    }

}
