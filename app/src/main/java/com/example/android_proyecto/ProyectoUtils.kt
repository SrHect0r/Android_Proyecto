package com.example.android_proyecto

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val PREFS_NAME = "mis_prefs"

// Guardar un proyecto para un usuario específico
fun guardarProyecto(context: Context, usuarioEmail: String, proyecto: Proyecto) {
    val listaProyectos = obtenerProyectos(context, usuarioEmail)
    listaProyectos.add(proyecto)
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putString("proyectos_$usuarioEmail", Gson().toJson(listaProyectos)).apply()
}

// Obtener todos los proyectos de un usuario
fun obtenerProyectos(context: Context, usuarioEmail: String): MutableList<Proyecto> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val proyectosJson = prefs.getString("proyectos_$usuarioEmail", null)
    val tipo = object : TypeToken<MutableList<Proyecto>>() {}.type
    return if (proyectosJson != null) {
        Gson().fromJson(proyectosJson, tipo)
    } else {
        mutableListOf()
    }
}

// Eliminar un proyecto específico de un usuario
fun eliminarProyecto(context: Context, usuarioEmail: String, proyecto: Proyecto) {
    val lista = obtenerProyectos(context, usuarioEmail)
    lista.remove(proyecto)
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit().putString("proyectos_$usuarioEmail", Gson().toJson(lista)).apply()
}
