/*
 * Copyright 2022 Krzysztof Slusarski
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
package pl.ks.jfr.parser;

import lombok.Builder;
import lombok.Value;
import pl.ks.jfr.parser.tuning.AdditionalLevel;
import pl.ks.jfr.parser.tuning.PreStackFilter;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@Value
class JfrParserContext {
    Path file;
    List<PreStackFilter> preStackFilters;
    JfrParsedFile jfrParsedFile;
    Set<AdditionalLevel> additionalLevels;

    boolean includeThreadName;
    boolean includeFileName;
    boolean includeTimestamp10SAndDate;
    boolean includeTimestamp1SAndDate;
    boolean includeTimestamp100MSAndDate;
    boolean includeAnyTimestampAndDate;

    @Builder
    public JfrParserContext(Path file, List<PreStackFilter> preStackFilters, JfrParsedFile jfrParsedFile, Set<AdditionalLevel> additionalLevels) {
        this.file = file;
        this.preStackFilters = preStackFilters;
        this.jfrParsedFile = jfrParsedFile;
        this.additionalLevels = additionalLevels;

        includeThreadName = additionalLevels.contains(AdditionalLevel.THREAD);
        includeFileName = additionalLevels.contains(AdditionalLevel.FILENAME);
        includeTimestamp10SAndDate = additionalLevels.contains(AdditionalLevel.TIMESTAMP_10_S);
        includeTimestamp1SAndDate = additionalLevels.contains(AdditionalLevel.TIMESTAMP_1_S);
        includeTimestamp100MSAndDate = additionalLevels.contains(AdditionalLevel.TIMESTAMP_100_MS);
        includeAnyTimestampAndDate = includeTimestamp10SAndDate || includeTimestamp1SAndDate || includeTimestamp100MSAndDate;
    }
}
