package com.ikm.myagenda.prolificinteractive.materialcalendarview.format;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.support.annotation.NonNull;

import com.ikm.myagenda.prolificinteractive.materialcalendarview.CalendarDay;

/**
 * Format using a {@linkplain DateFormat} instance.
 */
public class DateFormatDayFormatter implements DayFormatter {

    private final DateFormat dateFormat;

    /**
     * Format using a default format
     */
    public DateFormatDayFormatter() {
        this.dateFormat = new SimpleDateFormat("d", Locale.getDefault());
    }

    /**
     * Format using a specific {@linkplain DateFormat}
     *
     * @param format the format to use
     */
    public DateFormatDayFormatter(@NonNull DateFormat format) {
        this.dateFormat = format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull String format(@NonNull CalendarDay day) {
        return dateFormat.format(day.getDate());
    }
}
