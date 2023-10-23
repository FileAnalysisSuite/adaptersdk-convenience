/*
 * Copyright 2022-2023 Open Text.
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

import io.github.fileanalysissuite.adaptersdk.interfaces.extensibility.RepositorySettingDefinition;
import io.github.fileanalysissuite.adaptersdk.interfaces.framework.TypeCode;
import jakarta.annotation.Nonnull;
import java.util.Objects;

public final class ConvenientRepositorySettingDefinition
{
    private ConvenientRepositorySettingDefinition()
    {
    }

    @Nonnull
    public static RepositorySettingDefinition create(
        final String name,
        final TypeCode typeCode,
        final boolean required,
        final boolean encrypted
    )
    {
        return new Impl(name, typeCode, required, encrypted);
    }

    private static final class Impl implements RepositorySettingDefinition
    {
        private final String name;
        private final TypeCode typeCode;
        private final boolean required;
        private final boolean encrypted;

        public Impl(
            final String name,
            final TypeCode typeCode,
            final boolean required,
            final boolean encrypted
        )
        {
            this.name = Objects.requireNonNull(name);
            this.typeCode = Objects.requireNonNull(typeCode);
            this.required = required;
            this.encrypted = encrypted;
        }

        @Nonnull
        @Override
        public String getName()
        {
            return name;
        }

        @Nonnull
        @Override
        public TypeCode getTypeCode()
        {
            return typeCode;
        }

        @Override
        public boolean isRequired()
        {
            return required;
        }

        @Override
        public boolean isEncrypted()
        {
            return encrypted;
        }
    }
}
