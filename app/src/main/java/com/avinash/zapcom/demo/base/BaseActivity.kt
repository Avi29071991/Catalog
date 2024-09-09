package com.avinash.zapcom.demo.base

import android.app.Activity
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

abstract class BaseActivity <VS : BaseViewState,
        VMS : BaseViewModelState<VS>,
        VM : BaseViewModel<VS, VMS>
        > : ComponentActivity() {

    abstract val viewModel: VM

    @Composable
    abstract fun composeContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { composeContent() }
    }

    open fun isInternetConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}