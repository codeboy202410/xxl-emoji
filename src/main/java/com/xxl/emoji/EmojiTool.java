package com.xxl.emoji;

import com.xxl.emoji.encode.EmojiEncode;
import com.xxl.emoji.factory.EmojiFactory;
import com.xxl.emoji.fitzpatrick.FitzpatrickAction;
import com.xxl.emoji.model.AliasCandidate;
import com.xxl.emoji.model.Emoji;
import com.xxl.emoji.model.UnicodeCandidate;
import com.xxl.emoji.transformer.EmojiTransformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * emoji tool
 */
public class EmojiTool {


    // ------------------------ [encode unicode emoji] ------------------------

    /**
     * detects all unicode emojis, and replaces them with the return value of transformer.transform()
     * [unicode emoji >> other]
     *
     * @param input
     * @param transformer   emoji transformer to apply to each emoji
     * @return              input string with all emojis transformed
     */
    public static String encodeUnicode(String input, FitzpatrickAction fitzpatrickAction, EmojiTransformer transformer) {
        int prev = 0;
        StringBuilder sb = new StringBuilder();
        List<UnicodeCandidate> replacements = EmojiFactory.getUnicodeCandidates(input);
        for (UnicodeCandidate candidate : replacements) {
            sb.append(input.substring(prev, candidate.getEmojiStartIndex()));

            sb.append(transformer.transform(candidate, fitzpatrickAction));
            prev = candidate.getFitzpatrickEndIndex();
        }

        return sb.append(input.substring(prev)).toString();
    }

    public static String encodeUnicode(String input, FitzpatrickAction fitzpatrickAction, EmojiEncode dmojiEncodeType) {
        return encodeUnicode(input, fitzpatrickAction, dmojiEncodeType.getEmojiTransformer());
    }

    // ------------------------ [remove unicode emoji] ------------------------

    /**
     * remove all emojis
     *
     * [unicode emoji >> remove(provided) ]
     *
     * @param str
     * @return
     */
    public static String removeAllEmojis(String str) {
        EmojiTransformer emojiTransformer = new EmojiTransformer() {
            public String transform(UnicodeCandidate unicodeCandidate, FitzpatrickAction fitzpatrickAction) {
                return "";
            }
        };

        return encodeUnicode(str, null, emojiTransformer);
    }

    /**
     * remove provided emojis
     *
     * [unicode emoji >> remove(provided) ]
     *
     * @param str
     * @param emojisToRemove
     * @return
     */
    public static String removeEmojis(String str, final Collection<Emoji> emojisToRemove) {
        EmojiTransformer emojiTransformer = new EmojiTransformer() {
            public String transform(UnicodeCandidate unicodeCandidate, FitzpatrickAction fitzpatrickAction) {
                if (!emojisToRemove.contains(unicodeCandidate.getEmoji())) {
                    return unicodeCandidate.getEmoji().getUnicode() + unicodeCandidate.getFitzpatrickUnicode();
                }
                return "";
            }
        };

        return encodeUnicode(str, null, emojiTransformer);
    }

    /**
     * remove not provided emojis
     *
     * [unicode emoji >> remove(not provided) ]
     *
     * @param str
     * @param emojisToKeep
     * @return
     */
    /*public static String removeAllEmojisExcept(String str, final Collection<Emoji> emojisToKeep) {
        EmojiTransformer emojiTransformer = new EmojiTransformer() {
            public String transform(UnicodeCandidate unicodeCandidate, FitzpatrickAction fitzpatrickAction) {
                if (emojisToKeep.contains(unicodeCandidate.getEmoji())) {
                    return unicodeCandidate.getEmoji().getUnicode() + unicodeCandidate.getFitzpatrickUnicode();
                }
                return "";
            }
        };

        return encodeUnicode(str, null, emojiTransformer);
    }*/


    /**
     * extract emojis
     *
     * @param input
     * @return
     */
    public static List<String> extractEmojis(String input) {
        List<UnicodeCandidate> emojis = EmojiFactory.getUnicodeCandidates(input);
        List<String> result = new ArrayList<String>();
        for (UnicodeCandidate emoji : emojis) {
            result.add(emoji.getEmoji().getUnicode());
        }
        return result;
    }


    // ------------------------ [decode to unicode emoji] ------------------------

    /**
     * replace aliases and html representations by their unicode(modifiers).
     *
     * [unicode emoji << alias | html hex ]
     *
     * @param input
     * @return
     */
    public static String decodeToUnicode(String input) {
        // get all alias
        List<AliasCandidate> candidates = EmojiFactory.getAliasCandidates(input);

        // replace the aliases by their unicode
        String result = input;
        for (AliasCandidate candidate : candidates) {
            Emoji emoji = EmojiFactory.getForAlias(candidate.alias);
            if (emoji != null) {
                if (emoji.supportsFitzpatrick() || (!emoji.supportsFitzpatrick() && candidate.fitzpatrick == null)) {
                    String replacement = emoji.getUnicode();
                    if (candidate.fitzpatrick != null) {
                        replacement += candidate.fitzpatrick.unicode;
                    }
                    result = result.replace(":" + candidate.fullString + ":", replacement);
                }
            }
        }

        // replace the html by their unicode
        for (Emoji emoji : EmojiFactory.getAll()) {
            result = result.replace(emoji.getHtmlHexadecimal(), emoji.getUnicode());
            result = result.replace(emoji.getHtmlDecimal(), emoji.getUnicode());
        }

        return result;
    }


}
