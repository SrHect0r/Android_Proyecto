package com.example.android_proyecto

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class VerProyectosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProyectoAdapter
    private lateinit var tvSinProyectos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_proyectos)

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerProyectos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // TextView para mensaje cuando no hay proyectos
        tvSinProyectos = findViewById(R.id.tvSinProyectos)

        // Inicializar adaptador
        adapter = ProyectoAdapter(mutableListOf()) { proyectoJson ->
            Log.d("DEBUG", "Click en proyecto: $proyectoJson")
            if (!proyectoJson.isNullOrEmpty()) {
                val intent = Intent(this, DetalleProyectoActivity::class.java)
                intent.putExtra("proyecto", proyectoJson)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error: Proyecto vacío", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter

        // Botón para agregar nuevo proyecto
        val btnNuevoProyecto = findViewById<FloatingActionButton>(R.id.btnNuevoProyecto)
        btnNuevoProyecto.setOnClickListener {
            startActivity(Intent(this, AgregarProyectoActivity::class.java))
        }

        // Cargar proyectos
        actualizarListaProyectos()
    }

    override fun onResume() {
        super.onResume()
        actualizarListaProyectos()
    }

    private fun actualizarListaProyectos() {
        val prefs = getSharedPreferences("mis_prefs", MODE_PRIVATE)
        val usuarioActual = prefs.getString("usuario_actual", "") ?: ""
        val listaDeProyectos = obtenerProyectos(this, usuarioActual)

        adapter.actualizarLista(listaDeProyectos)

        // Mostrar mensaje si no hay proyectos
        tvSinProyectos.visibility = if (listaDeProyectos.isEmpty()) TextView.VISIBLE else TextView.GONE
    }

    // --- Función para obtener proyectos guardados ---
    private fun guardarProyecto(context: Context, usuarioEmail: String, proyecto: Proyecto) {
        val prefs = context.getSharedPreferences("proyectos_$usuarioEmail", Context.MODE_PRIVATE)
        val gson = Gson()
        val proyectosJson = prefs.getString("lista_proyectos", "[]")
        val type = object : TypeToken<MutableList<Proyecto>>() {}.type

        val proyectos: MutableList<Proyecto> =
            gson.fromJson<MutableList<Proyecto>>(proyectosJson, type)?.filterNotNull()?.toMutableList()
                ?: mutableListOf()

        proyectos.add(proyecto)

        prefs.edit().putString("lista_proyectos", gson.toJson(proyectos)).apply()
    }
}
