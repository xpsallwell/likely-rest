/**
 * 
 */
package com.xps.rest.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Johnny
 * 
 */
public class DateConverterBase {
    private String datePattern = "yyyyMMdd";
    private String monthPattern = "yyyyMM";
    private String timePattern = "HHmmss";
    private DateFormat dateFormat = new SimpleDateFormat(datePattern);
    private DateFormat monthFormat = new SimpleDateFormat(monthPattern);
    private DateFormat dateTimeFormat = new SimpleDateFormat(datePattern + timePattern);

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * @return the dateTimeFormat
     */
    public DateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     * @param dateTimeFormat the dateTimeFormat to set
     */
    public void setDateTimeFormat(DateFormat dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public DateFormat getMonthFormat() {
        return monthFormat;
    }

    public void setMonthFormat(DateFormat monthFormat) {
        this.monthFormat = monthFormat;
    }
}
