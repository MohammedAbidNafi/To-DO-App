package com.margsapp.todo

import android.app.Application

class CheckedIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
    }

}