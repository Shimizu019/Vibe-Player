package com.example.vibesplayer.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vibesplayer.ui.theme.VibesTheme

/**
 * Debug-only entry point for the components gallery (spec section 19.1: "Add a
 * debug-only components gallery"). It lives in src/debug, so release builds do not
 * contain this class. Open it from the launcher entry "Components gallery" or run:
 * adb shell am start -n com.example.vibesplayer/.gallery.ComponentsGalleryActivity
 */
class ComponentsGalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VibesTheme {
                ComponentsGalleryScreen()
            }
        }
    }
}
