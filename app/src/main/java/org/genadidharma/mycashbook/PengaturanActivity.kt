package org.genadidharma.mycashbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.genadidharma.mycashbook.databinding.ActivityPengaturanBinding
import org.genadidharma.mycashbook.db.MyCashBookDatabase

@InternalCoroutinesApi
class PengaturanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPengaturanBinding
    private lateinit var db: MyCashBookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengaturanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyCashBookDatabase.getDatabase(applicationContext)

        binding.btnSimpan.setOnClickListener {
            val currentPassword = binding.edtCurrentPassword.text.toString()
            val newPassword = binding.edtNewPassword.text.toString()

            if (inputCheck(currentPassword, newPassword)) {
                changePassword(currentPassword, newPassword)
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

    private fun changePassword(currentPassword: String, newPassword: String) {
        GlobalScope.launch {
            if (db.userDao().checkPassword(currentPassword)) {
                db.userDao().changePassword(currentPassword, newPassword)
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "Password berhasil diubah",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        "Masukan password lama yang sesuai",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun inputCheck(currentPassword: String, newPassword: String): Boolean {
        return !(TextUtils.isEmpty(currentPassword) && TextUtils.isEmpty(newPassword))
    }
}