package com.suafacul

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Usuario::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "suafacul_db"
                )
                    .fallbackToDestructiveMigration() // ESTA LINHA RESOLVE O ERRO
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
