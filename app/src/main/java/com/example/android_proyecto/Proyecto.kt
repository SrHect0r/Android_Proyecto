package com.example.android_proyecto

data class Proyecto(
    val id: Long,
    val titulo: String = "",
    val descripcion: String = "",
    val estado: String = "",
    val fecha: String = "",
    val prioridad: String = "",
    val usuarioEmail: String = "",
    val tareas: List<Tarea> = emptyList(),
    val usuariosCompartidos: List<String> = emptyList()
                   )

