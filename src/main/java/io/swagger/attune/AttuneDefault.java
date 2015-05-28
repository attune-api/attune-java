package io.swagger.attune;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneDefault extends AttuneConfigurable {
    private final String ENDPOINT = "https://api.attune-staging.co";

    public AttuneDefault() {
        this.endpoint = ENDPOINT;
        this.disabled = false;
        //this.exceptionHandler = true;
        this.timeout = 1;
    }
}
