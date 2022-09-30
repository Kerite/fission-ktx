package com.kerite.fission.android

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.kerite.fission.android.extensions.Internals

class StartActivityResultIntentWrapper(
    private val intentProducer: IntentProducer,
    private val launcher: ActivityResultLauncher<Intent>
) {
    @Suppress("unused")
    fun launch(vararg params: Pair<String, Any?>) {
        val intent = intentProducer()
        launcher.launch(Internals.internalFillIntent(intent, *params))
    }

    @Suppress("unused")
    fun unregister() {
        launcher.unregister()
    }
}