package com.cargamos.cargamosshopper

import android.content.*
import android.os.IBinder
import com.cargamos.cargamosshopper.di.DaggerAppComponent
import com.cargamos.cargamosshopper.services.MainService
import com.google.firebase.FirebaseApp
import com.pixplicity.easyprefs.library.Prefs
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {

    companion object {
        lateinit var instance: App private set
    }

    init {
        instance = this
    }

    var mainService: MainService? = null

    private val masterServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MainService.MainServiceBinder
            mainService = binder.instance
        }

        override fun onServiceDisconnected(name: ComponentName) {
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        initializeFirebase()
        initializeEasyPrefs()

        val masterServiceIntent = Intent(this, MainService::class.java)
        this.bindService(masterServiceIntent, masterServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()


    private fun initializeEasyPrefs() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

    private fun initializeFirebase() {
        Timber.d("initializing firebase...")
        FirebaseApp.initializeApp(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}