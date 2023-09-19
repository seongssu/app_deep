package com.android.appdevelop_deep.Miseya

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.android.appdevelop_deep.R
import com.android.appdevelop_deep.databinding.ActivityMiseyaBinding
import com.android.appdevelop_deep.shortToast
import com.skydoves.powerspinner.IconSpinnerAdapter
import kotlinx.coroutines.launch

class MiseyaActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMiseyaBinding.inflate(layoutInflater) }
    var items = mutableListOf<DustItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            spinnerSido.setOnSpinnerItemSelectedListener { oldIndex, oldItem, newIndex,
                                                           newItem: String ->
                communicateNetWork(setUpDustParameter(newItem))
            }
            spinnerGoo.setOnSpinnerItemSelectedListener { oldIndex, oldItem, newIndex,
                                                          newItem: String ->
                var selectedItem = items.filter { it -> it.stationName == newItem }

                binding.apply {
                    if(selectedItem[0].sidoName !=null && selectedItem[0].stationName !=null){cityname.text = selectedItem[0].sidoName + "\t" + selectedItem[0].stationName}
                    else{shortToast("데이터가 없습니다. 다른 지역을 선택해 주세요.")}

                    if(selectedItem[0].dataTime !=null){date.text = selectedItem[0].dataTime}
                    else {shortToast("데이터가 없습니다. 다른 지역을 선택해 주세요.")}

                    if (selectedItem[0].pm10Value == null){
                        value.text = selectedItem[0].pm10Value + " - ㎍/㎥"
                    }else {shortToast("데이터가 없습니다. 다른 지역을 선택해 주세요.")}
                }
                when (getGrade(selectedItem[0].pm10Value)) {
                    1 -> binding.apply {
                        miseyaBackground.setBackgroundColor(Color.parseColor("#9ED2EC"))
                        smile.setImageResource(R.drawable.smile1)
                        grade.text = "좋음"
                    }

                    2 -> binding.apply {
                        miseyaBackground.setBackgroundColor(Color.parseColor("#D6A478"))
                        smile.setImageResource(R.drawable.smile2)
                        grade.text = "보통"
                    }

                    3 -> binding.apply {
                        miseyaBackground.setBackgroundColor(Color.parseColor("#DF7766"))
                        smile.setImageResource(R.drawable.smile3)
                        grade.text = "나쁨"
                    }

                    4 -> binding.apply {
                        miseyaBackground.setBackgroundColor(Color.parseColor("#BB3320"))
                        smile.setImageResource(R.drawable.smile4)
                        grade.text = "매우나쁨"
                    }

                    else -> shortToast("지역을 다시 선택 해주세요.")
                }
            }
        }
    }

    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch {
        val responseData = NetWorkClient.dustNetWork.getDust(param)

        val adapter = IconSpinnerAdapter(binding.spinnerGoo)
        items = responseData.response.dustBody.dustItem!!

        val goo = ArrayList<String>()
        items.forEach {
            if (it.stationName != null){ goo.add(it.stationName) }
            else{shortToast("망할")}}
        runOnUiThread {
            binding.spinnerGoo.setItems(goo)
        }
    }

    private fun setUpDustParameter(sido: String): HashMap<String, String> {
        val authKey =
            "QTMrNjFv7ipAgDfIpMNVMB6XZfsKw/Tk1w55PSe/stVc/dgoQVWRKNQq3ZiMTls6Gu5lmS1WYuEcJfYwQhNDHQ=="

        return hashMapOf(
            "serviceKey" to authKey,
            "returnType" to "json",
            "numOfRows" to "100",
            "pageNo" to "1",
            "sidoName" to sido,
            "ver" to "1.0"
        )
    }

    fun getGrade(value: String): Int {
        val Value = value?.toIntOrNull() ?: 0

        return when (Value) {
            in 0..30 -> 1
            in 31..80 -> 2
            in 81..100 -> 3
            else -> 4
        }
    }
}