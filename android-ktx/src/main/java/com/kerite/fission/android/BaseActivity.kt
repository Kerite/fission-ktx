package com.kerite.fission.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.kerite.fission.AntiShaker

abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: ViewBindingInflate<VB>,
) : AppCompatActivity() {
    private var mBinding: VB? = null

    @Suppress("unused")
    private val antiShaker = AntiShaker()

    @get:SuppressWarnings("unused")
    val binding: VB get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mBinding == null) {
            mBinding = inflate(layoutInflater)
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}