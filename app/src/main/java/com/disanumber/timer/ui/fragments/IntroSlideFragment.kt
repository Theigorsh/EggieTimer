package com.disanumber.timer.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.disanumber.timer.R
import com.disanumber.timer.util.Constants


class IntroSlideFragment : Fragment() {


    private var type: Int? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View? = null

        type = this.arguments?.getInt(Constants.ARG_FRAGMENT)
        when (type) {
            1 -> rootView = inflater.inflate(R.layout.fragment_intro, container, false)
            2 -> rootView = inflater.inflate(R.layout.fragment_intro_two, container, false)
            3 -> rootView = inflater.inflate(R.layout.fragment_intro_three, container, false)



        }

        return rootView
    }


    companion object {
        fun newInstance(type: Int): IntroSlideFragment {
            val fragment = IntroSlideFragment()
            val args = Bundle()
            args.putInt(Constants.ARG_FRAGMENT, type)
            fragment.arguments = args
            return fragment

        }
    }
}