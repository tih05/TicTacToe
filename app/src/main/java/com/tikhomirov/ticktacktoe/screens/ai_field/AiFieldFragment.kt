package com.tikhomirov.ticktacktoe.screens.ai_field

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tikhomirov.ticktacktoe.base.BaseFragment
import com.tikhomirov.ticktacktoe.databinding.FragmentAiFieldBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AiFieldFragment : BaseFragment() {
    private val viewModel: AiFieldViewModel by viewModel()
    private lateinit var binding: FragmentAiFieldBinding

    companion object {
        private const val BOARD_SIZE = "board_size"
        private const val RESTORE_GAME = "restore_game"
        fun newInstance(boardSize: Int, restoreGame: Boolean = false) = AiFieldFragment().apply {
            arguments = Bundle().apply {
                putInt(BOARD_SIZE, boardSize)
                putBoolean(RESTORE_GAME, restoreGame)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentAiFieldBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .let { it.root }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
        requireArguments()
            .getBoolean(RESTORE_GAME)
            .takeIf { shouldRestore -> shouldRestore }
            ?.let { viewModel.restoreGame() }
            ?: run {
                setupField()
            }
    }

    private fun setupField() {
        requireArguments()
            .getInt(BOARD_SIZE)
            .let {
                binding.field.setBoardSize(it)
                viewModel.setBoardSize(it)
            }

    }

    private fun setupListeners() {

        with(binding) {
            field.setOnMoveListener { viewModel.onMove(it) }
            ivRestart.setOnClickListener { viewModel.onRestartClicked() }
            ivHome.setOnClickListener { viewModel.onHomeClicked() }
            cmTimer.setOnChronometerTickListener {
                val elapsedMillis = SystemClock.elapsedRealtime().minus(it.base)
                viewModel.onTick(elapsedMillis)
            }
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            moveLiveData.subscribe {
                binding.field.addMoves(it)
            }


            movesCountLiveData.subscribe {
                binding.tvMovesCount.text = it
            }

            chronoLiveData.subscribe { shouldStart ->
                if (shouldStart) {
                    binding.cmTimer.base = SystemClock.elapsedRealtime()
                    binding.cmTimer.start()
                } else {
                    binding.cmTimer.stop()
                }
            }

            chronoRestoreLiveData.subscribe {
                binding.cmTimer.base = it
                binding.cmTimer.start()
            }

            movesSetLiveData.subscribe {
                binding.field.setMoves(it)
            }
            boardSizeLiveData.subscribe {
                binding.field.setBoardSize(it)
            }
        }
    }

}