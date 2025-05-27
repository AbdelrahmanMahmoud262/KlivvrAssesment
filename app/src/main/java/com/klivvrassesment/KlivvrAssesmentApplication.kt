package com.klivvrassesment

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.logging.Level

class KlivvrAssesmentApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KlivvrAssesmentApplication)
            androidLogger()

            modules(

            )
        }
    }
}