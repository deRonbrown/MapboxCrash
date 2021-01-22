package com.xspotlivin.mapboxcrash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.SharedElementCallback
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.xspotlivin.mapboxcrash.databinding.FragmentRecyclerViewBinding

class RecyclerViewFragment : Fragment(R.layout.fragment_recycler_view) {

    private var binding: FragmentRecyclerViewBinding? = null
    private val locationData: List<String> by lazy {
        (1..50).map { "Location $it" }
    }

    private var clickedPosition = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRecyclerViewBinding.bind(view).apply {
            recyclerView.adapter = LocationAdapter(locationData) { clickedView, position ->
                clickedPosition = position

                exitTransition = Hold().apply {
                    excludeTarget(clickedView, true)
                }

                setExitSharedElementCallback(object : SharedElementCallback() {
                    override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
                        val startView = binding?.recyclerView?.findViewHolderForAdapterPosition(clickedPosition)?.itemView ?: return
                        if (names.isNotEmpty()) {
                            sharedElements[names[0]] = startView
                        }
                    }
                })

                val args = Bundle().apply { putString("sharedElementTransitionName", clickedView.transitionName) }
                val extras = FragmentNavigatorExtras(clickedView to clickedView.transitionName)

                android.util.Log.d("ItemClick", "clicked $position")
                findNavController().navigate(R.id.action_nav_to_map, args, null, extras)
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    class LocationAdapter(
        private val data: List<String>,
        private val onItemClick: (View, Int) -> Unit
    ) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_location, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.transitionName = "transition_$position"
            holder.itemView.setOnClickListener { onItemClick(holder.itemView, position) }
            holder.textView.text = data[position]
        }

        override fun getItemCount() = data.size

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.textView)
        }
    }
}
