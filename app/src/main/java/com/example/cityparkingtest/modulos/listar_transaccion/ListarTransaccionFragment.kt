package com.example.cityparkingtest.modulos.listar_transaccion

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
import com.example.cityparkingtest.utilidades.RecyclerVAdapterTransacciones

//Este fragmento lista las transacciones guardadas (una vez se saca un vehiculo y se paga la factura, se crea una transaccion)
class ListarTransaccionFragment : Fragment(),View.OnClickListener {
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
            binding.tituloLista.text = getString(R.string.tituloTransacciones)
            binding.totalLista.text =
                getString(
                    R.string.totalItemsLista,
                    (activity as MainActivity).constantes.TRANSACCIONES.size.toString()
                )
            binding.recyclerViewLista.adapter = RecyclerVAdapterTransacciones(
                (activity as MainActivity).constantes.TRANSACCIONES,
                resources
            )
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