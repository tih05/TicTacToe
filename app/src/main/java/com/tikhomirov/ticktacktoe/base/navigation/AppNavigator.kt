package com.tikhomirov.ticktacktoe.base.navigation

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator

class AppNavigator @JvmOverloads constructor(
    activity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager = activity.supportFragmentManager,
    fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory
) : AppNavigator(
    activity,
    containerId,
    fragmentManager,
    fragmentFactory
) {

    private val handler = Handler(Looper.getMainLooper())

    override fun applyCommands(commands: Array<out Command>) {
        try {
            super.applyCommands(commands)
        } catch (e:IllegalStateException) {
            handler.postDelayed({ applyCommands(commands) }, 100)
        }
    }

}