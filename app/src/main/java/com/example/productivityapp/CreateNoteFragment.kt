package com.example.productivityapp

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_create_note.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateNoteFragment : Fragment() {

    private var param1: Long? = null
    private var param2: String? = null
    private var param3: String? = null


    private lateinit var viewModel: CreateNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getLong(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }
        viewModel = ViewModelProvider(this,CreateNoteViewModelFactory(requireActivity().application))[CreateNoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var entry: Note? = null

        noteTitle.text = Editable.Factory.getInstance().newEditable(param2)
        noteContent.text = Editable.Factory.getInstance().newEditable(param3)

        saveButton.setOnClickListener {
            if(noteTitle.text.toString().isNotBlank() && noteContent.text.toString().isNotBlank()){
                entry = if(param1 == -1L){
                    Note(System.currentTimeMillis(),noteTitle.text.toString(),noteContent.text.toString())
                }else{
                    Note(param1!!,noteTitle.text.toString(),noteContent.text.toString())
                }

                viewModel.insertNote(entry!!)
                requireActivity().onBackPressed()
            }else{
                Toast.makeText(requireContext(),"Please enter Title and some content",Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @param param3 Parameter 3.
         * @return A new instance of fragment NotesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Long, param2: String, param3: String) =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3,param3)
                }
            }
    }
}