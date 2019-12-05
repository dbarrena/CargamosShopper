package com.cargamos.cargamosshopper.adapter.picking

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.extension.DynamicSearchAdapter
import com.cargamos.cargamosshopper.models.Picking
import kotlinx.android.synthetic.main.list_item_picking_pool.view.*

class PickingPoolRecyclerViewAdapter(
    val context: Context,
    private val items: ArrayList<Picking>
) : RecyclerView.Adapter<PickingPoolRecyclerViewAdapter.ViewHolder>(), Filterable {
    var listener: ItemClickListener? = null

    var originalList = items
    var filteredList = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_picking_pool, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picking = filteredList[position]
        holder.bind(picking)

        holder.actionBtn.setOnClickListener {
            listener?.onConfirmBtnClick(picking, position)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun addItem(item: Picking) {
        filteredList.add(item)
        originalList.add(item)
        notifyItemInserted(filteredList.size - 1)
        notifyDataSetChanged()
    }

    fun addAllItems(newItems: ArrayList<Picking>) {
        originalList.clear()

        originalList.addAll(newItems)
        filteredList = originalList

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
        val actionBtn: ImageView = view.actionBtn

        @SuppressLint("SetTextI18n")
        fun bind(picking: Picking) {
            pickingCounter.text = picking.count.toString()
            product.text = picking.description
            productID.text = "ID ${picking.id}"
            location.text = picking.sorting
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filteredList = originalList
                } else {
                    val filtered = arrayListOf<Picking>()
                    for (item in originalList) {
                        if (item.sorting!!.toLowerCase().contains(charString.toLowerCase()) ||
                            item.description.toLowerCase().contains(charString.toLowerCase()) ||
                            item.id.toLowerCase().contains(charString.toLowerCase())
                        ) {
                            filtered.add(item)
                        }
                    }
                    filteredList = filtered
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(p0: CharSequence, filterResults: FilterResults) {
                filteredList = filterResults.values as ArrayList<Picking>
                notifyDataSetChanged()
            }

        }
    }

    interface ItemClickListener {
        fun onConfirmBtnClick(picking: Picking, position: Int)
    }
}
