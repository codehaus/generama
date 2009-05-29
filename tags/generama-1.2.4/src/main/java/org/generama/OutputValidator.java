package org.generama;

import java.net.URL;

/**
 * @author Anatol Pomozov
 */
public interface OutputValidator {
    void validate(URL url) throws OutputValidationError;
}
