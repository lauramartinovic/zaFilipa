package org.unizd.rma.curko.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.unizd.rma.curko.databinding.ItemApodBinding
import org.unizd.rma.curko.model.ApodItem

class ApodAdapter(
    private val onClick: (ApodItem) -> Unit
) : RecyclerView.Adapter<ApodAdapter.VH>() {

    private val items = mutableListOf<ApodItem>()

    fun submit(list: List<ApodItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class VH(val b: ItemApodBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemApodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.b.txtTitle.text = it.title ?: ""
        holder.b.txtDate.text = it.date ?: ""
        val thumb = it.thumbnail_url ?: it.url
        if (!thumb.isNullOrBlank()) holder.b.imgThumb.load(Uri.parse(thumb))
        holder.b.root.setOnClickListener { onClick(items[position]) }
    }

    override fun getItemCount(): Int = items.size
}
