package com.suafacul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlin.concurrent.thread

class ListaUsuariosActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_usuarios)

        recyclerView = findViewById(R.id.rvUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "suafacul_db"
        ).build()

        carregarUsuarios()
    }

    private fun carregarUsuarios() {
        thread {
            val listaUsuarios = db.usuarioDao().listar()

            runOnUiThread {
                recyclerView.adapter = UsuarioAdapter(
                    listaUsuarios,
                    onEditar = { usuario -> abrirDialogoEditar(usuario) },
                    onDeletar = { usuario -> abrirDialogoDeletar(usuario) }
                )
            }
        }
    }

    private fun abrirDialogoEditar(usuario: Usuario) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Usuário")

        // Criar EditTexts para nome e email
        val layoutEdit = android.widget.LinearLayout(this)
        layoutEdit.orientation = android.widget.LinearLayout.VERTICAL
        layoutEdit.setPadding(16, 16, 16, 16)

        val editNome = EditText(this)
        editNome.hint = "Nome"
        editNome.setText(usuario.nome)

        val editEmail = EditText(this)
        editEmail.hint = "Email"
        editEmail.setText(usuario.email)

        val editSenha = EditText(this)
        editSenha.hint = "Senha"
        editSenha.setText(usuario.senha)
        editSenha.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD

        layoutEdit.addView(editNome)
        layoutEdit.addView(editEmail)
        layoutEdit.addView(editSenha)

        builder.setView(layoutEdit)

        builder.setPositiveButton("Salvar") { _, _ ->
            val novoNome = editNome.text.toString()
            val novoEmail = editEmail.text.toString()
            val novaSenha = editSenha.text.toString()

            if (novoNome.isEmpty() || novoEmail.isEmpty() || novaSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val usuarioAtualizado = usuario.copy(
                nome = novoNome,
                email = novoEmail,
                senha = novaSenha
            )

            thread {
                db.usuarioDao().atualizar(usuarioAtualizado)

                runOnUiThread {
                    Toast.makeText(this, "Usuário atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    carregarUsuarios()
                }
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun abrirDialogoDeletar(usuario: Usuario) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deletar Usuário")
        builder.setMessage("Tem certeza que deseja deletar ${usuario.nome}?")

        builder.setPositiveButton("Deletar") { _, _ ->
            thread {
                db.usuarioDao().deletar(usuario)

                runOnUiThread {
                    Toast.makeText(this, "Usuário deletado com sucesso", Toast.LENGTH_SHORT).show()
                    carregarUsuarios()
                }
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}
