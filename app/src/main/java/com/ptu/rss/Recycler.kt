package com.ptu.rss

import android.annotation.SuppressLint
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prof.rssparser.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.lang.Exception
import java.net.URL


class Adapter(private val activity: AppCompatActivity, var items: MutableList<Article>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], activity)
    }

}

class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(data: Article, activity: AppCompatActivity) {
        Picasso.get().load(data.image).into(view.imageView)
        view.buttonLink.setOnClickListener {
            val url = URL(data.link)
            GlobalScope.launch(Dispatchers.IO) {
                val response = Retrofit.Builder().baseUrl(url.protocol + "://" + url.host).build()
                    .create(Request::class.java).request(url.path).execute()
                GlobalScope.launch(Dispatchers.Main) {
                    val html = response.body()?.string()
                    activity.startActivity(DetailActivity.newInstance(activity, html))
                }
            }
        }
        view.date.text = data.pubDate
        view.webView.loadData(data.description, null, null)
        val empty = "пусто"
        view.textView.text = """
            author: ${data.author ?: empty}
            categories: ${data.categories.joinToString(", ")}
            title: ${data.title ?: empty}
            content: ${data.content ?: empty}
        """.trimIndent()
    }
}