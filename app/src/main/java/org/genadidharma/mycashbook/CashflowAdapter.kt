package org.genadidharma.mycashbook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.genadidharma.mycashbook.db.Cash
import java.text.NumberFormat
import java.util.*

class CashflowAdapter(private val listCash: List<Cash>) :
    RecyclerView.Adapter<CashflowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvMoney.text = formatRupiah(listCash[position].money.toDouble())
        holder.tvDescription.text = listCash[position].description
        holder.tvDate.text = listCash[position].date
        if (listCash[position].isIncome) {
            holder.tvStatus.text = "[+]"
            holder.ivStatus.setImageResource(R.drawable.arrow_left)
        } else {
            holder.tvStatus.text = "[-]"
            holder.ivStatus.setImageResource(R.drawable.arrow_right)
        }

    }

    override fun getItemCount(): Int = listCash.size

    private fun formatRupiah(number: Double): String {
        val localeID = Locale("in", "ID");
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvStatus: TextView
        val tvMoney: TextView
        val tvDescription: TextView
        val tvDate: TextView
        val ivStatus: ImageView

        init {
            tvStatus = view.findViewById(R.id.tv_status)
            tvMoney = view.findViewById(R.id.tv_money)
            tvDescription = view.findViewById(R.id.tv_description)
            tvDate = view.findViewById(R.id.tv_date)
            ivStatus = view.findViewById(R.id.iv_status)
        }
    }

}