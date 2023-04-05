package adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fragments.HabitsViewPagerFragment
import models.HabitType

class HabitListViewPagerAdapter(
    activity: AppCompatActivity,
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HabitsViewPagerFragment.newInstance(HabitType.Good)
            else -> HabitsViewPagerFragment.newInstance(HabitType.Bad)
        }
    }
}