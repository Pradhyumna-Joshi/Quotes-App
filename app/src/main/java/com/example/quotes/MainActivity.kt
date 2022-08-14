package com.example.quotes

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quotes.Adapter.ItemListener
import com.example.quotes.Adapter.QuoteAdapter
import com.example.quotes.Model.Quote
import com.example.quotes.Utils.ColorPicker
import com.example.quotes.databinding.ActivityMainBinding
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), ItemListener {

    lateinit var adapter: QuoteAdapter
    var quotesList = ArrayList<Quote>()
    private var binding_: ActivityMainBinding? = null
    val binding get() = binding_!!
    lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding_ = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = QuoteAdapter(this, quotesList, this)
        binding.recyclerView.adapter = adapter

        progressDialog = Dialog(this@MainActivity, R.style.dialog)
        progressDialog.setContentView(R.layout.dialog_progress)

        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        progressDialog.show()


        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        binding.recyclerView.layoutManager = layoutManager

        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                binding.rootLayout.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
            }
        })
        getQuotes()
    }

    private fun getQuotes() {

        val quotes = QuoteService.quoteInstance.getQuotes()
        quotes.enqueue(object : Callback<ArrayList<Quote>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<ArrayList<Quote>>,
                response: Response<ArrayList<Quote>>,
            ) {
                val quoteslist = response.body()!!
                progressDialog.dismiss()
                quotesList.addAll(quoteslist)
                adapter.notifyDataSetChanged()

            }

            override fun onFailure(call: Call<ArrayList<Quote>>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Error Fetching Quotes",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCopyClicked(text: String) {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("copied_data", text)
        clipboard.setPrimaryClip(clipData)
        Toast.makeText(this@MainActivity, "Copied to Clipboard", Toast.LENGTH_SHORT).show()
    }


    override fun onShareClicked(quote: Quote) {
        val intent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, quote.text)
            this.type = "text/plain"

        }
        startActivity(intent)
    }

}