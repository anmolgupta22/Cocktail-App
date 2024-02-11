package com.example.cocktailapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cocktailapp.model.Drinks
import com.example.cocktailapp.R
import com.example.cocktailapp.databinding.CocktailItemsBinding

class CocktailAdapter(private val context: Context, private val viewClicked: ViewClicked) :
    RecyclerView.Adapter<CocktailAdapter.ViewHolder>() {

    private val items: ArrayList<Drinks> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(drinksItems: ArrayList<Drinks>?) {
        items.clear()
        if (drinksItems != null) {
            items.addAll(drinksItems)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        CocktailItemsBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], context, viewClicked)
    }

    override fun getItemCount() = items.size


    class ViewHolder(private val binding: CocktailItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Drinks, context: Context, viewClicked: ViewClicked) {
            with(binding) {
                itemName.text = item.strDrink
                itemType.text = item.strTags
                name.text = item.strCategory
                Glide.with(context).load(item.strDrinkThumb).apply(
                    RequestOptions.placeholderOf(R.drawable.ic_cocktail_drink)
                        .error(R.drawable.ic_cocktail_drink)
                ).into(image)

                viewBtn.setOnClickListener {
                    item.strDrink?.let { it1 -> viewClicked.clicked(it1) }
                }
            }
        }
    }

    interface ViewClicked {
        fun clicked(data: String)
    }

}