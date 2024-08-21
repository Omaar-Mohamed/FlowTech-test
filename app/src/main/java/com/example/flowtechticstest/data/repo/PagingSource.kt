package com.example.flowtechticstest.data.repo
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.flowtechticstest.data.model.characters_response.CharacterResult
//import com.example.flowtechticstest.data.source.remote.NetworkServices
//
//class CharacterPagingSource(private val apiService: NetworkServices) : PagingSource<Int, CharacterResult>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterResult> {
//        val position = params.key ?: 1
//        return try {
//            val response = apiService.getCharacters(position)
//            val nextKey = if (response.info.next != null) position + 1 else null
//            LoadResult.Page(
//                data = response.results,
//                prevKey = if (position == 1) null else position - 1,
//                nextKey = nextKey
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, CharacterResult>): Int? {
//        // Return the page index to refresh
//        return null
//    }
//}
