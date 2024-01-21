package com.whereismymotivation.di.module

import com.whereismymotivation.di.component.BroadcastComponent
import com.whereismymotivation.di.qualifier.ScopeDefault
import com.whereismymotivation.di.qualifier.ScopeIO
import com.whereismymotivation.di.qualifier.ScopeMain
import com.whereismymotivation.utils.coroutine.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

@Module
@InstallIn(BroadcastComponent::class)
object BroadcastModule {

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