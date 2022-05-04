package com.jihoonyoon.bookapitest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jihoonyoon.bookapitest.R
import com.jihoonyoon.bookapitest.model.Book

class BookRecyclerViewAdapter(
    private val books: List<Book>,
    private val context: Context
) : RecyclerView.Adapter<BookRecyclerViewAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder
        = BookViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent,false), context
        )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size

    class BookViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val titleText: TextView = itemView.findViewById(R.id.titleText)
        private val authorText: TextView = itemView.findViewById(R.id.authorText)
        private val publisherText: TextView = itemView.findViewById(R.id.publisherText)

        fun bind(book: Book) {
            Glide.with(context)
                .load(book.image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .fitCenter()
                .into(imageView)


            titleText.text = book.title.replace("<b>","").replace("</b>","")
            authorText.text = book.author.replace("<b>","").replace("</b>","")
            publisherText.text = book.publisher.replace("<b>","").replace("</b>","")
        }
    }

}