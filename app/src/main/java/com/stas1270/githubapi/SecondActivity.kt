package com.stas1270.githubapi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    // Class name for Log tag.
    private val LOG_TAG = SecondActivity::class.java.simpleName

    companion object {

        val EXTRA_REPLY = "com.example.android.twoactivities.extra.REPLY"
    }

    // EditText for the reply.
    private var mReply: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        // Initialize view variables.

        // Initialize view variables.
        mReply = findViewById(R.id.editText_second)

        // Get the intent that launched this activity, and the message in
        // the intent extra.

        // Get the intent that launched this activity, and the message in
        // the intent extra.
        val intent = intent
        val message = intent.getStringExtra(FirstActivity.EXTRA_MESSAGE)

        // Put that message into the text_message TextView.

        // Put that message into the text_message TextView.
        val textView: TextView = findViewById(R.id.text_message)
        textView.text = message
    }

    fun returnReply(view: View?) {
        // Get the reply message from the edit text.
        val reply = mReply!!.text.toString()

        // Create a new intent for the reply, add the reply message to it
        // as an extra, set the intent result, and close the activity.
        val replyIntent = Intent()
        replyIntent.putExtra(EXTRA_REPLY, reply)
        setResult(RESULT_OK, replyIntent)
        finish()
    }
}