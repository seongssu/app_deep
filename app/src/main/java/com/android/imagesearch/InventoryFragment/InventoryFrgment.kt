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
    private lateinit var spf : SPF
    private var inventory_items = ArrayList<SearchData>()  //SPF에서 get한 데이터(String)을 넣어주는 리스트
    private var filter_items = ArrayList<SearchData>()
    private val favoreitesList = arrayListOf<SearchData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        spf = SPF(requireContext())

        return binding.root
    }
    override fun onResume() {
        super.onResume()

        UpdateItems()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ImageSearchs", "Fragment_onCreate")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ImageSearchs","Fragment_onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ImageSearchs","Fragment_onDestroy")
    }

    private fun UpdateItems() {
        Log.d("ImageSearchs","호출되냐?")
        val items = spf.getData()
        Log.d("ImageSearchs","검색결과: ${items}")
        inventory_items = Gson().fromJson(items, object:
            TypeToken<ArrayList<SearchData>>(){}.type)
        filter_items = ArrayList(inventory_items.filter { it.isLike == true })
        binding.inventoryRecyclerView.apply {
            adapter = InventoryFragmentAdapter(filter_items,requireContext()).apply {
                layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)

                itemClick = object :InventoryFragmentAdapter.ItemClick{
                    override fun onFavoritesClick(view: View, position: Int) {
                        if(position in 0..filter_items.size){
                            val item = filter_items[position]
                            item.isLike = !item.isLike
                            context.shortToast("보관함에서 제거 되었습니다.")
                            favoreitesList.clear()
                            favoreitesList.add(item)
                            filter_items.removeAt(position)
                            notifyItemRemoved(position)

                            val bundle = Bundle()
                            bundle.putParcelableArrayList("filteritems",favoreitesList)
                            setFragmentResult("filteritemsKey", bundle)

                        }
                    }
                }
            }
        }
    }
}