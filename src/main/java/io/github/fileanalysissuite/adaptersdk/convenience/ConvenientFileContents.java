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

import io.github.fileanalysissuite.adaptersdk.interfaces.extensibility.FileContents;
import io.github.fileanalysissuite.adaptersdk.interfaces.extensibility.OpenStreamFunction;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class ConvenientFileContents
{
    private ConvenientFileContents()
    {
    }

    @Nonnull
    public static FileContents create(final OpenStreamFunction contentStream)
    {
        return new Impl(contentStream);
    }

    private static final class Impl implements FileContents
    {
        private final OpenStreamFunction contentStream;

        public Impl(final OpenStreamFunction contentStream)
        {
            this.contentStream = Objects.requireNonNull(contentStream);
        }

        @Nonnull
        @Override
        public OpenStreamFunction getContentStream()
        {
            return contentStream;
        }
    }
}
