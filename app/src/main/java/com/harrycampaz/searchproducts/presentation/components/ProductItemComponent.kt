package com.harrycampaz.searchproducts.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.harrycampaz.searchproducts.databinding.ViewItemProductBinding

class ProductItemComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleRes) {

    private var _binding: ViewItemProductBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = ViewItemProductBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setProductItem(itemVO: ProductItemVO) {

        binding.labelTitle.text = itemVO.title
        binding.labelPrice.text = itemVO.price.toString()

        Glide.with(context)
            .load(itemVO.thumbnail)
            .into(binding.imageProduct)

    }

}