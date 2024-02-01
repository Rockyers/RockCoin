package utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String applySha256(String input) {
        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static final String prefix = "\u001B[";
    private static final HashMap<Character, Integer> colorMap = new HashMap<>();
    static {
        colorMap.put('f', 0);
        colorMap.put('b', 30);
        colorMap.put('r', 31);
        colorMap.put('g', 32);
        colorMap.put('y', 33);
        colorMap.put('l', 34);
        colorMap.put('p', 35);
        colorMap.put('c', 36);
        colorMap.put('w', 37);
    }
    private static final Pattern colorPattern = Pattern.compile("&([fbrgylpcw]){1,2}");

    public static String color(String input) {
        Matcher matcher = colorPattern.matcher(input);
        while (matcher.find()) {
            String group = matcher.group();
            boolean background = group.length() == 3 && group.charAt(1) == 'b';
            String ANSI_CODE = prefix + (colorMap.get(group.charAt(group.length() - 1)) + (background ? 10 : 0)) + "m";
            input = input.replace(matcher.group(), ANSI_CODE);
        }
        return input + prefix + "0m";
    }

    public static void backspace() {
        backspace(1);
    }

    public static void backspace(int amount) {
        System.out.print("\b".repeat(Math.max(1, amount)));
    }
}
