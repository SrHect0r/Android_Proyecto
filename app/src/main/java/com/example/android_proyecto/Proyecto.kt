// Proyecto.kt
package com.example.android_proyecto

import java.io.Serializable

data class Proyecto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val fechaInicio: String,
    val fechaFin: String,
    val usuariosAsignados: List<String>,
    val tareas: MutableList<Tarea>
                   ) : Serializable
