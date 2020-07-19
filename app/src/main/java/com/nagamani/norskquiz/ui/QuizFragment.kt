package com.nagamani.norskquiz.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.nagamani.norskquiz.R
import com.nagamani.norskquiz.databinding.FragmentQuizBinding
import java.util.ResourceBundle.getBundle

//Fragment to display questions
class QuizFragment : Fragment() {
    private var questions = mutableListOf<Question>()


    data class Question(
        val text: String,
        val answers: List<String>
    )


    private fun prepareQuestions() {

        context?.assets?.open("questions.txt")?.bufferedReader()?.forEachLine {
            var line = it.split(".,")
            questions.add(Question(text = line[0], answers = line[1].split(",")))
        }
        for(i in 0..questions.size-1) Log.d("nagamani","${questions.get(i)}")
        Log.d("nagamani","question size: ${questions.size}")
    }

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private var correctAnswered = 0
    private val numQuestions by lazy { Math.min((questions.size + 1) / 5, 10) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentQuizBinding>(
            inflater, R.layout.fragment_quiz, container, false
        )
        Log.d("tej","oncreateView")
        prepareQuestions()
        randomizeQuestions()

        // Bind this fragment class to the layout
        binding.quiz = this


        // onClickListener for the submitButton
        binding.nextButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }

                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    correctAnswered++
                }
                // Advance to the next question
                if (questionIndex < numQuestions) {
                    //change text of quit button to submit
                    if(questionIndex==numQuestions-1) {
                        binding.submitButton.text = getString(R.string.submit_button)
                        binding.nextButton.visibility = View.GONE
                    }

                    currentQuestion = questions[questionIndex]
                    setQuestion()
                    binding.invalidateAll()
                } else checkResults(view)
            }

        }

        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            checkResults(view)
        }
        return binding.root
    }

    fun checkResults(view: View) {
        if (correctAnswered >= 0.7 * numQuestions)
        // Won scenario
            view.findNavController()
                .navigate(R.id.action_quizFragment_to_quizWonFragment, getBundle())
        else view.findNavController()
            .navigate(R.id.action_quizFragment_to_quizOverFragment, getBundle())
    }

    fun getBundle(): Bundle {
        return bundleOf(
            "questionsCount" to numQuestions,
            "correctlyAnsweredCount" to correctAnswered
        )
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()

    }

    //randomize the answers
    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        questionIndex++
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.question_title).plus(getString(R.string.title_number, questionIndex, numQuestions))
    }
}
