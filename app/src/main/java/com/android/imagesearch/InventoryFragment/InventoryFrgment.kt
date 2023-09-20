package com.android.imagesearch.InventoryFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.imagesearch.R
import com.android.imagesearch.api.Document
import com.android.imagesearch.api.Kakao
import com.android.imagesearch.databinding.FragmentInventoryFrgmentBinding
import com.android.imagesearch.sharedPreferences.SPF
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class InventoryFrgment : Fragment() {
    private val binding by lazy { FragmentInventoryFrgmentBinding.inflate(layoutInflater) }
    private lateinit var spf : SPF
    private var inventory_items = ArrayList<Document>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        spf = SPF(requireContext())

        val items = spf.getData()
        inventory_items = Gson().fromJson(items, object:
            TypeToken<ArrayList<Document>>(){}.type)

        val filter_items = ArrayList(inventory_items.filter { it.isLike == true })

        binding.inventoryRecyclerView.apply {
            adapter = InventoryFragmentAdapter(filter_items,requireContext()).apply {
                layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                setHasFixedSize(true)
            }
        }

        return binding.root
    }

    private fun UpdateItems(items : ArrayList<Document>) {

    }
}