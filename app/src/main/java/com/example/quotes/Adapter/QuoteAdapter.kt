package com.example.quotes.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.Model.Quote
import com.example.quotes.databinding.ItemQuoteBinding

class QuoteAdapter(
    private val context: Context,
    private val arrayList: ArrayList<Quote>,
    private val listener: ItemListener,
) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    inner class QuoteViewHolder(val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = ItemQuoteBinding.inflate(LayoutInflater.from(context), parent, false)
        return QuoteViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val currentQuote = arrayList[position]
        holder.binding.apply {
            text.text = "“ " + currentQuote.text + " ”"
            author.text = " ~ " + currentQuote.author
        }

        holder.binding.btnShare.setOnClickListener {
            listener.onShareClicked(currentQuote)
        }

        holder.binding.btnCopy.setOnClickListener {
            listener.onCopyClicked(currentQuote.text)
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

interface ItemListener {
    fun onCopyClicked(text: String)
    fun onShareClicked(quote: Quote)
}

