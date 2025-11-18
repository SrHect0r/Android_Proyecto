// Proyecto.kt
package com.example.android_proyecto

import java.io.Serializable

data class Proyecto(
    val nombre: String,
    val descripcion: String,
    val fechaLimite: String,
    val tareas: MutableList<Tarea>
                   ) : Serializable
