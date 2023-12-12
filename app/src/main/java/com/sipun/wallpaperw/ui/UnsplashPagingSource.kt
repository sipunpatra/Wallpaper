package com.sipun.wallpaperw.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sipun.wallpaperw.data.model.UnSplashPhoto
import com.sipun.wallpaperw.data.server.Api

class UnsplashPagingSource(private val unsplashApi: Api) : PagingSource<Int, UnSplashPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnSplashPhoto> {
        return try {
            val page = params.key ?: 1
            val photos = unsplashApi.getPhotos(page = page)
            LoadResult.Page(
                data = photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnSplashPhoto>): Int? {
        return state.anchorPosition
    }
}