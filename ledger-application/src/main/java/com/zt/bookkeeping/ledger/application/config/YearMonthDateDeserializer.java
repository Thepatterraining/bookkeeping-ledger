package com.zt.bookkeeping.ledger.application.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 自定义年月格式日期反序列化器
 * 支持将"yyyy-MM"格式的字符串转换为LocalDate，日期默认为1号
 */
public class YearMonthDateDeserializer extends StdDeserializer<LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    public YearMonthDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        try {
            // 解析年月
            YearMonth yearMonth = YearMonth.parse(value, FORMATTER);
            // 返回当月第一天
            return yearMonth.atDay(1);
        } catch (DateTimeParseException e) {
            // 如果解析失败，尝试使用标准格式解析
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException ex) {
                throw new IOException("Cannot parse date value '" + value + "' : " + e.getMessage(), e);
            }
        }
    }
}

