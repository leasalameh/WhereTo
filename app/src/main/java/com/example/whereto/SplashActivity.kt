package com.example.whereto

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Get the ImageView for the splash background
        val splashBackground = findViewById<ImageView>(R.id.splash_background)

        // Randomly select one of the 10 wallpapers from the drawable folder
        val wallpaperIds = arrayOf(
            R.drawable.wallpaper1, R.drawable.wallpaper2, R.drawable.wallpaper3,
            R.drawable.wallpaper4, R.drawable.wallpaper5, R.drawable.wallpaper6,
            R.drawable.wallpaper7, R.drawable.wallpaper8, R.drawable.wallpaper9, R.drawable.wallpaper10
        )

        // Randomly pick a wallpaper
        val randomWallpaper = wallpaperIds[Random.nextInt(wallpaperIds.size)]

        // Set the randomly selected wallpaper as the background of the splash screen
        splashBackground.setImageResource(randomWallpaper)

        // Wait for 2-3 seconds and then navigate to the login page
        Handler().postDelayed({
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish() // Close SplashActivity to prevent returning back to it
        }, 2000) // 2000ms = 2 seconds
    }
}
