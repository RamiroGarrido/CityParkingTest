package com.example.cityparkingtest.utilidades

import com.example.cityparkingtest.modelos.*

//Constantes guarda los datos generados mientras la aplicacion se encuentra activa y contiene valores estaticos
class Constantes {

    val TAG_GENERAL = "RGM ->"
    var ID_FACTURAS = 0
    val VALOR_VALET = 10000
    val VALOR_PARQUEO = 5000
    //Despues de 12 horas se cobra extra
    val HORAS_MAX = 12
    val KEY_OPERARIO = "operarioKey"

    val OPERARIOS = listOf<Operario>(
        Operario(0,"Juan Perez"),
        Operario(1,"Ana Rodriguez"),
        Operario(2,"Camilo Vanegas")
    )

    val PARQUEADEROS = listOf<Parqueadero>(
        Parqueadero("Parqueadero 1", mutableListOf<Vehiculo>(), mutableListOf<Factura>()),
        Parqueadero("Parqueadero 2", mutableListOf<Vehiculo>(), mutableListOf<Factura>()),
        Parqueadero("Parqueadero 3", mutableListOf<Vehiculo>(), mutableListOf<Factura>())
    )

    var TRANSACCIONES = mutableListOf<Transaccion>()
    var ACCIONES_OPERARIOS = mutableListOf<Accion>()

    val FORMATO_DATE = "dd/MM/yyyy"
    val FORMATO_TIME = "HH:mm"
    val FORMATO_DATETIME = "dd/MM/yyyy HHmm"
}