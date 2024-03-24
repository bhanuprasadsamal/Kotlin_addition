package com.example.tic
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var flag = 0
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun check(view: View) {
        val btnCurrent = view as Button
        if (btnCurrent.text == "") {
            count++
            if (flag == 0) {
                btnCurrent.text = "x"
                flag = 1;
            } else {
                btnCurrent.text = "O"
                flag = 0;
            }
            checkWinner()
        }
    }

    private fun checkWinner() {
        // Get references to all buttons
        val btns = mutableListOf<Button>()
        for (i in 1..9) {
            val id = resources.getIdentifier("btn$i", "id", packageName)
            findViewById<Button>(id)?.let { btns.add(it) }
        }

        val combinations = arrayOf(
            intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9),
            intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), intArrayOf(3, 6, 9),
            intArrayOf(1, 5, 9), intArrayOf(3, 5, 7)
        )

        for (combo in combinations) {
            val b1 = btns[combo[0] - 1].text.toString()
            val b2 = btns[combo[1] - 1].text.toString()
            val b3 = btns[combo[2] - 1].text.toString()

            if (b1 == b2 && b2 == b3 && b3 != "") {
                Toast.makeText(this@MainActivity, "Winner is: $b1", Toast.LENGTH_LONG).show()
                newGame(btns)
                return
            }
        }

        if (count == 9) {
            Toast.makeText(this@MainActivity, "Match is Drawn", Toast.LENGTH_LONG).show()
            newGame(btns)
        }
    }

    private fun newGame(btns: List<Button>) {
        for (btn in btns) {
            btn.text = ""
        }
        flag = 0
        count = 0
    }
}
