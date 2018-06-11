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
public class NoSuchEntityException extends Exception {

    public NoSuchEntityException(Long id, Class entityClass) {
	    super("No entity of class [" + entityClass.getName() + "] with id [" + id + "]");
    }

    public NoSuchEntityException(String field, String value, Class entityClass) {
	    super("No entity of class [" + entityClass + "] with [" + field + "] [" + value + "]");
    }
}