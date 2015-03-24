/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.android.inputmethod.latin;

import java.io.File;
import java.math.BigDecimal;
import java.util.Locale;

public class DictionaryStats {
    public static final int NOT_AN_ENTRY_COUNT = -1;

    public final Locale mLocale;
    public final String mDictName;
    public final String mDictFilePath;
    public final long mDictFileSize;
    public final int mContentVersion;

    public DictionaryStats(final Locale locale, final String dictName, final File dictFile,
            final int contentVersion) {
        mLocale = locale;
        mDictName = dictName;
        mDictFilePath = (dictFile == null) ? null : dictFile.getName();
        mDictFileSize = (dictFile == null || !dictFile.exists()) ? 0 : dictFile.length();
        mContentVersion = contentVersion;
    }

    public String getFileSizeString() {
        if (mDictFileSize == 0) {
            return "0";
        }
        BigDecimal bytes = new BigDecimal(mDictFileSize);
        BigDecimal kb = bytes.divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP);
        if (kb.longValue() == 0) {
            return bytes.toString() + " bytes";
        }
        BigDecimal mb = kb.divide(new BigDecimal(1024), 2, BigDecimal.ROUND_HALF_UP);
        if (mb.longValue() == 0) {
            return kb.toString() + " kb";
        }
        return mb.toString() + " Mb";
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(mDictName);
        if (mDictName.equals(Dictionary.TYPE_MAIN)) {
            builder.append(" (");
            builder.append(mContentVersion);
            builder.append(")");
        }
        builder.append(": ");
        builder.append(mDictFilePath);
        builder.append(" / ");
        builder.append(getFileSizeString());
        return builder.toString();
    }

    public static String toString(final Iterable<DictionaryStats> stats) {
        final StringBuilder builder = new StringBuilder("LM Stats");
        for (DictionaryStats stat : stats) {
            builder.append("\n    ");
            builder.append(stat.toString());
        }
        return builder.toString();
    }
}
