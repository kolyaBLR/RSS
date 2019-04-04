package com.ptu.rss

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {

        private const val DATA_KEY = "DATA_KEY_PTU"

        fun newInstance(context: Context, data: String?) = Intent(context, DetailActivity::class.java).apply {
            putExtra(DATA_KEY, data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        webView.loadData(intent?.getStringExtra(DATA_KEY), null, null)
    }
}