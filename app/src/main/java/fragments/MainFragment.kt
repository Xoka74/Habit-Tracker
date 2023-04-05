package fragments

import adapters.HabitListViewPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hometask3.R
import com.example.hometask3.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.addHabitButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_habitDetailsFragment)
        }
        binding.viewPager.adapter = HabitListViewPagerAdapter(activity as AppCompatActivity)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.good)
                else -> tab.text = getString(R.string.bad)
            }
        }.attach()

        return binding.root
    }
}