package com.example.qr_code_gen_scan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import java.nio.channels.WritePendingException

class MainActivity : AppCompatActivity() {
    var im: ImageView? = null
    var bGenerate: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        im = findViewById(R.id.imageView)
        bGenerate = findViewById(R.id.button)
        bGenerate?.setOnClickListener {
            GenerateQRCode("Туйа, мин эйигин таптыыбын")
        }
    }

    private fun GenerateQRCode(text: String) {
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 600)
        try {
            val bMap = qrGenerator.getBitmap()
            im?.setImageBitmap(bMap)
        } catch (e: Exception) {

        }

    }
}