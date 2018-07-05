package com.xxl.emoji.transformer;

import com.xxl.emoji.fitzpatrick.FitzpatrickAction;
import com.xxl.emoji.model.UnicodeCandidate;

/**
 * emoji transformer
 */
public interface EmojiTransformer {

    /**
     * @param unicodeCandidate
     * @param fitzpatrickAction     the action to apply for the fitzpatrick modifiers
     * @return
     */
    public String transform(UnicodeCandidate unicodeCandidate, FitzpatrickAction fitzpatrickAction);

}