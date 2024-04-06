package com.example.ezlotestapp.presentation.features.deviceDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ezlotestapp.R
import com.example.ezlotestapp.databinding.FragmentDeviceDetailsBinding
import com.example.ezlotestapp.presentation.utils.getImageResourceIdByName
import com.example.ezlotestapp.presentation.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeviceDetailFragment : Fragment() {

    private var binding: FragmentDeviceDetailsBinding? = null

    private val args: DeviceDetailFragmentArgs by navArgs()

    private val deviceDetailViewModel: DeviceDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeviceDetailsBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                deviceDetailViewModel.state.collect { state ->
                    when (state) {
                        is DeviceDetailPageState.Completed -> handleCompletedState()
                        DeviceDetailPageState.Loading -> binding?.progress?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun handleCompletedState() {
        binding?.progress?.visibility = View.GONE
        navigateBack(true)
    }

    private fun initUi() {
        val model = args.deviceModel
        val isEditMode = args.isEditMode

        binding?.let {
            with(it) {
                if (isEditMode) {
                    tvDeviceName.visibility = View.GONE
                    etDeviceName.visibility = View.VISIBLE
                    etDeviceName.setText(model.deviceName)
                    etDeviceName.showKeyboard()
                } else {
                    tvDeviceName.text = model.deviceName
                    ibSave.visibility = View.GONE
                }
                ivImage.setImageResource(requireContext().getImageResourceIdByName(model.imageResourceName))
                tvSnName.text = getString(R.string.sn_number, model.id)
                tvFirmwareName.text = getString(R.string.firmware, model.firmwareName)
                tvModelName.text = getString(R.string.model, model.platform)
                tvMacAddressName.text = getString(R.string.mac_address, model.macAddress)

                ibBack.setOnClickListener {
                    navigateBack(false)
                }

                ibSave.setOnClickListener {
                    val input = etDeviceName.text.toString()
                    if (isNameValid(input)) {
                        deviceDetailViewModel.saveChanges(model.copy(deviceName = input))
                    } else {
                        showInputError()
                    }
                }
            }
        }
    }

    private fun navigateBack(shouldUpdate: Boolean) {
        findNavController().navigate(
            DeviceDetailFragmentDirections.actionDeviceDetailFragmentToDeviceListFragment(
                isShouldUpdateList = shouldUpdate
            )
        )
    }

    private fun showInputError() {
        Toast.makeText(requireContext(),
            getString(R.string.input_name_empty_error), Toast.LENGTH_SHORT).show()
    }

    private fun isNameValid(name: String):Boolean {
        return name.isNotEmpty()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}