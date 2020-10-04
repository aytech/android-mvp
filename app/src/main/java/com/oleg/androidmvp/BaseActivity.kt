package com.oleg.androidmvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    protected lateinit var localDataSource: LocalDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localDataSource = LocalDatabase.getInstance(this)
    }
}