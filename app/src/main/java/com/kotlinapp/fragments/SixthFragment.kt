package com.kotlinapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.kotlinapp.R

class SixthFragment : Fragment() {

    lateinit var textView: TextView
    var inputText: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View =
            inflater.inflate(R.layout.first_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.first_fragment_text)

        inputText = arguments?.getString("TITLE")
        System.out.println("Sixth"+inputText)

        textView.text = "Sixth"
        /*if (Message.equals("CHOOSE", ignoreCase = true)) {
            pre_post_layout.setVisibility(View.INVISIBLE)
        }*/
    }
}