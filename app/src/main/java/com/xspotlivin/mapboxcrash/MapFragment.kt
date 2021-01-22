package com.xspotlivin.mapboxcrash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.commit
import com.google.android.material.transition.MaterialContainerTransform
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.maps.SupportMapFragment
import com.xspotlivin.mapboxcrash.databinding.FragmentMapBinding

class MapFragment : Fragment(R.layout.fragment_map) {

    private var binding: FragmentMapBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)?.also {
            binding = FragmentMapBinding.bind(it)
        }

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = ResourcesCompat.getColor(resources, android.R.color.transparent, null)
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }

        binding?.scrollViewRoot?.transitionName = arguments?.getString("sharedElementTransitionName")

        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(names: MutableList<String>, sharedElements: MutableMap<String, View>) {
                if (names.isNotEmpty()) {
                    binding?.scrollViewRoot?.let { sharedElements[names[0]] = it }
                }
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.commit {
            replace(R.id.mapContainer, SupportMapFragment.newInstance().apply {
                getMapAsync { map ->
                    with(map.uiSettings) {
                        setAllGesturesEnabled(false)
                        isCompassEnabled = false
                        isAttributionEnabled = false
                    }
                    map.setStyle(Style.MAPBOX_STREETS)
                }
            })
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
