package com.example.productivityapp

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_reminder.*
import kotlin.math.max

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReminderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReminderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: ReminderListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(this,ReminderListViewModelFactory(requireActivity().application))[ReminderListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var nextRequestCode  = 0
        with(reminders_list){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ReminderAdapter({
                showDialog(it)
            }){ requestCode,title,date ->
                (activity as MainActivity).setReminder(requestCode,title,date)
            }
            setHasFixedSize(true)
        }

        viewModel.reminders.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                messageRem.visibility = View.VISIBLE
            }else{
                messageRem.visibility = View.INVISIBLE
                for(elt: Reminder in it){
                    nextRequestCode = max(nextRequestCode,elt.requestCode+1)
                }
            }

            (reminders_list.adapter as ReminderAdapter).submitList(it)
        })

        addReminders.setOnClickListener{
            (activity as MainActivity).setReminder(nextRequestCode,"","")
        }
    }

    private fun showDialog(reminder: Reminder){
        val builder = AlertDialog.Builder(requireContext())
        with(builder){
            setTitle("Are you sure you want to delete this reminder ?")
            setPositiveButton("YES"){_,_ ->
                deleteReminder(reminder)
            }
            setNegativeButton("NO"){_,_ ->

            }
            show()
        }
    }

    private fun deleteReminder(reminder: Reminder){
        val alarmManager : AlarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(),AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(requireContext(),reminder.requestCode,intent,0)
        alarmManager.cancel(pendingIntent)
        viewModel.deleteReminder(reminder)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReminderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReminderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}