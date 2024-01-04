package com.whereismymotivation.data.remote.utils

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String = "No internet connection"
}