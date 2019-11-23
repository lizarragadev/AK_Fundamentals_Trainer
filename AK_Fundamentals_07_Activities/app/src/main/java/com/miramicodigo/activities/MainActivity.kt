package com.miramicodigo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnIrAUno.setOnClickListener {
            startActivity(Intent(this, UnoActivity::class.java))
        }

        btnIrADos.setOnClickListener {
            startActivity(Intent(this, DosActivity::class.java))
        }

        btnIrATres.setOnClickListener {
            startActivity(Intent(this, TresActivity::class.java))
            finish()
        }
    }
}
