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

import io.github.fileanalysissuite.adaptersdk.interfaces.extensibility.FileMetadata;
import jakarta.annotation.Nonnull;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

final class StringMapFileMetadata implements FileMetadata
{
    private enum FixedMetadataField
    {
        FILEPATH("file location"),
        FILE_NAME("file name"),
        TITLE("title"),
        DATE_CREATED("creation date"),
        DATE_MODIFIED("modification date");

        private final String description;

        FixedMetadataField(final String description)
        {
            this.description = description;
        }

        public String getDescription()
        {
            return description;
        }
    }

    private static final Set<String> FIXED_FILE_METADATA_FIELDS
        = Collections.unmodifiableSet(Arrays.stream(FixedMetadataField.values()).map(Enum::name).collect(Collectors.toSet()));

    private final Map<String, List<String>> metadata;

    public StringMapFileMetadata(final Map<String, List<String>> metadata)
    {
        this.metadata = metadata;
    }

    @Nonnull
    @Override
    public String getFileLocation()
    {
        return getMandatorySingleValueField(FixedMetadataField.FILEPATH);
    }

    @Nonnull
    @Override
    public String getName()
    {
        return getMandatorySingleValueField(FixedMetadataField.FILE_NAME);
    }

    @Override
    public String getTitle()
    {
        return getOptionalSingleValueField(FixedMetadataField.TITLE);
    }

    @Override
    public Instant getCreatedTime()
    {
        return getOptionalSingleValueInstantField(FixedMetadataField.DATE_CREATED);
    }

    @Override
    public Instant getAccessedTime()
    {
        return null;
    }

    @Nonnull
    @Override
    public Instant getModifiedTime()
    {
        return getMandatorySingleValueInstantField(FixedMetadataField.DATE_MODIFIED);
    }

    @Override
    public Integer getVersion()
    {
        return null;
    }

    @Nonnull
    @Override
    public Map<String, ? extends Iterable<String>> getAdditionalMetadata()
    {
        return metadata.entrySet().stream()
            .filter(entry -> !FIXED_FILE_METADATA_FIELDS.contains(entry.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Nonnull
    private String getMandatorySingleValueField(final FixedMetadataField field)
    {
        final String value = getOptionalSingleValueField(field);
        if (value == null) {
            throw new RuntimeException("The " + field.getDescription() + " has not been set!");
        }

        return value;
    }

    @Nonnull
    private Instant getMandatorySingleValueInstantField(final FixedMetadataField field)
    {
        final Instant value = getOptionalSingleValueInstantField(field);
        if (value == null) {
            throw new RuntimeException("The " + field.getDescription() + " has not been set!");
        }

        return value;
    }

    private String getOptionalSingleValueField(final FixedMetadataField field)
    {
        final List<String> values = metadata.get(field.name());
        if (values == null || values.isEmpty()) {
            return null;
        }

        if (values.size() > 1) {
            throw new RuntimeException("Logic error: There are multiple " + field.getDescription() + "s!");
        }

        return values.get(0);
    }

    private Instant getOptionalSingleValueInstantField(final FixedMetadataField field)
    {
        final String value = getOptionalSingleValueField(field);
        if (value == null) {
            return null;
        }

        final long parseLong;
        try {
            parseLong = Long.parseLong(value);
        } catch (final NumberFormatException ex) {
            throw new RuntimeException("The " + field.getDescription() + " is not in the expected format!", ex);
        }

        try {
            return Instant.ofEpochSecond(parseLong);
        } catch (final DateTimeException ex) {
            throw new RuntimeException("The " + field.getDescription() + ", " + value + ", is not supported!", ex);
        }
    }
}
