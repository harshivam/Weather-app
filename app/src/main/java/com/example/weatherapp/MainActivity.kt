package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

class MainActivity : AppCompatActivity() {

    var tvTemp:TextView? = null
    var tvDateTop:TextView? = null
    private var tvDate1:TextView? = null
    private var tvDate2:TextView? = null
    private var tvDate3:TextView? = null
    private var tempDay1:TextView?=null
    private var tempDay2:TextView?=null
    private var tempDay3:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTemp = findViewById(R.id.tvDegree)
        tvDateTop = findViewById(R.id.tvDate)
        tvDate1= findViewById(R.id.tvDay1)
        tvDate2= findViewById(R.id.tvDay2)
        tvDate3= findViewById(R.id.tvDay3)
        tempDay1= findViewById(R.id.tempDay1)
        tempDay2= findViewById(R.id.tempDay2)
        tempDay3= findViewById(R.id.tempDay3)




      fetchWeatherData()
    }
    fun convertUnixTimestampToDate(unixTimestamp: Long): String {
        val date = Date(unixTimestamp * 1000L)
        val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
        return sdf.format(date)
    }


    private fun fetchWeatherData(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val response= retrofitBuilder.getWeatherData("Lucknow","f1375664f0447ee9042f337057148cf4","metric")


        response.enqueue(object :Callback<example>{
            override fun onResponse(call: Call<example>, response: Response<example>) {
               val responseBody = response.body()!!
                tvTemp!!.text = responseBody.list[0].main.temp.toString()+"°"+"c"
                tempDay1!!.text = responseBody.list[1].main.temp.toString()+"°"+"c"
                tempDay2!!.text = responseBody.list[2].main.temp.toString()+"°"+"c"
                tempDay3!!.text = responseBody.list[3].main.temp.toString()+"°"+"c"

                val unixTimestamptop = responseBody.list[0].dt.toLong()
                tvDateTop!!.text= convertUnixTimestampToDate(unixTimestamptop)
                val unixTimestampDay1 = responseBody.list[1].dt.toLong()
                val date1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(responseBody.list[1].dt_txt)
                tvDate1!!.text = SimpleDateFormat("dd/MM", Locale.getDefault()).format(date1)

                val date2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(responseBody.list[9].dt_txt)
                tvDate2!!.text = SimpleDateFormat("dd/MM", Locale.getDefault()).format(date2)

                val date3 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(responseBody.list[17].dt_txt)
                tvDate3!!.text = SimpleDateFormat("dd/MM", Locale.getDefault()).format(date3)

            }

            override fun onFailure(call: Call<example>, t: Throwable) {
                    Log.d("Main Activity","onFailure" + t.message)
            }
        })
    }
}


