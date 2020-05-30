package exceptions;

public class IllegalImageRelation extends IllegalArgumentException {
    public IllegalImageRelation(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
