package com.example.deezer.core.data

import android.database.StaleDataException
import android.database.sqlite.SQLiteException
import com.example.deezer.BuildConfig
import java.lang.IllegalArgumentException

open class BaseLocalDataSource {
    suspend fun <T> performDatabaseOperation(
        call: suspend ()->T
    ): DataResult<T,String>{
        return try{
            Success(call.invoke())
        }catch( exception: Exception){
            if(BuildConfig.DEBUG)
                exception.printStackTrace()
            handleError(exception)
        }
    }

    private fun handleError(exception: Exception): DataResult<Nothing,String> {
        return when(exception){
            is SQLiteException->{
                Error(
                    "Table creation error: Invalid table name or table field not properly defined"
                )
            }
            is StaleDataException->{
                Error(
                    "Query results are stale: Please re-run the query or update the database"
                )
            }
            is IllegalArgumentException->{
                Error(
                    "Database transaction error"
                )
            }

            else -> Error(exception.message?: "An error occured")
        }

    }
}