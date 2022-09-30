package org.genadidharma.mycashbook

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import org.genadidharma.mycashbook.databinding.ActivityMainBinding
import org.genadidharma.mycashbook.databinding.ActivityTambahPemasukanBinding
import org.genadidharma.mycashbook.db.Cash
import org.genadidharma.mycashbook.db.MyCashBookDatabase
import org.genadidharma.mycashbook.db.User
import java.lang.reflect.Array
import java.text.SimpleDateFormat
import java.util.*

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: MyCashBookDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyCashBookDatabase.getDatabase(applicationContext)

        drawGraph()

        binding.ivTambahPemasukan.setOnClickListener {
            startActivity(Intent(this, TambahPemasukanActivity::class.java))
        }

        binding.ivTambahPengeluaran.setOnClickListener {
            startActivity(Intent(this, TambahPengeluaranActivity::class.java))
        }

        binding.ivDetailCashflow.setOnClickListener {
            startActivity(Intent(this, DetailCashflowActivity::class.java))
        }

        binding.ivPengaturan.setOnClickListener {
            startActivity(Intent(this, PengaturanActivity::class.java))
        }
    }

    private fun drawGraph() {
        GlobalScope.launch {
            var index = 0

            val label = arrayListOf<String>()

            val listIncome = arrayListOf<Entry>()
            val listExpense = arrayListOf<Entry>()

            val cashes = db.cashDao().getAllCashes().sortedBy { it.date }.groupBy { it.date }

            cashes.forEach { entry ->
                label.add(entry.key)

                var addedIncomeMoney = 0
                var addedExpenseMoney = 0

                entry.value.forEach {
                    if (it.isIncome) {
                        addedIncomeMoney += it.money.toInt()
                    } else {
                        addedExpenseMoney += it.money.toInt()
                    }
                }

                if (addedIncomeMoney > 0) {
                    listIncome.add(Entry(index.toFloat(), addedIncomeMoney.toFloat()))
                }

                if (addedExpenseMoney > 0) {
                    listExpense.add(Entry(index.toFloat(), addedExpenseMoney.toFloat()))
                }
                index++
            }


            val incomeDataset = LineDataSet(listIncome, "Pemasukan")
            incomeDataset.mode = LineDataSet.Mode.LINEAR
            incomeDataset.color = Color.GREEN
            incomeDataset.circleRadius = 5f
            incomeDataset.setCircleColor(Color.GREEN)

            val expenseDataset = LineDataSet(listExpense, "Pengeluaran")
            incomeDataset.mode = LineDataSet.Mode.LINEAR
            incomeDataset.color = Color.RED
            incomeDataset.circleRadius = 5f
            incomeDataset.setCircleColor(Color.RED)

            val tanggal = AxisDateFormatter(label.toArray(arrayOfNulls<String>(label.size)))
            binding.gvStatistik.xAxis?.valueFormatter = tanggal

            //Setup Legend
            val legend = binding.gvStatistik.legend
            legend.isEnabled = true
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false)

            binding.gvStatistik.description.isEnabled = false
            binding.gvStatistik.xAxis.position = XAxis.XAxisPosition.BOTTOM
            binding.gvStatistik.data = LineData(incomeDataset, expenseDataset)

            runOnUiThread {
                binding.tvPengeluaran.text = "Pengeluaran: ${listExpense.sumOf { it.y.toInt() }}"
                binding.tvPemasukan.text = "Pemasukan: ${listIncome.sumOf { it.y.toInt() }}"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        drawGraph()
    }
}