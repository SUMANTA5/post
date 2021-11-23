package com.sumanta.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumanta.post.adapter.PhoneAdapter
import com.sumanta.post.databinding.ActivityMainBinding
import com.sumanta.post.ui.MainViewModel
import com.sumanta.post.util.ApiState
import com.sumanta.post.util.showMsg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var phoneAdapter: PhoneAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()

        getPhone()

        setPhone()
    }

    private fun setPhone() {
        binding.apply {
            save.setOnClickListener {
                if (!TextUtils.isEmpty(name.text.toString()) && !TextUtils.isEmpty(phoneNo.text.toString())) {
                    lifecycleScope.launchWhenStarted {
                        mainViewModel.setPhone(
                            name.text.toString(),
                            phoneNo.text.toString().toLong()
                        )
                            .catch { e ->
                                Log.d("main", "${e.message}")
                            }.collect {
                                Log.d("main", "$it")
                                showMsg("data Loader successfully...")
                                getPhone()
                            }
                    }
                } else {
                    showMsg("Please fill all fields.....")
                }
            }
        }
    }

    private fun getPhone() {
        mainViewModel.getPhone()
        lifecycleScope.launchWhenCreated {
            binding.apply {
                mainViewModel.apiState.collect {
                    when (it) {
                        is ApiState.Success -> {
                            recyclerview.isVisible = true
                            progressBar.isVisible = false

                            phoneAdapter.submitList(it.data)
                        }
                        is ApiState.Failure -> {
                            recyclerview.isVisible = false
                            progressBar.isVisible = false
                            Log.d("main", "${it.msg}")
                        }
                        is ApiState.Loading -> {
                            recyclerview.isVisible = false
                            progressBar.isVisible = true
                        }
                        is ApiState.Empty -> {

                        }
                    }
                }
            }
        }

    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = phoneAdapter
            }
        }
    }
}