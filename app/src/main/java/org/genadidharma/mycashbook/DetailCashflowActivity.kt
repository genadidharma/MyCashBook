package org.genadidharma.mycashbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import org.genadidharma.mycashbook.databinding.ActivityDetailCashflowBinding
import org.genadidharma.mycashbook.db.Cash
import org.genadidharma.mycashbook.db.MyCashBookDatabase

@InternalCoroutinesApi
class DetailCashflowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailCashflowBinding
    private lateinit var db: MyCashBookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCashflowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyCashBookDatabase.getDatabase(applicationContext)

        GlobalScope.launch {
            val listCash = getCashes()

            runOnUiThread {
                val adapter = CashflowAdapter(listCash)
                binding.rvCashflow.adapter = adapter
                binding.rvCashflow.layoutManager = LinearLayoutManager(applicationContext)
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getCashes(): List<Cash> {
        return db.cashDao().getAllCashes()
    }
}