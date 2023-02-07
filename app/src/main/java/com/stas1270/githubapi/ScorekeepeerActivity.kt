package com.stas1270.githubapi

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class ScorekeepeerActivity : AppCompatActivity() {

    // Member variables for holding the score
    private var mScore1 = 0
    private var mScore2 = 0

    // Member variables for the two score TextView elements
    private var mScoreText1: TextView? = null
    private var mScoreText2: TextView? = null

    // Tags to be used as the keys in OnSavedInstanceState
    val STATE_SCORE_1 = "Team 1 Score"
    val STATE_SCORE_2 = "Team 2 Score"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scorekeepeer)

        //Find the TextViews by ID
        mScoreText1 = findViewById(R.id.score_1)
        mScoreText2 = findViewById(R.id.score_2)

        // Restores the scores if there is savedInstanceState.
        if (savedInstanceState != null) {
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1)
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2)

            //Set the score text views
            mScoreText1?.setText(mScore1.toString())
            mScoreText2?.setText(mScore2.toString())
        }
    }

    /**
     * Handles the onClick of both the decrement buttons.
     *
     * @param view The button view that was clicked
     */
    fun decreaseScore(view: View) {
        // Get the ID of the button that was clicked.
        val viewID: Int = view.getId()
        when (viewID) {
            R.id.decreaseTeam1 -> {
                // Decrement the score and update the TextView.
                mScore1--
                mScoreText1!!.text = mScore1.toString()
            }
            R.id.decreaseTeam2 -> {
                // Decrement the score and update the TextView.
                mScore2--
                mScoreText2!!.text = mScore2.toString()
            }
        }
    }

    /**
     * Handles the onClick of both the increment buttons.
     *
     * @param view The button view that was clicked
     */
    fun increaseScore(view: View) {
        // Get the ID of the button that was clicked.
        val viewID: Int = view.getId()
        when (viewID) {
            R.id.increaseTeam1 -> {
                // Increment the score and update the TextView.
                mScore1++
                mScoreText1!!.text = mScore1.toString()
            }
            R.id.increaseTeam2 -> {
                // Increment the score and update the TextView.
                mScore2++
                mScoreText2!!.text = mScore2.toString()
            }
        }
    }

    /**
     * Creates the night mode menu option.
     *
     * @param menu The menu in the action bar
     * @return True to display the menu, false to hide it
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        // Change the label of the menu based on the state of the app.
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode)
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode)
        }
        return true
    }


    /**
     * Handles options menu item clicks.
     *
     * @param item The item that was pressed
     * @return returns true since the item click wa handled
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Check if the correct item was clicked.
        if (item.getItemId() === R.id.night_mode) {
            // Get the night mode state of the app.
            val nightMode = AppCompatDelegate.getDefaultNightMode()
            // Set the theme mode for the restarted activity.
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            // Recreate the activity for the theme change to take effect.
            recreate()
        }
        return true
    }

    /**
     * Method that is called when the configuration changes,
     * used to preserve the state of the app.
     *
     * @param outState The bundle that will be passed in to the Activity when it is restored.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        // Save the scores.
        outState.putInt(STATE_SCORE_1, mScore1)
        outState.putInt(STATE_SCORE_2, mScore2)
        super.onSaveInstanceState(outState)
    }

}