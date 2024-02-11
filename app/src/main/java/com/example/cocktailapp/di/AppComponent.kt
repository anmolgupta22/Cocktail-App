package com.example.cocktailapp.di

import com.example.cocktailapp.ui.CocktailListFragment
import com.example.cocktailapp.ui.DetailsCocktailFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface AppComponent {
    fun inject(fragment: CocktailListFragment)
    fun inject(fragment: DetailsCocktailFragment)
}