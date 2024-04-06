package com.example.ezlotestapp.presentation.features.deviceList

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ezlotestapp.R
import com.example.ezlotestapp.databinding.FragmentDevicesListBinding
import com.example.ezlotestapp.domain.models.DeviceModel
import com.example.ezlotestapp.presentation.adapter.DevicesAdapter
import com.example.ezlotestapp.presentation.adapter.ListItemEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DeviceListFragment : Fragment() {

    private var binding: FragmentDevicesListBinding? = null

    private val deviceListViewModel:DeviceListViewModel by activityViewModels()

    private var devicesAdapter: DevicesAdapter? = null

    private val args: DeviceListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDevicesListBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                deviceListViewModel.state.collect { state ->
                    when (state) {
                        is UiState.Error -> handleErrorState(state.message)
                        UiState.Loading -> handleLoadingState()
                        is UiState.Success -> handleSuccessState(state.data)
                    }
                }
            }
        }

        binding?.ibUpdate?.setOnClickListener {
            deviceListViewModel.getDevices(isFetching = true)
        }

        if (args.isShouldUpdateList) {
            deviceListViewModel.getDevices(false)
        }
    }

    private fun initAdapter() {
        devicesAdapter = DevicesAdapter(object : ListItemEventListener {
            override fun openDetailClick(model: DeviceModel) {
                navigateToDetails(model, false)
            }

            override fun onLongClickToDeleteItem(model: DeviceModel) {
                showDialogDelete(model)
            }

            override fun editDeviceClick(model: DeviceModel) {
                navigateToDetails(model, true)
            }
        })
        binding?.rvDevices?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        );
        binding?.rvDevices?.adapter = devicesAdapter
    }

    private fun showDialogDelete(model: DeviceModel) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder
            .setTitle(getString(R.string.delete_dialog_title, model.deviceName))
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                deviceListViewModel.deleteDevice(model)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun navigateToDetails(model: DeviceModel, isEditMode: Boolean) {
        findNavController().navigate(
            DeviceListFragmentDirections.actionDeviceListFragmentToDeviceDetailFragment(
                deviceModel = model,
                isEditMode = isEditMode
            )
        )
    }

    private fun handleSuccessState(data: List<DeviceModel>) {
        binding?.progress?.visibility = View.GONE
        devicesAdapter?.submitList(data)
    }

    private fun handleLoadingState() {
        binding?.let {
            with(it) {
                tvErrorMessage.visibility = View.GONE
                progress.visibility = View.VISIBLE
            }
        }
    }

    private fun handleErrorState(message: String) {
        binding?.let {
            with(it) {
                progress.visibility = View.GONE
                tvErrorMessage.visibility = View.VISIBLE
                tvErrorMessage.text = message
            }
        }
    }

    override fun onDestroyView() {
        devicesAdapter = null
        binding = null
        super.onDestroyView()
    }
}