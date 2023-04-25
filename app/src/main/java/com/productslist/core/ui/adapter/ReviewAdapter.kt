package com.productslist.core.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.productslist.R
import com.productslist.core.domain.model.Product
import com.productslist.core.domain.model.Review


class ReviewAdapter(private var data: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviwViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviwViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_review, parent, false)
        return ReviwViewHolder(view)
    }
    private var sort:Boolean = true

    override fun onBindViewHolder(holder: ReviwViewHolder, position: Int) {
        val itemData = data[position]
        holder.bind(itemData)
    }
    fun setData(data: List<Review>) {
        this.data = data
        notifyDataSetChanged()
    }
    fun sortByRating() {
        if(sort){
            data = data.sortedByDescending { it.rating }
            notifyDataSetChanged()
        }else{
            data = data.sortedBy { it.rating }
            notifyDataSetChanged()
        }
        sort = !sort
    }

    class ReviwViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val name: TextView = itemView.findViewById(R.id.name)
        private val comment: TextView = itemView.findViewById(R.id.reviw)
        private val note: RatingBar = itemView.findViewById(R.id.note)

        fun bind(reviw: Review) {
            name.text = reviw.name+" : "
            if(reviw.name == null || reviw.name.isBlank() || reviw.name.isNullOrEmpty()){
                name.text = "Annonymose : "
            }
            comment.text = "  "+reviw.text
            if(reviw.text == null || reviw.text.isBlank() || reviw.text.isNullOrEmpty()){
                name.text = ""
            }

            note.setRating(reviw.rating?.toFloat() ?: 5f)
        }
        }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.name)
    }



}