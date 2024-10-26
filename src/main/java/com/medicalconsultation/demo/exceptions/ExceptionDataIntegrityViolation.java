    package com.medicalconsultation.demo.exceptions;

    public class ExceptionDataIntegrityViolation extends RuntimeException{
        public ExceptionDataIntegrityViolation(String message) {
            super(message);
        }
    }
