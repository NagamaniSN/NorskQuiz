package com.nagamani.norskquiz.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.nagamani.norskquiz.R
import com.nagamani.norskquiz.databinding.FragmentAboutBinding


class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAboutBinding>(
            inflater, R.layout.fragment_about, container, false
        )

        binding.contactMeButton.setOnClickListener { view: View ->
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:nagamani.sn@outlook.com")
                putExtra(Intent.EXTRA_SUBJECT, "Regarding Norsk Quiz app")
            }

            startActivity(Intent.createChooser(intent, "Send Email..."))
        }

        return binding.root
    }
}
