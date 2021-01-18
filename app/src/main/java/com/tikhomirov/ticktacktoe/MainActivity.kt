package com.tikhomirov.ticktacktoe

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.tikhomirov.ticktacktoe.base.navigation.AppNavigator
import com.tikhomirov.ticktacktoe.base.navigation.Screens
import org.koin.android.ext.android.inject

class MainActivity : FragmentActivity() {
    private val navigator = AppNavigator(this, R.id.flMain)
    private val cicerone: Cicerone<Router> by inject()
    private val navigatorHolder = cicerone.getNavigatorHolder()
    private val router: Router by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(null)
        router.navigateTo(Screens.menu(true))
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}