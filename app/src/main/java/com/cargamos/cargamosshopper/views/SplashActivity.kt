package com.cargamos.cargamosshopper.views

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.views.login.LoginActivity
import com.cargamos.cargamosshopper.views.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private var delayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1000 //1 second
    private val PERMISSIONS_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        delayHandler = Handler()
        delayHandler!!.postDelayed(runnable, SPLASH_DELAY)
    }

    internal val runnable: Runnable = Runnable {
        checkPermissions()
    }

    @Suppress("DEPRECATED_IDENTITY_EQUALS")
    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            !== PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            !== PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            !== PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            !== PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
            !== PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN)
            !== PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this@SplashActivity,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.INTERNET
                ), PERMISSIONS_REQUEST
            )
        } else {
            initApp()
        }
    }

    private fun initApp() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finishAffinity()
        } else {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST -> {
                var ok = true
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        ok = false
                        break
                    }
                }
                if (ok) {
                    initApp()
                }
            }
        }
    }
}
