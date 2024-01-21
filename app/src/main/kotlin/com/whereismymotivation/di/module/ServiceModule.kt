package com.whereismymotivation.di.module

import com.whereismymotivation.di.qualifier.ScopeIO
import com.whereismymotivation.utils.coroutine.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ScopeIO
    @ServiceScoped
    fun provideIOCoroutineScope(dispatcher: Dispatcher) =
        CoroutineScope(dispatcher.io() + SupervisorJob())

}