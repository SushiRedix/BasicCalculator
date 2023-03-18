package com.sushi.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var tvInput : TextView? = null

    private var isLastNumeric : Boolean = false
    private var isLastDot : Boolean = false
    private var hasAlreadyDecimal : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.mainTv)
    }

    fun onDigit(view : View) {
        tvInput?.append((view as Button).text)
        isLastNumeric = true
        isLastDot = false
    }

    fun onClear(view : View) {
        tvInput?.text = ""
    }

    fun onDecimal(view : View) {
        if(isLastNumeric && !isLastDot && !hasAlreadyDecimal) {
            tvInput?.append(".")
            isLastNumeric = false
            isLastDot = true
            hasAlreadyDecimal = true
        }
    }

    fun onOperator(view : View) {
        tvInput?.text?.let {
            if(isLastNumeric && !isOperator(it.toString())) {
                tvInput?.append((view as Button).text)
                isLastNumeric = false
                isLastDot = false
                hasAlreadyDecimal = false
            }
        }
    }

    fun onEqual(view : View) {
        if(isLastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = (one.toDouble() - two.toDouble()).toString()
                } else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    val result = one.toDouble() + two.toDouble()
                    tvInput?.text = result.toString()
                } else if(tvValue.contains("x")) {
                    val splitValue = tvValue.split("x")

                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    val result = one.toDouble() * two.toDouble()
                    tvInput?.text = result.toString()
                }
            }
            catch (e : java.lang.ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperator(value : String) : Boolean {
        return if(value.startsWith("-")) {
            false
        }
        else {
            value.contains("+") || value.contains("-") || value.contains("*")
        }
    }
}