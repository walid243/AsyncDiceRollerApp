package com.example.asyncdicerollerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.asyncdicerollerapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val diceList = listOf(R.drawable.dice_1,R.drawable.dice_2,R.drawable.dice_3,R.drawable.dice_4,R.drawable.dice_5,R.drawable.dice_6)
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.dice1.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                val index = getRandomNum()
                while (index)
                binding.dice1.setImageResource(diceList[])
            }
        }
        binding.dice2.setOnClickListener {
            binding.dice2.setImageResource(diceList[getRandomNum()])

        }
        binding.dice3.setOnClickListener {
            binding.dice3.setImageResource(diceList[getRandomNum()])
        }
        binding.dice4.setOnClickListener {
            binding.dice4.setImageResource(diceList[getRandomNum()])
        }
    }
    private suspend fun getRandomNum(): Int {
        val value = CoroutineScope(Dispatchers.Default).async {
            delay((500..3000).random().toLong())
            (0..5).random()
        }
        return value.await()
    }
}