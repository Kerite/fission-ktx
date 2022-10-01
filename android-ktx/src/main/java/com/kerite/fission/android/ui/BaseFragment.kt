package com.kerite.fission.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kerite.fission.android.ViewBindingInflate

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: ViewBindingInflate<VB>
) : Fragment() {
    private var mBinding: VB? = null

    @get:SuppressWarnings("unused")
    val binding: VB get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}