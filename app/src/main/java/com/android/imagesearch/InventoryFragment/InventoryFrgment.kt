package com.android.imagesearch.InventoryFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.imagesearch.Method.shortToast
import com.android.imagesearch.databinding.FragmentInventoryFrgmentBinding
import com.android.imagesearch.sharedPreferences.SearchData
import com.android.imagesearch.sharedPreferences.SPF
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class InventoryFrgment : Fragment() {
    private val binding by lazy { FragmentInventoryFrgmentBinding.inflate(layoutInflater) }
    private lateinit var spf: SPF                          //sharedpreferences 객체
    private var inventory_items = ArrayList<SearchData>()  //SPF에서 get한 데이터(String)을 넣어주는 리스트
    private var filter_items =
        ArrayList<SearchData>()      // get한 데이터중에서 isLike = true인 항목만 보여주기위해서 넣어주는 리스트
    private val favoreitesList =
        arrayListOf<SearchData>()  // Bundle()을이용해서 업데이트된데이터를 SearchFragment로 보내주기 위한 데이터리스트

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        spf = SPF(requireContext())

        return binding.root
    }

    override fun onResume() {   //onCreateView는 처음 프래그먼트 생성시 한번만 생성되므로 호출이 되지않아 처음에만 갱신되기때문에 앱이 종료되기 전 계속 갱신하기위해서 onResume을 이용했습니다.
        super.onResume()

        UpdateItems()   // SearchView에서 보관함에 저장한 데이터를 불러온 후 화면에 표시해주고, heart버튼을 이용해서 데이터를 추가&제거 후 Bundle()을 이용해서 다시 SearchView로 넘겨주는 함수
    }

    override fun onCreate(savedInstanceState: Bundle?) {        //혹시 프래그먼트가 무한 재생성되는지 확인
        super.onCreate(savedInstanceState)
        Log.d("ImageSearchs", "Fragment_onCreate")
    }

    override fun onDestroyView() {                                  //혹시 프래그먼트가 무한 재생성되는데, 기존의 프래그먼트가 파괴되는지 확인
        super.onDestroyView()
        Log.d("ImageSearchs", "Fragment_onDestroyView")
    }

    override fun onDestroy() {                                  //혹시 프래그먼트가 무한 재생성되는데, 기존의 프래그먼트가 파괴되는지 확인
        super.onDestroy()
        Log.d("ImageSearchs", "Fragment_onDestroy")
    }

    private fun UpdateItems() {
        Log.d("ImageSearchs", "호출되냐?")
        val items = spf.getData()                               // sharedpreference를 이용해서 데이터를 얻어옴
        Log.d("ImageSearchs", "검색결과: ${items}")
        inventory_items =
            Gson().fromJson(items, object :                // 얻어온데이터는 String형식이므로 ArrayList로 변환
                TypeToken<ArrayList<SearchData>>() {}.type)
        filter_items = ArrayList(inventory_items.filter { it.isLike == true })  // 불러온 데이터중에서 isLike가 true인 애들만 출력

        binding.inventoryRecyclerView.apply {
            adapter = InventoryFragmentAdapter(filter_items, requireContext()).apply {  //어댑터에 필터된데이터와 context를 넣어준다. context는 glide를 쓰기위해서 같이 넣어준다.
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)

                itemClick = object : InventoryFragmentAdapter.ItemClick {       //아이템을 추가 제거하는 클릭 이벤트
                    override fun onFavoritesClick(view: View, position: Int) {
                        if (position in 0..filter_items.size) {
                            val item = filter_items[position]
                            item.isLike = !item.isLike
                            context.shortToast("보관함에서 제거 되었습니다.")   //확장함수를 이용한 Toast메시지
                            favoreitesList.clear()                          //SearchFragment에서 SPF에 저장된데이터를 위에서 계속 불러오므로 갱신된 데이터를 보내주기위해 새로운 리스트를 만듬
                            favoreitesList.add(item)                        //갱신된 item을 그 리스트에 넣어줌
                            filter_items.removeAt(position)                 //화면에서 heart버튼을 이용해서 해당 항목 제거 후 앱에 표시
                            notifyItemRemoved(position)

                            val bundle = Bundle()                       //bundle을 이용해서 SearchFragment로 데이터를 넘겨줌
                            bundle.putParcelableArrayList("filteritems", favoreitesList)
                            setFragmentResult("filteritemsKey", bundle)

                        }
                    }
                }
            }
        }
    }
}
