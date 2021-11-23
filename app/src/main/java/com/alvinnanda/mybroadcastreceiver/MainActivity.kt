package com.alvinnanda.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alvinnanda.mybroadcastreceiver.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val SMS_REQUEST_CODE = 101
        const val ACTION_DOWNLOAD_STATUS = "download_status"
    }

    private var binding: ActivityMainBinding? = null
    private lateinit var downloadReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.btnPermission?.setOnClickListener(this)
        binding?.btnDownload?.setOnClickListener(this)

        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d(DownloadService.TAG, "Download Selesai")
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)
    }

    override fun onClick(v: View) {
        when (v.id) {
             R.id.btn_download -> {
                val downloadServiceIntent = Intent(this, DownloadService::class.java)
                startService(downloadServiceIntent)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
        binding = null
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            when {
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    }

}