package com.suafacul

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface UsuarioDao {

    @Insert
    fun inserir(usuario: Usuario)

    @Query("SELECT * FROM usuario")
    fun listar(): List<Usuario>

    @Query("SELECT * FROM usuario WHERE email = :email AND senha = :senha LIMIT 1")
    fun login(email: String, senha: String): Usuario?

    @Delete
    fun deletar(usuario: Usuario)

    @Update
    fun atualizar(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE id = :id LIMIT 1")
    fun obterPorId(id: Int): Usuario?
}