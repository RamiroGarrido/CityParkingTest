package com.example.cityparkingtest.utilidades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cityparkingtest.R
import com.example.cityparkingtest.modelos.Accion

//Adaptador para mostrar las acciones realizadas por operarios de valet
class RecyclerVAdapterAcciones(
    val acciones: List<Accion>
) : RecyclerView.Adapter<RecyclerVAdapterAcciones.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerVAdapterAcciones.ViewHolder {
        var itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_child_acciones_operario, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: RecyclerVAdapterAcciones.ViewHolder, position: Int) {
        holder.idOperario.text = acciones[position].Operario.id.toString()
        holder.operario.text = acciones[position].Operario.nombre
        var vehiculosReg = 0
        var vehiculosRet = 0
            when(acciones[position].Operario.id){
            0 -> {
                vehiculosReg = acciones.count { it?.Operario.id==0 && it?.registroVehiculo != null }
                vehiculosRet = acciones.count {  it?.Operario.id==0 && it?.retiroVehiculo != null }
            }
            1 ->{
                vehiculosReg = acciones.count { it?.Operario.id==1 && it?.registroVehiculo != null }
                vehiculosRet = acciones.count {  it?.Operario.id==1 && it?.retiroVehiculo != null }
            }
            2 ->{
                vehiculosReg = acciones.count { it?.Operario.id==2 && it?.registroVehiculo != null }
                vehiculosRet = acciones.count {  it?.Operario.id==2 && it?.retiroVehiculo != null }
            }
        }
        holder.vehiculosReg.text = vehiculosReg.toString()
        holder.vehiculosRet.text = vehiculosRet.toString()
    }

    override fun getItemCount(): Int {
        return acciones.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val idOperario = v.findViewById<TextView>(R.id.idOperarioRVCA)
        val operario = v.findViewById<TextView>(R.id.operarioRVCA)
        val vehiculosReg = v.findViewById<TextView>(R.id.vehiculosRegRVCA)
        val vehiculosRet = v.findViewById<TextView>(R.id.vehiculosRetRVCA)
    }
}