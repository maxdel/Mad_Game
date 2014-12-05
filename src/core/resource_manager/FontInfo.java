package core.resource_manager;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class FontInfo {
    private Font font;
    private TrueTypeFont ttf;

    public FontInfo(Font font, TrueTypeFont ttf) {
        this.font = font;
        this.ttf = ttf;
    }

    public TrueTypeFont getFont() {
        return ttf;
    }
}
