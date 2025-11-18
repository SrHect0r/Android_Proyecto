package com.example.android_proyecto

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val PREFS_NAME = "mis_prefs"
private const val KEY_USUARIOS = "usuarios"

// Obtener lista de todos los usuarios guardados
fun obtenerListaUsuarios(context: Context): MutableList<Usuario> {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val usuariosJson = prefs.getString(KEY_USUARIOS, null)
    val tipo = object : TypeToken<MutableList<Usuario>>() {}.type
    return if (usuariosJson != null) {
        Gson().fromJson(usuariosJson, tipo)
    } else {
        mutableListOf()
    }
}

// Guardar un nuevo usuario si no existe
fun guardarUsuario(context: Context, usuario: Usuario) {
    val listaUsuarios = obtenerListaUsuarios(context)
    if (listaUsuarios.none { it.email == usuario.email }) {
        listaUsuarios.add(usuario)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_USUARIOS, Gson().toJson(listaUsuarios)).apply()
    }
}

// Validar login con múltiples usuarios
fun validarUsuario(context: Context, email: String, password: String): Boolean {
    val listaUsuarios = obtenerListaUsuarios(context)
    return listaUsuarios.any { it.email == email && it.password == password }
}
