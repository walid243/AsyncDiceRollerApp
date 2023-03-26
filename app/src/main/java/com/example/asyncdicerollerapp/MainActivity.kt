package com.example.asyncdicerollerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.asyncdicerollerapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var players: List<Player>
    private val diceList = listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        players = listOf(
            Player(binding.player1.text.toString(), binding.dice1, binding.dice2),
            Player(binding.player2.text.toString(), binding.dice3, binding.dice4),
            Player(binding.player3.text.toString(), binding.dice5, binding.dice6),
            Player(binding.player4.text.toString(), binding.dice7, binding.dice8)
        )
        binding.launchButton.setOnClickListener {
            val job =CoroutineScope(Dispatchers.Default).launch {
                players[0].valueDice1 = launchCoroutineAsync(players[0].dice1)
                players[0].valueDice2 = launchCoroutineAsync(players[0].dice2)

                players[1].valueDice1 = launchCoroutineAsync(players[1].dice1)
                players[1].valueDice2 = launchCoroutineAsync(players[1].dice2)

                players[2].valueDice1 = launchCoroutineAsync(players[2].dice1)
                players[2].valueDice2 = launchCoroutineAsync(players[2].dice2)

                players[3].valueDice1 = launchCoroutineAsync(players[3].dice1)
                players[3].valueDice2 = launchCoroutineAsync(players[3].dice2)
            }
            CoroutineScope(Dispatchers.Default).launch {
                job.join()
                val result = listOf(
                    players[0].result(),
                    players[1].result(),
                    players[2].result(),
                    players[3].result()
                )
                val winner = result.maxOrNull()
                val winnerIndex = result.indexOf(winner)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "The winner is ${players[winnerIndex].name}", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    private suspend fun getRandomNumAsync(): Deferred<Int> {
        val value = CoroutineScope(Dispatchers.Default).async {
            delay((500..3000).random().toLong())
            getRandomNum()
        }
        return value
    }

    private fun getRandomNum(): Int {
        return (0..5).random()
    }

    private fun launchCoroutineAsync(imageView: ImageView): Deferred<Int> {

        return CoroutineScope(Dispatchers.Default).async {
            val index = getRandomNumAsync()
            while (index.isActive) {
                updateUI(imageView)
            }
            imageView.setImageResource(diceList[index.await()])
            index.await()
        }
    }

    private suspend fun updateUI(imageView: ImageView) {
        withContext(Dispatchers.Main) {
            imageView.setImageResource(diceList[getRandomNum()])
        }
    }
}