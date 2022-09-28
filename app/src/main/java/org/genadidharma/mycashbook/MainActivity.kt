package org.genadidharma.mycashbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.InternalCoroutinesApi
import org.genadidharma.mycashbook.databinding.ActivityMainBinding
import org.genadidharma.mycashbook.databinding.ActivityTambahPemasukanBinding
import org.genadidharma.mycashbook.db.User

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivTambahPemasukan.setOnClickListener {
            startActivity(Intent(this, TambahPemasukanActivity::class.java))
        }

        binding.ivTambahPengeluaran.setOnClickListener {
            startActivity(Intent(this, TambahPengeluaranActivity::class.java))
        }
    }
}