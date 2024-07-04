package com.whereismymotivation.init

import android.content.Context
import com.whereismymotivation.data.repository.AppMetricRepository
import com.whereismymotivation.utils.common.SystemUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetricInit @Inject constructor(
    @ApplicationContext private val context: Context,
    private var appMetricRepository: AppMetricRepository
) : Initializer {
    override suspend fun init() {
        appMetricRepository.setCurrentAppVersion(SystemUtils.getAppVersionCode(context))
    }
}