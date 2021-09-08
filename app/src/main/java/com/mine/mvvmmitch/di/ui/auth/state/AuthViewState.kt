package com.mine.mvvmmitch.di.ui.auth.state

import com.mine.mvvmmitch.models.AuthToken

data class AuthViewState(
    var registrationFields: RegistrationFields? = RegistrationFields(),
    var loginFields: LoginFields? = LoginFields(),
    var authToken: AuthToken? = null
)

data class RegistrationFields(
    var registrationEmail: String? = null,
    var registrationUserName: String? = null,
    var registrationPassword: String? = null,
    var registrationConfirmPassword: String? = null
) {
    class RegistrationError() {
        companion object {
            fun mustFillAllFields(): String = "All Fields are required"
            fun passwordDoNotMatch(): String = "Password much match"
            fun none(): String = "None"

        }
    }


    fun isValidForRegistration(): String {
        if (registrationEmail.isNullOrEmpty()
            || registrationUserName.isNullOrEmpty()
            || registrationPassword.isNullOrEmpty()
            || registrationConfirmPassword.isNullOrEmpty()
        ) {
            return RegistrationError.mustFillAllFields()
        }
        if (!registrationConfirmPassword.equals(registrationPassword)) {
            return RegistrationError.passwordDoNotMatch()
        }

        return RegistrationError.none()
    }
}


data class LoginFields(
    var login_email: String? = null,
    var login_password: String? = null
){
    class LoginError {

        companion object{

            fun mustFillAllFields(): String{
                return "You can't login without an email and password."
            }

            fun none():String{
                return "None"
            }

        }
    }
    fun isValidForLogin(): String{

        if(login_email.isNullOrEmpty()
            || login_password.isNullOrEmpty()){

            return LoginError.mustFillAllFields()
        }
        return LoginError.none()
    }

    override fun toString(): String {
        return "LoginState(email=$login_email, password=$login_password)"
    }
}