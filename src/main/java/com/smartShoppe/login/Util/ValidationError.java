package com.smartShoppe.login.Util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {

    private final String field;
    private final String message;

    @Override
    public String toString(){
        return "Field: " +
                field +
                ", Error: " +
                message;
    }
}
