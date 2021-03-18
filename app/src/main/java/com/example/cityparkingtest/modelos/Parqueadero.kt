package com.example.cityparkingtest.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Un parqueadero tiene nombre propio, listas de vehiculos y listas de facturas asociadas a el. Al sacar un vehiculo, tambien se saca la factura y se guarda como Transaccion
@Parcelize
data class Parqueadero(
    var nombre:String,
    var listaVehiculos:MutableList<Vehiculo>,
    var listaFacturas:MutableList<Factura>
): Parcelable