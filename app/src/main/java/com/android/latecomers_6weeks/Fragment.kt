package com.android.latecomers_6weeks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.latecomers_6weeks.databinding.FragmentBinding

class Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("6weeks!", "Fragment_onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("6weeks!", "Fragment_onCreateView")
        val binding by lazy{FragmentBinding.inflate(layoutInflater)}
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("6weeks!", "Fragment_onViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("6weeks!", "Fragment_onViewStateRestored")
    }

    override fun onStart() {
        super.onStart()
        Log.d("6weeks!","Fragment_onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("6weeks!","Fragment_onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("6weeks!","Fragment_onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("6weeks!","Fragment_onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("6weeks!","Fragment_onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("6weeks!","Fragment_onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("6weeks!","Fragment_onDestroy")
    }
}