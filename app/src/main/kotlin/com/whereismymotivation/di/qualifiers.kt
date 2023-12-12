package com.whereismymotivation.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AccessTokenInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiKeyInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DeviceIdInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppVersionCodeInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RefreshTokenInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DatabaseInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PrefsInfo

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class GoogleClientId

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Staggered2Horizontal

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Staggered3Horizontal

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StaggeredVertical