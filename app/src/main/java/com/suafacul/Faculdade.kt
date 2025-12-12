package com.suafacul

data class Faculdade(
    val nome: String,
    val logo: Int, // Usando Int para o ID do drawable
    var isSalva: Boolean = false
)