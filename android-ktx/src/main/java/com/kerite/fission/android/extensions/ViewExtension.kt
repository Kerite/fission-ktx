package com.kerite.fission.android.extensions

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.kerite.fission.android.StartActivityResultIntentWrapper

inline fun <reified T> ViewGroup.findChild(): T? = children.find { it is T } as T?

@Suppress("unused")
inline fun <reified T : Activity> Activity.startActivity(
    vararg params: Pair<String, Any>
) {
    val intent = Intent(this, T::class.java)
    startActivity(Internals.internalFillIntent(intent, *params))
}

@Suppress("unused")
inline fun <reified T : Activity> Fragment.startActivity(
    vararg params: Pair<String, Any?>
) {
    val intent = Intent(requireContext(), T::class.java)
    startActivity(Internals.internalFillIntent(intent, *params))
}

@Suppress("unused")
inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int, vararg params: Pair<String, Any?>
) {
    val intent = Intent(this, T::class.java)
    startActivityForResult(Internals.internalFillIntent(intent, *params), requestCode)
}

/**
 * @author Kerite
 */
@Suppress("unused")
inline fun <reified T : Activity> Fragment.registerForActivityResult(
    crossinline resultCallback: (ActivityResult) -> Unit,
    noinline intentProducer: () -> Intent = { Intent(requireContext(), T::class.java) },
): StartActivityResultIntentWrapper {
    return StartActivityResultIntentWrapper(
        intentProducer, registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            resultCallback(it)
        }
    )
}
