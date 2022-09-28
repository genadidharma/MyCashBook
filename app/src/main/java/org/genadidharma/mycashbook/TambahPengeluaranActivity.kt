package org.genadidharma.mycashbook

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import org.genadidharma.mycashbook.databinding.ActivityTambahPemasukanBinding
import org.genadidharma.mycashbook.databinding.ActivityTambahPengeluaranBinding
import org.genadidharma.mycashbook.db.Cash
import org.genadidharma.mycashbook.db.MyCashBookDatabase
import java.util.*

@InternalCoroutinesApi
class TambahPengeluaranActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahPengeluaranBinding
    private lateinit var db: MyCashBookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahPengeluaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyCashBookDatabase.getDatabase(applicationContext)

        binding.edtDate.setOnClickListener {
            showDateDialog()
        }

        binding.ivCalendar.setOnClickListener {
            showDateDialog()
        }

        binding.btnSave.setOnClickListener {
            val date = binding.edtDate.text.toString()
            val money = binding.edtMoney.text.toString()
            val description = binding.edtDescription.text.toString()

            if (inputCheck(date, money, description)) {
                GlobalScope.launch {
                    insert(date, money, description)
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Pengeluaran berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Isi seluruh data terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun insert(date: String, money: String, description: String) {
        val cash = Cash(date, money, description, false)
        db.cashDao().insert(cash)
    }

    private fun inputCheck(date: String, money: String, description: String): Boolean {
        return !(TextUtils.isEmpty(date) && TextUtils.isEmpty(money) && TextUtils.isEmpty(
            description
        ))
    }

    private fun showDateDialog() {
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val date = (dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                binding.edtDate.setText(date)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}