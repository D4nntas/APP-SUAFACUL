package com.suafacul

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlin.concurrent.thread

class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var edtNome: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtSenha: EditText
    private lateinit var btnSalvar: Button
    private lateinit var btnVoltar: Button
    private lateinit var db: AppDatabase
    private var usuarioId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        // Inicializar banco de dados
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "suafacul_db"
        ).build()

        // Inicializar views
        edtNome = findViewById(R.id.edtNomePerfil)
        edtEmail = findViewById(R.id.edtEmailPerfil)
        edtSenha = findViewById(R.id.edtSenhaPerfil)
        btnSalvar = findViewById(R.id.btnSalvarPerfil)
        btnVoltar = findViewById(R.id.btnVoltarPerfil)

        // Obter ID do usuário do intent
        usuarioId = intent.getIntExtra("usuario_id", 0)

        // Carregar dados do usuário
        carregarDadosUsuario()

        // Configurar listeners
        btnSalvar.setOnClickListener {
            salvarAlteracoes()
        }

        btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun carregarDadosUsuario() {
        thread {
            val usuario = db.usuarioDao().obterPorId(usuarioId)

            runOnUiThread {
                if (usuario != null) {
                    edtNome.setText(usuario.nome)
                    edtEmail.setText(usuario.email)
                    edtSenha.setText(usuario.senha)
                } else {
                    Toast.makeText(
                        this,
                        "Erro ao carregar dados do usuário",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }

    private fun salvarAlteracoes() {
        val novoNome = edtNome.text.toString().trim()
        val novoEmail = edtEmail.text.toString().trim()
        val novaSenha = edtSenha.text.toString().trim()

        // Validação
        if (novoNome.isEmpty()) {
            edtNome.error = "Nome não pode estar vazio"
            return
        }

        if (novoEmail.isEmpty()) {
            edtEmail.error = "Email não pode estar vazio"
            return
        }

        if (novaSenha.isEmpty()) {
            edtSenha.error = "Senha não pode estar vazia"
            return
        }

        if (novaSenha.length < 6) {
            edtSenha.error = "Senha deve ter no mínimo 6 caracteres"
            return
        }

        // Salvar no banco de dados
        thread {
            val usuario = db.usuarioDao().obterPorId(usuarioId)

            if (usuario != null) {
                val usuarioAtualizado = usuario.copy(
                    nome = novoNome,
                    email = novoEmail,
                    senha = novaSenha
                )

                db.usuarioDao().atualizar(usuarioAtualizado)

                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Perfil atualizado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }
}
