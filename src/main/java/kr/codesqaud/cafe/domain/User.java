package kr.codesqaud.cafe.domain;

/**
 * 회원을 의미하는 객체
 */
public class User {

    private long sequence;
    private String userId;
    private String password;
    private String name;
    private String email;

    public long getSequence() {
        return sequence;
    }

    public void setSequence(final long sequence) {
        this.sequence = sequence;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
