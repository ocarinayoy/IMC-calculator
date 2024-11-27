package com.tdm.imc

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.start_page) // Inicialmente cargamos la pantalla de bienvenida

        val startButton = findViewById<Button>(R.id.startButton) // Botón para ingresar

        // Al pulsar el botón startButton, cambiamos al layout principal (activity_main)
        startButton.setOnClickListener {
            setContentView(R.layout.activity_main) // Cargamos el layout de la pantalla principal

            // Aquí ya podemos acceder a los EditText y el Button de la pantalla principal
            val calcularButton = findViewById<Button>(R.id.calcular_button)
            val pesoEditText = findViewById<EditText>(R.id.peso)
            val alturaEditText = findViewById<EditText>(R.id.altura)
            val resultadoTextView = findViewById<TextView>(R.id.resultado)

            // Configuramos el listener del botón para calcular el IMC
            calcularButton.setOnClickListener {
                val peso = pesoEditText.text.toString().toDoubleOrNull() // Convertimos el texto a Double
                val altura = alturaEditText.text.toString().toDoubleOrNull()

                // Si los valores son válidos, calculamos el IMC
                if (peso != null && altura != null) {
                    val imc = calcularIMC(peso, altura)
                    resultadoTextView.text = "IMC: %.2f".format(imc) // Mostramos el resultado
                } else {
                    resultadoTextView.text = "Por favor ingresa valores válidos." // Si no son válidos
                }

                hideKeyboard(alturaEditText) // Ocultamos el teclado
            }
        }

        // Este código parece correcto para manejar los márgenes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Función para calcular el IMC
    fun calcularIMC(peso: Double, altura: Double): Double {
        val alturaMetros = altura / 100.0 // Convertimos la altura a metros
        val imc = peso / (alturaMetros * alturaMetros) // Fórmula del IMC
        return imc
    }

    // Función para cerrar el teclado
    private fun hideKeyboard(view: EditText) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
