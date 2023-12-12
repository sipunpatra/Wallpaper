package com.sipun.wallpaperw.ui

import android.app.Dialog
import android.app.WallpaperManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sipun.wallpaperw.databinding.ActivityMainBinding
import com.sipun.wallpaperw.databinding.DialogLoadingBinding
import com.sipun.wallpaperw.utils.SetWallpaperTask
import com.sipun.wallpaperw.viewmodel.UnsplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: UnsplashViewModel by viewModels()

    @Inject
    lateinit var adapter : UnSplashAdapter

    private lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var wallpaperManager : WallpaperManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            recyclerView.adapter =adapter

            swipeRefreshLayout.setOnRefreshListener {
                adapter.refresh()
            }

            lifecycleScope.launch {
                viewModel.photos.collectLatest { pagingData ->
                    adapter.submitData(pagingData)
                }
            }
            lifecycleScope.launch {
                adapter.loadStateFlow.collectLatest { loadStates ->
                    swipeRefreshLayout.isRefreshing =loadStates.refresh is LoadState.Loading
                }
            }
            adapter.setOnItemClickListener {
                val loading =Dialog(this@MainActivity)
                val bindingLoading = DialogLoadingBinding.inflate(layoutInflater)
                loading.setCancelable(false)
                lifecycleScope.launch {
                    loading.show()
                    if (SetWallpaperTask(it.urls.large,wallpaperManager).execute()){
                        loading.dismiss()
                        Toast.makeText(this@MainActivity , "The wallpaper has been successfully changed." , Toast.LENGTH_SHORT).show()

                    }else{
                        loading.dismiss()
                        Toast.makeText(this@MainActivity , "error" , Toast.LENGTH_SHORT).show()

                    }
                }
            }

        }
    }
}