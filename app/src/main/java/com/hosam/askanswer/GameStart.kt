package com.hosam.askanswer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hosam.askanswer.databinding.FragmentGameStartBinding
import kotlinx.android.synthetic.main.fragment_game_start.*

// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [GameStart.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameStart : Fragment() {
    data class Question(
        var text: String,
        var answer: List<String>

    )

    lateinit var CurrentQuestion: Question
    lateinit var Answer: MutableList<String>
    val numberquestion = 3
    var questionIndex=0
    private val questions: MutableList<Question> = mutableListOf(
        Question("who is the donkey?", listOf("hazem"," hosam", "laila", "dina")),
        Question("5*5", listOf("25", "30", "20", "15")),
        Question("150+75", listOf("225", "275", "220", "215")),
        Question("150/3", listOf("50", "30", "15", "60")),
        Question("226-26", listOf("200", "252", "180", "100"))
    )


    var binding: FragmentGameStartBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_start, container, false)
        binding!!.game=this
        randomize()
        binding!!.button.setOnClickListener {

            var checkId=binding!!.radiogroupbutton.checkedRadioButtonId
            if (-1!=checkId)
            {
                var answerindex=0
                when(checkId)
                {
                    R.id.radioButton2->answerindex=1
                    R.id.radioButton3->answerindex=2
                    R.id.radioButton4->answerindex=3
                }
                if (Answer[answerindex]==CurrentQuestion.answer[0])
                {
                    questionIndex++
                    if (questionIndex<numberquestion)
                    {
                        CurrentQuestion=questions[questionIndex]
                        setQuestion()
                        binding!!.invalidateAll()
                    }
                    else
                    {
                        findNavController().navigate(R.id.action_gameStart_to_fragmentWon)

                    }

                }
            else
                {
                    findNavController().navigate(R.id.action_gameStart_to_fragmentLose)
                }
            }
        }

        return binding!!.root
    }
    fun randomize()
    {
        questions.shuffle()
        questionIndex=0
        setQuestion()
    }

    private fun setQuestion() {
      CurrentQuestion= questions[questionIndex]
        Answer=CurrentQuestion.answer.toMutableList()
        Answer.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title,questionIndex+1,numberquestion)

    }


}

