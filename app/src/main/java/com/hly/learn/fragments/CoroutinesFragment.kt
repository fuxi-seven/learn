package com.hly.learn.fragments

import SampleViewModel
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hly.coroutines_retrofit_okhttp.adapter.SampleAdapter
import com.hly.learn.R

class CoroutinesFragment : BaseFragment() {

    private lateinit var mModel: SampleViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initData(view: View) {
        mModel = ViewModelProviders.of(this)[SampleViewModel::class.java]
        mModel.data.observe(this, Observer {
            val mainAdapter = this.context?.let { it1 -> SampleAdapter(it1, it) }
            val linearLayoutManager = LinearLayoutManager(this.context)
            val rv = view.findViewById<RecyclerView>(R.id.rv)
            rv.layoutManager = linearLayoutManager
            rv.adapter = mainAdapter
        })
        mModel.getDataFromServer()
    }
}