package com.whereismymotivation.di.module

import com.whereismymotivation.di.qualifier.ServiceScopeIO
import com.whereismymotivation.utils.coroutine.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ServiceScopeIO
    @ServiceScoped
    fun provideIOCoroutineScope(dispatcher: Dispatcher) = CoroutineScope(dispatcher.io())

}