package com.harrycampaz.searchproducts.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.harrycampaz.searchproducts.R
import com.harrycampaz.searchproducts.common.loadImgList
import com.harrycampaz.searchproducts.common.toPriceFormat
import com.harrycampaz.searchproducts.databinding.ViewItemProductBinding
import java.text.NumberFormat
import java.util.*

class ProductItemComponent @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleRes) {

    private var _binding: ViewItemProductBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = ViewItemProductBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setProductItem(itemVO: ProductItemVO) {

        val format = NumberFormat.getCurrencyInstance(Locale("es", "AR"))
        format.maximumFractionDigits = 0

        binding.labelTitle.text = itemVO.title
        binding.labelPrice.text = itemVO.price.toPriceFormat()
        binding.imageProduct.loadImgList(itemVO.thumbnail)
    }

}

