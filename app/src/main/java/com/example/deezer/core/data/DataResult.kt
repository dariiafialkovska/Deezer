package com.example.deezer.core.data


sealed class DataResult<out V,out E>() {

    inline infix fun onSuccess(action: (V?)->Unit): DataResult<V?,E> {
        if (this is Success) {
            action(data)
        }
        return this
    }
    inline infix fun onFailure(action: (E?) -> Unit):DataResult<V,E>{
        if(this is Error){
            action(error)
        }
        return this
    }
}


data class Success<out V>(val data:V?) :DataResult<V,Nothing>()

data class Error<out E>(val error:E?): DataResult<Nothing,E>()