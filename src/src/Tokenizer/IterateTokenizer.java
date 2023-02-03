package Tokenizer;

public class IterateTokenizer implements Tokenizer {
    private String src, next;
    private int pos;

    public IterateTokenizer(String src) {
        this.src = src;
        pos = 0;
        computeNext();
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public String peek() throws IllegalStateException{
        if(next == null)
            throw new IllegalStateException("No more tokens");
        return next;
    }

    @Override
    public boolean peek(String s){
        if (!hasNext()) {
            return false;
        } else {
            return next.equals(s);
        }
    }

    @Override
    public String consume() throws IllegalStateException{
        if (!hasNext()) {
            throw new IllegalStateException("No more tokens");
        } else {
            String result = next;
            computeNext();
            return result;
        }
    }

    @Override
    public boolean consume(String s) throws IllegalStateException{
        if (!hasNext()) {
            throw new IllegalStateException("No more tokens");
        } else {
            if (next.equals(s)) {
                computeNext();
                return true;
            } else {
                throw new IllegalStateException("Expected " + s + " but got " + next);
            }
        }
    }

    private void computeNext() throws IllegalArgumentException {
        StringBuilder sb = new StringBuilder();
        while (pos < src.length() && Character.isWhitespace(src.charAt(pos))) {
            pos++;
        }
        if (pos == src.length()) {
            next = null;
            return;
        }
        char c = src.charAt(pos);
        if (Character.isDigit(c)) {
            while (pos < src.length() && Character.isDigit(src.charAt(pos))) {
                sb.append(src.charAt(pos));
                pos++;
            }
        } else if (Character.isLetter(c)) {
            while (pos < src.length() && Character.isLetter(src.charAt(pos))) {
                sb.append(src.charAt(pos));
                pos++;
            }
        } else if (c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^') {
            sb.append(src.charAt(pos));
            pos++;
        } else {
            throw new IllegalArgumentException("Illegal character: " + c);
        }
        next = sb.toString();
    }
}
