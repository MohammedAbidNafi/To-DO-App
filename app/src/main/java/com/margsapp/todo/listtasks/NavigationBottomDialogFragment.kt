package com.margsapp.todo.listtasks

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.margsapp.todo.MainActivity
import com.margsapp.todo.R
import com.margsapp.todo.settings.SettingsActivity
import kotlinx.android.synthetic.main.settings_activity.*

class NavigationBottomDialogFragment: BottomSheetDialogFragment() {

    private lateinit var settingsButton: TextView
    private lateinit var privacyButton: TextView
    private lateinit var bugsButton: TextView
    private lateinit var translateButton: TextView

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dialog, container, false)

        settingsButton = view.findViewById(R.id.settings_button)
        privacyButton = view.findViewById(R.id.privacy_button)
        bugsButton = view.findViewById(R.id.bugs_button)
      //  translateButton = view.findViewById(R.id.translate_button)


      settingsButton.setOnClickListener {
          val intent = Intent(activity, SettingsActivity::class.java)
          intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
          val pairs = listOf<Pair<View, String>>(
              Pair(settings_button, "text")
          )
          val options = ActivityOptions.makeSceneTransitionAnimation(activity, pairs[0])
          startActivity(intent, options.toBundle())

      }



        privacyButton.setOnClickListener{
            toURL("https://margsglobal.weebly.com/to-do-list.html")
        }

        bugsButton.setOnClickListener {
            val emails = arrayOf<String>("margsglobal@gmail.com")
            val subject = "Bug Report For TO-DO App"
            composeEmail(emails, subject)

        }

    //    translateButton.setOnClickListener {
    //        toURL("https://github.com/peterdpong/checked-android/wiki/Translating")
    //    }


        return view
    }

    private fun toURL(url: String){
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun composeEmail(address: Array<String>, subject: String){
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, address)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }

    }
}