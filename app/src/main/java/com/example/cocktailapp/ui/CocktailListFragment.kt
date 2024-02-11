package com.example.cocktailapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cocktailapp.model.CocktailModel
import com.example.cocktailapp.model.Drinks
import com.example.cocktailapp.MyApplication
import com.example.cocktailapp.R
import com.example.cocktailapp.adapter.CocktailAdapter
import com.example.cocktailapp.databinding.FragmentCocktailListBinding
import com.example.cocktailapp.utils.readJSONFromAssets
import com.example.cocktailapp.viewmodel.CocktailViewModel
import com.example.cocktailapp.viewmodel.CocktailViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

class CocktailListFragment : Fragment(), CocktailAdapter.ViewClicked {

    @Inject
    lateinit var viewModel: CocktailViewModel

    @Inject
    lateinit var viewModelFactory: CocktailViewModelFactory

    private var _binding: FragmentCocktailListBinding? = null
    private val binding get() = _binding!!
    private var cocktailAdapter: CocktailAdapter? = null
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this /* lifecycle owner */,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Back is pressed... Finishing the activity
                    requireActivity().finish()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCocktailListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().application as MyApplication).appComponent.inject(this)
        if (::viewModel.isInitialized) {
            viewModel =
                ViewModelProvider(this, viewModelFactory)[CocktailViewModel::class.java]
        }

        navHostFragment = requireActivity().supportFragmentManager.findFragmentById(
            R.id.nav_graph_host_fragment
        ) as NavHostFragment

        val linearLayoutManager =
            GridLayoutManager(requireContext(), 2)
        cocktailAdapter = CocktailAdapter(requireContext(), this)
        binding.rvCocktail.apply {
            layoutManager = linearLayoutManager
            adapter = cocktailAdapter
            itemAnimator = null
            setHasFixedSize(true)
        }

        //  convert to json to gson
        val json = readJSONFromAssets(requireContext(), "Cocktail.json")
        val gson = Gson()
        val data = gson.fromJson(json, CocktailModel::class.java)

        lifecycleScope.launch {
            val cocktailData = viewModel.fetchCocktail()
            if (cocktailData == null) {
                viewModel.insertCocktail(data)
                cocktailAdapter?.apply {
                    setData(data.drinks)
                }
            } else {
                cocktailListAdd()
            }
            data.drinks.forEach {
                viewModel.filterHm[it.strDrink] = it
            }
        }



        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchKeyword = s.toString()
                if (searchKeyword.isNotEmpty()) {
                    val matchingResults = viewModel.filterHm.filterKeys {
                        it?.contains(searchKeyword, ignoreCase = true)
                            ?: false
                    }
                    val matchingList = ArrayList<Drinks>()
                    if (matchingResults.isNotEmpty()) {
                        matchingResults.forEach { drink ->
                            matchingList.add(drink.value)
                        }
                    }
                    cocktailAdapter?.apply {
                        setData(matchingList)
                    }
                } else {
                    cocktailListAdd()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    private fun cocktailListAdd() {
        lifecycleScope.launch {
            cocktailAdapter?.apply {
                setData(viewModel.fetchCocktail()?.drinks)
            }
        }
    }

    override fun clicked(data: String) {
        val bundle = bundleOf("drink_name" to data)
        navHostFragment.navController.navigate(R.id.detailsCocktailFragment, bundle)

    }
}