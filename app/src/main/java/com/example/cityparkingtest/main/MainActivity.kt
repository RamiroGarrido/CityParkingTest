package com.example.cityparkingtest.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cityparkingtest.modulos.MainFragment
import com.example.cityparkingtest.R
import com.example.cityparkingtest.databinding.ActivityMainBinding
import com.example.cityparkingtest.utilidades.Constantes
//El rol de MainActivity es cargar el fragmento principal y mantener 1 sola instancia de datos constantes para todos los fragments del app
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val constantes = Constantes()

    //Se carga el contenedor de fragmentos
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerFragments, MainFragment()).commitNow()
        } catch (e: Exception) {
            Log.i(constantes.TAG_GENERAL, e.message!!)
        }
    }

    override fun onBackPressed() {
        //No se desea volver al splash
    }
}