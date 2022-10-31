package com.smartestidea.houseofthedargonquiz.data

import android.util.Log
import com.smartestidea.houseofthedargonquiz.R
import com.smartestidea.houseofthedargonquiz.data.model.Quiz
import javax.inject.Inject

class QuizProvider @Inject constructor() {
    private val corlys = "Lord Corlys Velaryon"
    private val daemon = "Prince Daemon Targaryen"
    private val criston = "Ser Criston Cole"
    private val rhaenyra = "Princess Rhaenyra Targaryen"
    private val leanor = "Leanor Velaryon"
    private val visreys = "Visreys Targaryen"
    private val quizs = mutableListOf(
        Quiz(
            1,
            R.string.q1,
            mutableListOf(
                "Ser Otto Hightower",
                rhaenyra,
                "Mysaria",
                criston
            ),
            2
        ),
        Quiz(
            2,
            R.string.q2,
            mutableListOf(
                "King Viserys I Targaryen",
                "Ser Otto Hightower",
                daemon,
                corlys
            ),
            3
        ),
        Quiz(
            3,
            R.string.q3,
            mutableListOf(
                "Lady Alicent Hightower",
                "Princess Rhaenys Targaryen",
                rhaenyra,
                "Queen Aemma Arryn"
            ),
            1
        ),
        Quiz(
            4,
            R.string.q4,
            mutableListOf(
                corlys,
                "Jasper Wylde",
                "Prince Aegon Targaryen",
                "Prince Jacaerys Velaryon"
            ),
            1
        ),
        Quiz(
            5,
            R.string.q5,
            mutableListOf(
                "Lady Alicent",
                "Mysaria",
                "Princess Rhaenys",
                "Princess Rhaenyra"
            ),
            0
        ),
        Quiz(
            6,
            R.string.q6,
            mutableListOf(
                "Aegon Targaryen",
                visreys,
                "Jaehaerys Targaryen",
                "Rhaegar Targaryen"
            ),
            0
        ),
        Quiz(
            7,
            R.string.q7,
            mutableListOf("Maegor the cruel", "Hodor", "Three eyed Ravon", "The Mad king"),
            0
        ),
        Quiz(
            8,
            R.string.q8,
            mutableListOf("Balerion", "Viserion", "Drogon", "Rhaegal"),
            0
        ),
        Quiz(
            9,
            R.string.q9,
            mutableListOf(
                "Rhaegar Targaryen",
                "Joffery Baratheon",
                "Daemon Targaryen",
                "Aerys II Targaryen"
            ),
            2
        ),
        Quiz(
            10,
            R.string.q10,
            mutableListOf("Vhagar", "Syrax", "Caraxes", "None of the above"),
            1
        ),
        Quiz(
            11,
            R.string.q11,
            mutableListOf("Mysaria", "Laena Velaryon", "Alicent Hightower", "Helaena"),
            2
        ),
        Quiz(
            12,
            R.string.q12,
            mutableListOf(daemon, "Lord Lyonel Strong", "Alicent Hightower", "Ser Otto Hightower"),
            3
        ),
        Quiz(
            13,
            R.string.q13,
            mutableListOf(criston, "Ser Harwin Strong", leanor, daemon),
            1
        ),
        Quiz(
            14,
            R.string.q14,
            mutableListOf(
                "Princess Rhaenys Targaryen",
                rhaenyra,
                "Aegon Targaryen",
                visreys),
            3
        ),
        Quiz(
            15,
            R.string.q15,
            mutableListOf(
                "Martell",
                "Hightower",
                "Velaryon",
                "Greyjoy"),
            2
        ),
        Quiz(
            16,
            R.string.q16,
            mutableListOf(
                "Ser Harold Westerling",
                criston,
                "Ser Harwin Strong",
                leanor),
            1
        ),
        Quiz(
            17,
            R.string.q17,
            mutableListOf(
                "Leanor",
                "Corlys II",
                "Laena",
                "Vaemond"),
            0
        ),
        Quiz(
            18,
            R.string.q18,
            mutableListOf(
                "Jason and Tyland",
                "Lancel and Leanor",
                "Willem and Martyn",
                "Tyrek and Tytos"),
            0
        ),
        Quiz(
            19,
            R.string.q19,
            mutableListOf(
                corlys,
                leanor,
                rhaenyra,
                visreys),
            1
        ),
        
    )


    fun getRandomsQuiz(): MutableList<Quiz> {
        val mutableList = mutableListOf<Quiz>()
        while(mutableList.size!=10) {
            val random = quizs.randomOrNull()
            if (random != null && random !in mutableList){
                mutableList.add( random)
            }
            Log.e("Question", mutableList.last().id.toString())
        }
        return mutableList
    }
}








