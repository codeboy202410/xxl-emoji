import com.xxl.emoji.EmojiTool;
import com.xxl.emoji.core.FitzpatrickAction;

public class DemojiTest {

    public static void main(String[] args) {

        String str = "An 😀awesome 😃string with a few 😉emojis!";

        // alias
        String str1 = EmojiTool.encodeToAliases(str, FitzpatrickAction.PARSE);
        System.out.println(str1);
        System.out.println(EmojiTool.decodeToUnicode(str1));   // back to emoji

        // to html decimal
        String str2 = EmojiTool.encodeToHtmlDecimal(str, FitzpatrickAction.PARSE);
        System.out.println(str2);
        System.out.println(EmojiTool.decodeToUnicode(str2));


        // to html hex decimal
        String str3 = EmojiTool.encodeToHtmlHexadecimal(str, FitzpatrickAction.PARSE);
        System.out.println(str3);
        System.out.println(EmojiTool.decodeToUnicode(str3));
    }

}
