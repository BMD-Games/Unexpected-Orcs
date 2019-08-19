package GUI;

import static Utility.Constants.*;

public class WrappedText {

    public String string;
    public int lines;
    public int textSize;
    public int textHeight;
    public float width;

    public WrappedText(String string, int lines, int textSize) {
        this.string = string;
        this.lines = lines;
        this.textSize = textSize;
        this.textHeight = lines * textSize;
    }

    public WrappedText(String string, int lines, int textSize, float width) {
        this.width = width;
        this.string = string;
        this.lines = lines;
        this.textSize = textSize;
        this.textHeight = lines * textSize;
    }

    /*Manually wrap text that goes over a certain width
    returns the number of lines it will take.
    */
    public static WrappedText wrapText(String string, float w, int textSize) {
        if (textSize == 0) {
            return new WrappedText("", 0, 1);
        }
        game.textSize(textSize);
        float charSize = game.textWidth("A"); //get width of a single character
        String newString = "";
        int numLines = 1;
        int lastSpace = 0;
        int lastNewLine = 0;

        for (int i = 0; i < string.length(); i ++) {
            if (string.charAt(i) == '\n') {
                newString += string.substring(lastNewLine, i + 1);
                numLines += 1;
                lastNewLine = i + 1;
            } else if (string.charAt(i) == ' ') {
                lastSpace = i;
            } else if (i == string.length() - 1) {
                newString += string.substring(lastNewLine, i + 1);
            } else if ((i - lastNewLine) * charSize >= w) {
                if (lastSpace > lastNewLine) {
                    newString += string.substring(lastNewLine, lastSpace);
                    lastNewLine = lastSpace + 1;
                } else {
                    newString += string.substring(lastNewLine, i + 1);
                    lastNewLine = i + 1;
                }
                newString += '\n';
                numLines += 1;
            }
        }

        return new WrappedText(newString, numLines, textSize, w);
    }
}