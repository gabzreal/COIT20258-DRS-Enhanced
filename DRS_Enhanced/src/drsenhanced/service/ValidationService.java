/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.service;

/**
 * Handles validation of user inputs.
 *
 * @author Hajera Begum Shaik - 12281382
 */
public class ValidationService {

    /**
     * Checks whether a string is empty.
     *
     * @param value input value
     * @return true if empty
     */
    public boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Checks whether a number is positive.
     *
     * @param number input number
     * @return true if positive
     */
    public boolean isPositiveNumber(int number) {
        return number > 0;
    }

    /**
     * Validates email format.
     *
     * @param email email address
     * @return true if valid
     */
    public boolean isValidEmail(String email) {

        if (email == null) {
            return false;
        }

        return email.contains("@")
                && email.contains(".");
    }

    /**
     * Validates phone number length.
     *
     * @param phone phone number
     * @return true if valid
     */
    public boolean isValidPhoneNumber(String phone) {

        if (phone == null) {
            return false;
        }

        return phone.matches("\\d{10}");
    }
}
