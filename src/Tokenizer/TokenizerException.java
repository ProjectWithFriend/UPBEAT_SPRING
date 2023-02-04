package Tokenizer;

import Parser.ParserException;

public class TokenizerException extends RuntimeException {
    public TokenizerException(String message) {
        super(message);
    }

    public static class NoToken extends TokenizerException {
        public NoToken() {
            super("no more token");
        }
    }

    public static class NotMatchToken extends TokenizerException {
        public NotMatchToken(String expected, String got) {
            super("Expected " + expected + " but got " + got);
        }
    }

    public static class BadCharacter extends TokenizerException {
        public BadCharacter(char got) {
            super("Bad character " + got);
        }
    }
}
