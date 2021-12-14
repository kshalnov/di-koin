package ru.gb.course1.di1

import android.app.Application
import ru.gb.course1.di1.di.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        module(this)
    }

}