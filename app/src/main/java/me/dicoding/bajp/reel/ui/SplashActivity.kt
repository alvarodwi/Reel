package me.dicoding.bajp.reel.ui

import android.content.Intent
import android.os.Bundle
import com.jintin.bindingextension.BindingActivity
import me.dicoding.bajp.reel.databinding.ActivitySplashBinding
import timber.log.Timber

class SplashActivity : BindingActivity<ActivitySplashBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToMainActivity()
    }

    private fun goToActivity(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    private fun goToMainActivity() {
        Timber.d("Splash / Launch library")
        Intent(this, MainActivity::class.java).also { goToActivity(it) }
    }
}
