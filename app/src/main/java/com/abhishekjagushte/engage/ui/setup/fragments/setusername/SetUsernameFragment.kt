package com.abhishekjagushte.engage.ui.setup.fragments.setusername

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.abhishekjagushte.engage.EngageApplication
import com.abhishekjagushte.engage.R
import com.abhishekjagushte.engage.utils.Constants
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class SetUsernameFragment: Fragment() {

    private val TAG: String = "SetUsernameFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SetUsernameViewModel> { viewModelFactory }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.set_username_fragment, container, false)

        val nameInput = view.findViewById<TextInputEditText>(R.id.name_input)
        val usernameInput = view.findViewById<TextInputEditText>(R.id.username_input)
        val confirmButton = view.findViewById<Button>(R.id.confirm_button)
        val noteText = view.findViewById<TextView>(R.id.noteText)

        val args = SetUsernameFragmentArgs.fromBundle(requireArguments())
        viewModel.email = args.email
        viewModel.password = args.password

        //check username to be unique from firebase
        usernameInput.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, s.toString())
                viewModel.checkUsername(s.toString())
            }

        })

        viewModel.noteText.observe( viewLifecycleOwner, Observer {
            noteText.setText(it?: "")
            Log.d(TAG,it)
        })

        viewModel.changeCompleteStatus.observe(viewLifecycleOwner, Observer {
            when(it){
                Constants.LOCAL_DB_SUCCESS -> updateUI()
                Constants.FIREBASE_CHANGE_FAILED -> confirmFailed()
                Constants.LOCAL_DB_FAILED -> confirmFailed()
                else -> Log.d(TAG,"Not initiated")
            }
        })

        confirmButton.setOnClickListener {

            val name = nameInput.text.toString()
            val username = usernameInput.text.toString()

            if(checkInputs(name, username)){
                viewModel.confirmSetup(name, username)
            }

        }

        return view
    }

    private fun confirmFailed() {
        Log.d(TAG, "Failed")
    }


    private fun updateUI() {
        Log.d(TAG,"Updated Successfully")
        findNavController().navigate(R.id.action_setUsernameFragment_to_mainActivity2)
    }


    private fun checkInputs(name: String, username: String): Boolean {
        return checkName(name) && viewModel.usernameValid.value?:false
    }

    private fun checkName(name: String): Boolean{
        return name.isNotEmpty()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EngageApplication).appComponent.addSetUsernameComponent()
            .create().inject(this)
    }
}