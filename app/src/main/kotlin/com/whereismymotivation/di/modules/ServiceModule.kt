package com.whereismymotivation.di.modules

import com.whereismymotivation.di.ScopeDefault
import com.whereismymotivation.di.ScopeIO
import com.whereismymotivation.di.ScopeMain
import com.whereismymotivation.utils.coroutine.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ScopeDefault
    @ServiceScoped
    fun provideDefaultCoroutineScope(dispatcher: Dispatcher) = CoroutineScope(dispatcher.default())

    @Provides
    @ScopeIO
    @ServiceScoped
    fun provideIOCoroutineScope(dispatcher: Dispatcher) = CoroutineScope(dispatcher.io())

    @Provides
    @ScopeMain
    @ServiceScoped
    fun provideMainCoroutineScope(dispatcher: Dispatcher) = MainScope()
}