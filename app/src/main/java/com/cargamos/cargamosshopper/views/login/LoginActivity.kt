package com.cargamos.cargamosshopper.views.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cargamos.cargamosshopper.extension.vm
import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.views.main.MainActivity
import com.ncorti.slidetoact.SlideToActView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy { vm(viewModelFactory, LoginActivityViewModel::class) }

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        observe()
    }

    private fun init() {
        mAuth = FirebaseAuth.getInstance()

        registerSlider.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {
                progressBar.visibility = View.VISIBLE
                login()
            }
        }
    }

    private fun observe() {
        viewModel.batchCommitReady.observe(this,
            Observer {
                it?.let {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }
            })
    }

    private fun login() {
        if (email.text.toString().isNotEmpty() || password.text.toString().isNotEmpty()) {
            mAuth?.signInWithEmailAndPassword(email.text.toString(), password.text.toString())?.addOnCompleteListener {
                progressBar.visibility = View.GONE
                if (it.isSuccessful) {
                    viewModel.getShopperProperties()
                } else {
                    Toast.makeText(this, "Los datos son incorrectos, por favor intenta de nuevo.", Toast.LENGTH_LONG)
                        .show()
                    registerSlider.resetSlider()
                }
            }
        } else {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Por favor ingresa los datos necesarios.", Toast.LENGTH_LONG).show()
        }
    }
}
