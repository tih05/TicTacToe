package com.tikhomirov.ticktacktoe.base

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {
    val router: Router by inject()
    val resourcesManager: ResourcesManager by inject()
}