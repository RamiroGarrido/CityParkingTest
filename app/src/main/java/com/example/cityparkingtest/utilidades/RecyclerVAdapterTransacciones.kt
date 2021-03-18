package com.example.cityparkingtest.utilidades

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.cityparkingtest.R
import com.example.cityparkingtest.modelos.Parqueadero
import com.example.cityparkingtest.modelos.Transaccion
import com.example.cityparkingtest.modulos.retirar_vehiculo.RetirarVehiculoFragment

//Adaptador para mostrar las transacciones guardadas al sacar un vehiculo y haber pagado una factura.
class RecyclerVAdapterTransacciones(
    val transacciones: List<Transaccion>,
    val resources: Resources
) : RecyclerView.Adapter<RecyclerVAdapterTransacciones.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerVAdapterTransacciones.ViewHolder {
        var itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_child_transaccion, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: RecyclerVAdapterTransacciones.ViewHolder, position: Int) {
        holder.facturaId.text = transacciones[position].factura.id.toString()
        holder.placa.text = transacciones[position].factura.placaVehiculo
        holder.valorT.text = resources.getString(
            R.string.dinero,
            transacciones[position].factura.valorCobrado
        )
        holder.fechaE.text = transacciones[position].factura.fechaEntrada
        holder.fechaS.text = transacciones[position].factura.fechaSalida

    }

    override fun getItemCount(): Int {
        return transacciones.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val facturaId = v.findViewById<TextView>(R.id.facturaRVCT)
        val placa = v.findViewById<TextView>(R.id.placaRVCT)
        val valorT = v.findViewById<TextView>(R.id.valorTotalRVCT)
        val fechaE = v.findViewById<TextView>(R.id.fechaEntradaRVCT)
        val fechaS = v.findViewById<TextView>(R.id.fechaSalidaRVCT)
    }
}
