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

import io.github.fileanalysissuite.adaptersdk.interfaces.extensibility.FailureDetails;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import javax.annotation.Nonnull;

public final class ConvenientFailureDetails
{
    private ConvenientFailureDetails()
    {
    }

    @Nonnull
    public static FailureDetails create(final String message)
    {
        return new Impl(message, null);
    }

    @Nonnull
    public static FailureDetails create(final String message, final Exception exception)
    {
        return new Impl(message, Collections.singletonList(exception));
    }

    @Nonnull
    public static FailureDetails create(final String message, final Exception... exceptions)
    {
        return new Impl(message, Arrays.asList(exceptions));
    }

    @Nonnull
    public static FailureDetails create(final String message, final Iterable<Exception> exceptions)
    {
        return new Impl(message, exceptions);
    }

    private static final class Impl implements FailureDetails
    {
        private final String message;
        private final Iterable<Exception> exceptions;

        public Impl(final String message, final Iterable<Exception> exceptions)
        {
            this.message = Objects.requireNonNull(message);
            this.exceptions = exceptions;
        }

        @Override
        public Iterable<Exception> getExceptions()
        {
            return exceptions;
        }

        @Nonnull
        @Override
        public String getMessage()
        {
            return message;
        }
    }
}
