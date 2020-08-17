package com.dave45.net.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    // region Properties

    var times = 0

    //endregion Properties

    // region Setup methods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val zipcodeEditText: EditText = findViewById(R.id.zipcodeEditText)
        val enterButton: Button = findViewById(R.id.enterButton)
        val locationIcon: ImageView = findViewById(R.id.locationIcon)
        val titleTextView: TextView = findViewById(R.id.titleTextView)


        enterButton.setOnClickListener {
            val zipCode = zipcodeEditText.text.toString()
            if(zipCode.length != 5)
                Toast.makeText(this, R.string.incorrectZipCodeValueError, Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Zip code : \"$zipCode\"", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    // endregion Setup methods


    // region Teardown methods

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    // endregion Teardown methods

}