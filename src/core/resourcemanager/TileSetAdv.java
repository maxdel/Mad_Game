package core.resourcemanager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TileSetAdv {
    private final TiledMapAdv map;
    public int index;
    public String name;
    public int firstGID;
    public int lastGID = 2147483647;
    public int tileWidth;
    public int tileHeight;
    public SpriteSheet tiles;
    public int tilesAcross;
    public int tilesDown;
    private HashMap props = new HashMap();
    protected int tileSpacing = 0;
    protected int tileMargin = 0;

    public TileSetAdv(TiledMapAdv map, Element element, boolean loadImage) throws SlickException {
        this.map = map;
        this.name = element.getAttribute("name");
        this.firstGID = Integer.parseInt(element.getAttribute("firstgid"));
        String source = element.getAttribute("source");
        if(source != null && !source.equals("")) {
            try {
                InputStream tileWidthString = ResourceLoader.getResourceAsStream(map.getTilesLocation() + "/" + source);
                DocumentBuilder tileHeightString = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document sv = tileHeightString.parse(tileWidthString);
                Element mv = sv.getDocumentElement();
                element = mv;
            } catch (Exception var25) {
                Log.error(var25);
                throw new SlickException("Unable to load or parse sourced tileset: " + this.map.tilesLocation + "/" + source);
            }
        }

        String var26 = element.getAttribute("tilewidth");
        String var27 = element.getAttribute("tileheight");
        if(var26.length() != 0 && var27.length() != 0) {
            this.tileWidth = Integer.parseInt(var26);
            this.tileHeight = Integer.parseInt(var27);
            String var28 = element.getAttribute("spacing");
            if(var28 != null && !var28.equals("")) {
                this.tileSpacing = Integer.parseInt(var28);
            }

            String var29 = element.getAttribute("margin");
            if(var29 != null && !var29.equals("")) {
                this.tileMargin = Integer.parseInt(var29);
            }

            NodeList list = element.getElementsByTagName("image");
            Element imageNode = (Element)list.item(0);
            String ref = imageNode.getAttribute("source");
            Color trans = null;
            String t = imageNode.getAttribute("trans");
            if(t != null && t.length() > 0) {
                int pElements = Integer.parseInt(t, 16);
                trans = new Color(pElements);
            }

            if(loadImage) {
                Image var30 = new Image(map.getTilesLocation() + "/" + ref, false, 2, trans);
                this.setMadTileSetImage(var30);
            }

            NodeList var31 = element.getElementsByTagName("tile");

            for(int i = 0; i < var31.getLength(); ++i) {
                Element tileElement = (Element)var31.item(i);
                int id = Integer.parseInt(tileElement.getAttribute("id"));
                id += this.firstGID;
                Properties tileProps = new Properties();
                Element propsElement = (Element)tileElement.getElementsByTagName("properties").item(0);
                NodeList properties = propsElement.getElementsByTagName("property");

                for(int p = 0; p < properties.getLength(); ++p) {
                    Element propElement = (Element)properties.item(p);
                    String name = propElement.getAttribute("name");
                    String value = propElement.getAttribute("value");
                    tileProps.setProperty(name, value);
                }

                this.props.put(new Integer(id), tileProps);
            }

        } else {
            throw new SlickException("TiledMapAdv requires that the map be created with tilesets that use a single image.  Check the WiKi for more complete information.");
        }
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public int getTileSpacing() {
        return this.tileSpacing;
    }

    public int getTileMargin() {
        return this.tileMargin;
    }

    public void setMadTileSetImage(Image image) {
        this.tiles = new SpriteSheet(image, this.tileWidth, this.tileHeight, this.tileSpacing, this.tileMargin);
        this.tilesAcross = this.tiles.getHorizontalCount();
        this.tilesDown = this.tiles.getVerticalCount();
        if(this.tilesAcross <= 0) {
            this.tilesAcross = 1;
        }

        if(this.tilesDown <= 0) {
            this.tilesDown = 1;
        }

        this.lastGID = this.tilesAcross * this.tilesDown + this.firstGID - 1;
    }

    public Properties getProperties(int globalID) {
        return (Properties)this.props.get(new Integer(globalID));
    }

    public int getTileX(int id) {
        return id % this.tilesAcross;
    }

    public int getTileY(int id) {
        return id / this.tilesAcross;
    }

    public void setLimit(int limit) {
        this.lastGID = limit;
    }

    public boolean contains(int gid) {
        return gid >= this.firstGID && gid <= this.lastGID;
    }
}
