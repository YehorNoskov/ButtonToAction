package com.innovecs.test.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import com.innovecs.test.Network.checkConnectivity
import com.innovecs.test.R
import com.innovecs.test.databinding.ActivityMainBinding
import com.innovecs.test.domain.ButtonAction

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ButtonToActionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ButtonToActionViewModel::class.java]

        subscribeForUpdates()
        setNotificationChannel()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.performButton.setOnClickListener {
            if (checkConnectivity(this)) {
                viewModel.performActionClick()
            }
        }
    }

    private fun subscribeForUpdates() {
        viewModel.buttonAction.observe(this) {
            when (it) {
                ButtonAction.ANIMATION -> binding.performButton.animate().rotation(ROTATION_VALUE)
                    .start()
                ButtonAction.TOAST -> checkConnectivityAndShowToast()
                ButtonAction.CALL -> startActivity(Intent(Intent.ACTION_DIAL))
                ButtonAction.NOTIFICATION -> showNotification()
                //todo use Timber instead of Log
                else -> Log.d(
                    MainActivity::class.java.simpleName,
                    "Something went wrong"
                )
            }
        }
    }

    private fun setNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_TITLE)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(this).notify(NOTIFICATION_NOTIFY_ID, builder.build())
    }

    private fun checkConnectivityAndShowToast() {
        if (checkConnectivity(this)) {
            Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val CHANNEL_ID = "channel_id"
        const val CHANNEL_DESCRIPTION = "channel description"
        const val NOTIFICATION_TITLE = "Notification"
        const val NOTIFICATION_NOTIFY_ID = 12345
        const val ROTATION_VALUE = 360f
    }
}