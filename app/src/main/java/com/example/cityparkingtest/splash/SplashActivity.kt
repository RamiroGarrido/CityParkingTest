package com.example.cityparkingtest.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.cityparkingtest.R
import com.example.cityparkingtest.main.MainActivity

//Esta actividad unicamente sirve como presentacion inicial a la aplicacion
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        this.supportActionBar?.hide()
        iniciarAnimacion()
    }

    fun iniciarAnimacion() {
        val contenedor = findViewById<RelativeLayout>(R.id.splashContainer)
        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                //No se desea hacer nada
            }

            override fun onAnimationStart(animation: Animation?) {
                //No se desea hacer nada
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        })
        contenedor.startAnimation(animation)
    }
}