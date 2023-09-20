package com.android.imagesearch.SearchFragment

import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.imagesearch.Method.shortToast
import com.android.imagesearch.api.Document
import com.android.imagesearch.api.NetWorkClient
import com.android.imagesearch.databinding.FragmentSearchBinding
import com.android.imagesearch.sharedPreferences.SPF
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private lateinit var spf : SPF
    var items = ArrayList<Document>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            if (responseData.documents != null) {
                items = responseData.documents
                val gson = Gson()
                val save_items = gson.toJson(items)
                spf.saveData(save_items)

                val get_items = spf.getData()
                val push_items:ArrayList<Document> = Gson().fromJson(get_items, object:
                    TypeToken<ArrayList<Document>>(){}.type)

                binding.recyclerView.apply {
                    Log.d("ImageSearchs","SearchFragment spf 데이터 : ${save_items}")
                    adapter = SearchFragmentAdapter(push_items,context)
                    layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                    setHasFixedSize(true)
                }

            } else requireContext().shortToast("잘못 입력하셨습니다.")
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