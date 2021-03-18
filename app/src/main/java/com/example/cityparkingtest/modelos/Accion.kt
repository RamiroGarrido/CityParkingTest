package com.example.cityparkingtest.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Una accion es un registro o un retiro por parte de un operario, con sus respectivas fechas
@Parcelize
data class Accion(
    val Operario:Operario,
    val registroVehiculo:Vehiculo?,
    val fechaRegistro:String?,
    val retiroVehiculo:Vehiculo?,
    val fechaRetiro:String?
): Parcelable