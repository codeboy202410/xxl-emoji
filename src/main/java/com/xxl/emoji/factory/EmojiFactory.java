package com.xxl.emoji.factory;

import com.xxl.emoji.EmojiTool;
import com.xxl.emoji.model.Emoji;
import com.xxl.emoji.loader.EmojiTrie;
import com.xxl.emoji.exception.XxlEmojiException;
import com.xxl.emoji.loader.EmojiDataLoader;
import com.xxl.emoji.loader.impl.LocalEmojiDataLoader;
import com.xxl.emoji.model.AliasCandidate;
import com.xxl.emoji.model.UnicodeCandidate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * emoji factory
 *
 * @author xuxueli 2018-07-06 20:15:22
 */
public class EmojiFactory {

    private static List<Emoji> ALL_EMOJIS = null;                   // all emoji
    private static EmojiTrie EMOJI_TRIE = null;                     // tree trie

    private static Map<String, Emoji> EMOJIS_BY_ALIAS = null;       // alias-emoji, N:1
    private static Map<String, Set<Emoji>> EMOJIS_BY_TAG = null;    // tag-emoji, N:1

    private static EmojiDataLoader emojiLoader = new LocalEmojiDataLoader();

    public static void setEmojiLoader(EmojiDataLoader emojiLoader) {
        EmojiFactory.emojiLoader = emojiLoader;
    }

    public static void loadEmoji(){
        List<Emoji> emojis = emojiLoader.loadEmojiData();
        if (emojis==null || emojis.size()==0) {
            throw new XxlEmojiException("emoji loader fail");
        }

        ALL_EMOJIS = emojis;
        EMOJI_TRIE = new EmojiTrie(ALL_EMOJIS);

        EMOJIS_BY_ALIAS = new HashMap<String, Emoji>();
        EMOJIS_BY_TAG = new HashMap<String, Set<Emoji>>();

        for (Emoji emoji : ALL_EMOJIS) {
            for (String alias : emoji.getAliases()) {
                EMOJIS_BY_ALIAS.put(alias, emoji);
            }
            for (String tag : emoji.getTags()) {
                if (EMOJIS_BY_TAG.get(tag) == null) {
                    EMOJIS_BY_TAG.put(tag, new HashSet<Emoji>());
                }
                EMOJIS_BY_TAG.get(tag).add(emoji);
            }
        }

    }

    static {
        loadEmoji();
    }


    // ---------------------- emoji base util ----------------------

    public static Emoji getForAlias(String alias) {
        if (alias == null) {
            return null;
        }
        return EMOJIS_BY_ALIAS.get(trimAlias(alias));
    }

    private static String trimAlias(String alias) {
        String result = alias;
        if (result.startsWith(":")) {
            result = result.substring(1, result.length());
        }
        if (result.endsWith(":")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    public static Set<Emoji> getForTag(String tag) {
        if (tag == null) {
            return null;
        }
        return EMOJIS_BY_TAG.get(tag);
    }

    public static Collection<String> getAllTags() {
        return EMOJIS_BY_TAG.keySet();
    }

    public static Emoji getByUnicode(String unicode) {
        if (unicode == null) {
            return null;
        }
        return EMOJI_TRIE.getEmoji(unicode);
    }

    public static Collection<Emoji> getAll() {
        return ALL_EMOJIS;
    }

    public static boolean isEmoji(String string) {
        if (string == null) {
            return false;
        }

        UnicodeCandidate unicodeCandidate = getNextUnicodeCandidate(string.toCharArray(), 0);
        return unicodeCandidate != null &&
                unicodeCandidate.getEmojiStartIndex() == 0 &&
                unicodeCandidate.getFitzpatrickEndIndex() == string.length();
    }

    public static boolean isOnlyEmojis(String string) {
        return string != null && EmojiTool.removeEmojis(string, null, null).length()==0;
    }

    public static EmojiTrie.Matches isEmoji(char[] sequence) {
        return EMOJI_TRIE.isEmoji(sequence);
    }


    // ------------------------ unicode/alias util ------------------------

    private static final Pattern ALIAS_CANDIDATE_PATTERN = Pattern.compile("(?<=:)\\+?(\\w|\\||\\-)+(?=:)");

    /**
     * find AliasCandidate for each emoji alias
     *
     * @param input
     * @return
     */
    public static List<AliasCandidate> getAliasCandidates(String input) {
        List<AliasCandidate> candidates = new ArrayList<AliasCandidate>();

        Matcher matcher = ALIAS_CANDIDATE_PATTERN.matcher(input);
        matcher = matcher.useTransparentBounds(true);
        while (matcher.find()) {
            String match = matcher.group();
            if (!match.contains("|")) {
                candidates.add(new AliasCandidate(match, match, null));
            } else {
                String[] splitted = match.split("\\|");
                if (splitted.length == 2 || splitted.length > 2) {
                    candidates.add(new AliasCandidate(match, splitted[0], splitted[1]));
                } else {
                    candidates.add(new AliasCandidate(match, match, null));
                }
            }
        }
        return candidates;
    }

    /**
     * find UnicodeCandidate for each unicode emoji, include Fitzpatrick modifier if follwing emoji.
     *
     *      Finally, it contains start and end index of unicode emoji itself (WITHOUT Fitzpatrick modifier whether it is there or not!).
     *
     * @param input
     * @return
     */
    public static List<UnicodeCandidate> getUnicodeCandidates(String input) {
        char[] inputCharArray = input.toCharArray();
        List<UnicodeCandidate> candidates = new ArrayList<UnicodeCandidate>();
        UnicodeCandidate next;
        for (int i = 0; (next = getNextUnicodeCandidate(inputCharArray, i)) != null; i = next.getFitzpatrickEndIndex()) {
            candidates.add(next);
        }

        return candidates;
    }

    /**
     * find next UnicodeCandidate after given starting index
     *
     * @param chars
     * @param start
     * @return
     */
    protected static UnicodeCandidate getNextUnicodeCandidate(char[] chars, int start) {
        for (int i = start; i < chars.length; i++) {
            int emojiEnd = getEmojiEndPos(chars, i);

            if (emojiEnd != -1) {
                Emoji emoji = EmojiFactory.getByUnicode(new String(chars, i, emojiEnd - i));
                String fitzpatrickString = (emojiEnd + 2 <= chars.length) ? new String(chars, emojiEnd, 2) : null;
                return new UnicodeCandidate(emoji, fitzpatrickString, i);
            }
        }

        return null;
    }

    /**
     * find end index of a unicode emoji, starting at index startPos, -1 if not found
     *
     * match the longest matching emoji, when emoji contain others
     *
     * @param text
     * @param startPos
     * @return
     */
    protected static int getEmojiEndPos(char[] text, int startPos) {
        int best = -1;
        for (int j = startPos + 1; j <= text.length; j++) {
            EmojiTrie.Matches status = EmojiFactory.isEmoji(Arrays.copyOfRange(text, startPos, j));

            if (status.exactMatch()) {
                best = j;
            } else if (status.impossibleMatch()) {
                return best;
            }
        }

        return best;
    }

}
