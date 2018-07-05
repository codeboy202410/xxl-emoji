package com.xxl.emoji.transformer;

import com.xxl.emoji.model.UnicodeCandidate;

/**
 * emoji transformer
 */
public interface EmojiTransformer {

    public String transform(UnicodeCandidate unicodeCandidate);

}