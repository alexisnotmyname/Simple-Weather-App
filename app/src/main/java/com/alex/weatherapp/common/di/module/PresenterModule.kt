package com.alex.weatherapp.common.di.module

import com.alex.weatherapp.network.ApiServiceInterface
import com.alex.weatherapp.ui.main.MainContract
import com.alex.weatherapp.ui.main.MainPresenter
import com.alex.weatherapp.common.util.GoogleLocationHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideMainPresenter(apiServiceInterface: ApiServiceInterface, googleLocationHelper: GoogleLocationHelper): MainContract.Presenter =
        MainPresenter(apiServiceInterface, googleLocationHelper)
}