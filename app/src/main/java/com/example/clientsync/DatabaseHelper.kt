package com.example.clientsync

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.json.JSONObject

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE clientes (
                id INTEGER PRIMARY KEY,
                nome TEXT,
                cpf TEXT,
                created_at TEXT,
                updated_at TEXT
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS clientes")
        onCreate(db)
    }

    fun inserirCliente(cliente: JSONObject) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id", cliente.optInt("codigo"))
            put("nome", cliente.optString("nome"))
            put("cpf", cliente.optString("cpf"))
        }
        db.insertWithOnConflict("clientes", null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun listarClientes(): List<String> {
        val clientes = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM clientes", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val cpf = cursor.getString(cursor.getColumnIndexOrThrow("cpf"))
            clientes.add("$id - $nome\nCPF: $cpf")
        }
        cursor.close()
        db.close()
        return clientes
    }

    companion object {
        const val DATABASE_NAME = "clientsync.db"
        const val DATABASE_VERSION = 1
    }
}
