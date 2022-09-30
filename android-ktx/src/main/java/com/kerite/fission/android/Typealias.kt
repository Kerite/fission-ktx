package com.kerite.fission.android

import android.content.Intent
import android.view.LayoutInflater

typealias ViewBindingInflate<T> = (LayoutInflater) -> T

typealias IntentProducer = () -> Intent
