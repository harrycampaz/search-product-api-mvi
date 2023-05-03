package com.harrycampaz.searchproducts.presentation.ui.detailsProduct

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.harrycampaz.searchproducts.R
import com.harrycampaz.searchproducts.common.loadImgList
import com.harrycampaz.searchproducts.common.toPriceFormat
import com.harrycampaz.searchproducts.databinding.FragmentDetailsProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsProductFragment: Fragment() {

    private var _binding: FragmentDetailsProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsProductViewModel by activityViewModels()

    private val args: DetailsProductFragmentArgs by lazy {
        DetailsProductFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsProductBinding.inflate(inflater, container, false).also {
            setupView(it)
        }
        return  binding.root
    }

    private fun setupView(detailsProductBinding: FragmentDetailsProductBinding) {
        detailsProductBinding.containerNavBarContainer.containerNavIconContainer.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.sendAction(DetailsProductViewModel.DetailsProductAction.NavigateBack)
            }
        }

        detailsProductBinding.imageProduct.loadImgList(args.productItem.thumbnail)
        detailsProductBinding.labelProductName.text = args.productItem.title
        detailsProductBinding.labelProductPrice.text = args.productItem.price.toPriceFormat()
        detailsProductBinding.labelProductAvailableQuantity.text = getString(R.string.label_available_quantity, args.productItem.availableQuantity.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModelStates()
    }

    private fun observerViewModelStates() {
        viewLifecycleOwner.lifecycleScope.launch {

                viewModel.detailsProductState.collectLatest { state ->
                    when (state) {
                        DetailsProductViewModel.DetailsProductState.GoBack -> findNavController().navigateUp()
                    }

            }
        }
    }

}