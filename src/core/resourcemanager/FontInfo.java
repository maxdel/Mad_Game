package core.resourcemanager;

import org.newdawn.slick.TrueTypeFont;

public class FontInfo {

    private TrueTypeFont ttf;

    public FontInfo(TrueTypeFont ttf) {
        this.ttf = ttf;
    }

    public TrueTypeFont getFont() {
        return ttf;
    }

}
