package com.example.productivityapp

import android.app.AlertDialog
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_notes.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: NotesListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(this,NotesListViewModelFactory(requireActivity().application))[NotesListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(notes_list){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = NoteAdapter({
                showDialog(it)
            }){ createdAt,title,content ->
                (activity as MainActivity).createNotes(createdAt,title,content)
            }
            setHasFixedSize(true)
        }

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                message.visibility = View.VISIBLE
            }else{
                message.visibility = View.INVISIBLE
            }

            (notes_list.adapter as NoteAdapter).submitList(it)
        })

        addNotes.setOnClickListener {
            (activity  as MainActivity).createNotes(-1L,"","")
        }
    }

    private fun showDialog(note: Note){
        val builder = AlertDialog.Builder(requireContext())
        with(builder){
            setTitle("Are you sure you want to delete this note ?")
            setPositiveButton("YES"){_,_ ->
                viewModel.deleteNote(note)
            }
            setNegativeButton("NO"){_,_ ->

            }
            show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}