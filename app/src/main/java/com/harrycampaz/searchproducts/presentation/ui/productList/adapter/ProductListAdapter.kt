package com.harrycampaz.searchproducts.presentation.ui.productList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harrycampaz.searchproducts.databinding.ItemProductBinding
import com.harrycampaz.searchproducts.presentation.ui.productList.ProductListViewModel
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject.ProductViewObject
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject.toItemView

class ProductListAdapter(
    private val clickItem: (ProductViewObject) -> Unit
) : ListAdapter<ProductViewObject, ProductListAdapter.ProductListViewHolder>(DiffUtilCallback) {

    class ProductListViewHolder(
        private val binding: ItemProductBinding,
        private val clickItem: (ProductViewObject) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            productViewObject: ProductViewObject
        ) {
            binding.viewProductItem.setProductItem(productViewObject.toItemView())
            itemView.setOnClickListener {
                clickItem(productViewObject)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductListViewHolder(
            binding,
            clickItem
        )
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

private object DiffUtilCallback : DiffUtil.ItemCallback<ProductViewObject>() {
    override fun areItemsTheSame(oldItem: ProductViewObject, newItem: ProductViewObject): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductViewObject, newItem: ProductViewObject): Boolean {
        return oldItem == newItem
    }
}
