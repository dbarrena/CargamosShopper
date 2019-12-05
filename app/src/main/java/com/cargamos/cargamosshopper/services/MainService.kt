package com.cargamos.cargamosshopper.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.api.PickingService
import com.cargamos.cargamosshopper.extension.vm
import com.cargamos.cargamosshopper.utils.NotificationsUtils
import com.cargamos.cargamosshopper.views.main.MainActivity
import com.cargamos.cargamosshopper.views.main.MainActivityViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainService : LifecycleService() {
    @Inject
    lateinit var pickingService: PickingService

    lateinit var pm: PowerManager
    lateinit var wl: PowerManager.WakeLock
    private val binder = MainServiceBinder()

    lateinit var viewModel: MainServiceViewModel

    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        val notificationIntent = Intent(this, MainActivity::class.java)

        viewModel = MainServiceViewModel(pickingService)

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )

        val notification = NotificationsUtils.showBindedNotification(baseContext)

        startForeground(1337, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, ":My Tag")
        wl.acquire()
        return android.app.Service.START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        super.onBind(intent)
        mediaPlayer = MediaPlayer.create(this, R.raw.ding)
        observe()
        return this.binder
    }

    private fun observe() {
        viewModel.getPickingItems()
        viewModel.poolItems.observe(this,
            Observer {
                it?.let {
                    val lastValue = viewModel.newPickingsLastValue
                    viewModel.newPickingsLastValue = it

                    val firstTimeLoading = viewModel.firstTimeLoading

                    if (it != 0) {
                        if (firstTimeLoading) {
                            viewModel.firstTimeLoading = false
                        } else if (it > lastValue) {
                            mediaPlayer.start()
                        }
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        wl.release()
    }

    inner class MainServiceBinder : Binder() {
        val instance: MainService
            get() = this@MainService
    }
}