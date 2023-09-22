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
    private lateinit var spf: SPF               //sharedpreferences 객체
    var items = ArrayList<Document>()           // image아이템
    var items1 = ArrayList<VideoDocument>()     // video아이템
    val search_items = ArrayList<SearchData>()   // image 아이템 + video아이템
    var sum_items = ArrayList<SearchData>()     // search_item을 시간순 정렬한 데이터

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        spf = SPF(requireContext())

        binding.searchView.apply {                                  //searchView를 이용한 검색
            isSubmitButtonEnabled = true                                        //엔터 눌렀을때 검색실행 1
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        val searchQuery = query.trim()
                        spf.saveTitle(searchQuery)              //검색어를 sharedpreferencs를 이용해서 저장
                        communicateNetWork(                     //쿼리 파라미터+키를 넣어줘서 서버로부터 데이터를 불러옴
                            setUpParameter(searchQuery),        //이미지 검색 쿼리 파라미터 키
                            setUpVideoParameter(searchQuery)    //비디오 검색 쿼리 파라미터 키

                        )

                    }
                    context.shortToast("검색 되었습니다.")
                    return false                                    //엔터 눌렀을때 검색실행 2
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
            val getTitle = spf.getTitle()   // sharedpreferences에 저장된 마지막 검색기록을 불러옴
            if (getTitle.isNotEmpty()) {
                setQuery(getTitle, false)
            }
        }
        return binding.root
    }

    private fun communicateNetWork(
        param: HashMap<String, String>, param1: HashMap<String,     //이미지, 비디오 쿼리파라미터 함수를 따로만들어줘서 param을 두개 받아옴, 2개의 데이터는 동일
                 String>
    ) = lifecycleScope
        .launch {
            val REST_API_KEY = "9b21bf534817ea027eda3d1a32af0df7"   //REST API 키
            val Authorization = "KakaoAK $REST_API_KEY"             //키를 형식에 맞게 변환
            val responseData = NetWorkClient.KakaoNetWork.getImage(Authorization, param)     //서버에서 받아온 이미지 데이터
            val responseData1 = NetWorkClient.KakaoNetWork.getVideo(Authorization, param1)// 서버에서 받아온 디비오 데이터
            items = responseData.documents
            items1 = responseData1.documents
            Log.d("ImageSearchs", "비디오데이터 : ${items1}")     //처음 이미지데이터만 받아왔었는데 비디오데이터도 받아오는지 확인
            requireActivity().runOnUiThread {
                items.forEach {
                    val type = 0                                    //이미지 데이터와 비디오데이터를 구분하기위해 데이터 클래스에 타입 추가
                    val display_sitename = it.display_sitename
                    val datetime = it.datetime
                    val image_url = it.image_url
                    search_items.add(SearchData(type,display_sitename, datetime, image_url))    //이미지데이터와 비디오데이터의 리스트를 합치기위해 새로운 리스트에 넣어줌
                }
                items1.forEach {
                    val type = 1                                        //이미지 데이터와 비디오데이터를 구분하기위해 데이터 클래스에 타입 추가
                    val title = it.title
                    val thumbnail = it.thumbnail
                    val datetime = it.datetime
                    search_items.add(SearchData(type,title,datetime,thumbnail))     //이미지데이터와 비디오데이터의 리스트를 합치기위해 새로운 리스트에 넣어줌
                }
                sum_items = ArrayList(search_items.distinctBy { it.image_url }.sortedBy { it.datetime })    //이미지+비디오 데이터 중복체크 후 datetime으로 정렬

                parentFragmentManager.setFragmentResultListener(                    //보관함에서 갱신되어 bundle로 넘겨준 데이터를 받아와서 기존의 데이터를 수정해줌
                    "filteritemsKey", this@SearchFragment
                ) { key, result ->
                    val get_items = result.getParcelableArrayList<SearchData>("filteritems")
                    Log.d("ImageSearchs", "bundle로 받아온 데이터: ${get_items}")
                    if (get_items != null) {
                        get_items.forEach { item ->
                            val index = sum_items.indexOfFirst { it.image_url == item.image_url }
                            //갱신된 데이터와 기존의 정렬된 데이터의 image_url이 같은 데이터를 찾아서 index를 찾는다.( 기존의데이터를 갱신된 데이터로 수정해주기 위해서)
                            sum_items[index].isLike = false        //기존의 데이터에서 갱신된 isLike를 false로 수정(isLike가 true인 데이터들은 그대로 유지되고, 바뀐데이터만 갱신해주면 되므로)
                           Log.d("ImageSearchs", "바뀐 isLike : ${sum_items}")

                            saveItems()     //갱신된 데이터를 sharedpreferences를 이용해서 저장한다. (보관함에서는 이 저장된 데이터를 불러서 사용하므로)
                            Log.d("ImageSearchs", "bundle 저장데이터${sum_items}")
                        }
                        binding.recyclerView.adapter?.notifyDataSetChanged()    //데이터가 갱신되면 앱화면에서도 갱신되었다고 알려준다.(데이터는 갱신되었는데 앱화면에 표시는 최신화가 안되므로)
                  }
                }

                binding.recyclerView.apply {
                    adapter = SearchFragmentAdapter(sum_items, context).apply {
                        itemClick = object : SearchFragmentAdapter.ItemClick {        //보관함에 저장하는 클릭 이벤트
                            override fun onFavoritesClick(view: View, position: Int) {
                                if (position in 0..sum_items.size) {
                                    val item = sum_items[position]
                                    item.isLike = !item.isLike
                                    if (item.isLike) {
                                        context.shortToast("보관함에 추가 되었습니다.")    //확장함수를 이용한 Toast메시지
                                    } else
                                        context.shortToast("보관함에서 제거 되었습니다.")   //확장함수를 이용한 Toast메시지
                                    Log.d("ImageSearchs", "Search의 데이터 : ${item}")
                                    saveItems()                   //버튼을 눌러서 저장할 때도 데이터가 갱신되므로 sharedpreferences로 저장
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

    private fun setUpParameter(name: String): HashMap<String, String> { //이미지 쿼리 파라미터 키
        return hashMapOf(
            "query" to name,
            "sort" to "accuracy",
            "page" to "1",
            "size" to "80"
        )
    }

    private fun setUpVideoParameter(name: String): HashMap<String, String> {    //비디오 쿼리 파라미터 키
        return hashMapOf(
            "query" to name,
            "sort" to "accuracy",
            "page" to "1",
            "size" to "15"
        )
    }

    private fun saveItems() {       //sharedpreferences를 이용한 저장 함수
        val gson = Gson()
        val save_items = gson.toJson(sum_items)
        spf.saveData(save_items)
    }
}