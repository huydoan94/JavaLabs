/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Huy
 */
public class InvalidArgumentException extends Exception {

    public InvalidArgumentException(String s) {
        super(s);
    }

    public InvalidArgumentException() {
    }
}
