package com.stas1270.githubapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {


    companion object {
        // Unique tag for the intent reply
        val TEXT_REQUEST = 1
        // Unique tag required for the intent extra
        val EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE"
    }


    // Class name for Log tag
    private val LOG_TAG: String = FirstActivity::class.java.getSimpleName()

    // EditText view for the message
    private var mMessageEditText: EditText? = null

    // TextView for the reply header
    private var mReplyHeadTextView: TextView? = null

    // TextView for the reply body
    private var mReplyTextView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        // Initialize all the view variables.

        // Initialize all the view variables.
        mMessageEditText = findViewById(R.id.editText_main)
        mReplyHeadTextView = findViewById(R.id.text_header_reply)
        mReplyTextView = findViewById(R.id.text_message_reply)
    }

    fun launchSecondActivity(view: View?) {
        Log.d(LOG_TAG, "Button clicked!")
        val intent = Intent(this, SecondActivity::class.java)
        val message = mMessageEditText!!.text.toString()
        intent.putExtra(EXTRA_MESSAGE, message)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    /**
     * Handles the data in the return intent from SecondActivity.
     *
     * @param requestCode Code for the SecondActivity request.
     * @param resultCode Code that comes back from SecondActivity.
     * @param data Intent data sent back from SecondActivity.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Test for the right intent reply.
        if (requestCode == TEXT_REQUEST) {
            // Test to make sure the intent reply result was good.
            if (resultCode == RESULT_OK) {
                val reply = data!!.getStringExtra(SecondActivity.EXTRA_REPLY)

                // Make the reply head visible.
                mReplyHeadTextView!!.visibility = View.VISIBLE

                // Set the reply and make it visible.
                mReplyTextView!!.text = reply
                mReplyTextView!!.visibility = View.VISIBLE
            }
        }
    }
}