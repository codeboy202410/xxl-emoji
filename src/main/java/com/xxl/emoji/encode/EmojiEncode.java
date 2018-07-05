package com.xxl.emoji.encode;

import com.xxl.emoji.fitzpatrick.FitzpatrickAction;
import com.xxl.emoji.model.UnicodeCandidate;
import com.xxl.emoji.transformer.EmojiTransformer;

/**
 * emoji encode type
 */
public enum EmojiEncode {

    /**
     * encode unicode to aliases
     *
     * replace emoji unicode by one of their first alias (between 2 ':')
     *
     * [unicode emoji >> first alias ]
     *
     */
    ALIASES(new EmojiTransformer() {
        public String transform(UnicodeCandidate unicodeCandidate, FitzpatrickAction fitzpatrickAction) {
            switch (fitzpatrickAction) {
                default:
                case PARSE:
                    if (unicodeCandidate.hasFitzpatrick()) {
                        return ":" +
                                unicodeCandidate.getEmoji().getAliases().get(0) +
                                "|" +
                                unicodeCandidate.getFitzpatrickType() +
                                ":";
                    }
                case REMOVE:
                    return ":" +
                            unicodeCandidate.getEmoji().getAliases().get(0) +
                            ":";
                case IGNORE:
                    return ":" +
                            unicodeCandidate.getEmoji().getAliases().get(0) +
                            ":" +
                            unicodeCandidate.getFitzpatrickUnicode();
            }
        }
    }),

    /**
     * encode unicode html decimal
     *
     * replace unicode emoji by their html representation.
     *
     * [unicode emoji >> html hex ]
     *
     */
    HTML_DECIMAL(new EmojiTransformer() {
        @Override
        public String transform(UnicodeCandidate unicodeCandidate, FitzpatrickAction fitzpatrickAction) {
            switch (fitzpatrickAction) {
                default:
                case PARSE:
                case REMOVE:
                    return unicodeCandidate.getEmoji().getHtmlDecimal();    // parse+remove, will deletec modifier
                case IGNORE:
                    return unicodeCandidate.getEmoji().getHtmlDecimal() + unicodeCandidate.getFitzpatrickUnicode();     // IGNORE, will ignored and remain modifier
            }
        }
    }),

    /**
     * encode unicode html hex decimal
     *
     * replace unicode emoji by their html hex representation
     *
     * [unicode emoji >> html hex ]
     */
    HTML_HEX_DECIMAL(new EmojiTransformer() {
        @Override
        public String transform(UnicodeCandidate unicodeCandidate, FitzpatrickAction fitzpatrickAction) {
            switch (fitzpatrickAction) {
                default:
                case PARSE:
                case REMOVE:
                    return unicodeCandidate.getEmoji().getHtmlHexadecimal();
                case IGNORE:
                    return unicodeCandidate.getEmoji().getHtmlHexadecimal() + unicodeCandidate.getFitzpatrickUnicode();
            }
        }
    });

    private EmojiTransformer emojiTransformer;

    EmojiEncode(EmojiTransformer emojiTransformer) {
        this.emojiTransformer = emojiTransformer;
    }
    public EmojiTransformer getEmojiTransformer() {
        return emojiTransformer;
    }
}
