package com.excite.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            disableAnswerButtons()
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            disableAnswerButtons()
        }

        nextButton.setOnClickListener {
            // циклически получаем индекс вопроса из списка вопросов, который необходимо отобразить
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            enableAnswerButtons()
        }

        backButton.setOnClickListener {
            // циклически получаем индекс вопроса из списка вопросов, который необходимо отобразить
            if (currentIndex == 0) {
                currentIndex = questionBank.size - 1
            } else {
                currentIndex -= 1
            }
            updateQuestion()
            enableAnswerButtons()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()

    }

    // делаем кнопки ответа на вопрос активными при смене вопроса
    private fun enableAnswerButtons() {
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

    // делаем кнопки ответа на вопрос неактивными после нажатия на True/False
    private fun disableAnswerButtons() {
        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    // функция, которая обоновляет текст, отображаемый в TextView
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    // функция, которяа проверяет ответ пользователя на вопрос и в зависимости от ответа выводит сообщение-Toast
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }
}