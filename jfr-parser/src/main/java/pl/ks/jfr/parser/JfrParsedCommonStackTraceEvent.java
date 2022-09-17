package pl.ks.jfr.parser;

import static pl.ks.jfr.parser.tuning.AdditionalLevel.ECID;
import static pl.ks.jfr.parser.tuning.AdditionalLevel.FILENAME;
import static pl.ks.jfr.parser.tuning.AdditionalLevel.THREAD;
import static pl.ks.jfr.parser.tuning.AdditionalLevel.TIMESTAMP_100_MS;
import static pl.ks.jfr.parser.tuning.AdditionalLevel.TIMESTAMP_10_S;
import static pl.ks.jfr.parser.tuning.AdditionalLevel.TIMESTAMP_1_S;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import pl.ks.jfr.parser.tuning.AdditionalLevel;

interface JfrParsedCommonStackTraceEvent {
    ThreadLocal<SimpleDateFormat> OUTPUT_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US));
    ThreadLocal<DecimalFormat> TIME_STAMP_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat("0000000000000"));

    String[] getStackTrace();

    long getCorrelationId();

    String getThreadName();

    String getFilename();

    Instant getEventTime();

    default void addCommonStackTraceElements(List<String[]> fullStackTrace, Set<AdditionalLevel> additionalLevels) {
        if (additionalLevels.contains(ECID)) {
            fullStackTrace.add(new String[]{String.valueOf(getCorrelationId())});
        }
        if (additionalLevels.contains(TIMESTAMP_100_MS)) {
            long time = getEventTime().toEpochMilli() / 100;
            fullStackTrace.add(new String[]{
                    TIME_STAMP_FORMAT.get().format(time) + "_" + OUTPUT_FORMAT.get().format(new Date(time * 100)) + "_[k];"
            });
        }
        if (additionalLevels.contains(TIMESTAMP_1_S)) {
            long time = getEventTime().toEpochMilli() / 1000;
            fullStackTrace.add(new String[]{
                    TIME_STAMP_FORMAT.get().format(time) + "_" + OUTPUT_FORMAT.get().format(new Date(time * 1000)) + "_[k];"
            });
        }
        if (additionalLevels.contains(TIMESTAMP_10_S)) {
            long time = getEventTime().toEpochMilli() / 10000;
            fullStackTrace.add(new String[]{
                    TIME_STAMP_FORMAT.get().format(time) + "_" + OUTPUT_FORMAT.get().format(new Date(time * 10000)) + "_[k];"
            });
        }
        if (additionalLevels.contains(FILENAME)) {
            fullStackTrace.add(new String[]{getFilename() + "_[i];"});
        }
        if (additionalLevels.contains(THREAD)) {
            fullStackTrace.add(new String[]{getThreadName()});
        }
        fullStackTrace.add(getStackTrace());
    }
}