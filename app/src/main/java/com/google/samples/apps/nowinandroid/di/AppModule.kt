/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.google.samples.apps.nowinandroid.data.UserPreferences
import com.google.samples.apps.nowinandroid.data.UserPreferencesSerializer
import com.google.samples.apps.nowinandroid.data.fake.FakeNewsRepository
import com.google.samples.apps.nowinandroid.data.fake.FakeNiANetwork
import com.google.samples.apps.nowinandroid.data.fake.FakeTopicsRepository
import com.google.samples.apps.nowinandroid.data.network.NiANetwork
import com.google.samples.apps.nowinandroid.data.repository.NewsRepository
import com.google.samples.apps.nowinandroid.data.repository.TopicsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindsNiANetwork(
        fakeNiANetwork: FakeNiANetwork
    ): NiANetwork

    @Binds
    fun bindsTopicRepository(fakeTopicsRepository: FakeTopicsRepository): TopicsRepository

    @Binds
    fun bindsNewsResourceRepository(
        fakeNewsRepository: FakeNewsRepository
    ): NewsRepository

    @Binds
    fun bindsNiaDispatchers(defaultNiaDispatchers: DefaultNiaDispatchers): NiaDispatchers

    companion object {
        @Provides
        @Singleton
        fun providesUserPreferencesDataStore(
            @ApplicationContext context: Context,
            userPreferencesSerializer: UserPreferencesSerializer
        ): DataStore<UserPreferences> =
            DataStoreFactory.create(
                serializer = userPreferencesSerializer
            ) {
                context.dataStoreFile("user_preferences.pb")
            }

        @Provides
        @Singleton
        fun providesNetworkJson(): Json = Json {
            ignoreUnknownKeys = true
        }
    }
}
