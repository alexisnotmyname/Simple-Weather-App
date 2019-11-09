package com.alex.weatherapp.ui.base

import android.app.Application
import com.alex.weatherapp.common.di.component.AppComponent
import com.alex.weatherapp.common.di.component.DaggerAppComponent
import com.alex.weatherapp.common.di.module.AppModule

class BaseApplication : Application() {

    lateinit var component: AppComponent

    private fun initDagger(app: BaseApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()

    override fun onCreate() {
        super.onCreate()
        component = initDagger(this)

    }
}