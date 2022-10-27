package com.example.qr_code_gen_scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var im: ImageView? = null
    private var bGenerate: Button? = null
    private var bScanner: Button? = null
    private var tvText: TextView? = null

    private var launcher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val text = result.data?.getStringExtra("scanResult")
                Log.d("MyLog", "Result: $text")
                tvText?.text = text
            }
        }
        im = findViewById(R.id.imageView)
        bGenerate = findViewById(R.id.button)
        bScanner = findViewById(R.id.bScan)
        tvText = findViewById(R.id.tvText)
        tvText?.onFocusChangeListener {}
        bGenerate?.setOnClickListener {
            generateQRCode(tvText?.text.toString())
        }
        bScanner?.setOnClickListener {
            // startActivity(Intent(this, ScannerActivity::class.java))
            checkCameraPermission()
        }

    }

    private fun generateQRCode(text: String) {
        val qrGenerator = QRGEncoder(text, null, QRGContents.Type.TEXT, 600)
        try {
            val bMap = qrGenerator.getBitmap()
            im?.setImageBitmap(bMap)
        } catch (e: Exception) {
        }
    }

    private fun checkCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 12)
        } else {
            // startActivity(Intent(this, ScannerActivity::class.java))
            launcher?.launch(Intent(this, ScannerActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 12){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //startActivity(Intent(this, ScannerActivity::class.java))
                launcher?.launch(Intent(this, ScannerActivity::class.java))
            }
        }
    }
}

private fun TextView.onFocusChangeListener(value: () -> Unit) {

}

}
