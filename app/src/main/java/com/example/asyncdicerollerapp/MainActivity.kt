package com.example.asyncdicerollerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.asyncdicerollerapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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

        binding.launchButton.setOnClickListener {
            val order = mutableListOf(
                    launchCoroutine(binding.dice1),
                    launchCoroutine(binding.dice2),
                    launchCoroutine(binding.dice3),
                    launchCoroutine(binding.dice4))

        }
    }

    private suspend fun getRandomDeferredNum(): Deferred<Int> {
        val value = CoroutineScope(Dispatchers.Default).async {
            delay((500..3000).random().toLong())
            getRandomNum()
        }
        return value
    }

    private fun getRandomNum(): Int {
        return (0..5).random()
    }

    private fun launchCoroutine(imageView: ImageView): Deferred<Unit> {
        return CoroutineScope(Dispatchers.Default).async {
            val index = getRandomDeferredNum()
            while (index.isActive) {
                updateUI(imageView)
            }
            imageView.setImageResource(diceList[index.await()])
        }
    }

    private suspend fun updateUI(imageView: ImageView) {
        withContext(Dispatchers.Main) {
            imageView.setImageResource(diceList[getRandomNum()])
        }
    }
}