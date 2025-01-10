/*
 * Copyright 2022-2025 Open Text.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.fileanalysissuite.adaptersdk.convenience;

import io.github.fileanalysissuite.adaptersdk.interfaces.extensibility.AdapterDescriptor;
import io.github.fileanalysissuite.adaptersdk.interfaces.extensibility.RepositorySettingDefinition;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class ConvenientAdapterDescriptor
{
    private ConvenientAdapterDescriptor()
    {
    }

    @Nonnull
    public static AdapterDescriptor create(final String adapterType)
    {
        return new Impl(adapterType, Collections.emptyList());
    }

    @Nonnull
    public static AdapterDescriptor create(
        final String adapterType,
        final RepositorySettingDefinition settingDefinition
    )
    {
        return new Impl(adapterType, Collections.singletonList(settingDefinition));
    }

    @Nonnull
    public static AdapterDescriptor create(
        final String adapterType,
        final RepositorySettingDefinition... settingDefinitions
    )
    {
        return new Impl(adapterType, Arrays.asList(settingDefinitions));
    }

    @Nonnull
    public static AdapterDescriptor create(
        final String adapterType,
        final Iterable<RepositorySettingDefinition> settingDefinitions
    )
    {
        return new Impl(adapterType, settingDefinitions);
    }

    private static final class Impl implements AdapterDescriptor
    {
        private final String adapterType;
        private final Iterable<RepositorySettingDefinition> settingDefinitions;

        public Impl(
            final String adapterType,
            final Iterable<RepositorySettingDefinition> settingDefinitions
        )
        {
            this.adapterType = Objects.requireNonNull(adapterType);
            this.settingDefinitions = Objects.requireNonNull(settingDefinitions);
        }

        @Nonnull
        @Override
        public String getAdapterType()
        {
            return adapterType;
        }

        @Nonnull
        @Override
        public Iterable<RepositorySettingDefinition> getSettingDefinitions()
        {
            return settingDefinitions;
        }
    }
}
