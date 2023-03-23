package fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hometask3.databinding.FragmentAppInfoBinding


class AppInfoFragment : Fragment() {

    private lateinit var binding: FragmentAppInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppInfoBinding.inflate(layoutInflater)
        binding.developerInfoTextView.movementMethod = LinkMovementMethod.getInstance()
        return binding.root
    }
}