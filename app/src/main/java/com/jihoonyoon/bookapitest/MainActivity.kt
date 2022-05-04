package com.jihoonyoon.bookapitest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.jihoonyoon.bookapitest.adapter.BookRecyclerViewAdapter
import com.jihoonyoon.bookapitest.api.BookService
import com.jihoonyoon.bookapitest.databinding.ActivityMainBinding
import com.jihoonyoon.bookapitest.model.Book
import com.jihoonyoon.bookapitest.model.BookDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var bookService: BookService
    private var current: Int = 1

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)

        initSearchButton()
        initArrowUI()
    }

    private fun initSearchButton(){
        binding.searchButton.setOnClickListener {
            current = 1
            getBook(binding.editText.text.toString(), current)
        }
    }

    private fun getBook(query: String, start: Int){
        bookService.getBooksByQuery(BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_KEY, query, start)
            .enqueue(object : Callback<BookDTO> {
                override fun onResponse(call: Call<BookDTO>, response: Response<BookDTO>) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "onResponse: fail")
                        binding.textView.visibility = View.VISIBLE
                        return
                    }

                    response.body()?.let {
                        binding.textView.visibility = View.GONE
                        Log.d(TAG, "onResponse: ${it.toString()}")
                        setArrowUI(it.start, it.total)

                        setRecyclerView(it.items)
                    }
                }

                override fun onFailure(call: Call<BookDTO>, t: Throwable) {
                    Log.d(TAG, "onFailure: fail ${t.toString()}")
                }

            })
    }

    private fun setRecyclerView(books: List<Book>){
        val adapter = BookRecyclerViewAdapter(books, this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setArrowUI(start: Int, total: Int){
        if(total-10 > current){
            binding.forwardButton.isEnabled = true
            binding.forwardButton.isClickable = true
        } else {
            binding.forwardButton.isEnabled = false
            binding.forwardButton.isClickable = false
        }

        if(start > 10){
            binding.backButton.isEnabled = true
            binding.backButton.isClickable = true
        } else {
            binding.backButton.isEnabled = false
            binding.backButton.isClickable = false
        }
    }

    private fun initArrowUI(){
        binding.backButton.setOnClickListener {
            if(current > 10){
                current -= 10
                getBook(binding.editText.text.toString(), current)
            }
        }

        binding.forwardButton.setOnClickListener {
            current += 10
            getBook(binding.editText.text.toString(), current)
        }
    }

    companion object{
        private const val BASE_URL = "https://openapi.naver.com"
        private const val TAG = "MainActivity"
    }
}