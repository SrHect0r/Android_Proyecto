package com.example.android_proyecto

data class Proyecto(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val estado: String,
    val fecha: String,
    val prioridad: String,
    val usuarioEmail: String // Asociación con el usuario
                   )
