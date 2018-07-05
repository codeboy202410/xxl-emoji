import com.xxl.emoji.EmojiTool;
import com.xxl.emoji.encode.EmojiEncode;
import com.xxl.emoji.fitzpatrick.FitzpatrickAction;

public class DemojiTest {

    public static void main(String[] args) {

        String str = "An 😀awesome 😃string with a few 😉emojis!";

        // alias
        String str1 = EmojiTool.encodeUnicode(str, FitzpatrickAction.PARSE, EmojiEncode.ALIASES);
        System.out.println(str1);
        System.out.println(EmojiTool.decodeToUnicode(str1));   // back to emoji

        // to html decimal
        String str2 = EmojiTool.encodeUnicode(str, FitzpatrickAction.PARSE, EmojiEncode.HTML_DECIMAL);
        System.out.println(str2);
        System.out.println(EmojiTool.decodeToUnicode(str2));


        // to html hex decimal
        String str3 = EmojiTool.encodeUnicode(str, FitzpatrickAction.PARSE, EmojiEncode.HTML_HEX_DECIMAL);
        System.out.println(str3);
        System.out.println(EmojiTool.decodeToUnicode(str3));
    }

}
