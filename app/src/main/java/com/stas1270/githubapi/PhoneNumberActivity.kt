package com.stas1270.githubapi

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PhoneNumberActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    companion object {
        private val TAG: String = PhoneNumberActivity::class.java.getSimpleName()
    }
    private var mSpinnerLabel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)


        // Create the spinner.

        // Create the spinner.
        val spinner: Spinner = findViewById(R.id.label_spinner)
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this)
        }

        // Create ArrayAdapter using the string array and default
        // spinner layout.

        // Create ArrayAdapter using the string array and default
        // spinner layout.
        val adapter = ArrayAdapter.createFromResource(
            this, R.array.labels_array,
            android.R.layout.simple_spinner_item
        )
        // Specify the layout to use when the list of choices appears.
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner.
        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.adapter = adapter
        }
    }

    /**
     * Retrieves the text and spinner item and shows them in text_phonelabel.
     *
     * @param view  The view containing editText_main.
     */
    fun showText(view: View?) {
        val editText: EditText = findViewById(R.id.editText_main)
        val textView: TextView = findViewById(R.id.text_phonelabel)
        if (editText != null) {
            // Assign to showString both the entered string and mSpinnerLabel.
            val showString = editText.text.toString() +
                    " - " + mSpinnerLabel
            // Display a Toast message with showString
            Toast.makeText(this, showString, Toast.LENGTH_SHORT).show()
            // Set the TextView to showString.
            textView.text = showString
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        mSpinnerLabel = p0!!.getItemAtPosition(p2).toString()
        showText(p1)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
//        TODO("Not yet implemented")
    }
}