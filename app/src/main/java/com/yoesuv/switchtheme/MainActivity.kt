package com.yoesuv.switchtheme

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yoesuv.switchtheme.databinding.ActivityMainBinding

const val IS_DARK = "isDarkTheme"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var dialogExit: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setupToolbar()
        setupSwitch()
        setupButton()
    }

    // https://stackoverflow.com/a/59694159/3559183
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentNightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        //Log.d("TAG_DEBUG","MainActivity # current night mode $currentNightMode")
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.switchTheme.isChecked = false
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.switchTheme.isChecked = true
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupSwitch() {
        val isDarkEnable = MyApp.prefHelper?.getBoolean(IS_DARK) ?: false
        binding.switchTheme.isChecked = isDarkEnable
        val theme = if (isDarkEnable) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(theme)
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            Log.d("TAG_DEBUG","MainActivity # switch is checked : $isChecked")
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                MyApp.prefHelper?.setBoolean(IS_DARK, true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                MyApp.prefHelper?.setBoolean(IS_DARK, false)
            }
        }
    }

    private fun setupButton() {
        val dialogBuilder = MaterialAlertDialogBuilder(this, R.style.DialogTheme)
            .setTitle(getString(R.string.exit).uppercase())
            .setMessage(R.string.exit_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                dialogExit?.dismiss()
                finish()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ ->
                dialogExit?.dismiss()
            }
        dialogExit = dialogBuilder.create()
        binding.btnExit.setOnClickListener {
            dialogExit?.show()
        }
    }

}