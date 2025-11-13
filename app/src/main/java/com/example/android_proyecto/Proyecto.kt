package com.example.android_proyecto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Proyecto(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val estado: String,
    val fecha: String,
    val prioridad: String
                   ) : Parcelable
