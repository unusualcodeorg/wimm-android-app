package com.whereismymotivation.di.component

import com.whereismymotivation.di.scope.BroadcastScope
import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent

@BroadcastScope
@DefineComponent(parent = SingletonComponent::class)
interface BroadcastComponent
