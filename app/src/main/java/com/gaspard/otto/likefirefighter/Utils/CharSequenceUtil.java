package com.gaspard.otto.likefirefighter.Utils;

public class CharSequenceUtil {

    public static CharSequence removeWhitespaces(CharSequence text) {
        if (text == null) return null;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                result.append(text.charAt(i));
            }
        }
        return result;
    }
}
