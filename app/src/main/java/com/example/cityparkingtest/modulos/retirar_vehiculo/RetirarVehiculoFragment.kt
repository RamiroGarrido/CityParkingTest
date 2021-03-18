package com.example.cityparkingtest.modulos.retirar_vehiculo

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cityparkingtest.R
import com.example.cityparkingtest.databinding.FragmentListasBinding
import com.example.cityparkingtest.main.MainActivity
import com.example.cityparkingtest.modelos.Accion
import com.example.cityparkingtest.modelos.Operario
import com.example.cityparkingtest.modelos.Transaccion
import com.example.cityparkingtest.modulos.MainFragment
import com.example.cityparkingtest.utilidades.RecyclerVAdapterRetirarV
import java.text.SimpleDateFormat
import java.util.*
//Este fragmento se encarga de retirar un vehiculo y de ajustar el cargo a la factura dependiendo si la persona se paso de las 12horas iniciales. Tambien crea una transaccion al sacar un vehiculo y una factura ha sido pagada.
class RetirarVehiculoFragment : Fragment(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentListasBinding
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
                    R.layout.fragment_listas,
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
            binding.recyclerViewLista.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            var vehiculos = 0
            (activity as MainActivity).constantes.PARQUEADEROS.forEach { it ->
                vehiculos += it.listaVehiculos.size
            }
            binding.totalLista.text =
                getString(R.string.totalItemsLista, vehiculos.toString())
            binding.recyclerViewLista.adapter = RecyclerVAdapterRetirarV(
                true,
                (activity as MainActivity).constantes.PARQUEADEROS,
                this
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
                R.id.buttonRetirarRVC -> {
                    val posParYVeh = v.tag as String
                    val posiciones = posParYVeh.split("/")
                    val calendarioO = Calendar.getInstance()
                    val fechaEntrada =
                        (activity as MainActivity).constantes.PARQUEADEROS[posiciones[0].toInt()].listaFacturas[posiciones[1].toInt()].fechaEntrada
                    val parser =
                        SimpleDateFormat((activity as MainActivity).constantes.FORMATO_DATE)
                    calendarioO.time = parser.parse(fechaEntrada)
                    val datePD = DatePickerDialog(requireContext())
                    datePD.datePicker.tag = v.tag as String
                    datePD.datePicker.minDate = calendarioO.timeInMillis
                    datePD.setOnDateSetListener(this)
                    datePD.show()
                }
                R.id.okFechaSalida -> {

                }
            }
        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        try {
            val posParYVeh = view!!.tag as String
            val posiciones = posParYVeh.split("/")
            val calendarioE = Calendar.getInstance()
            calendarioE.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            calendarioE.set(Calendar.MONTH, month)
            calendarioE.set(Calendar.YEAR, year)
            val calendarioO = Calendar.getInstance()
            val fechaEntrada =
                (activity as MainActivity).constantes.PARQUEADEROS[posiciones[0].toInt()].listaFacturas[posiciones[1].toInt()].fechaEntrada
            val parser = SimpleDateFormat((activity as MainActivity).constantes.FORMATO_DATE)
            calendarioO.time = parser.parse(fechaEntrada)
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.custom_dialog_escoger_fecha_salida)
            var textoFecha = dialog.findViewById<TextView>(R.id.textoFechaEscogidaSalida)
            var horaSalida = dialog.findViewById<EditText>(R.id.editTextHoraSalida)
            var buttonOk = dialog.findViewById<Button>(R.id.okFechaSalida)
            buttonOk.setOnClickListener {
                //Se agrega la hora a la fecha de entrada
                val parser =
                    SimpleDateFormat((activity as MainActivity).constantes.FORMATO_DATETIME)
                calendarioO.time = parser.parse(fechaEntrada)!!
                //Se agrega la hora a la fecha de salida
                val fechaHoraE = getString(
                    R.string.fechaEscogida,
                    dayOfMonth.toString(),
                    month.plus(1).toString(),
                    year.toString()
                ) + " " + horaSalida.text.toString().trim()
                calendarioE.time = parser.parse(fechaHoraE)!!
                //Se le suman 12 horas a la fecha y hora de entrada
                calendarioO.add(
                    Calendar.HOUR_OF_DAY,
                    (activity as MainActivity).constantes.HORAS_MAX
                )
                val factura =
                    (activity as MainActivity).constantes.PARQUEADEROS[posiciones[0].toInt()].listaFacturas[posiciones[1].toInt()]
                val vehiculo =
                    (activity as MainActivity).constantes.PARQUEADEROS[posiciones[0].toInt()].listaVehiculos[posiciones[1].toInt()]
                factura.fechaSalida = parser.format(calendarioE.time)
                //Se compara la nueva fecha y hora de entrada con la fecha y hora de salida
                //Si es igual, o supera las 12 horas de parqueo, se paga extra y se saca el vehiculo
                if (calendarioO < calendarioE) {
                    factura.valorCobrado =
                        (activity as MainActivity).constantes.VALOR_VALET.plus((activity as MainActivity).constantes.VALOR_PARQUEO)
                            .toString()
                }
                //Se añade la factura a las transacciones realizadas
                (activity as MainActivity).constantes.TRANSACCIONES.add(Transaccion(
                    (activity as MainActivity).constantes.PARQUEADEROS[posiciones[0].toInt()].nombre,
                    factura
                ))
                //Se añade una accion al operario
                (activity as MainActivity).constantes.ACCIONES_OPERARIOS.add(
                    Accion(operarioEscogido, null, null, vehiculo, factura.fechaSalida)
                )
                //Se remueve la factura del cliente y el vehiculo del parqueadero
                (activity as MainActivity).constantes.PARQUEADEROS[posiciones[0].toInt()].listaFacturas.removeAt(
                    posiciones[1].toInt()
                )
                (activity as MainActivity).constantes.PARQUEADEROS[posiciones[0].toInt()].listaVehiculos.removeAt(
                    posiciones[1].toInt()
                )
                Toast.makeText(
                    requireContext(),
                    getString(R.string.vehiculoRetirado, operarioEscogido.nombre),
                    Toast.LENGTH_LONG
                ).show()
                dialog.cancel()
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.containerFragments, MainFragment()).commitNow()
            }
            var buttonCancelar = dialog.findViewById<Button>(R.id.cancelFechaSalida)
            buttonCancelar.setOnClickListener {
                dialog.cancel()
            }
            textoFecha.text = getString(
                R.string.fechaEscogida,
                dayOfMonth.toString(),
                month.plus(1).toString(),
                year.toString()
            )
            dialog.create()
            dialog.show()

        } catch (e: Exception) {
            Log.i((activity as MainActivity).constantes.TAG_GENERAL, e.message!!)
        }
    }

    private fun decidirCargoFactura() {

    }
}
