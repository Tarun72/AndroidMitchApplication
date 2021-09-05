package com.mine.mvvmmitch.di.ui.auth

import com.mine.mvvmmitch.di.ui.*

data class DataState<T>(
    var error: Event<StateError>? = null,
    var loading: Loading = Loading(false),
    var data: Data<T>? = null
) {
    companion object {
        fun <T> error(
            response: Response
        ): DataState<T> {
            return DataState(
                error = Event(
                    StateError(response = response)
                )
            )
        }

        fun <T> loading(
            isLoading: Boolean,
            cachedData: T? = null
        ): DataState<T> {
            return DataState(
                loading = Loading(isLoading),
                data = Data(
                    response = null,
                    data = Event.dataEvent(
                        cachedData
                    )
                )
            )
        }


        fun <T> data(
            data: T?= null,
            response: Response?= null
        ):DataState<T>{
            return DataState(
                data =  Data(
                    Event.dataEvent(data),
                    Event.responseEvent(response)
                )
            )
        }
    }
}











