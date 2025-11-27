// Tarea.kt
package com.example.android_proyecto

import java.io.Serializable

data class Tarea(
    val id: String,
    val nombre: String,
    val descripcion: String,
    var estado: String,
    val prioridad: String,
    val fechaCreacion: String,
    val fechaInicio: String,
    val fechaFin: String,
    var completada: Boolean,
    val usuariosAsignados: List<String>,
    var horas: Int = 0,          // Inicializado por defecto
    var comentario: String = ""  // Inicializado por defecto
                ) : Serializable

