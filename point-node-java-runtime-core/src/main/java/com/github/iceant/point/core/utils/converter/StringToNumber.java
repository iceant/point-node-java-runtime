package com.github.iceant.point.core.utils.converter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class StringToNumber implements IConverter<String, Number>{
    Locale locale;
    Currency currency;
    DecimalFormatSymbols decimalFormatSymbols;
    Integer groupingSize;
    Boolean groupingUsed;
    Integer multiplier;
    Boolean decimalSeperatorAlwaysShown;
    Integer maximumFractionDigits;
    Integer minimumFractionDigits;
    Integer maximumIntegerDigits;
    Integer minimumIntegerDigit;
    String pattern;
    String negativePrefix;
    String negativeSuffix;
    Boolean parseBigDecimal;
    String positivePrefix;
    String positiveSuffix;
    RoundingMode roundingMode;

    //////////////////////////////////////////////////////////////////////
    ////
    public NumberFormat getNumberFormat(){
        locale = (locale==null)?Locale.getDefault():locale;
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        if(currency!=null) {
            numberFormat.setCurrency(currency);
        }
        if(decimalFormatSymbols!=null) {
            numberFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        }
        if(groupingSize!=null) {
            numberFormat.setGroupingSize(groupingSize);
        }
        if(groupingUsed!=null) {
            numberFormat.setGroupingUsed(groupingUsed);
        }
        if(multiplier!=null) {
            numberFormat.setMultiplier(multiplier);
        }
        if(decimalSeperatorAlwaysShown!=null) {
            numberFormat.setDecimalSeparatorAlwaysShown(decimalSeperatorAlwaysShown);
        }
        if(maximumFractionDigits!=null) {
            numberFormat.setMaximumFractionDigits(maximumFractionDigits);
        }
        if(maximumIntegerDigits!=null) {
            numberFormat.setMaximumIntegerDigits(maximumIntegerDigits);
        }
        if(minimumFractionDigits!=null) {
            numberFormat.setMinimumFractionDigits(minimumFractionDigits);
        }
        if(negativePrefix!=null) {
            numberFormat.setNegativePrefix(negativePrefix);
        }
        if(minimumIntegerDigit!=null) {
            numberFormat.setMinimumIntegerDigits(minimumIntegerDigit);
        }
        if(negativeSuffix!=null) {
            numberFormat.setNegativeSuffix(negativeSuffix);
        }
        if(parseBigDecimal!=null) {
            numberFormat.setParseBigDecimal(parseBigDecimal);
        }
        if(positivePrefix!=null) {
            numberFormat.setPositivePrefix(positivePrefix);
        }
        if(positiveSuffix!=null) {
            numberFormat.setPositiveSuffix(positiveSuffix);
        }
        if(roundingMode!=null) {
            numberFormat.setRoundingMode(roundingMode);
        }
        if(pattern!=null) {
            numberFormat.applyPattern(pattern);
        }
        return numberFormat;
    }

    //////////////////////////////////////////////////////////////////////
    ////
    @Override
    public Number convertFromAToB(String s, Number def) {
        if(s==null) return def;
        try {
            getNumberFormat().parse(s);
        }catch (Exception err){}
        return def;
    }

    //////////////////////////////////////////////////////////////////////
    ////


    public Currency getCurrency() {
        return currency;
    }

    public StringToNumber setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public DecimalFormatSymbols getDecimalFormatSymbols() {
        return decimalFormatSymbols;
    }

    public StringToNumber setDecimalFormatSymbols(DecimalFormatSymbols decimalFormatSymbols) {
        this.decimalFormatSymbols = decimalFormatSymbols;
        return this;
    }

    public Integer getGroupingSize() {
        return groupingSize;
    }

    public StringToNumber setGroupingSize(Integer groupingSize) {
        this.groupingSize = groupingSize;
        return this;
    }

    public Boolean getGroupingUsed() {
        return groupingUsed;
    }

    public StringToNumber setGroupingUsed(Boolean groupingUsed) {
        this.groupingUsed = groupingUsed;
        return this;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public StringToNumber setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
        return this;
    }

    public Boolean getDecimalSeperatorAlwaysShown() {
        return decimalSeperatorAlwaysShown;
    }

    public StringToNumber setDecimalSeperatorAlwaysShown(Boolean decimalSeperatorAlwaysShown) {
        this.decimalSeperatorAlwaysShown = decimalSeperatorAlwaysShown;
        return this;
    }

    public Integer getMaximumFractionDigits() {
        return maximumFractionDigits;
    }

    public StringToNumber setMaximumFractionDigits(Integer maximumFractionDigits) {
        this.maximumFractionDigits = maximumFractionDigits;
        return this;
    }

    public Integer getMinimumFractionDigits() {
        return minimumFractionDigits;
    }

    public StringToNumber setMinimumFractionDigits(Integer minimumFractionDigits) {
        this.minimumFractionDigits = minimumFractionDigits;
        return this;
    }

    public Integer getMaximumIntegerDigits() {
        return maximumIntegerDigits;
    }

    public StringToNumber setMaximumIntegerDigits(Integer maximumIntegerDigits) {
        this.maximumIntegerDigits = maximumIntegerDigits;
        return this;
    }

    public Integer getMinimumIntegerDigit() {
        return minimumIntegerDigit;
    }

    public StringToNumber setMinimumIntegerDigit(Integer minimumIntegerDigit) {
        this.minimumIntegerDigit = minimumIntegerDigit;
        return this;
    }

    public String getNegativePrefix() {
        return negativePrefix;
    }

    public StringToNumber setNegativePrefix(String negativePrefix) {
        this.negativePrefix = negativePrefix;
        return this;
    }

    public String getNegativeSuffix() {
        return negativeSuffix;
    }

    public StringToNumber setNegativeSuffix(String negativeSuffix) {
        this.negativeSuffix = negativeSuffix;
        return this;
    }

    public Boolean getParseBigDecimal() {
        return parseBigDecimal;
    }

    public StringToNumber setParseBigDecimal(Boolean parseBigDecimal) {
        this.parseBigDecimal = parseBigDecimal;
        return this;
    }

    public String getPositivePrefix() {
        return positivePrefix;
    }

    public StringToNumber setPositivePrefix(String positivePrefix) {
        this.positivePrefix = positivePrefix;
        return this;
    }

    public String getPositiveSuffix() {
        return positiveSuffix;
    }

    public StringToNumber setPositiveSuffix(String positiveSuffix) {
        this.positiveSuffix = positiveSuffix;
        return this;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    public StringToNumber setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public StringToNumber setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public StringToNumber setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }
}