package FantasyBasketball.exceptions;

/*
 * Resource exception will be used to throw generic exceptions to be
 * communicated back to the client to explain why their request is wrong or ill-formatted.
 * */
public class resourceException extends Exception {
    public resourceException(String str) {
        super(str);
    }
}

