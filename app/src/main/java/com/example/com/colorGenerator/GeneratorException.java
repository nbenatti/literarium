package app.literarium.colorGenerator;

public final class GeneratorException extends RuntimeException {

    public GeneratorException() {
        this("");
    }

    public GeneratorException(String msg) {
        super(msg);
    }

}
