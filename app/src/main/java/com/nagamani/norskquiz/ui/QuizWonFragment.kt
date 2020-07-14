package com.nagamani.norskquiz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.nagamani.norskquiz.R
import com.nagamani.norskquiz.databinding.FragmentQuizWonBinding


class QuizWonFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {

        val binding = DataBindingUtil.inflate<FragmentQuizWonBinding>(
            inflater, R.layout.fragment_quiz_won, container, false)

        binding.nextMatchButton.setOnClickListener{view: View->
            view.findNavController().navigate(R.id.action_quizWonFragment_to_welcomeFragment)}
        return binding.root
    }


}
