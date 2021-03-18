package com.example.cityparkingtest.modelos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//Un operario es un trabajador de valet parking
@Parcelize
data class Operario(
 val id:Int,
 val nombre:String
) : Parcelable