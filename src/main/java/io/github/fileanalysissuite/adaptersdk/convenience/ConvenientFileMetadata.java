/*
 * Copyright 2022 Micro Focus or one of its affiliates.
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

import io.github.fileanalysissuite.adaptersdk.interfaces.extensibility.FileMetadata;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class ConvenientFileMetadata
{
    private ConvenientFileMetadata()
    {
    }

    @Nonnull
    public static Builder builder()
    {
        return new Builder();
    }

    @Nonnull
    public static FileMetadata create(
        final String fileLocation,
        final String name,
        final Instant modifiedTime
    )
    {
        return builder()
            .fileLocation(fileLocation)
            .name(name)
            .modifiedTime(modifiedTime)
            .build();
    }

    public static final class Builder
    {
        private String fileLocation;
        private String name;
        private String title;
        private Instant createdTime;
        private Instant accessedTime;
        private Instant modifiedTime;
        private Integer version;
        private final Map<String, Iterable<String>> additionalMetadata;

        private Builder()
        {
            this.fileLocation = null;
            this.name = null;
            this.title = null;
            this.createdTime = null;
            this.accessedTime = null;
            this.modifiedTime = null;
            this.version = null;
            this.additionalMetadata = new HashMap<>();
        }

        @Nonnull
        public Builder fileLocation(final String value)
        {
            fileLocation = Objects.requireNonNull(value);
            return this;
        }

        @Nonnull
        public Builder name(final String value)
        {
            name = Objects.requireNonNull(value);
            return this;
        }

        @Nonnull
        public Builder title(final String value)
        {
            title = value;
            return this;
        }

        @Nonnull
        public Builder createdTime(final Instant value)
        {
            createdTime = value;
            return this;
        }

        @Nonnull
        public Builder accessedTime(final Instant value)
        {
            accessedTime = value;
            return this;
        }

        @Nonnull
        public Builder modifiedTime(final Instant value)
        {
            modifiedTime = Objects.requireNonNull(value);
            return this;
        }

        @Nonnull
        public Builder version(final Integer value)
        {
            version = value;
            return this;
        }

        @Nonnull
        public Builder additionalMetadata(final String key, final Iterable<String> value)
        {
            additionalMetadata.put(key, value);
            return this;
        }

        private Map<String, Iterable<String>> getAdditionalMetadata()
        {
            return additionalMetadata.isEmpty()
                ? null
                : Collections.unmodifiableMap(additionalMetadata);
        }

        @Nonnull
        public FileMetadata build()
        {
            return new Impl(this);
        }
    }

    private static final class Impl implements FileMetadata
    {
        private final String fileLocation;
        private final String name;
        private final String title;
        private final Instant createdTime;
        private final Instant accessedTime;
        private final Instant modifiedTime;
        private final Integer version;
        private final Map<String, Iterable<String>> additionalMetadata;

        private Impl(final Builder builder)
        {
            this.fileLocation = Objects.requireNonNull(builder.fileLocation, "fileLocation must not be null");
            this.name = Objects.requireNonNull(builder.name, "name must not be null");
            this.title = builder.title;
            this.createdTime = builder.createdTime;
            this.accessedTime = builder.accessedTime;
            this.modifiedTime = Objects.requireNonNull(builder.modifiedTime, "modifiedTime must not be null");
            this.version = builder.version;
            this.additionalMetadata = builder.getAdditionalMetadata();
        }

        @Nonnull
        @Override
        public String getFileLocation()
        {
            return fileLocation;
        }

        @Nonnull
        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public String getTitle()
        {
            return title;
        }

        @Override
        public Instant getCreatedTime()
        {
            return createdTime;
        }

        @Override
        public Instant getAccessedTime()
        {
            return accessedTime;
        }

        @Nonnull
        @Override
        public Instant getModifiedTime()
        {
            return modifiedTime;
        }

        @Override
        public Integer getVersion()
        {
            return version;
        }

        @Override
        public Map<String, Iterable<String>> getAdditionalMetadata()
        {
            return additionalMetadata;
        }
    }
}
