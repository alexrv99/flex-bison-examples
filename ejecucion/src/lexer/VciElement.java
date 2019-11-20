package lexer;

public class VciElement {
    private Token token;
    private String string;
    private Integer priority;

    public VciElement(Token token, String string) {
        this.token = token;
        this.string = string;
    }

    public VciElement(Token token, String string, Integer priority) {
        this.token = token;
        this.string = string;
        this.priority = priority;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "VciElement{" +
                "token=" + token +
                ", string='" + string + '\'' +
                ", priority=" + priority +
                '}';
    }
}
