package com.harrycampaz.searchproducts.presentation.ui.searchProduct

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.harrycampaz.searchproducts.R
import com.harrycampaz.searchproducts.common.SoftKeyboardHelper
import com.harrycampaz.searchproducts.databinding.FragmentSearchProductBinding
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject.ListProductViewObject
import com.harrycampaz.searchproducts.presentation.ui.searchProduct.viewobject.toListViewObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchProductBinding.inflate(inflater, container, false).also {
            setupView(it)
        }
        return binding.root
    }

    private fun setupView(searchProductBinding: FragmentSearchProductBinding) {

        searchProductBinding.btnSubmit.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.sendAction(SearchProductViewModel.SearchProductAction.SendRequest)
            }

            SoftKeyboardHelper.hideSoftKeyboard(searchProductBinding.inputText)
        }

        searchProductBinding.inputText.apply {
            doAfterTextChanged {
                it?.let { editable ->
                    if (editable.isNotEmpty()) {
                        viewModel.queryText.value = editable.toString()
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerViewModelStates()
    }

    private fun observerViewModelStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchProductState.collectLatest { state ->
                    when (state) {
                        SearchProductState.ErrorInput -> showErrorInput()
                        SearchProductState.HideLoading -> hideLoading()
                        SearchProductState.InternetError -> showInternetError()
                        is SearchProductState.NavigateToProductList -> {
                            val action = SearchProductFragmentDirections
                                .actionSearchProductFragmentToProductListFragment(
                                    ListProductViewObject(
                                        state.productList.toListViewObject()
                                    )
                                )
                           findNavController().navigate(action)
                        }
                        SearchProductState.ServerError -> showServerError()
                        SearchProductState.ShowLoading -> showLoading()
                        SearchProductState.UnknownError -> showUnknownError()
                        SearchProductState.EmptyList -> handleEmptyList()
                    }
                }
            }
        }
    }

    private fun handleEmptyList() {
        binding.progressBarLoading.isVisible = false
        binding.layoutSearchProduct.isVisible = true
        Toast.makeText(context, getString(R.string.query_not_found), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showErrorInput() {
        Toast.makeText(context, getString(R.string.msg_validation), Toast.LENGTH_SHORT)
            .show()
    }

    private fun hideLoading() {

        binding.progressBarLoading.isVisible = false
        binding.layoutSearchProduct.isVisible = true
    }

    private fun showLoading() {
        binding.layoutSearchProduct.isVisible = false
        binding.progressBarLoading.isVisible = true
    }

    private fun showInternetError() {
        Toast.makeText(context, "Internet Error", Toast.LENGTH_SHORT).show()
    }

    private fun showServerError() {
        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
    }

    private fun showUnknownError() {
        Toast.makeText(context, "Unknown Error", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}