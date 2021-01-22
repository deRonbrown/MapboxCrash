package com.xspotlivin.mapboxcrash

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

class MapboxCrashApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(this, "TOKEN_GOES_HERE")
    }
}
