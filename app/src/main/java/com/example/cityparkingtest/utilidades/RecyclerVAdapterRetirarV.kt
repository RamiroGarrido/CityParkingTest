package com.example.cityparkingtest.utilidades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.cityparkingtest.R
import com.example.cityparkingtest.modelos.Parqueadero
import com.example.cityparkingtest.modulos.retirar_vehiculo.RetirarVehiculoFragment

//Adaptador para mostrar la lista de vehiculos con opcion de retiro o sin opcion de retiro
class RecyclerVAdapterRetirarV
    (
    val operario: Boolean,
    val parqueaderos: List<Parqueadero>,
    val handler: View.OnClickListener
) : RecyclerView.Adapter<RecyclerVAdapterRetirarV.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerVAdapterRetirarV.ViewHolder {
        var itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_child_lista_vehiculos, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: RecyclerVAdapterRetirarV.ViewHolder, position: Int) {
        var parqueA: Int = 0
        for (i in 0..parqueaderos.size.minus(1)) {
            parqueA += parqueaderos[i].listaVehiculos.size
            if (position < parqueA) {
                //Se le resta a la posicion la cantidad de vehiculos ya revisada en anteriores parqueaderos
                var vehiculo = parqueaderos[i].listaVehiculos.getOrNull(
                    position.minus(
                        parqueA.minus(parqueaderos[i].listaVehiculos.size)
                    )
                )
                var factura = parqueaderos[i].listaFacturas.getOrNull(
                    position.minus(
                        parqueA.minus(parqueaderos[i].listaFacturas.size)
                    )
                )
                if (vehiculo != null && factura != null) {
                    holder.parqueadero.text = parqueaderos[i].nombre
                    holder.nombreV.text = vehiculo.nombre
                    holder.placaV.text = vehiculo.placa
                    holder.ubicacion.text = vehiculo.ubicacion
                    holder.fechaE.text = factura.fechaEntrada
                    holder.montoAP.text = (handler as Fragment).getString(
                        R.string.dinero,
                        factura.valorCobrado
                    )
                    if (operario) {
                        holder.buttonRetirarV.tag = "$i/$position"
                        holder.buttonRetirarV.visibility = View.VISIBLE
                        holder.buttonRetirarV.setOnClickListener(handler as RetirarVehiculoFragment)
                    }
                    break
                }
            }

        }

    }

    override fun getItemCount(): Int {
        var vehiculos = 0
        parqueaderos.forEach { it ->
            vehiculos += it.listaVehiculos.size
        }
        return vehiculos
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val parqueadero = v.findViewById<TextView>(R.id.parqueaderoRVC)
        val ubicacion = v.findViewById<TextView>(R.id.ubicacionRVC)
        val nombreV = v.findViewById<TextView>(R.id.nombreVehiculoRVC)
        val placaV = v.findViewById<TextView>(R.id.placaVehiculoRVC)
        val fechaE = v.findViewById<TextView>(R.id.fechaEntradaRVC)
        val montoAP = v.findViewById<TextView>(R.id.montoAPagarRVC)
        val buttonRetirarV = v.findViewById<Button>(R.id.buttonRetirarRVC)
    }

}