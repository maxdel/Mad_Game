package core.view;

import core.model.Authors;
import core.resourcemanager.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.TrueTypeFont;

public class AuthorsView {

    private Authors authors;
    private TrueTypeFont font;

    public AuthorsView(Authors authors) {
        this.authors = authors;
        font = ResourceManager.getInstance().getFont("menufont");

    }
    public void render(GameContainer gc) {
        font.drawString(gc.getWidth() / 2 - font.getWidth(authors.getTitle()) / 2, 50, authors.getTitle(), Color.red);

        font.drawString(gc.getWidth()/2 - gc.getWidth()/4 - font.getWidth(authors.getAuthorsNames().get(0)) / 2,
               150, authors.getAuthorsNames().get(0), Color.orange);

        font.drawString(gc.getWidth()/2 + gc.getWidth()/4 - font.getWidth(authors.getAuthorsNames().get(0)) / 2,
               150, authors.getAuthorsNames().get(1), Color.orange);
        font.drawString(gc.getWidth()/2 - font.getWidth(authors.getBody()) / 2, 250, authors.getBody(), Color.green);
    }
}
