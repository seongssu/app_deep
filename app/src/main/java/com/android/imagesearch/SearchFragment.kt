package com.android.imagesearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.android.imagesearch.api.Document
import com.android.imagesearch.api.Kakao
import com.android.imagesearch.api.NetWorkClient
import com.android.imagesearch.databinding.FragmentSearchBinding
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    var items = ArrayList<Document>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.searchView.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
        val responseData = NetWorkClient.KakaoNetWork.getImage(Authorization,param)
        if(responseData.document != null){
            items = responseData.document
        }else Toast.makeText(requireContext(),"검색어를 다시 입력해주세요", Toast.LENGTH_SHORT).show()
    }

    private fun setUpParameter(name:String): HashMap<String, String>{
        return hashMapOf(
            "query" to name,
            "sort" to "accuracy",
            "page" to "1",
            "size" to "80"
        )
    }
}