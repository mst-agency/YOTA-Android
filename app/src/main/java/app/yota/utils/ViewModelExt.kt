package app.yota.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> Fragment.getViewModel(viewModelFactory: ViewModelProvider.Factory): T {
    return getViewModel(T::class.java, viewModelFactory)
}

fun <T : ViewModel> Fragment.getViewModel(
    java: Class<T>,
    viewModelFactory: ViewModelProvider.Factory
): T = ViewModelProvider(this, viewModelFactory).get(java)

