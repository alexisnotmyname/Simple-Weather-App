package com.alex.weatherapp.common.di.module

import android.content.Context
import com.alex.weatherapp.common.util.GoogleLocationHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {

    @Provides
    @Singleton
    fun providesLocation(context: Context) : GoogleLocationHelper {
        return GoogleLocationHelper(context)
    }

}