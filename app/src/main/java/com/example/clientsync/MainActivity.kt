package com.example.clientsync

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    companion object {
        private const val API_URL =
            "http://hwsistemas.homelinux.com/apiclienteteste/api/cliente/retornaclientes?tipo=json"
        private const val TIMEOUT = 10000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)

        val btnSincronizar = findViewById<Button>(R.id.btn_sincronizar)
        btnSincronizar.setOnClickListener {
            sincronizarClientes()
        }
    }

    private fun sincronizarClientes() {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val jsonArray = baixarClientes()
                salvarClientes(jsonArray)
                mostrarMensagem("Sincronização concluída!")
                abrirListaClientes()
            } catch (e: Exception) {
                mostrarMensagem("Erro: ${e.message}")
            }
        }
    }

    private fun baixarClientes(): JSONArray {
        val connection = URL(API_URL).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = TIMEOUT
        connection.readTimeout = TIMEOUT

        val response = BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
            reader.readText()
        }
        connection.disconnect()

        return JSONArray(response)
    }

    private fun salvarClientes(jsonArray: JSONArray) {
        for (i in 0 until jsonArray.length()) {
            val cliente = jsonArray.getJSONObject(i)
            dbHelper.inserirCliente(cliente)
        }
    }

    private fun mostrarMensagem(mensagem: String) {
        runOnUiThread {
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirListaClientes() {
        runOnUiThread {
            startActivity(Intent(this, ListaActivity::class.java))
        }
    }
}
