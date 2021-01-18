package com.tikhomirov.ticktacktoe.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.core.component.inject


abstract class BaseFragment : Fragment() {
    val router: Router by inject()


    protected fun <T> LiveData<T>.subscribe(observer: (result: T) -> Unit) {
        this.observe(this@BaseFragment.viewLifecycleOwner, observer)
    }
}