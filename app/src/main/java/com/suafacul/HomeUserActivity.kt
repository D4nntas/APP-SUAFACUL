package com.suafacul

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_user)

        val nomeUsuario = intent.getStringExtra("nome") ?: "Usuário"
        val txtBemVindo = findViewById<TextView>(R.id.txtBemVindo)
        txtBemVindo.text = getString(R.string.welcome_message, nomeUsuario)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

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
            setupBottomNavigation(R.id.bottomNavigationView)

            insets
        }
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

    private fun setupBottomNavigation(navId: Int) {
        val bottomNavigationView = findViewById<BottomNavigationView>(navId)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_settings -> true
                R.id.navigation_profile -> true
                else -> false
            }
        }
    }
}