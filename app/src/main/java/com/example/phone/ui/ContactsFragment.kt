package com.example.phone.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phone.R
import com.example.phone.adapter.ContactsAdapter
import com.example.phone.databinding.FragmentContactsBinding
import com.example.phone.model.Contact
import com.example.phone.sql.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsFragment : Fragment() {
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
        val binding = FragmentContactsBinding.inflate(inflater, container, false)

        val sqLiteOpenHelper = DBHelper(requireContext())
//
        var  list = sqLiteOpenHelper.contacts()

        val adapter = ContactsAdapter(list)
        binding.contactsRecycle.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.contactsRecycle.layoutManager = layoutManager

        if (list.isEmpty()){
            binding.box.visibility = View.VISIBLE
        }
        else{
            binding.contactsRecycle.adapter = adapter
            binding.box.visibility = View.GONE
        }

        binding.addContact.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.main_activity,
                AddContactFragment()
            ).commit()


            val phone_intent = Intent(Intent.ACTION_CALL)


            // Set data of Intent through Uri by parsing phone number
            phone_intent.data = Uri.parse("tel:${list[list.size].number}")
            startActivity(phone_intent)
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
         * @return A new instance of fragment ContactsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}