package com.yoesuv.switchtheme

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yoesuv.switchtheme.databinding.ActivityMainBinding
import com.yoesuv.switchtheme.utils.insetsPadding

const val IS_DARK = "isDarkTheme"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var dialogExit: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setupToolbar()
        setupSwitch()
        setupButton()
        setupEdgeToEdge()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentNightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.switchTheme.isChecked = false
                setupDarkMode(false)
                recreate()
            }

            Configuration.UI_MODE_NIGHT_YES -> {
                binding.switchTheme.isChecked = true
                setupDarkMode(true)
                recreate()
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
        setupDarkMode(isDarkEnable)
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            setupDarkMode(isChecked)
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

    private fun setupDarkMode(isDarkMode: Boolean) {
        val night =
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(night)
        MyApp.prefHelper?.setBoolean(IS_DARK, isDarkMode)
    }

    private fun setupEdgeToEdge() {
        val color = ContextCompat.getColor(this, R.color.primary)
        insetsPadding(binding.corMain, top = true, color = color)
    }

}