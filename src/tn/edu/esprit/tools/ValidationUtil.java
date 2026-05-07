package tn.edu.esprit.tools;

public class ValidationUtil {

    private ValidationUtil() {}

    /**
     * Valid email: standard format check.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        String e = email.trim();
        return e.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Valid password: 8-64 chars, at least one uppercase, one lowercase,
     * one digit, one special character.
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 64) return false;
        boolean hasUpper  = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower  = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit  = password.chars().anyMatch(Character::isDigit);
        boolean hasSymbol = password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
        return hasUpper && hasLower && hasDigit && hasSymbol;
    }

    /**
     * Valid name: 2-50 letters (including accented/Arabic characters).
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) return false;
        String n = name.trim();
        return n.length() >= 2 && n.length() <= 50 && n.matches("[\\p{L} '-]+");
    }

    /**
     * Valid 6-digit code: exactly 6 numeric digits.
     */
    public static boolean isValidSixDigitCode(String code) {
        if (code == null) return false;
        return code.trim().matches("\\d{6}");
    }
}
