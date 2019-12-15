package com.miramicodigo.trivia

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var posicion = 0
    lateinit var lista: MutableList<Pais>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        lista = cargarDatos()

        crearSwitcher()
        asignarAnimacion()

        isImagen.setImageResource(lista[posicion].imagen)
        tsUno.setText(lista[posicion].opciones[0])
        tsDos.setText(lista[posicion].opciones[1])
        tsTres.setText(lista[posicion].opciones[2])
        tsUno.background = ContextCompat.getDrawable(this, R.drawable.boton_opciones)
        tsDos.background = ContextCompat.getDrawable(this, R.drawable.boton_opciones)
        tsTres.background = ContextCompat.getDrawable(this, R.drawable.boton_opciones)
        tsUno.isEnabled = true
        tsDos.isEnabled = true
        tsTres.isEnabled = true


        btnSiguiente.setOnClickListener {
            if(posicion < lista.size - 1) {
                if(lista[posicion].seleccionado != -1) {
                    if(posicion != lista.size - 1) {
                        posicion++
                        isImagen.setImageResource(lista[posicion].imagen)
                        tsUno.setText(lista[posicion].opciones[0])
                        tsDos.setText(lista[posicion].opciones[1])
                        tsTres.setText(lista[posicion].opciones[2])
                        tsUno.background = ContextCompat.getDrawable(this, R.drawable.boton_opciones)
                        tsDos.background = ContextCompat.getDrawable(this, R.drawable.boton_opciones)
                        tsTres.background = ContextCompat.getDrawable(this, R.drawable.boton_opciones)
                        tsUno.isEnabled = true
                        tsDos.isEnabled = true
                        tsTres.isEnabled = true
                    }
                } else {
                    val anim = AnimationUtils.loadAnimation(this, R.anim.shake)
                    tsUno.startAnimation(anim)
                    tsDos.startAnimation(anim)
                    tsTres.startAnimation(anim)
                }
            } else {
                btnSiguiente.text = "VER RESULTADOS"
                calcularResultado(lista)
            }
        }

        tsUno.setOnClickListener {
            opcionSeleccionada(0)
        }
        tsDos.setOnClickListener {
            opcionSeleccionada(1)
        }
        tsTres.setOnClickListener {
            opcionSeleccionada(2)
        }

    }

    fun opcionSeleccionada(pos: Int) {
        lista[posicion].seleccionado = pos

        tsUno.isEnabled = false
        tsDos.isEnabled = false
        tsTres.isEnabled = false

        Log.e("POSICION QUE LLEGA", "Posicion: $pos")
        Log.e("POSICION SELECCIONADA", "Posicion: ${lista[posicion].seleccionado}")
        Log.e("POSICION RESPUESTA", "Posicion: ${lista[posicion].respuesta}")

        if(lista[posicion].seleccionado == lista[posicion].respuesta) {
            when(pos) {
                0 -> {
                    tsUno.background = ContextCompat.getDrawable(this, R.drawable.correcto)
                    tsDos.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                    tsTres.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                }
                1 -> {
                    tsDos.background = ContextCompat.getDrawable(this, R.drawable.correcto)
                    tsUno.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                    tsTres.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                }
                2 -> {
                    tsTres.background = ContextCompat.getDrawable(this, R.drawable.correcto)
                    tsUno.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                    tsDos.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                }
            }
        } else {
            when(pos) {
                0 -> {
                    tsUno.background = ContextCompat.getDrawable(this, R.drawable.incorrecto)
                    if(lista[posicion].respuesta == 1) {
                        tsDos.background = ContextCompat.getDrawable(this, R.drawable.correcto_hide)
                        tsTres.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                    } else {
                        tsDos.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                        tsTres.background = ContextCompat.getDrawable(this, R.drawable.correcto_hide)
                    }
                }
                1 -> {
                    tsDos.background = ContextCompat.getDrawable(this, R.drawable.incorrecto)
                    if(lista[posicion].respuesta == 0) {
                        tsUno.background = ContextCompat.getDrawable(this, R.drawable.correcto_hide)
                        tsTres.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                    } else {
                        tsUno.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                        tsTres.background = ContextCompat.getDrawable(this, R.drawable.correcto_hide)
                    }
                }
                2 -> {
                    tsTres.background = ContextCompat.getDrawable(this, R.drawable.incorrecto)
                    if(lista[posicion].respuesta == 0) {
                        tsUno.background = ContextCompat.getDrawable(this, R.drawable.correcto_hide)
                        tsDos.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                    } else {
                        tsUno.background = ContextCompat.getDrawable(this, R.drawable.incorrecto_hide)
                        tsDos.background = ContextCompat.getDrawable(this, R.drawable.correcto_hide)
                    }
                }
            }
        }


    }

    fun crearSwitcher() {
        isImagen.setFactory {
            val imageView = ImageView(this)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView
        }
        tsUno.setFactory {
            val textView = TextView(this)
            textView.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            textView.textSize = 30f
            textView.setTextColor(Color.WHITE)
            textView
        }
        tsDos.setFactory {
            val textView = TextView(this)
            textView.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            textView.textSize = 30f
            textView.setTextColor(Color.WHITE)
            textView
        }
        tsTres.setFactory {
            val textView = TextView(this)
            textView.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            textView.textSize = 30f
            textView.setTextColor(Color.WHITE)
            textView
        }
    }

    fun asignarAnimacion() {
        val animIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val animOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

        isImagen.inAnimation = animIn
        isImagen.outAnimation = animOut
        tsUno.inAnimation = animIn
        tsUno.outAnimation = animOut
        tsDos.inAnimation = animIn
        tsDos.outAnimation = animOut
        tsTres.inAnimation = animIn
        tsTres.outAnimation = animOut
    }

    fun calcularResultado(lista: MutableList<Pais>) {
        lista[0].seleccionado
        val intent = Intent(this, ResultadoActivity::class.java)
        intent.putParcelableArrayListExtra("lista", lista as ArrayList<out Parcelable>)
        startActivity(intent)
    }

    fun cargarDatos(): MutableList<Pais> {
        val listaPaises = mutableListOf<Pais>()
        val opciones = resources.getStringArray(R.array.opciones)
        val imagenes = resources.obtainTypedArray(R.array.imagenes)
        val respuestas = resources.getIntArray(R.array.respuestas2)

        for (i in opciones.indices) {
            val p = Pais(
                opciones[i].split(",".toRegex()).toTypedArray(),
                imagenes.getResourceId(i, -1),
                respuestas[i],
                -1
            )
            listaPaises.add(p)
        }
        listaPaises.shuffle()
        return listaPaises
    }
}
