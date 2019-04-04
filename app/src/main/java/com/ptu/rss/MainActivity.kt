package com.ptu.rss

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.prof.rssparser.Article
import com.prof.rssparser.OnTaskCompleted
import com.prof.rssparser.Parser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://news.tut.by/rss/index.rss"

        val parser = Parser()
        recycler.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(this, mutableListOf())
        recycler.adapter = adapter
        parser.onFinish(object : OnTaskCompleted {
            override fun onError(e: Exception) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle(R.string.error_message)
                    .setMessage(e.message)
                    .create().show()
            }

            override fun onTaskCompleted(list: MutableList<Article>) {
                GlobalScope.launch(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    adapter.items = list
                    adapter.notifyDataSetChanged()
                }
            }

        })
        parser.execute(url)
    }
}
