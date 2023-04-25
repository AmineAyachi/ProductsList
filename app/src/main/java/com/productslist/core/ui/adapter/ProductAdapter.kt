package com.productslist.core.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.productslist.R
import com.productslist.core.domain.model.Product

class ProductAdapter(
    private val products: List<Product>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable {
    private var filteredProducts = products

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = filteredProducts[position]
        holder.bind(product)

        holder.image.setOnClickListener {
                holder.toggleExpansion()
        }
        holder.nameTextView.setOnClickListener {
            holder.toggleExpansion()
        }
        holder.priceTextView.setOnClickListener {
            holder.toggleExpansion()
        }
        holder.descriptionTextView.setOnClickListener {
            holder.toggleExpansion()
        }

        holder.filterImage.setOnClickListener {
            holder.sortByRating()
        }

//        holder.itemView.setOnClickListener {
//            if (it.id != R.id.filter_image) {
//                holder.toggleExpansion()
//            } else {
//                holder.sortByRating()
//            }
//        }
//
//        holder.itemView.setOnLongClickListener {
//            if (it.id != R.id.filter_image) {
//                holder.toggleExpansion()
//            } else {
//                holder.sortByRating()
//            }
//            true
//        }




    }

    fun updateProducts(newProductsList: List<Product>) {
        filteredProducts = newProductsList
        notifyDataSetChanged()

    }

    override fun getItemCount() = filteredProducts.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         val cardView: CardView = itemView.findViewById(R.id.card_view)
         val nameTextView: TextView = itemView.findViewById(R.id.product_name)
         val priceTextView: TextView = itemView.findViewById(R.id.product_price)
         val descriptionTextView: TextView = itemView.findViewById(R.id.product_description)
         val image: ImageView = itemView.findViewById(R.id.product_image)
         val filterImage: ImageView = itemView.findViewById(R.id.filter_image)
         val expandedLayout: LinearLayout = itemView.findViewById(R.id.layout_expanded)
        val reviewRecyclerView : RecyclerView = itemView.findViewById(R.id.reviews_recycler_view)
        private val reviewAdapter: ReviewAdapter = ReviewAdapter(emptyList())

        init {
            reviewRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            reviewRecyclerView.adapter = reviewAdapter
        }
        fun bind(product: Product) {
            nameTextView.text = product.product_name
            priceTextView.text = "$${product.price}"
            descriptionTextView.text = product.description

             val options:RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
            Glide.with(itemView.context).load(product.images_url.small).apply(options).into(image)
            val reviews = product.reviews
            if (reviews != null) {
                reviewAdapter.setData(reviews)
            }
        }

        fun toggleExpansion() {
            if (expandedLayout.visibility == View.VISIBLE) {
                expandedLayout.visibility = View.GONE
                cardView.cardElevation = 8f
            } else {
                expandedLayout.visibility = View.VISIBLE
                cardView.cardElevation = 0f
            }
        }

        fun sortByRating() {
            reviewAdapter.sortByRating()
        }
    }

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrEmpty()) {
                    products
                } else {
                    products.filter { it.product_name.contains(constraint, true) }
                }

                return FilterResults().apply {
                    values = filteredList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredProducts = results?.values as? List<Product> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }

}