package com.cargamos.cargamosshopper.views.main.picking.pool


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.cargamos.cargamosshopper.R
import com.cargamos.cargamosshopper.adapter.picking.PickingPoolRecyclerViewAdapter
import com.cargamos.cargamosshopper.models.Picking
import com.cargamos.cargamosshopper.views.main.MainActivity
import com.cargamos.cargamosshopper.views.main.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_picking_pool.*

class PickingPoolFragment : BaseFragment<PickingPoolFragmentViewModel>(PickingPoolFragmentViewModel::class),
    SearchView.OnQueryTextListener {
    lateinit var activity: MainActivity
    lateinit var adapter: PickingPoolRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picking_pool, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
    }

    private fun init() {
        adapter = PickingPoolRecyclerViewAdapter(activity, arrayListOf())

        adapter.listener = object : PickingPoolRecyclerViewAdapter.ItemClickListener {
            override fun onConfirmBtnClick(picking: Picking, position: Int) {
                progressBar.visibility = View.VISIBLE
                viewModel.takePicking(picking.id)
            }
        }

        pickingPoolRecyclerView.layoutManager = LinearLayoutManager(activity)
        pickingPoolRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        pickingPoolRecyclerView.adapter = adapter

        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)

        assignAllBtn.setOnClickListener {
            val ids = arrayListOf<String>()

            for (item in adapter.filteredList) {
                ids.add(item.id)
            }

            viewModel.takeAllPickings(ids)
        }
    }

    private fun observe() {
        viewModel.getPickingItems()

        viewModel.poolItems.observe(this,
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
                    progressBar.visibility = View.GONE
                }
            })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return true
    }

}
