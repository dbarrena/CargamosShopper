package com.cargamos.cargamosshopper.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.extension.vm
import com.cargamos.cargamosshopper.views.login.LoginActivity
import com.cargamos.cargamosshopper.views.main.packing.PackingFragment
import com.cargamos.cargamosshopper.views.main.picking.PickingParentFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { vm(viewModelFactory, MainActivityViewModel::class) }

    val pickingFragment = PickingParentFragment()
    val packingFragment = PackingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        loadFragments()
        setupBottomNavBar()

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Picking"
    }

    private fun setupBottomNavBar() {
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.picking -> {
                    supportActionBar?.title = "Picking"
                    supportFragmentManager.beginTransaction().hide(viewModel.activeFragment.value!!)
                        .show(pickingFragment).commit()
                    viewModel.activeFragment.value = pickingFragment
                }

                R.id.packing -> {
                    supportActionBar?.title = "Packing"
                    supportFragmentManager.beginTransaction().hide(viewModel.activeFragment.value!!)
                        .show(packingFragment).commit()
                    viewModel.activeFragment.value = packingFragment
                }
            }
            true
        }
    }

    private fun loadFragments() {
        supportFragmentManager.beginTransaction().add(R.id.content, packingFragment, "packing")
            .hide(packingFragment)
            .commit()

        supportFragmentManager.beginTransaction().add(R.id.content, pickingFragment, "picking")
            .commit()

        viewModel.activeFragment.value = pickingFragment
    }

    override fun onBackPressed() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }
}
