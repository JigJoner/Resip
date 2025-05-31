package com.example.resip

import android.app.Application
import com.example.resip.data.AppContainer
import com.example.resip.data.ResipAppContainer

class ResipApplication: Application(){
    lateinit var container: AppContainer

    override fun onCreate(){
        super.onCreate()
        container = ResipAppContainer(this)
    }
}