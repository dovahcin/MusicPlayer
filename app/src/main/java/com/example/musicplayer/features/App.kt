package com.example.musicplayer.features

import android.app.Application
import com.example.musicplayer.features.di.databaseModule
import com.example.musicplayer.features.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    mainModule
                )
            )
        }
    }
}