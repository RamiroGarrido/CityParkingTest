package com.example.cityparkingtest.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Una transaccion es un recibo ya pagado por parte del usuario al sacar su vehiculo. Esta tiene un parqueadero asociado.
@Parcelize
data class Transaccion(
    var parqueadero: String,
    var factura:Factura,

): Parcelable