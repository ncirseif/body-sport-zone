package tn.edu.esprit.tools;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L}][\\p{L}'\\-\\s]{1,49}$");
    private static final Pattern HAS_UPPER = Pattern.compile(".*[A-Z].*");
    private static final Pattern HAS_LOWER = Pattern.compile(".*[a-z].*");
    private static final Pattern HAS_DIGIT = Pattern.compile(".*\\d.*");
    private static final Pattern HAS_SPECIAL = Pattern.compile(".*[^A-Za-z0-9].*");
    private static final Pattern SIX_DIGIT = Pattern.compile("^\\d{6}$");

    private ValidationUtil() {
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String e = email.trim();
        if (e.isEmpty() || e.length() > 254) return false;
        return EMAIL_PATTERN.matcher(e).matches();
    }

    public static boolean isValidName(String name) {
        if (name == null) return false;
        String n = name.trim();
        if (n.isEmpty()) return false;
        return NAME_PATTERN.matcher(n).matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        String p = password;
        if (p.length() < 8 || p.length() > 64) return false;
        if (!HAS_UPPER.matcher(p).matches()) return false;
        if (!HAS_LOWER.matcher(p).matches()) return false;
        if (!HAS_DIGIT.matcher(p).matches()) return false;
        if (!HAS_SPECIAL.matcher(p).matches()) return false;
        return true;
    }

    public static boolean isValidSixDigitCode(String code) {
        if (code == null) return false;
        return SIX_DIGIT.matcher(code.trim()).matches();
    }
}
