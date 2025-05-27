package com.klivvrassesment

import android.app.Application
import com.klivvrassesment.data.local.di.localDataModule
import com.klivvrassesment.ui.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KlivvrAssesmentApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KlivvrAssesmentApplication)
            androidLogger()

            modules(
                localDataModule,
                presentationModule
            )
        }
    }
}