package com.red.rickandmorty.viewmodel

import android.app.Activity
import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.print.PrintHelper
import com.red.rickandmorty.R
import com.red.rickandmorty.databinding.ItemPrintBinding
import com.red.rickandmorty.view.util.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.OutputStream
import javax.inject.Inject

const val IMAGES_FOLDER_NAME = "R&M"

@HiltViewModel
class CharacterPrintViewModel @Inject constructor(
    val app: Application,
) : ViewModel() {

    fun saveCard(preview: ItemPrintBinding?, name: String, onComplete: () -> Unit) {
        loadBitmap(preview) { bitmap ->
            saveImage(bitmap,
                resources.getString(R.string.app_name) + " - $name character data").let {
                if (it) onComplete()
                else toast("can't save image")
            }
        }
    }

    fun sendToPrint(
        preview: ItemPrintBinding?,
        activity: Activity,
        name: String,
        onComplete: () -> Unit,
    ) {
        loadBitmap(preview) { bitmap ->
            PrintHelper(activity).apply {
                scaleMode = PrintHelper.SCALE_MODE_FIT
            }.also { printHelper ->
                printHelper.printBitmap(
                    resources.getString(R.string.app_name) +
                            " - $name character data", bitmap)
                onComplete()
            }
        }
    }

    private fun loadBitmap(itemPrintBinding: ItemPrintBinding?, block: Context.(Bitmap) -> Unit) {
        if (itemPrintBinding == null) {
            app.applicationContext.toast("Preview no available")
            return
        }
        val bitmap = loadBitmapFromView(itemPrintBinding.root)
        if (bitmap != null) {
            with(app.applicationContext) {
                block(this, bitmap)
            }
        }
    }

    private fun loadBitmapFromView(view: View): Bitmap? = try {
        val bitmap: Bitmap = Bitmap.createBitmap(view.width,
            view.height,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    private fun Context.saveImage(bitmap: Bitmap, name: String): Boolean {
        val saved: Boolean
        val fos: OutputStream

        val resolver: ContentResolver = contentResolver

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/$IMAGES_FOLDER_NAME")
        }


        val imageUri =
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        fos = imageUri?.let { resolver.openOutputStream(it) } ?: return false

        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()

        if (saved) toast("Saved to Photos")
        return saved
    }

}

