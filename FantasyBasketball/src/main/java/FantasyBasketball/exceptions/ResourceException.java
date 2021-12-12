package FantasyBasketball.exceptions;

/*
 * Resource exception will be used to throw generic exceptions to be
 * communicated back to the client to explain why their request is wrong or ill-formatted.
 * */
public class ResourceException extends Exception {
    public ResourceException(String str) {
        super(str);
    }
}

