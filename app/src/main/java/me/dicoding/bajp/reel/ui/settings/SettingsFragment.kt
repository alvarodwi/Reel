package me.dicoding.bajp.reel.ui.settings

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.jintin.bindingextension.BindingFragment
import me.dicoding.bajp.reel.R
import me.dicoding.bajp.reel.databinding.FragmentSettingsBinding

class SettingsFragment : BindingFragment<FragmentSettingsBinding>() {
    private val toolbar get() = binding.toolbar

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        with(toolbar) {
            title = getString(R.string.text_settings)
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back)
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
    }
}
