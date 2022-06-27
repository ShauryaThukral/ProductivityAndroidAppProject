package com.example.productivityapp


import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_set_reminder.*

import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [SetReminderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetReminderFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var param1: Int? = null
    private var param2: String? = null
    private var param3: String? = null

    private var hour: Int = -1
    private var minute: Int = -1
    private var day: Int = -1
    private var month: Int = -1
    private var year: Int = -1

    private var savedHour: Int = -1
    private var savedMinute: Int = -1
    private var savedDay: Int = -1
    private var savedMonth: Int = -1
    private var savedYear: Int = -1

    private val calendar: Calendar = Calendar.getInstance()
    private var reminderTime: Long = -1

    private lateinit var viewModel: SetReminderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }
        viewModel = ViewModelProvider(this,SetReminderViewModelFactory(requireActivity().application))[SetReminderViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        minute = calendar.get(Calendar.MINUTE)
        hour = calendar.get(Calendar.HOUR)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)

        reminderTitle.text = Editable.Factory.getInstance().newEditable(param2)
        test.text = param3

        timeButton.setOnClickListener {
            DatePickerDialog(this.requireContext(),this,year,month,day).show()
        }

        reminderButton.setOnClickListener {
            if(reminderTime != -1L && reminderTitle.text.toString().isNotBlank()){
                setReminder(reminderTime, param1!!,reminderTitle.text.toString())
                Toast.makeText(requireContext(),"Reminder added successfully !!", Toast.LENGTH_LONG).show()
                requireActivity().onBackPressed()
            }else{
                Toast.makeText(requireContext(),"Please fill in all the fields",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year
        TimePickerDialog(this.requireContext(),this,hour,minute,true).show()
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        savedHour = hour
        savedMinute = minute
        test.text = "Reminder set for $savedHour:$savedMinute on $savedDay/${savedMonth+1}/$savedYear"

        val alarmDate = Calendar.getInstance()
        alarmDate.timeInMillis = System.currentTimeMillis()
        alarmDate.set(savedYear,savedMonth,savedDay,savedHour,savedMinute)
        reminderTime = alarmDate.timeInMillis
    }

    private fun setReminder(reminderTime: Long, requestCode: Int, title: String){
        val alarmManager : AlarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(),AlarmReceiver::class.java)
        intent.putExtra("TITLE",title)

        val pendingIntent = PendingIntent.getBroadcast(requireContext(),requestCode,intent,0)

        alarmManager.set(AlarmManager.RTC_WAKEUP,reminderTime,pendingIntent)
        viewModel.insertReminder(Reminder(requestCode,title,test.text.toString(),reminderTime))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @param param3 Parameter 3.
         * @return A new instance of fragment SetReminderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String, param3: String) =
            SetReminderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }
}