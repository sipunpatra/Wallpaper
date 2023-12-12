package com.sipun.wallpaperw.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.sipun.wallpaperw.data.model.UnSplashPhoto
import com.sipun.wallpaperw.data.server.Api
import com.sipun.wallpaperw.ui.UnsplashPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UnsplashViewModel @Inject constructor(private val unsplashApi: Api) : ViewModel() {
    val photos: Flow<PagingData<UnSplashPhoto>> = Pager(
        config = PagingConfig(
            pageSize = 40,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { UnsplashPagingSource(unsplashApi) }
    ).flow
}