package com.abhishekjagushte.engage.ui.main.fragments.people

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.abhishekjagushte.engage.R

class PeopleFragment : Fragment() {

    companion object {
        fun newInstance() = PeopleFragment()
    }

    private lateinit var viewModel: PeopleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.people_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PeopleViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
