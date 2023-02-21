package com.example.lifestyleapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val b: Button? = findViewById(R.id.button)
        if (b != null) {
            b.setOnClickListener(this)
        }
        val camButton: Button? = findViewById(R.id.cameraButton)
        if(camButton != null){
            camButton.setOnClickListener(this)
        }

    }

    override fun onClick(view: View?) {
        if (view != null) {
            if (view.getId().equals(R.id.button)) {
                val firstNameInput: String = findViewById<EditText>(R.id.firstname).text.toString()
                val lastNameInput: String = findViewById<EditText>(R.id.lastname).text.toString()
                //Replace the fragment container
                val fTrans: FragmentTransaction = supportFragmentManager.beginTransaction()
                fTrans.replace(
                    R.id.fl_frag_container,
                    ProfileFragment.newInstance(
                        firstNameInput,
                        lastNameInput
                    ),
                    "profile_frag",
                )
                fTrans.commit()
                val ll: LinearLayout = findViewById(R.id.linearLayout2)
                ll.removeAllViews()
            }
            else if (view.getId().equals(R.id.cameraButton)) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    cameraActivity.launch(cameraIntent)
                } catch (ex: ActivityNotFoundException) {
                    //Do error handling here
                }
            }
        }
    }

    private val cameraActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val im: ImageView = findViewById<View>(R.id.imageView) as ImageView

                if (Build.VERSION.SDK_INT >= 33) {
                    val thumbnailImage =
                        result.data!!.getParcelableExtra("data", Bitmap::class.java)
                    im.setImageBitmap(thumbnailImage)
                } else {
                    val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                    im.setImageBitmap(thumbnailImage)
                }


            }
        }
}