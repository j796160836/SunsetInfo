package com.johnny.sunsetinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val df = SimpleDateFormat("HH:mm", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_retry.setOnClickListener {
            loadData()
        }
        loadData()

    }

    fun loadData() {
        view_group_loading.visibility = View.VISIBLE
        view_group_error.visibility = View.GONE
        view_group_loaded.visibility = View.GONE
        NetworkAPI.getSunsetDataSync()
            .done { response ->
                view_group_loading.visibility = View.GONE
                view_group_error.visibility = View.GONE
                view_group_loaded.visibility = View.VISIBLE
                val sunsetDate = SunsetDateUtil.dateFormater.parse(response.results.sunset)
                label_time.text = df.format(sunsetDate)
            }.fail { e ->
                view_group_loading.visibility = View.GONE
                view_group_error.visibility = View.VISIBLE
                view_group_loaded.visibility = View.GONE
                e.printStackTrace()
                Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show()
            }
    }
}
