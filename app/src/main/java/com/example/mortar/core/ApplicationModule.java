/*
 * Copyright 2013 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.mortar.core;

import com.example.mortar.model.Chats;
import com.example.mortar.model.QuoteService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Parcer;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

@Module(includes = Chats.Module.class, library = true)
public class ApplicationModule {
  @Provides @Singleton Gson provideGson() {
    return new GsonBuilder().create();
  }

  @Provides @Singleton Parcer<Object> provideParcer(Gson gson) {
    return new GsonParcer<Object>(gson);
  }

  @Provides @Singleton QuoteService provideQuoteService() {
    RestAdapter restAdapter =
        new RestAdapter.Builder()
            .setEndpoint("http://www.iheartquotes.com/api/v1/")
            .setConverter(new GsonConverter(new Gson()))
            .build();
    return restAdapter.create(QuoteService.class);
  }
}
