package com.suafacul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.concurrent.thread

class HomeUserActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var usuarioId: Int = 0
    private var usuarioNome: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user)

        // Inicializar banco de dados
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "suafacul_db"
        ).build()

        // Obter dados do usuário do intent
        usuarioId = intent.getIntExtra("usuario_id", 0)
        usuarioNome = intent.getStringExtra("nome") ?: "Usuário"

        val txtBemVindo = findViewById<TextView>(R.id.txtBemVindo)
        txtBemVindo.text = getString(R.string.welcome_message, usuarioNome)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Configurar dados das faculdades
        val dadosTags = listOf(Tag("USP"), Tag("UNAPIAGET"), Tag("MACKENZIE"))

        val dadosPublicos = listOf(
            Faculdade("USP", R.drawable.usp, isSalva = true),
            Faculdade("UNICAMP", R.drawable.piaget, isSalva = false)
        )

        val dadosPrivadas = listOf(
            Faculdade("Mackenzie", R.drawable.mackenzie, isSalva = false),
            Faculdade("UNAPIAGET", R.drawable.piaget, isSalva = true)
        )

        setupTagRecyclerView(R.id.recyclerViewTags, dadosTags)
        setupFaculdadeRecyclerView(R.id.recyclerViewPublicas, dadosPublicos)
        setupFaculdadeRecyclerView(R.id.recyclerViewPrivadas, dadosPrivadas)

        // Configurar navegação inferior
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    // Já estamos na home
                    true
                }
                R.id.navigation_profile -> {
                    // Navegar para tela de editar perfil
                    abrirTelaEditarPerfil()
                    true
                }
                R.id.navigation_settings -> {
                    // Configurações (pode implementar depois)
                    true
                }
                else -> false
            }
        }
    }

    private fun abrirTelaEditarPerfil() {
        val intent = Intent(this, EditarPerfilActivity::class.java)
        intent.putExtra("usuario_id", usuarioId)
        startActivity(intent)
    }

    private fun setupFaculdadeRecyclerView(recyclerViewId: Int, dataList: List<Faculdade>) {
        val recyclerView = findViewById<RecyclerView>(recyclerViewId)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = FaculdadeAdapter(dataList)
    }

    private fun setupTagRecyclerView(recyclerViewId: Int, dataList: List<Tag>) {
        val recyclerView = findViewById<RecyclerView>(recyclerViewId)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = TagAdapter(dataList)
    }
}
