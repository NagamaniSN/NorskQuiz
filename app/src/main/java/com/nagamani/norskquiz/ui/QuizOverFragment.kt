package com.nagamani.norskquiz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.nagamani.norskquiz.R
import com.nagamani.norskquiz.databinding.FragmentQuizOverBinding


class QuizOverFragment : Fragment() {
    private val args: QuizWonFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentQuizOverBinding>(
            inflater, R.layout.fragment_quiz_over, container, false
        )

        binding.retryButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_quizOverFragment_to_welcomeFragment)
        }
        binding.scoreTextView.text = getString(R.string.score).plus(
            getString(R.string.title_number, args.correctlyAnsweredCount, args.questionsCount)
        )

        return binding.root
    }


}
