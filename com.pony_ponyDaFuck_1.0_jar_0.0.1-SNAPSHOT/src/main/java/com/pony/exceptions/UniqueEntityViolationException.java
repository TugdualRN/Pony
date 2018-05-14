/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.exceptions;

/**
 *
 * @author Gotug
 */

public class UniqueEntityViolationException extends Exception {

    public UniqueEntityViolationException(String field, String value, Class entityClass) {
	super("Unique entity violation for class [" + entityClass + "] with [" + field + "] [" + value + "]");
    }
}

