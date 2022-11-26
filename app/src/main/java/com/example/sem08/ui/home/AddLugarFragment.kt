package com.example.sem08.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sem08.R
import com.example.sem08.databinding.FragmentAddLugarBinding
import com.example.sem08.model.Lugar
import com.example.sem08.viewModel.HomeViewModel

class AddLugarFragment : Fragment() {

    private var _binding: FragmentAddLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentAddLugarBinding.inflate(inflater, container, false)

        binding.btAgregar.setOnClickListener { agregarLugar() }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun agregarLugar() {
        val nombre = binding.etNombre.toString()
        val coreo = binding.etCorreo.toString()
        val telefono = binding.etTelefono.toString()
        val web = binding.etWeb.toString()

        if (nombre.isNotEmpty()) {
            val lugar = Lugar("", nombre, coreo, telefono, web)
            homeViewModel.guardarLugar(lugar)
            Toast.makeText(requireContext(), getText(R.string.ms_AddLugar), Toast.LENGTH_LONG)
                .show()
            findNavController().navigate(R.id.action_addLugarFragment_to_nav_home)
        } else {
            Toast.makeText(requireContext(), getText(R.string.ms_FaltaValores), Toast.LENGTH_LONG)
                .show()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}