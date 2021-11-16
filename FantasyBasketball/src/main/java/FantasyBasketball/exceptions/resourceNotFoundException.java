package FantasyBasketball.exceptions;

/*
 * resourceNotFoundException will be used to throw generic exceptions to be
 * communicated back to the client to let them know that the resource they're looking
 * for is not found.
 * */
public class resourceNotFoundException extends Exception {
    public resourceNotFoundException (String str) {
        super(str);
    }
}
