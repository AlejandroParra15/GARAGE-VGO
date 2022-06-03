package com.example.garage_vgo

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.garage_vgo.databinding.ActivityNotificationSoatTecnicoImpueBinding
import java.util.*

class NotificationSoatTecnicoImpue : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationSoatTecnicoImpueBinding
    private lateinit var datePickerDialog : DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSoatTecnicoImpueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextDate.setOnClickListener {
            val c: Calendar = Calendar.getInstance()
            val mYear: Int = c.get(Calendar.YEAR)

            val mMonth: Int = c.get(Calendar.MONTH)

            val mDay: Int = c.get(Calendar.DAY_OF_MONTH)

            datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    binding.editTextDate.setText(
                        dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        val documento:String = intent.getStringExtra("tipo").toString()

        when (documento) {
            "impuestos" -> binding.textView8.text = "Fecha de vencimiento de los impuestos"
            "tecnicomecanico" -> binding.textView8.text = "Fecha de vencimiento del tecnico mecanico"
            "soat" -> binding.textView8.text = "Fecha de vencimiento del SOAT"
        }

        binding.btEnviarSoat.setOnClickListener {  }

        binding.btCancelarSoat.setOnClickListener { finish() }

    }
}