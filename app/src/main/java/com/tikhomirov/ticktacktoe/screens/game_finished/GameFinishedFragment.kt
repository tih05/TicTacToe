package com.tikhomirov.ticktacktoe.screens.game_finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.MutableLiveData
import com.tikhomirov.ticktacktoe.base.BaseFragment
import com.tikhomirov.ticktacktoe.databinding.FragmentGameFinishedBinding
import com.tikhomirov.ticktacktoe.domain.models.GameStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameFinishedFragment : BaseFragment() {
    private lateinit var binding: FragmentGameFinishedBinding
    private val viewModel: GameFinishedViewModel by viewModel()

    companion object {
        private const val GAME_STATUS = "game_status"
        private const val BOARD_SIZE = "board_size"
        fun newInstance(gameStatus: GameStatus, boardSize: Int) = GameFinishedFragment().apply {
            arguments = Bundle().apply {
                putSerializable(GAME_STATUS, gameStatus)
                putInt(BOARD_SIZE, boardSize)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentGameFinishedBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .let { it.root }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gameStatus = requireArguments()
            .get(GAME_STATUS)
            .let { it as GameStatus }
        val boardSize = requireArguments()
            .getInt(BOARD_SIZE)

        viewModel.onViewCreated(gameStatus, boardSize)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        with(binding) {
            btnRetry.setOnClickListener {
                viewModel.onRetryClicked()
            }
            btnHome.setOnClickListener {
                viewModel.onHomeClicked()
            }
            btnStats.setOnClickListener {
                viewModel.onStatsClicked()
            }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            backgroundLiveData.subscribe { color ->
                binding.tvStatusText.background.colorFilter = BlendModeColorFilterCompat
                    .createBlendModeColorFilterCompat(
                        color,
                        BlendModeCompat.SRC_ATOP
                    )
            }
            statusTextLiveData.subscribe {
                binding.tvStatusText.text = it
            }
        }
    }


}