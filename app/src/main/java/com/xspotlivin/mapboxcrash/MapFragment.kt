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
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.scalebar.scalebar
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
            replace(R.id.mapContainer, MapboxFragment.newInstance().apply {
                getMapAsync { map ->
                    with(getMapView()) {
                        compass.enabled = false
                        attribution.enabled = false
                        scalebar.enabled = false

                        // Why can't I just disable all gestures...
                        gestures.scrollEnabled = false
                        gestures.rotateEnabled = false
                        gestures.pitchEnabled = false
                        gestures.doubleTapToZoomInEnabled = false
                        gestures.doubleTouchToZoomOutEnabled = false
                        gestures.pinchToZoomEnabled = false
                        gestures.quickZoomEnabled = false
                    }
                    map.loadStyleUri(Style.MAPBOX_STREETS)
                }
            })
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
