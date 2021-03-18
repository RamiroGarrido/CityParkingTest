package com.example.cityparkingtest.modulos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cityparkingtest.R
import com.example.cityparkingtest.databinding.FragmentMainBinding
import com.example.cityparkingtest.main.MainActivity
import com.example.cityparkingtest.modelos.Operario
import com.example.cityparkingtest.modulos.listar_operario.ListarAccionesFragment
import com.example.cityparkingtest.modulos.listar_transaccion.ListarTransaccionFragment
import com.example.cityparkingtest.modulos.listar_vehiculo.ListarVehiculosFragment
import com.example.cityparkingtest.modulos.registro_vehiculo.RegistroVehiculoFragment
import com.example.cityparkingtest.modulos.retirar_vehiculo.RetirarVehiculoFragment

//Este fragmento contiene las operaciones principales de la aplicacion.
open class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentMainBinding
    private var operarioEscogido: Operario? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return try {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
            binding.root
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            super.onViewCreated(view, savedInstanceState)
            binding.buttonListadoVehiculos.setOnClickListener(this)
            binding.buttonInfoTransacciones.setOnClickListener(this)
            binding.buttonOperarios.setOnClickListener(this)
            binding.buttonRegistrarVehiculo.setOnClickListener(this)
            binding.buttonSacarVehiculo.setOnClickListener(this)
            binding.cardViewValet1.setOnClickListener(this)
            binding.cardViewValet2.setOnClickListener(this)
            binding.cardViewValet3.setOnClickListener(this)
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
        }
    }

    override fun onClick(v: View?) {
        try {
            when (v!!.id) {
                R.id.cardViewValet1 -> {
                    operarioEscogido = (activity as MainActivity).constantes.OPERARIOS[0]
                    binding.cardViewValet1.setCardBackgroundColor(
                        resources.getColor(
                            R.color.SkyBlue,
                            null
                        )
                    )
                    binding.cardViewValet2.setCardBackgroundColor(
                        resources.getColor(
                            R.color.White,
                            null
                        )
                    )
                    binding.cardViewValet3.setCardBackgroundColor(
                        resources.getColor(
                            R.color.White,
                            null
                        )
                    )
                }
                R.id.cardViewValet2 -> {
                    operarioEscogido = (activity as MainActivity).constantes.OPERARIOS[1]
                    binding.cardViewValet2.setCardBackgroundColor(
                        resources.getColor(
                            R.color.SkyBlue,
                            null
                        )
                    )
                    binding.cardViewValet1.setCardBackgroundColor(
                        resources.getColor(
                            R.color.White,
                            null
                        )
                    )
                    binding.cardViewValet3.setCardBackgroundColor(
                        resources.getColor(
                            R.color.White,
                            null
                        )
                    )
                }
                R.id.cardViewValet3 -> {
                    operarioEscogido = (activity as MainActivity).constantes.OPERARIOS[2]
                    binding.cardViewValet3.setCardBackgroundColor(
                        resources.getColor(
                            R.color.SkyBlue,
                            null
                        )
                    )
                    binding.cardViewValet2.setCardBackgroundColor(
                        resources.getColor(
                            R.color.White,
                            null
                        )
                    )
                    binding.cardViewValet1.setCardBackgroundColor(
                        resources.getColor(
                            R.color.White,
                            null
                        )
                    )
                }
                R.id.buttonListadoVehiculos -> {
                    var vehiculos = 0
                    (activity as MainActivity).constantes.PARQUEADEROS.forEach { it ->
                        vehiculos += it.listaVehiculos.size
                    }
                    //Si existen algun vehiculo en algun parqueadero, entra
                    if (vehiculos != 0) {
                        (activity as MainActivity).supportFragmentManager.beginTransaction()
                         .replace(R.id.containerFragments,ListarVehiculosFragment()).commitNow()
                    }
                    //Si no existe ningun vehiculo en ningun parqueadero
                    else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.errorNoHayDatos),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                R.id.buttonInfoTransacciones -> {
                    //Si existen alguna transaccion, entra
                    if ((activity as MainActivity).constantes.TRANSACCIONES.size != 0) {
                        (activity as MainActivity).supportFragmentManager.beginTransaction()
                          .replace(R.id.containerFragments,ListarTransaccionFragment()).commitNow()
                    }
                    //Si no existe ninguna transaccion realizada
                    else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.errorNoHayDatos),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                R.id.buttonOperarios -> {
                    //Si existen alguna accion de algun operario, entra
                    if ((activity as MainActivity).constantes.ACCIONES_OPERARIOS.size != 0) {
                        (activity as MainActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.containerFragments,ListarAccionesFragment()).commitNow()
                    }
                    //Si no existe ninguna accion de ningun operario en ningun parqueadero
                    else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.errorNoHayDatos),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                R.id.buttonRegistrarVehiculo -> {
                    if (operarioEscogido != null) {
                        val bundle = bundleOf(
                            Pair(
                                (activity as MainActivity).constantes.KEY_OPERARIO,
                                operarioEscogido
                            )
                        )
                        val fragment = RegistroVehiculoFragment()
                        fragment.arguments = bundle
                        (activity as MainActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.containerFragments, fragment).commitNow()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.errorOperario),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                R.id.buttonSacarVehiculo -> {
                    if (operarioEscogido != null) {
                        val bundle = bundleOf(
                            Pair(
                                (activity as MainActivity).constantes.KEY_OPERARIO,
                                operarioEscogido
                            )
                        )
                        val fragment = RetirarVehiculoFragment()
                        fragment.arguments = bundle
                        (activity as MainActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.containerFragments, fragment).commitNow()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.errorOperario),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
        }
    }
}