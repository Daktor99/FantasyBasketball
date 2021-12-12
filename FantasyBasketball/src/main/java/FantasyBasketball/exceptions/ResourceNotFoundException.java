package FantasyBasketball.exceptions;

/*
 * ResourceNotFoundException will be used to throw generic exceptions to be
 * communicated back to the client to let them know that the resource they're looking
 * for is not found.
 * */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String str) {
        super(str);
    }
}
