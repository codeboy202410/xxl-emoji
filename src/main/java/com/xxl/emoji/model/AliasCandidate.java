package com.xxl.emoji.model;

import com.xxl.emoji.fitzpatrick.Fitzpatrick;

/**
 * alias candidate
 */
public class AliasCandidate {
    public final String fullString;
    public final String alias;
    public final Fitzpatrick fitzpatrick;

    public AliasCandidate(String fullString, String alias, String fitzpatrickString) {
        this.fullString = fullString;
        this.alias = alias;
        if (fitzpatrickString == null) {
            this.fitzpatrick = null;
        } else {
            this.fitzpatrick = Fitzpatrick.fitzpatrickFromType(fitzpatrickString);
        }
    }

}