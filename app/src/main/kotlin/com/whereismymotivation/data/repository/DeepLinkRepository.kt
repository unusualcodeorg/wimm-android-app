package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.db.DatabaseService
import com.whereismymotivation.data.model.DeepLinkData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepLinkRepository @Inject constructor(
    private val databaseService: DatabaseService
) {

    private var data: DeepLinkData? = null

    fun setDeepLinkData(data: DeepLinkData) {
        this.data = data
    }

    fun getDeepLinkData() = this.data

    fun clearDeepLinkData() {
        this.data = null
    }

    fun hasDeepLinkData(): Boolean = this.data != null

}