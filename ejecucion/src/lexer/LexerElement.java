package lexer;

public class LexerElement {
    private String string;
    private Token token;

    public LexerElement(String string, Token token) {
        this.string = string;
        this.token = token;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LexerElement{" +
                "string='" + string + '\'' +
                ", token=" + token +
                '}';
    }
}
