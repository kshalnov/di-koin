package ru.gb.course1.di1

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.gb.course1.di1.di.appModule
import ru.gb.course1.di1.di.dbModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, dbModule)
        }
    }
}