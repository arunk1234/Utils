package com.example.picsrestapi;

import com.example.picsrestapi.exceptions.adapter.*;
import com.example.picsrestapi.exceptions.host.*;

import java.util.Map;
import java.util.function.Function;

public class ExceptionTranslator {
    private static final Map<Class<? extends Exception>, Function<Exception, Exception>> exceptionMap = Map.of(
        AdapterInvalidAuthException.class, e -> new HostInvalidAuthException(e.getMessage()),
        AdapterInvalidPinException.class, e -> new HostInvalidPinException(e.getMessage())
        // Add other exception mappings here
    );

    public void translateAndThrow(Exception adapterException) throws Exception {
        Function<Exception, Exception> exceptionFunction = exceptionMap.get(adapterException.getClass());
        if (exceptionFunction != null) {
            throw exceptionFunction.apply(adapterException);
        } else {
            // Optionally handle other types of exceptions or rethrow the original
            // throw adapterException; // Uncomment this if you want to rethrow the original exception for unhandled types
        }
    }
}
