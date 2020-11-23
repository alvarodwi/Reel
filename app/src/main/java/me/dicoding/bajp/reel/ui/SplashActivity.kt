package me.dicoding.bajp.reel.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.dicoding.bajp.reel.R
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

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