package com.harrycampaz.searchproducts.presentation.ui.detailsProduct

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.harrycampaz.searchproducts.databinding.FragmentDetailsProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsProductFragment: Fragment() {

    private var _binding: FragmentDetailsProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsProductBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModelStates()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sendAction(DetailsProductViewModel.DetailsProductAction.Idle)
        }
    }

    private fun observerViewModelStates() {
        viewLifecycleOwner.lifecycleScope.launch {

                viewModel.detailsProductState.collectLatest { state ->
                    when (state) {
                        DetailsProductViewModel.DetailsProductState.InitView -> {
                            Log.e("TAG", " Detalle de observerViewModelStates: ",)
                        }
                    }

            }
        }
    }

}