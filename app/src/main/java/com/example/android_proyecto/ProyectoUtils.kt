package com.example.android_proyecto

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

fun eliminarProyecto(context: Context, usuario: String, proyecto: Proyecto) {
    val prefs = context.getSharedPreferences("mis_prefs", Context.MODE_PRIVATE)
    val gson = Gson()
    val lista = obtenerProyectos(context, usuario)
    lista.remove(proyecto)
    prefs.edit().putString("proyectos_$usuario", gson.toJson(lista)).apply()
}
