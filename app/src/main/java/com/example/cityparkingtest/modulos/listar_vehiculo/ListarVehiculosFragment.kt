package com.example.cityparkingtest.modulos.listar_vehiculo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cityparkingtest.R
import com.example.cityparkingtest.databinding.FragmentListasBinding
import com.example.cityparkingtest.main.MainActivity
import com.example.cityparkingtest.modulos.MainFragment
import com.example.cityparkingtest.utilidades.RecyclerVAdapterRetirarV

//Este fragmento lista todos los vehiculos y su respectivo parqueadero, junto con mas detalles de registro.
class ListarVehiculosFragment:Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentListasBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return try {
            binding =
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.fragment_listas,
                    container,
                    false
                )
            binding.root
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            super.onViewCreated(view, savedInstanceState)
            binding.recyclerViewLista.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            var vehiculos = 0
            (activity as MainActivity).constantes.PARQUEADEROS.forEach { it ->
                vehiculos += it.listaVehiculos.size
            }
            binding.tituloLista.text = getString(R.string.tituloListaV)
            binding.totalLista.text =
                getString(R.string.totalItemsLista, vehiculos.toString())
            binding.recyclerViewLista.adapter = RecyclerVAdapterRetirarV(false,(activity as MainActivity).constantes.PARQUEADEROS,this)
            binding.buttonCancelarLista.setOnClickListener(this)
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
        }
    }

    override fun onClick(v: View?) {
        try {
            when (v!!.id) {
                R.id.buttonCancelarLista -> {
                    (activity as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.containerFragments, MainFragment()).commitNow()
                }
            }
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
        }
    }
}