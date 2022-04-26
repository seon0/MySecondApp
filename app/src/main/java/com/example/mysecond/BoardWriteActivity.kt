package com.example.mysecond

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mysecond.databinding.ActivityBoardWriteBinding

class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBoardWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWrite.setOnClickListener { view ->
            val title = binding.editTextTitle.text
            val content = binding.editTextContent.text

        }


    }
}