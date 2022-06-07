package com.example.mysecond

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.example.mysecond.databinding.ActivityWritePostsBinding
import com.example.mysecond.view.FlexibleTextView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class WritePostsActivity : AppCompatActivity() {

    private final val TAG = "WritePostsActivity"
    private lateinit var binding : ActivityWritePostsBinding
    private lateinit var imm :InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWritePostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        binding.writePostsBtnAddText.setOnClickListener { view ->
            binding.activityWritePostsEditTextLayout.isVisible = true
            binding.activityWritePostsEditText.requestFocus()
            imm.showSoftInput(binding.activityWritePostsEditText, 0)
        }

        binding.activityWritePostsEditTextLayout.setOnClickListener {view ->
            binding.activityWritePostsEditTextLayout.isVisible = false
            applyText()
            imm.hideSoftInputFromWindow(binding.activityWritePostsEditText.windowToken, 0)
        }

        binding.writePostsBtnDownload.setOnClickListener {
            binding.activityWritePostsBackgroundLayout.setDrawingCacheEnabled(true)
            val mbitmap = binding.activityWritePostsBackgroundLayout.getDrawingCache()
            createImage(mbitmap);
        }

    }


    fun applyText() {
        val editText = binding.activityWritePostsEditText

        val newTextView = FlexibleTextView(this)
        newTextView.setTextColor(Color.WHITE)
        newTextView.setBackgroundColor(Color.BLACK)
        newTextView.text = editText.text
        newTextView.setPadding(8)

        var layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER


        /*
        직접 설정
         */
//        var dX: Float = 0f
//        var dY: Float = 0f

//        newTextView.setOnTouchListener { view, event ->
//
//            when (event.action) {  // ( event.getAction() & MotionEvent.ACTION_MASK )  // 또 다른 예제
//                MotionEvent.ACTION_DOWN -> {
//                    Log.d("ACTION_DOWN", view.x.toString() +" - "+ event.rawX + " = " + dX)
//                    Log.d("ACTION_DOWN", view.y.toString() +" - "+ event.rawY + " = " + dY)
//                    dX = view.x - event.rawX
//                    dY = view.y - event.rawY
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    Log.d("ACTION_MOVE", event.rawX .toString() +" + " + dX)
//                    Log.d("ACTION_MOVE", event.rawY.toString() +" + " + dY)
//                    view.animate()
//                        .x(event.rawX + dX)
//                        .y(event.rawY + dY)
//                        .setDuration(0)
//                        .start()
//                }
//                else ->  false
//            }
//            true
//        }

        binding.activityWritePostsBackgroundLayout.addView(newTextView, layoutParams)

        editText.text = null
    }


    fun createImage(bmp: Bitmap) {
        val bytes = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes)
        val file = File( "/storage/0BFB-2607/Android/data/com.example.mysecond/files/"+SimpleDateFormat("yyyyMMddHHmm").format(Date())+".jpg")
        Log.d(TAG,"create file : " + file.absolutePath)
        try {
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            outputStream.write(bytes.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}