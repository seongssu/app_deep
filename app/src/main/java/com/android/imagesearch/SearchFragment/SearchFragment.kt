package com.android.imagesearch.SearchFragment

import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.imagesearch.api.Document
import com.android.imagesearch.api.NetWorkClient
import com.android.imagesearch.databinding.FragmentSearchBinding
import com.android.imagesearch.sharedPreferences.ImageSearchData
import com.android.imagesearch.sharedPreferences.SPF
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var spf: SPF
    var items = ArrayList<Document>()
    val image_items = ArrayList<ImageSearchData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        spf = SPF(requireContext())

        binding.searchView.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        val searchQuery = query.trim()
                        communicateNetWork(setUpParameter(searchQuery))
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
        return binding.root
    }

    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch {
        val REST_API_KEY = "9b21bf534817ea027eda3d1a32af0df7"
        val Authorization = "KakaoAK $REST_API_KEY"
        val responseData = NetWorkClient.KakaoNetWork.getImage(Authorization, param)

        requireActivity().runOnUiThread {

            items = responseData.documents
            items.forEach {
                val display_sitename = it.display_sitename
                val datetime = it.datetime
                val image_url = it.image_url
                image_items.add(ImageSearchData(display_sitename, datetime, image_url))
            }

            parentFragmentManager.setFragmentResultListener(
                "filteritemsKey", this@SearchFragment
            ) { key, result ->
                val get_items = result.getParcelableArrayList<ImageSearchData>("filteritems")
                Log.d("ImageSearchs", "bundle로 받아온 데이터: ${get_items}")
                if (get_items != null) {
                    get_items.forEach { item ->
                        val index = items.indexOfFirst { it.image_url == item.image_url }
                        image_items[index].isLike = false
                        Log.d("ImageSearchs", "바뀐 isLike : ${image_items}")
                    }
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            binding.recyclerView.apply {
                adapter = SearchFragmentAdapter(image_items, context).apply {
                    itemClick = object : SearchFragmentAdapter.ItemClick {
                        override fun onFavoritesClick(view: View, position: Int) {
                            if (position in 0..image_items.size) {
                                val item = image_items[position]
                                item.isLike = !item.isLike
                                Log.d("ImageSearchs", "Search의 데이터 : ${item}")
                                val gson = Gson()
                                val save_items = gson.toJson(image_items)
                                spf.saveData(save_items)

                                notifyDataSetChanged()

                            }
                        }
                    }
                }
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)
            }
        }
    }

    private fun setUpParameter(name: String): HashMap<String, String> {
        return hashMapOf(
            "query" to name,
            "sort" to "accuracy",
            "page" to "1",
            "size" to "80"
        )
    }
}