package com.harrycampaz.searchproducts.presentation.ui.productList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.harrycampaz.searchproducts.databinding.FragmentProductListBinding
import com.harrycampaz.searchproducts.presentation.ui.productList.adapter.ProductListAdapter
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.SearchProductFragmentDirections
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject.ProductViewObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductListViewModel by activityViewModels()

    val args: ProductListFragmentArgs by lazy {
        ProductListFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Log.e("TAG", "onViewCreated: ${args.listProduct}.", )
        observerViewModelStates()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sendAction(ProductListViewModel.ProductListAction.Idle)
        }
    }

    private fun observerViewModelStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productListState.collectLatest { state ->
                when (state) {
                    is ProductListViewModel.ProductListState.InitView -> {
                        initView(args.listProduct.products)
                    }
                    ProductListViewModel.ProductListState.GoToDetail -> {}
                }
            }
        }
    }

    private fun initView(productViewObjects: List<ProductViewObject>) {
        val adapter = ProductListAdapter(clickItem = ::clickItem).apply {
            submitList(productViewObjects)
        }

        binding.reciclerViewProductList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        binding.reciclerViewProductList.layoutManager = layoutManager
        binding.reciclerViewProductList.adapter = adapter

    }

    private fun clickItem(productViewObject: ProductViewObject) {
        val action =
            ProductListFragmentDirections.actionProductListFragmentToDetailsProductFragment(
                productViewObject
            )
        findNavController().navigate(action)
    }
}