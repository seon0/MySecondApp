package com.example.mysecond

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mysecond.data.BoardPost
import com.example.mysecond.database.AppBoardDatabase
import com.example.mysecond.databinding.ActivityWritePostBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WritePostActivity : AppCompatActivity() {

    private final val TAG = "BoardWriteActivity"
    private lateinit var binding : ActivityWritePostBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWritePostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWrite.setOnClickListener { view ->
            val title = binding.editTextTitle.text
            val content = binding.editTextContent.text

            Log.d(TAG, title.toString() + "\n" + content.toString())
            val posting = BoardPost(title = title.toString(), content = content.toString())

            // UI Thread는 긴 시간이 걸리면, 작업을 중단하고 에러를 발생시킴  ->  background Thread로 처리
            CoroutineScope(Dispatchers.IO).launch {
                AppBoardDatabase.getDatabase(application).boardPostDao().insert(posting)
//                Toast.makeText(applicationContext, "등록 완료", Toast.LENGTH_SHORT)  // 에러 유발
                finish()
            }

        }

        binding.btnCancel.setOnClickListener {
            finish()
        }


    }
}