package com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.userdetails

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gmail.nikismag.atraining_project_for_65apps.R
import com.gmail.nikismag.atraining_project_for_65apps.data.model.User
import com.gmail.nikismag.atraining_project_for_65apps.data.notify.ReminderReceiver
import com.gmail.nikismag.atraining_project_for_65apps.data.notify.getIntentFlags
import com.gmail.nikismag.atraining_project_for_65apps.databinding.FragmentUserDetailsBinding
import com.gmail.nikismag.atraining_project_for_65apps.presentation.MainActivity
import com.gmail.nikismag.atraining_project_for_65apps.presentation.model.Result
import com.gmail.nikismag.atraining_project_for_65apps.presentation.screens.Notifier
import kotlinx.android.synthetic.main.fragment_user_details.*
import java.time.LocalDate
import java.time.Month
import java.time.ZoneOffset

class UserDetailsFragment : Fragment(), Notifier {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var reminderIntent: Intent

    private val viewModel: UserDetailsViewModel by viewModels()

    override fun notifyFragment(isStarted: Boolean) {
        updateServiceState(isStarted)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActionBar()

        viewModel.state.observe(viewLifecycleOwner, ::handleUserResult)
        if ((requireActivity() as MainActivity).isStarted) {
            updateServiceState()
        }
        viewModel.userId = requireArguments().getLong(ARG_USER_ID)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initActionBar() {
        requireActivity().setTitle(R.string.contact_details)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateServiceState(isStarted: Boolean = true) {
        (requireActivity() as MainActivity).dataService?.let { viewModel.updateService(it) }
        viewModel.onServiceStarted(isStarted)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleUserResult(userDetails: Result<User>) {

        contentContainer.isVisible = userDetails is Result.SuccessResult
        progressBar.isVisible = userDetails !is Result.SuccessResult
        if (userDetails is Result.SuccessResult) {
            tvName.text = userDetails.data.name
            tvPhoneFirst.text = userDetails.data.phoneFirst
            ivPhoto.setImageResource(userDetails.data.photo)
            tvEmailFirst.text = userDetails.data.emailFirst
            tvEmailSecond.text = userDetails.data.emailSecond
            tvPhoneSecond.text = userDetails.data.phoneSecond
            tvCompany.text = userDetails.data.company
            tvBirthday?.text = this.getString(R.string.detail_birthday, userDetails.data.birthday)

            setupAlarm(userDetails.data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupAlarm(user: User) {
        if (this::reminderIntent.isInitialized) return

        reminderIntent = ReminderReceiver.newIntent(
            app,
            user.id,
            String.format(
                requireContext().getString(R.string.notification_birthday_message),
                user.name
            ),
            user
        )

        val isAlarmOn = checkIfAlarmIsOn(user.id)
        setDrawableSwitch(isAlarmOn)

        binding.swBirthday?.apply {
            isChecked = isAlarmOn
            setOnCheckedChangeListener { _, isChecked ->
                (app.getSystemService(Context.ALARM_SERVICE) as? AlarmManager)?.let { alarmManager ->
                    if (isChecked) {
                        setReminder(
                            alarmManager,
                            getReminderPendingIntent(user.id, getIntentFlags()),
                            user.birthday
                        )
                    } else {
                        cancelReminder(
                            alarmManager,
                            getReminderPendingIntent(
                                user.id,
                                getIntentFlags(PendingIntent.FLAG_NO_CREATE)
                            )
                        )
                    }
                }
                setDrawableSwitch(isChecked)
            }
        }
    }

    private fun setDrawableSwitch(isChecked: Boolean) {
        binding.swBirthday?.buttonDrawable =
            requireContext().getDrawable(if (isChecked) R.drawable.ic_notify else R.drawable.ic_dontotify)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setReminder(alarmManager: AlarmManager, intent: PendingIntent, date: LocalDate) {
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            getTimeInterval(date),
            intent
        )
    }

    private fun cancelReminder(alarmManager: AlarmManager, intent: PendingIntent) {
        alarmManager.cancel(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTimeInterval(date: LocalDate): Long {

        var year = LocalDate.now()
        val isFeb29 = date.month == Month.FEBRUARY && date.dayOfMonth == 29
        if (isFeb29) {
            while (!year.isLeapYear) {
                year = year.withYear(year.year + 1)
            }
        }
        var nextBirthday: LocalDate =
            year.withMonth(date.month.value).withDayOfMonth(date.dayOfMonth)
        val todayDate: LocalDate = LocalDate.now()

        if (todayDate.isAfter(nextBirthday)) {
            nextBirthday = nextBirthday.plusYears(if (isFeb29) 4 else 1)

        }

        return nextBirthday.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    private fun getReminderPendingIntent(userId: Long, flags: Int) =
        PendingIntent.getBroadcast(app, userId.toInt(), reminderIntent, flags)

    private fun checkIfAlarmIsOn(userId: Long) = getReminderPendingIntent(
        userId, getIntentFlags(PendingIntent.FLAG_NO_CREATE)
    ) != null

    private val app: Application get() = requireActivity().application

    companion object {

        private const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(userId: Long) = UserDetailsFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}


