package com.cargamos.cargamosshopper.adapter.picking

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.models.Picking
import com.travijuu.numberpicker.library.NumberPicker
import kotlinx.android.synthetic.main.list_item_picking.view.*

class PickingRecyclerViewAdapter(
    val context: Context,
    private val items: ArrayList<Picking>
) : RecyclerView.Adapter<PickingRecyclerViewAdapter.ViewHolder>() {
    var listener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_picking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picking = items[position]
        holder.bind(picking)

        holder.confirmBtn.setOnClickListener {
            val decrement = holder.pickingNumberPicker.value
            listener?.onConfirmBtnClick(picking, position, decrement)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Picking) {
        items.add(item)
        notifyItemInserted(items.size - 1)
        notifyDataSetChanged()
    }

    fun addAllItems(newItems: ArrayList<Picking>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    fun clearAll() {
        items.clear()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pickingCounter: TextView = view.pickingCounter
        val product: TextView = view.product
        val productID: TextView = view.productID
        val location: TextView = view.location
        val pickingNumberPicker: NumberPicker = view.actionBtn
        val confirmBtn: Button = view.confirmBtn

        @SuppressLint("SetTextI18n")
        fun bind(picking: Picking) {
            pickingCounter.text = picking.count.toString()
            product.text = picking.description
            productID.text = "ID ${picking.id}"
            location.text = picking.sorting

            pickingNumberPicker.value = picking.count.toInt()
            pickingNumberPicker.max = picking.count.toInt()
            pickingNumberPicker.min = 0
        }
    }

    interface ItemClickListener {
        fun onConfirmBtnClick(picking: Picking, position: Int, decrement: Int)
    }

}