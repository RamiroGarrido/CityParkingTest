package com.example.cityparkingtest.modulos.registro_vehiculo

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cityparkingtest.modulos.MainFragment
import com.example.cityparkingtest.R
import com.example.cityparkingtest.databinding.FragmentRegistroVehiculoBinding
import com.example.cityparkingtest.main.MainActivity
import com.example.cityparkingtest.modelos.Accion
import com.example.cityparkingtest.modelos.Factura
import com.example.cityparkingtest.modelos.Operario
import com.example.cityparkingtest.modelos.Vehiculo
import java.text.SimpleDateFormat
import java.util.*

//Este fragmento se encarga de registrar vehiculos recien ingresados, junto con los detalles pertinentes
class RegistroVehiculoFragment : Fragment(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentRegistroVehiculoBinding
    private lateinit var operarioEscogido: Operario
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return try {
            binding =
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.fragment_registro_vehiculo,
                    container,
                    false
                )
            operarioEscogido =
                arguments!!.getParcelable((activity as MainActivity).constantes.KEY_OPERARIO)!!
            binding.root
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
            null
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            super.onViewCreated(view, savedInstanceState)
            binding.buttonEscogerFechaRV.setOnClickListener(this)
            binding.buttonCancelarRV.setOnClickListener(this)
            binding.buttonGuardarRV.setOnClickListener(this)
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
        }
    }

    override fun onClick(v: View?) {
        try {
            when (v!!.id) {
                R.id.buttonEscogerFechaRV -> {
                    val dpDialog = DatePickerDialog(requireContext())
                    dpDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
                    dpDialog.setOnDateSetListener(this)
                    dpDialog.show()
                }
                R.id.buttonCancelarRV -> {
                    (activity as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.containerFragments, MainFragment()).commitNow()
                }

                R.id.buttonGuardarRV -> {
                    if (verificarDatosObligatorios()) {
                        //Se añade a un parqueadero aleatorio un vehiculo y una factura
                        val numAleatorio = (0..2).random()
                        añadirAParqueadero(numAleatorio)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.errorDatos),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
        }
    }

    private fun añadirAParqueadero(numAleatorio: Int) {
        val parqueaderoAleatorio =
            (activity as MainActivity).constantes.PARQUEADEROS[numAleatorio]
        val vehiculo = Vehiculo(
            binding.editTextNombreVehiculo.text.toString().trim(),
            binding.editTextPlaca.text.toString().trim(),
            binding.editTextUbicacion.text.toString().trim(),
            binding.editTextTelefonoContacto.text.toString().trim(),
            binding.editTextGolpeRayon.text.toString().trim(),
            binding.editTextObjetosVehiculo.text.toString().trim()
        )
        //Se añade un vehiculo al parqueadero aleatorio
        parqueaderoAleatorio.listaVehiculos.add(vehiculo)
        val fechaEntrada = binding.textoFechaEscogidaRV.text.toString()
            .trim() + " " + binding.editTextHoraRV.text.toString()
            .trim()
        //Se añade una factura al parqueadero aleatorio
        parqueaderoAleatorio.listaFacturas.add(
            Factura(
                (activity as MainActivity).constantes.ID_FACTURAS,
                binding.editTextPlaca.text.toString().trim(),
                (activity as MainActivity).constantes.VALOR_VALET.toString(),
                fechaEntrada,
                null
            )
        )
        (activity as MainActivity).constantes.ACCIONES_OPERARIOS.add(
            Accion(operarioEscogido,vehiculo,fechaEntrada, null,null)
        )
        (activity as MainActivity).constantes.ID_FACTURAS++
        Toast.makeText(
            requireContext(),
            getString(R.string.datosGuardados),
            Toast.LENGTH_LONG
        ).show()
        (activity as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragments, MainFragment()).commitNow()
    }

    private fun verificarDatosObligatorios(): Boolean {
        return try {
            binding.textoFechaEscogidaRV.text.isNotEmpty() &&
                    binding.editTextHoraRV.text.trim().isNotEmpty() &&
                    binding.editTextNombreVehiculo.text.trim().isNotEmpty() &&
                    binding.editTextUbicacion.text.trim().isNotEmpty() &&
                    binding.editTextPlaca.text.trim().isNotEmpty()
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
            false
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        try {
            var calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            val parser =
                SimpleDateFormat((activity as MainActivity).constantes.FORMATO_DATETIME)
            val fechaE = parser.format(calendar.time)
            /**binding.textoFechaEscogidaRV.text = getString(
                R.string.fechaEscogida,
                dayOfMonth.toString(),
                month.plus(1).toString(),
                year.toString()
            )**/
            val fecha = fechaE.split(" ")
            binding.textoFechaEscogidaRV.text = fecha[0]
            binding.textoFechaEscogidaRV.visibility = View.VISIBLE
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
        }
    }
}