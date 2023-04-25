package com.productslist.core.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.core.activities.view_models.MainViewModel
import com.productslist.R
import com.productslist.core.domain.model.Product
import com.productslist.core.ui.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



@AndroidEntryPoint
class MainActivity : AppCompatActivity() ,  ProductAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchButton: ImageView
    private lateinit var searchView: SearchView
    private lateinit var searchText: EditText
    private lateinit var closeButton: ImageView
    private lateinit var lodingBar: ProgressBar
    private lateinit var refreshHolder: RelativeLayout
    private lateinit var text: TextView
    private lateinit var refreshBtn : Button

    private lateinit var productAdapter: ProductAdapter
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initializeViews()
        setupSearchView()
        setFont()
        setSearchHint()
        initializeSearchView()
        initializeAdapter()
        showLoadingBar()

        lifecycleScope.launch {
            viewModel.eventFlow.collectLatest {
                    event ->
                when(event){
                    is MainViewModel.UIEvent.ShowToast->{
                        showtoast( event.message, this@MainActivity)

                    }
                    is MainViewModel.UIEvent.UpdateRceyclerView ->{

                        showRecyclerView ()
                    }
                    is MainViewModel.UIEvent.ShowLoadingBar->{

                        showLoadingBar()
                    }
                    is MainViewModel.UIEvent.ShowRefreshBtn->{

                        showRefreshBtn ()
                    }
                }
            }
        }

    }
    private fun hideRecyclerView () {
        recyclerView.visibility = View.GONE
    }
    private fun showRecyclerView (){
        hideRefreshBtn ()
        hideLoadingBar()
        updateRecyclerView()
        recyclerView.visibility = View.VISIBLE
    }
    private fun showRefreshBtn () {
        hideLoadingBar()
        hideRecyclerView ()
        refreshHolder.visibility = View.VISIBLE
    }
    private fun hideRefreshBtn () {
        refreshHolder.visibility = View.GONE
    }
    private fun showLoadingBar() {
        hideRefreshBtn()
        hideRecyclerView ()
        lodingBar.visibility = View.VISIBLE
    }

    private fun hideLoadingBar(){
        lodingBar.visibility = View.GONE
    }
    private fun initializeSearchView() {
        searchButton.visibility = View.GONE
        text.visibility = View.GONE
        searchView.visibility = View.VISIBLE
    }

    private fun initializeAdapter() {
        productAdapter = ProductAdapter(viewModel.mainState.value.products, this)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = productAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
        }

    }

    private fun updateRecyclerView() {
        (productAdapter as ProductAdapter).updateProducts(viewModel.mainState.value.products)
    }

    private fun initializeViews() {
        searchButton = findViewById(R.id.search)
        searchView = findViewById(R.id.searchView)
        refreshHolder = findViewById(R.id.refresh_holder)
        lodingBar = findViewById(R.id.loadingBar)
        refreshBtn = findViewById(R.id.refresh_btn)
        searchText = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
        closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        recyclerView = findViewById(R.id.recyclerView)
        text = findViewById(R.id.text)
        refreshOnClickListener ()
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
                val filteredList = viewModel.mainState.value.products.filter {
                    it.product_name.contains(newText, ignoreCase = true)
                }
                productAdapter.updateProducts(filteredList)
                return true
            }
        })
    }
    private fun refreshOnClickListener () {
        refreshBtn.setOnClickListener(View.OnClickListener {
            showLoadingBar()
            viewModel.products()
        })
    }

    private fun closeSearch() {
        searchView.setQuery("", false)
    }

    private fun setFont() {
        val font = ResourcesCompat.getFont(this, R.font.montserrat)
        searchText.typeface = font
        searchText.highlightColor = resources.getColor(R.color.colorSelection , this.theme)
    }

    private fun setSearchHint() {
        searchView.setQueryHint(getString(R.string.hint_recherche_pv))
    }
    private fun showtoast( msg:String , context:Context){
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.toast_layout, null) as LinearLayout
        val message = layout.findViewById<TextView>(android.R.id.message)
        message.text = msg
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    override fun onItemClick(product: Product) {

    }
}
