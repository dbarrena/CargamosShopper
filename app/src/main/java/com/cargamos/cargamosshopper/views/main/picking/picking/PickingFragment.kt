package com.cargamos.cargamosshopper.views.main.picking.picking


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.adapter.picking.PickingRecyclerViewAdapter
import com.cargamos.cargamosshopper.models.Picking
import com.cargamos.cargamosshopper.views.main.MainActivity
import com.cargamos.cargamosshopper.views.main.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_picking.*

class PickingFragment : BaseFragment<PickingFragmentViewModel>(PickingFragmentViewModel::class) {
    lateinit var activity: MainActivity
    lateinit var adapter: PickingRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
    }

    private fun init() {
        adapter = PickingRecyclerViewAdapter(activity, arrayListOf())

        adapter.listener = object : PickingRecyclerViewAdapter.ItemClickListener {
            override fun onConfirmBtnClick(picking: Picking, position: Int, decrement: Int) {
                progressBar.visibility = View.VISIBLE
                viewModel.pickItem(picking, decrement)
            }
        }

        pickingRecyclerView.layoutManager = LinearLayoutManager(activity)
        pickingRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        pickingRecyclerView.adapter = adapter


    }

    private fun observe() {
        viewModel.observePickingItems()

        viewModel.pickingItems.observe(this,
            Observer {
                it?.let {
                    val list = arrayListOf<Picking>()

                    for (item in it) {
                        if (item.count != 0L) {
                            list.add(item)
                        }
                    }

                    adapter.addAllItems(list)
                }
            })

        viewModel.batchCommitReady.observe(this,
            Observer {
                it?.let {
                    if (it) {
                        progressBar.visibility = View.GONE
                    }
                }
            })
    }

}
