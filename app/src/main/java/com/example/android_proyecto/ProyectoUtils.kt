package com.example.android_proyecto

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Guardar un proyecto para un usuario específico
fun guardarProyecto(context: Context, usuario: String, proyecto: Proyecto) {
    val prefs = context.getSharedPreferences("mis_prefs", Context.MODE_PRIVATE)
    val gson = Gson()

    val proyectosJson = prefs.getString("proyectos_$usuario", null)
    val tipo = object : TypeToken<MutableList<Proyecto>>() {}.type
    val listaProyectos: MutableList<Proyecto> = if (proyectosJson != null) {
        gson.fromJson(proyectosJson, tipo)
    } else {
        mutableListOf()
    }

    listaProyectos.add(proyecto)
    prefs.edit().putString("proyectos_$usuario", gson.toJson(listaProyectos)).apply()
}


// Obtener todos los proyectos de un usuario
fun obtenerProyectos(context: Context, usuario: String): MutableList<Proyecto> {
    val prefs = context.getSharedPreferences("mis_prefs", Context.MODE_PRIVATE)
    val gson = Gson()
    val proyectosJson = prefs.getString("proyectos_$usuario", null)
    val tipo = object : TypeToken<MutableList<Proyecto>>() {}.type
    return if (proyectosJson != null) {
        gson.fromJson(proyectosJson, tipo)
    } else {
        mutableListOf()
    }
}

// Eliminar un proyecto específico de un usuario
fun eliminarProyecto(context: Context, usuarioEmail: String, proyecto: Proyecto) {
    val prefs = context.getSharedPreferences("mis_prefs", Context.MODE_PRIVATE)
    val gson = Gson()
    val lista = obtenerProyectos(context, usuarioEmail)
    lista.remove(proyecto)
    prefs.edit().putString("proyectos_$usuarioEmail", gson.toJson(lista)).apply()
}
