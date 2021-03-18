package com.example.cityparkingtest.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Un vehiculo y su descripcion. La ubicacion hace referencia al puesto del parqueadero en el cual parqueo.
@Parcelize
data class Vehiculo(
    var nombre:String,
    var placa:String,
    var ubicacion:String,
    var telefono:String?,
    var golpeORayon:String?,
    var objetosDentro:String?
): Parcelable