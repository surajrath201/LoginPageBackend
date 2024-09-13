package com.smartShoppe.login.Util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult<T> {

    private Boolean Valid;
    private T data;
    private List<ValidationError> errors;

    public static <T> ValidationResult<T> success(T data){
        return new ValidationResult<>(Boolean.TRUE, data, new ArrayList<>());
    }

    public static ValidationResult error(List<ValidationError> errors) {
        return new ValidationResult<>(Boolean.FALSE, null, errors);
    }
}