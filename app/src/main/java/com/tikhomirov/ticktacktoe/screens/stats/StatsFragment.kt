@file:Suppress("SimpleRedundantLet")

package com.tikhomirov.ticktacktoe.screens.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.AnyChart
import com.tikhomirov.ticktacktoe.base.BaseFragment
import com.tikhomirov.ticktacktoe.base.navigation.Screens
import com.tikhomirov.ticktacktoe.databinding.FragmentMenuBinding
import com.tikhomirov.ticktacktoe.databinding.FragmentStatsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class StatsFragment : BaseFragment() {
    private lateinit var binding: FragmentStatsBinding
    private val viewModel: StatsViewModel by viewModel()

    companion object {
        fun newInstance() = StatsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentStatsBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .let { it.root }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadStats()
        setupListeners()
        setupObservers()
    }


    private fun setupListeners() {
        binding.btnHome.setOnClickListener {
            router.newRootScreen(Screens.menu())
        }
    }

    private fun setupObservers() {
        viewModel.statsLiveData.subscribe {
            val pie = AnyChart.pie()
            pie.data(it)
            binding.acvChart.setChart(pie)
        }
    }

}