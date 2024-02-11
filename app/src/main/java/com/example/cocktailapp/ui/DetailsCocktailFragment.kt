package com.example.cocktailapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cocktailapp.model.Drinks
import com.example.cocktailapp.MyApplication
import com.example.cocktailapp.R
import com.example.cocktailapp.databinding.FragmentDetailsCocktailBinding
import com.example.cocktailapp.utils.show
import com.example.cocktailapp.viewmodel.CocktailViewModel
import com.example.cocktailapp.viewmodel.CocktailViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailsCocktailFragment : Fragment() {

    @Inject
    lateinit var viewModel: CocktailViewModel

    @Inject
    lateinit var viewModelFactory: CocktailViewModelFactory

    private var _binding: FragmentDetailsCocktailBinding? = null
    private val binding get() = _binding!!
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsCocktailBinding.inflate(inflater)
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as MyApplication).appComponent.inject(this)
        if (::viewModel.isInitialized) {
            viewModel =
                ViewModelProvider(this, viewModelFactory)[CocktailViewModel::class.java]
        }

        val drinks = arguments?.getString("drink_name")

        navHostFragment = requireActivity().supportFragmentManager.findFragmentById(
            R.id.nav_graph_host_fragment
        ) as NavHostFragment

        val job = lifecycleScope.async {
            val cocktailData = viewModel.fetchCocktail()
            cocktailData?.drinks?.forEach {
                viewModel.filterHm[it.strDrink] = it
            }
        }

        lifecycleScope.launch {
            job.await()
            val drink: Drinks? = viewModel.filterHm[drinks]
            drink?.run {
                with(binding) {

                    Glide.with(requireContext()).load(strDrinkThumb).apply(
                        RequestOptions.placeholderOf(R.drawable.ic_cocktail_drink)
                            .error(R.drawable.ic_cocktail_drink)
                    ).into(image)

                    drinkName.text = strDrink
                    if (strTags != null) {
                        tagsLl.show()
                        tags.text = strTags
                    }
                    if (strIBA != null) {
                        ibaLl.visibility = View.VISIBLE
                        iba.text = strIBA
                    }
                    alcoholic.text = strAlcoholic
                    glass.text = strGlass
                    instructions.text = strInstructions
                    if (strInstructionsDE != null) {
                        instructionsDeLl.show()
                        instructionsDe.text = strInstructionsDE
                    }
                    if (strInstructionsIT != null) {
                        instructionsItLl.show()
                        instructionsIt.text = strInstructionsIT
                    }

                    val ingredients =
                        "$strIngredient1, $strIngredient2, $strIngredient3, $strIngredient4, $strIngredient5"
                    if (strIngredient6 != null) {
                        ingredients.plus(",$strIngredient6")
                    }
                    if (strIngredient7 != null) {
                        ingredients.plus(",$strIngredient7")
                    }
                    ingredient.text = ingredients

                    val measures = "$strMeasure1, $strMeasure2, $strMeasure3, $strMeasure4"
                    if (strMeasure5 != null) {
                        measures.plus(", $strMeasure5")
                    }
                    if (strMeasure6 != null && strMeasure6 != "") {
                        measures.plus(", $strMeasure6")
                    }

                    if (strMeasure7 != null && strMeasure7 != "") {
                        measures.plus(", $strMeasure7")
                    }
                    measure.text = measures

                    creativeCommonsConfirmed.text = strCreativeCommonsConfirmed

                    if (dateModified != null) {
                        dateModifiedLl.show()
                        date.text = dateModified
                    }
                }
            }
        }
    }
}
