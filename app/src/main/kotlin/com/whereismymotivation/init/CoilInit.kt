package com.whereismymotivation.init

import coil.Coil
import coil.ImageLoader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoilInit @Inject constructor(
    private val imageLoader: ImageLoader
) : Initializer {
    override suspend fun init() {
        Coil.setImageLoader(imageLoader)
    }
}