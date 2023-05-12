package com.example.phone.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.phone.R
import com.example.phone.databinding.FragmentAddContactBinding
import com.example.phone.model.Contact
import com.example.phone.sql.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [AddContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddContactBinding.inflate(inflater, container, false)

        val sqLiteOpenHelper:DBHelper = DBHelper(requireContext())

        binding.back.setOnClickListener {

        }

        binding.save.setOnClickListener {
            if (binding.nameOrg.text.isNullOrEmpty() || binding.surnameOrg.text.isNullOrEmpty() ||binding.numberOrg.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "fill fields, motherfucker", Toast.LENGTH_SHORT).show()
            }
            else{
                val contact = Contact(name = binding.nameOrg.text.toString(), number = binding.numberOrg.text.toString())

                Log.d("FGH", "onCreateView: "+contact.name+binding.nameOrg.text.toString())

                val status =sqLiteOpenHelper.addContact(contact)
                if (status > -1){
                    Toast.makeText(requireContext(), "new contact added succesfully", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction().replace(R.id.main_activity, ContactsFragment()).commit()
                }
                else{
                    Toast.makeText(requireContext(), "couldn't add new contact failed", Toast.LENGTH_SHORT).show()
                    binding.nameOrg.setText("")
                    binding.surnameOrg.setText("")
                    binding.numberOrg.setText("")
                    binding.nameOrg.requestFocus()
                }
            }
        }



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}