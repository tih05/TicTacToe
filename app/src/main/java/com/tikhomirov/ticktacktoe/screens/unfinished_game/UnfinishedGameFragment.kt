package com.tikhomirov.ticktacktoe.screens.unfinished_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.MutableLiveData
import com.tikhomirov.ticktacktoe.base.BaseFragment
import com.tikhomirov.ticktacktoe.base.navigation.Screens
import com.tikhomirov.ticktacktoe.databinding.FragmentGameFinishedBinding
import com.tikhomirov.ticktacktoe.databinding.FragmentUnfinishedGameBinding
import com.tikhomirov.ticktacktoe.domain.models.GameStatus
import org.koin.androidx.viewmodel.ext.android.viewModel

class UnfinishedGameFragment : BaseFragment() {
    private lateinit var binding: FragmentUnfinishedGameBinding
    private val viewModel: UnfinishedGameViewModel by viewModel()

    companion object {
        fun newInstance() = UnfinishedGameFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentUnfinishedGameBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .let { it.root }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            btnYes.setOnClickListener {
               viewModel.onYesClicked()
            }
            btnNo.setOnClickListener {
                viewModel.onNoClicked()
            }
        }
    }

}