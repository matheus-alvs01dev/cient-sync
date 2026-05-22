package com.example.clientsync

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        val dbHelper = DatabaseHelper(this)
        val clientes = dbHelper.listarClientes()

        val listView = findViewById<ListView>(R.id.list_clientes)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, clientes)
        listView.adapter = adapter
    }
}
