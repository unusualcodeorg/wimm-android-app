package com.whereismymotivation.di.module

import com.whereismymotivation.di.qualifier.CoroutineScopeIO
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
    @CoroutineScopeIO
    @ServiceScoped
    fun provideIOCoroutineScope(dispatcher: Dispatcher) = CoroutineScope(dispatcher.io())

}