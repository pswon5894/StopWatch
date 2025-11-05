package com.example.stopwatch

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer
import kotlin.concurrent.timer

class MainActivityUpgrade : AppCompatActivity() , View.OnClickListener {

    private lateinit var btn_start : Button
    private lateinit var btn_restart : Button
    private lateinit var tv_minute : TextView
    private lateinit var tv_second : TextView
    private lateinit var tv_millisecond: TextView

    private var isRunning = false

    private var timer : Timer? = null
    private var time = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_upgrade)

        btn_start = findViewById(R.id.btn_start)
        btn_restart = findViewById(R.id.btn_restart)
        tv_minute = findViewById(R.id.tv_minute)
        tv_second = findViewById(R.id.tv_second)
        tv_millisecond = findViewById(R.id.tv_millisecond)

        btn_start.setOnClickListener(this)
        btn_restart.setOnClickListener(this)
    }

    override fun onClick(view: View?){
        when(view?.id){
            R.id.btn_start -> {
                if(isRunning){
                    pause()
                }else{
                    start()
                }
            }

            R.id.btn_restart -> {
                restart()
            }
        }
    }

    private fun start() {
        btn_start.text = getString(R.string.btn_pause_eng)
        isRunning = true

        timer = timer(period = 10) {
            //1000 = 1초, 0.001초
            time++

            val milli_second = time%100
            val second = (time % 6000) / 100
            val minute = time / 6000

            runOnUiThread {
                if(isRunning) {
                    tv_millisecond.text = if (milli_second <10) ".0${milli_second}" else ".${milli_second}"
                    tv_second.text = if (second<10) ":0${second}" else "${second}"
                    tv_minute.text = "${minute}"
                }
            }
        }
    }

    private fun pause() {
        btn_start.text = getString(R.string.btn_start_eng)
        isRunning = false
        timer?.cancel()
    }

    private fun restart() {
        timer?.cancel()

        btn_start.text = getString(R.string.btn_start_eng)
        isRunning = false

        time = 0
        tv_minute.text = "00"
        tv_second.text = ":00"
        tv_millisecond.text = ",00"

    }
}