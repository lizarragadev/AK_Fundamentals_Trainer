package com.miramicodigo.trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ResultadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        val lista = intent.putParcelableArrayListExtra("lista", null!!) as MutableList<Pais>

        lista.forEach {
            println("PRINTTTTTTTTTT : ${it.respuesta}")
        }
    }
}
