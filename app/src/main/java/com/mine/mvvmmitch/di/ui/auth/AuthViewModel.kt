package com.mine.mvvmmitch.di.ui.auth
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mine.mvvmmitch.auth.network_response.LoginResponse
import com.mine.mvvmmitch.auth.network_response.RegistrationResponse
import com.mine.mvvmmitch.di.ui.auth.state.AuthStateEvent
import com.mine.mvvmmitch.di.ui.auth.state.AuthViewState
import com.mine.mvvmmitch.di.ui.auth.state.LoginFields
import com.mine.mvvmmitch.di.ui.auth.state.RegistrationFields
import com.mine.mvvmmitch.models.AuthToken
import com.mine.mvvmmitch.respository.auth.AuthRepository
import com.mine.mvvmmitch.utill.AbsentLiveData
import com.mine.mvvmmitch.utill.GenericApiResponse
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
): BaseViewModel<AuthStateEvent, AuthViewState>() {


    fun testLogin(): LiveData<GenericApiResponse<LoginResponse>> {
        return authRepository.testLogin("tarunshrm395@gmail.com","123456789")
    }

    fun testRegister(): LiveData<GenericApiResponse<RegistrationResponse>> {
        return authRepository.testRegister("tarunshrm395@gmail.com",
        username = "tarun719",password = "123456789",password2 = "123456789")
    }

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }

    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when(stateEvent){
            is AuthStateEvent.LoginAttemptEvent ->{
                return AbsentLiveData.create()
            }
            is AuthStateEvent.RegistrationAttemptEvent ->{
                return AbsentLiveData.create()
            }
            is AuthStateEvent.CheckPreviousAuthEvent->{
                return AbsentLiveData.create()
            }
        }
    }

    fun setRegistrationFields(registrationFields: RegistrationFields){
        val updateViewState =  initNewViewState()
        if(updateViewState.registrationFields == registrationFields){
            return
        }
        updateViewState.registrationFields = registrationFields
        _viewState.value = updateViewState
    }


    fun setLoginFields(loginFields: LoginFields){
        val updateViewState =  initNewViewState()
        if(updateViewState.loginFields == loginFields){
            return
        }
        updateViewState.loginFields = loginFields
        _viewState.value = updateViewState
    }


    fun setCheckAuthToken(authToken: AuthToken){
        val updateViewState =  initNewViewState()
        if(updateViewState.authToken == authToken){
            return
        }
        updateViewState.authToken = authToken
        _viewState.value = updateViewState
    }
}
