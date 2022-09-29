package com.kerite.fission.android.extensions

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment

inline fun <reified T> ViewGroup.findChild(): T? = children.find { it is T } as T?

inline fun <reified T : Activity> Activity.startActivity(
    vararg params: Pair<String, Any>
) {
    val intent = Intent(this, T::class.java)
    startActivity(Internals.internalFillIntent(intent, *params))
}

inline fun <reified T : Activity> Fragment.startActivity(
    vararg params: Pair<String, Any>
) {
    val intent = Intent(requireContext(), T::class.java)
    startActivity(Internals.internalFillIntent(intent, *params))
}

inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int, vararg params: Pair<String, Any>
) {
    val intent = Intent(this, T::class.java)
    startActivityForResult(Internals.internalFillIntent(intent, *params), requestCode)
}

inline fun <reified T : Activity> Fragment.startActivityForResult(
    requestCode: Int, vararg params: Pair<String, Any>
) {
    val intent = Intent(requireContext(), T::class.java)
    startActivity(Internals.internalFillIntent(intent, *params))
}
