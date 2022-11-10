package com.example.newsapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private val logTag = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        setupRecycleView()

        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            handleProgressBar(response)
        }
    }

    private fun setupRecycleView(){
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun handleProgressBar(response: Resource<NewsResponse>){
        when(response){
            is Resource.Success -> {
                hideProgressBar()
            }
            is Resource.Loading -> {
                showProgressBar()
            }
            is Resource.Error -> {
                hideProgressBar()
                response.message?.let { message ->
                    Log.e(logTag,"an error occurred: $message")
//                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
    }
}