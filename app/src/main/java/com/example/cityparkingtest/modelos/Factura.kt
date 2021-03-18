package com.example.cityparkingtest.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Una factura se genera cuando ingresa un vehiculo. Cuando se saca el vehiculo se llena el campo de fecha salida y se guarda la factura en Transaccion
@Parcelize
data class Factura(
    var id: Int,
    var placaVehiculo: String,
    var valorCobrado: String,
    var fechaEntrada: String,
    var fechaSalida: String?
) : Parcelable