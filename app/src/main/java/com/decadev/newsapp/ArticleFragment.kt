package com.decadev.newsapp

import android.app.Application
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.decadev.newsapp.NewsActivity
import com.decadev.newsapp.R
import com.decadev.newsapp.db.ArticleDataBase
import com.decadev.newsapp.repository.NewsRepository
import com.decadev.newsapp.util.NewsViewModelProviderFactory
import com.decadev.newsapp.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsRepository = NewsRepository(ArticleDataBase(requireActivity()))
        val viewModelProviderFactory = NewsViewModelProviderFactory(Application(), newsRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        val article = args.article

        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article has been Successfully added", Snackbar.LENGTH_LONG).show()
        }

    }
}