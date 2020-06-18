package com.viettel.arpu.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SpecificationUtils {

    /**
     * @description tạo câu truy vấn tìm kiếm giá trị lớn hơn hoặc bằng giá trị nhập vào
     * @param value
     * @param column
     * @param <T>
     * @return Specification
     */
    public static <T> Specification<T> greaterThanOrEqualTo(String value, String column) {
        return StringUtils.isEmpty(value) ? null : (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get(column), value.trim());
    }

    /**
     * @description tạo câu truy vấn tìm kiếm giá trị nhỏ hơn hoặc bằng giá trị nhập vào
     * @param value
     * @param column
     * @param <T>
     * @return Specification
     */
    public static <T> Specification<T> lessThanOrEqualTo(String value, String column) {
        return StringUtils.isEmpty(value) ? null : (root, cq, cb) -> cb.lessThanOrEqualTo(root.get(column), value.trim());
    }

    /**
     * @description tạo câu truy vấn tìm kiếm ngày tạo lớn hơn ngày nhập vào
     * @param value
     * @param column
     * @param <T>
     * @return Specification
     */
    public static <T> Specification<T> greaterThanOrEqualFromDateInstant(LocalDate value, String column) {
        return value == null? null : (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get(column), value.atTime(LocalTime.MIN));
    }

    /**
     * @description tạo câu truy vấn tìm kiếm ngày tạo nhỏ hơn ngày nhập vào
     * @param value
     * @param column
     * @param <T>
     * @return Specification
     */
    public static <T> Specification<T> lessThanOrEqualToDateInstant(LocalDate value, String column) {
        return value == null ? null : (root, cq, cb) -> cb.lessThanOrEqualTo(root.get(column), value.atTime(LocalTime.MAX));
    }

    /**
     * @description tạo câu truy vấn tìm kiếm giá trị bằng với giá trị đầu vào
     * @param value
     * @param column
     * @param <T>
     * @return Specification
     */
    public static <T> Specification<T> equalParam(String value, String column) {
        return StringUtils.isEmpty(value) ? null : (root, cq, cb) -> cb.equal(root.get(column), value.trim());
    }

    /**
     * @description tạo câu truy vấn tìm kiếm dữ liệu với danh sách giá trị cho trước
     * @param values
     * @param column
     * @param <T>
     * @return Specification
     */
    public static <T> Specification<T> hasListParam(List values, String column) {
        return values == null || values.isEmpty() ? null : (root, cq, cb) -> root.get(column).in(values);
    }

}
