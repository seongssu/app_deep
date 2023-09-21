package com.android.imagesearch.SearchFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.imagesearch.Method.shortToast
import com.android.imagesearch.api.Document
import com.android.imagesearch.api.NetWorkClient
import com.android.imagesearch.api.VideoDocument
import com.android.imagesearch.databinding.FragmentSearchBinding
import com.android.imagesearch.sharedPreferences.SearchData
import com.android.imagesearch.sharedPreferences.SPF
import com.google.gson.Gson
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var spf: SPF
    var items = ArrayList<Document>()
    var items1 = ArrayList<VideoDocument>()
    val image_items = ArrayList<SearchData>()
    var sum_items = ArrayList<SearchData>()

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
                        spf.saveTitle(searchQuery)
                        communicateNetWork(
                            setUpParameter(searchQuery),
                            setUpVideoParameter(searchQuery)

                        )

                    }
                    context.shortToast("검색 되었습니다.")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
            val getTitle = spf.getTitle()
            if (getTitle.isNotEmpty()) {
                setQuery(getTitle, false)
            }
        }
        return binding.root
    }

    private fun communicateNetWork(
        param: HashMap<String, String>, param1: HashMap<String,
                String>
    ) = lifecycleScope
        .launch {
            val REST_API_KEY = "9b21bf534817ea027eda3d1a32af0df7"
            val Authorization = "KakaoAK $REST_API_KEY"
            val responseData = NetWorkClient.KakaoNetWork.getImage(Authorization, param)
            val responseData1 = NetWorkClient.KakaoNetWork.getVideo(Authorization, param1)
            items = responseData.documents
            items1 = responseData1.documents
            Log.d("ImageSearchs", "비디오데이터 : ${items1}")
            requireActivity().runOnUiThread {

                items.forEach {
                    val type = 0
                    val display_sitename = it.display_sitename
                    val datetime = it.datetime
                    val image_url = it.image_url
                    image_items.add(SearchData(type,display_sitename, datetime, image_url))
                }
                items1.forEach {
                    val type = 1
                    val title = it.title
                    val thumbnail = it.thumbnail
                    val datetime = it.datetime
                    image_items.add(SearchData(type,title,datetime,thumbnail))
                }
                sum_items = ArrayList(image_items.distinctBy { it.image_url }.sortedBy { it.datetime })

                parentFragmentManager.setFragmentResultListener(
                    "filteritemsKey", this@SearchFragment
                ) { key, result ->
                    val get_items = result.getParcelableArrayList<SearchData>("filteritems")
                    Log.d("ImageSearchs", "bundle로 받아온 데이터: ${get_items}")
                    if (get_items != null) {
                        get_items.forEach { item ->
                            val index = sum_items.indexOfFirst { it.image_url == item.image_url }
                            sum_items[index].isLike = false
                            Log.d("ImageSearchs", "바뀐 isLike : ${sum_items}")

                            saveItems()
                            Log.d("ImageSearchs", "bundle 저장데이터${sum_items}")
                        }
                        binding.recyclerView.adapter?.notifyDataSetChanged()
                    }
                }

                binding.recyclerView.apply {
                    adapter = SearchFragmentAdapter(sum_items, context).apply {
                        itemClick = object : SearchFragmentAdapter.ItemClick {
                            override fun onFavoritesClick(view: View, position: Int) {
                                if (position in 0..sum_items.size) {
                                    val item = sum_items[position]
                                    item.isLike = !item.isLike
                                    if (item.isLike) {
                                        context.shortToast("보관함에 추가 되었습니다.")
                                    } else
                                        context.shortToast("보관함에서 제거 되었습니다.")
                                    Log.d("ImageSearchs", "Search의 데이터 : ${item}")
                                    saveItems()
                                    Log.d("ImageSearchs", "버튼 저장데이터${sum_items}")
                                    notifyDataSetChanged()

                                }
                            }
                        }
                    }
                    layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
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

    private fun setUpVideoParameter(name: String): HashMap<String, String> {
        return hashMapOf(
            "query" to name,
            "sort" to "accuracy",
            "page" to "1",
            "size" to "15"
        )
    }

    private fun saveItems() {
        val gson = Gson()
        val save_items = gson.toJson(sum_items)
        spf.saveData(save_items)
    }
}