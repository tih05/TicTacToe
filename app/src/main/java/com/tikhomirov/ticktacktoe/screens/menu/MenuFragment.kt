@file:Suppress("SimpleRedundantLet")

package com.tikhomirov.ticktacktoe.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tikhomirov.ticktacktoe.base.BaseFragment
import com.tikhomirov.ticktacktoe.databinding.FragmentMenuBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MenuFragment : BaseFragment() {
    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuViewModel by viewModel()

    companion object {
        private const val CHECK_FOR_SAVED_GAME = "check_for_saved_game"
        fun newInstance(checkForSavedGame: Boolean) = MenuFragment().apply {
            arguments = Bundle().apply {
                putBoolean(CHECK_FOR_SAVED_GAME, checkForSavedGame)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentMenuBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .let { it.root }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireArguments()
            .getBoolean(CHECK_FOR_SAVED_GAME)
            .let {shouldCheck->
                if(shouldCheck) {
                    viewModel.checkForSavedGame()
                }
            }
        setupListeners()
        setupObservers()
    }


    private fun setupListeners() {
        with(binding) {
            btnAi.setOnClickListener {
                viewModel.onAiClicked()
            }
            btnMinus.setOnClickListener {
                viewModel.onMinusClicked()
            }
            btnPlus.setOnClickListener {
                viewModel.onPlusClicked()
            }
            btnStats.setOnClickListener {
                viewModel.onStatsClicked()
            }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            fieldSizeLiveData.subscribe {
                binding.field.setBoardSize(it)
            }
            fieldSizeTextLiveData.subscribe {
                binding.tvFieldSize.text = it
            }
            movesLiveData.subscribe {
                binding.field.setMoves(it)
            }
        }
    }

}