package com.kerite.fission.android

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

typealias ViewBindingInflate<T> = (LayoutInflater) -> T
typealias ViewBindingInflateWithParent<T> = (LayoutInflater, ViewGroup, Boolean) -> T

typealias IntentProducer = () -> Intent
