package com.productslist.core.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import com.productslist.R

class MainActivity : AppCompatActivity() {

    private lateinit var searchButton: ImageView
    private lateinit var searchView: SearchView
    private lateinit var searchText: EditText
    private lateinit var closeButton: ImageView
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupSearchView()
        setFont()
        setSearchHint()

        searchButton.visibility = View.GONE
        text.visibility = View.GONE
        searchView.visibility = View.VISIBLE


    }

    private fun initializeViews() {
        searchButton = findViewById(R.id.search)
        searchView = findViewById(R.id.searchView)
        searchText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        text = findViewById(R.id.text)
    }

    private fun setupSearchView() {
        closeButton.setOnClickListener { closeSearch() }
        searchView.setOnCloseListener {
            closeSearch()
            false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun closeSearch() {
        // TODO: handle closing the search bar
    }

    private fun setFont() {
        val font = ResourcesCompat.getFont(this, R.font.montserrat)
        searchText.typeface = font
        searchText.highlightColor = resources.getColor(R.color.colorSelection , this.theme)
    }

    private fun setSearchHint() {
        searchView.setQueryHint(getString(R.string.hint_recherche_pv))
    }
}
