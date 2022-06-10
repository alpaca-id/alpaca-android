package com.bangkit.alpaca.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.bangkit.alpaca.R
import com.bangkit.alpaca.databinding.ActivityCameraBinding
import com.bangkit.alpaca.ui.processing.ProcessingActivity
import com.bangkit.alpaca.ui.processing.ProcessingActivity.Companion.EXTRA_IMAGE
import com.bangkit.alpaca.utils.MediaUtility.createFile
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CameraActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCameraBinding

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private val requestPermissionLauncher =
        // Handling request permission launcher
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startCamera()
            } else {
                // Show dialog message
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.izin_ditolak))
                    .setMessage(getString(R.string.dialog_no_permission_message))
                    .setPositiveButton(getString(R.string.dialog_positive_button)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()

                showNoPermissionMessage(true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar setup
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.pindai_gambar)
        }

        // OnClickListener
        binding.apply {
            btnGivePermission.setOnClickListener(this@CameraActivity)
            btnCameraShutter.setOnClickListener(this@CameraActivity)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_give_permission -> {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).also { intent ->
                    val uri = Uri.fromParts("package", packageName, null)

                    intent.data = uri
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }

            R.id.btn_camera_shutter -> {
                takePhoto()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkCameraPermission()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    /**
     * Take a photo from the CameraX
     */
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // Redirect to the ProcessingActivity to starting the upload process
                    Intent(this@CameraActivity, ProcessingActivity::class.java).also { intent ->
                        intent.putExtra(EXTRA_IMAGE, photoFile)
                        startActivity(intent)
                    }
                    finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        getString(R.string.camera_capture_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    /**
     * Change and set the status bar color
     *
     * @param isDark Status bar's theme
     */
    private fun changeStatusBarTheme(isDark: Boolean) {
        // Status bar color
        window.statusBarColor =
            ContextCompat.getColor(
                this,
                if (isDark) R.color.black
                else R.color.white_warm
            )

        // Status bar text color
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).also { windowInsetsControllerCompat ->
            windowInsetsControllerCompat.isAppearanceLightStatusBars = !isDark
        }
    }

    /**
     * Starting the CameraX interface
     */
    private fun startCamera() {
        // Initial Setting for the UI
        showNoPermissionMessage(false)

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraViewfinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()

                val camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

                // Setting camera's flash
                binding.btnToggleFlash.setOnCheckedChangeListener { _, checked ->
                    setCameraFlash(camera, checked)
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    getString(R.string.camera_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    /**
     * Turn On/Off camera flash
     *
     * @param camera Camera
     * @param isActive Flash state
     */
    private fun setCameraFlash(camera: Camera, isActive: Boolean) {
        if (camera.cameraInfo.hasFlashUnit()) {
            camera.cameraControl.enableTorch(isActive)
        }
    }

    /**
     * Show the explanation to the user why the camera feature is unavailable
     *
     * @param isVisible Message visibility
     */
    private fun showNoPermissionMessage(isVisible: Boolean) {
        if (isVisible) {
            binding.apply {
                cameraViewfinder.visibility = View.GONE
                btnCameraShutter.visibility = View.GONE
                btnToggleFlash.visibility = View.GONE
                cameraNoPermissionMsg.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                cameraViewfinder.visibility = View.VISIBLE
                btnCameraShutter.visibility = View.VISIBLE
                btnToggleFlash.visibility = View.VISIBLE
                cameraNoPermissionMsg.visibility = View.GONE
                toolbar.visibility = View.GONE
            }
        }

        changeStatusBarTheme(!isVisible)
    }

    /**
     * Handle the camera permission
     */
    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                startCamera()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                showNoPermissionMessage(true)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}