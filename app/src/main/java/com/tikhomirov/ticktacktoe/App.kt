package com.tikhomirov.ticktacktoe

import android.app.Application
import com.tikhomirov.ticktacktoe.base.di.dataModule
import com.tikhomirov.ticktacktoe.base.di.domainModule
import com.tikhomirov.ticktacktoe.base.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                presentationModule,
                domainModule,
                dataModule
            )
        }
    }

}
