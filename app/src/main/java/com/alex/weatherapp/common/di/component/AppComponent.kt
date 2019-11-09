package com.alex.weatherapp.common.di.component

import com.alex.weatherapp.common.di.module.AppModule
import com.alex.weatherapp.common.di.module.LocationModule
import com.alex.weatherapp.common.di.module.NetworkModule
import com.alex.weatherapp.common.di.module.PresenterModule
import com.alex.weatherapp.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PresenterModule::class, NetworkModule::class, LocationModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}