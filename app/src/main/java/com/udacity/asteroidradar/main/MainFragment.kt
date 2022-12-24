package com.udacity.asteroidradar.main

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.asDomainModels
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repository.Filter

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        val adapter = MainFragmentAdapter(AsteroidListener { asteroid ->
            viewModel.onAsterodClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter
        viewModel.aste.observe(viewLifecycleOwner) {

            it.let {
                if (it.isNotEmpty()){
                adapter.submitList(it)
                binding.asteroidRecycler.visibility=View.VISIBLE
                binding.statusLoadingWheel.visibility=View.GONE
            }else{
                    binding.asteroidRecycler.visibility=View.GONE
                    binding.statusLoadingWheel.visibility=View.VISIBLE

                }            }
        }


        viewModel.navToDeltali.observe(viewLifecycleOwner, Observer {
            aster -> aster?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(aster))
            viewModel.onDetailNavDon()
        }
        })
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_all_menu ->viewModel.nextSevenAsterid()
            R.id.show_rent_menu->viewModel.todayAsterid()
            else ->viewModel.savedAsterid()
        }
        return true
    }
}
