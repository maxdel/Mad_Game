package core.resourcemanager.tilemapadv;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class TiledMapAdv {
    private static boolean headless;
    protected int width;
    protected int height;
    protected int tileWidth;
    protected int tileHeight;
    protected String tilesLocation;
    protected Properties props;
    protected ArrayList tileSets;
    protected ArrayList layers;
    protected ArrayList objectGroups;
    protected static final int ORTHOGONAL = 1;
    protected static final int ISOMETRIC = 2;
    protected int orientation;
    private boolean loadMadTileSets;

    private static void setHeadless(boolean h) {
        headless = h;
    }

    public TiledMapAdv(String ref) throws SlickException {
        this(ref, true);
    }

    public TiledMapAdv(String ref, boolean loadMadTileSets) throws SlickException {
        this.tileSets = new ArrayList();
        this.layers = new ArrayList();
        this.objectGroups = new ArrayList();
        this.loadMadTileSets = true;
        this.loadMadTileSets = loadMadTileSets;
        ref = ref.replace('\\', '/');
        this.load(ResourceLoader.getResourceAsStream(ref), ref.substring(0, ref.lastIndexOf("/")));
    }

    public TiledMapAdv(String ref, String tileSetsLocation) throws SlickException {
        this.tileSets = new ArrayList();
        this.layers = new ArrayList();
        this.objectGroups = new ArrayList();
        this.loadMadTileSets = true;
        this.load(ResourceLoader.getResourceAsStream(ref), tileSetsLocation);
    }

    public TiledMapAdv(InputStream in) throws SlickException {
        this.tileSets = new ArrayList();
        this.layers = new ArrayList();
        this.objectGroups = new ArrayList();
        this.loadMadTileSets = true;
        this.load(in, "");
    }

    public TiledMapAdv(InputStream in, String tileSetsLocation) throws SlickException {
        this.tileSets = new ArrayList();
        this.layers = new ArrayList();
        this.objectGroups = new ArrayList();
        this.loadMadTileSets = true;
        this.load(in, tileSetsLocation);
    }

    public String getTilesLocation() {
        return this.tilesLocation;
    }

    public int getMadLayerIndex(String name) {
        boolean idx = false;

        for(int i = 0; i < this.layers.size(); ++i) {
            LayerAdv layer = (LayerAdv)this.layers.get(i);
            if(layer.name.equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public Image getTileImage(int x, int y, int layerIndex) {
        LayerAdv layer = (LayerAdv)this.layers.get(layerIndex);
        int tileSetIndex = layer.data[x][y][0];
        if(tileSetIndex >= 0 && tileSetIndex < this.tileSets.size()) {
            TileSetAdv tileSet = (TileSetAdv)this.tileSets.get(tileSetIndex);
            int sheetX = tileSet.getTileX(layer.data[x][y][1]);
            int sheetY = tileSet.getTileY(layer.data[x][y][1]);
            return tileSet.tiles.getSprite(sheetX, sheetY);
        } else {
            return null;
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileId(int x, int y, int layerIndex) {
        LayerAdv layer = (LayerAdv)this.layers.get(layerIndex);
        return layer.getTileID(x, y);
    }

    public void setTileId(int x, int y, int layerIndex, int tileid) {
        LayerAdv layer = (LayerAdv)this.layers.get(layerIndex);
        layer.setTileID(x, y, tileid);
    }

    public String getMapProperty(String propertyName, String def) {
        return this.props == null?def:this.props.getProperty(propertyName, def);
    }

    public String getMadLayerProperty(int layerIndex, String propertyName, String def) {
        LayerAdv layer = (LayerAdv)this.layers.get(layerIndex);
        return layer != null && layer.props != null?layer.props.getProperty(propertyName, def):def;
    }

    public String getTileProperty(int tileID, String propertyName, String def) {
        if(tileID == 0) {
            return def;
        } else {
            TileSetAdv set = this.findMadTileSet(tileID);
            Properties props = set.getProperties(tileID);
            return props == null?def:props.getProperty(propertyName, def);
        }
    }

    public void render(int x, int y) {
        this.render(x, y, 0, 0, this.width, this.height, false);
    }

    public void render(int x, int y, int layer) {
        this.render(x, y, 0, 0, this.getWidth(), this.getHeight(), layer, false);
    }

    public void render(int x, int y, int sx, int sy, int width, int height) {
        this.render(x, y, sx, sy, width, height, false);
    }

    public void render(int x, int y, int sx, int sy, int width, int height, int l, boolean lineByLine) {
        LayerAdv layer = (LayerAdv)this.layers.get(l);
        switch(this.orientation) {
            case 1:
                for(int ty = 0; ty < height; ++ty) {
                    layer.render(x, y, sx, sy, width, ty, lineByLine, this.tileWidth, this.tileHeight);
                }

                return;
            case 2:
                this.renderIsometricMap(x, y, sx, sy, width, height, layer, lineByLine);
        }

    }

    public void render(int x, int y, int sx, int sy, int width, int height, boolean lineByLine) {
        switch(this.orientation) {
            case 1:
                for(int ty = 0; ty < height; ++ty) {
                    for(int i = 0; i < this.layers.size(); ++i) {
                        LayerAdv layer = (LayerAdv)this.layers.get(i);
                        layer.render(x, y, sx, sy, width, ty, lineByLine, this.tileWidth, this.tileHeight);
                    }
                }

                return;
            case 2:
                this.renderIsometricMap(x, y, sx, sy, width, height, (LayerAdv)null, lineByLine);
        }

    }

    protected void renderIsometricMap(int x, int y, int sx, int sy, int width, int height, LayerAdv layer, boolean lineByLine) {
        ArrayList drawMadLayers = this.layers;
        if(layer != null) {
            drawMadLayers = new ArrayList();
            drawMadLayers.add(layer);
        }

        int maxCount = width * height;
        int allCount = 0;
        boolean allProcessed = false;
        int initialLineX = x;
        int initialLineY = y;
        int startLineTileX = 0;
        int startLineTileY = 0;

        while(!allProcessed) {
            int currentTileX = startLineTileX;
            int currentTileY = startLineTileY;
            int currentLineX = initialLineX;
            boolean min = false;
            int var24;
            if(height > width) {
                var24 = startLineTileY < width - 1?startLineTileY:(width - startLineTileX < height?width - startLineTileX - 1:width - 1);
            } else {
                var24 = startLineTileY < height - 1?startLineTileY:(width - startLineTileX < height?width - startLineTileX - 1:height - 1);
            }

            for(int burner = 0; burner <= var24; ++burner) {
                for(int layerIdx = 0; layerIdx < drawMadLayers.size(); ++layerIdx) {
                    LayerAdv currentLayerAdv = (LayerAdv)drawMadLayers.get(layerIdx);
                    currentLayerAdv.render(currentLineX, initialLineY, currentTileX, currentTileY, 1, 0, lineByLine, this.tileWidth, this.tileHeight);
                }

                currentLineX += this.tileWidth;
                ++allCount;
                ++currentTileX;
                --currentTileY;
            }

            if(startLineTileY < height - 1) {
                ++startLineTileY;
                initialLineX -= this.tileWidth / 2;
                initialLineY += this.tileHeight / 2;
            } else {
                ++startLineTileX;
                initialLineX += this.tileWidth / 2;
                initialLineY += this.tileHeight / 2;
            }

            if(allCount >= maxCount) {
                allProcessed = true;
            }
        }

    }

    public int getMadLayerCount() {
        return this.layers.size();
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException var3) {
            return 0;
        }
    }

    private void load(InputStream in, String tileSetsLocation) throws SlickException {
        this.tilesLocation = tileSetsLocation;

        try {
            DocumentBuilderFactory e = DocumentBuilderFactory.newInstance();
            e.setValidating(false);
            DocumentBuilder builder = e.newDocumentBuilder();
            builder.setEntityResolver(new EntityResolver() {
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    return new InputSource(new ByteArrayInputStream(new byte[0]));
                }
            });
            Document doc = builder.parse(in);
            Element docElement = doc.getDocumentElement();
            if(docElement.getAttribute("orientation").equals("orthogonal")) {
                this.orientation = 1;
            } else {
                this.orientation = 2;
            }

            this.width = this.parseInt(docElement.getAttribute("width"));
            this.height = this.parseInt(docElement.getAttribute("height"));
            this.tileWidth = this.parseInt(docElement.getAttribute("tilewidth"));
            this.tileHeight = this.parseInt(docElement.getAttribute("tileheight"));
            Element propsElement = (Element)docElement.getElementsByTagName("properties").item(0);
            NodeList layerNodes;
            int objectGroupNodes;
            Element i;
            if(propsElement != null) {
                layerNodes = propsElement.getElementsByTagName("property");
                if(layerNodes != null) {
                    this.props = new Properties();

                    for(objectGroupNodes = 0; objectGroupNodes < layerNodes.getLength(); ++objectGroupNodes) {
                        i = (Element)layerNodes.item(objectGroupNodes);
                        String current = i.getAttribute("name");
                        String objectGroup = i.getAttribute("value");
                        this.props.setProperty(current, objectGroup);
                    }
                }
            }

            if(this.loadMadTileSets) {
                layerNodes = null;
                TileSetAdv var15 = null;
                NodeList var16 = docElement.getElementsByTagName("tileset");

                for(int var19 = 0; var19 < var16.getLength(); ++var19) {
                    Element var21 = (Element)var16.item(var19);
                    TileSetAdv var14 = new TileSetAdv(this, var21, !headless);
                    var14.index = var19;
                    if(var15 != null) {
                        var15.setLimit(var14.firstGID - 1);
                    }

                    var15 = var14;
                    this.tileSets.add(var14);
                }
            }

            layerNodes = docElement.getElementsByTagName("layer");

            for(objectGroupNodes = 0; objectGroupNodes < layerNodes.getLength(); ++objectGroupNodes) {
                i = (Element)layerNodes.item(objectGroupNodes);
                LayerAdv var20 = new LayerAdv(this, i);
                var20.index = objectGroupNodes;
                this.layers.add(var20);
            }

            NodeList var17 = docElement.getElementsByTagName("objectgroup");

            for(int var18 = 0; var18 < var17.getLength(); ++var18) {
                Element var22 = (Element)var17.item(var18);
                TiledMapAdv.ObjectGroup var23 = new TiledMapAdv.ObjectGroup(var22);
                var23.index = var18;
                this.objectGroups.add(var23);
            }

        } catch (Exception var13) {
            Log.error(var13);
            throw new SlickException("Failed to parse tilemap", var13);
        }
    }

    public int getMadTileSetCount() {
        return this.tileSets.size();
    }

    public TileSetAdv getMadTileSet(int index) {
        return (TileSetAdv)this.tileSets.get(index);
    }

    public TileSetAdv getMadTileSetByGID(int gid) {
        for(int i = 0; i < this.tileSets.size(); ++i) {
            TileSetAdv set = (TileSetAdv)this.tileSets.get(i);
            if(set.contains(gid)) {
                return set;
            }
        }

        return null;
    }

    public TileSetAdv findMadTileSet(int gid) {
        for(int i = 0; i < this.tileSets.size(); ++i) {
            TileSetAdv set = (TileSetAdv)this.tileSets.get(i);
            if(set.contains(gid)) {
                return set;
            }
        }

        return null;
    }

    protected void renderedLine(int visualY, int mapY, int layer) {
    }

    public int getObjectGroupCount() {
        return this.objectGroups.size();
    }

    public int getObjectCount(int groupID) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            return grp.objects.size();
        } else {
            return -1;
        }
    }

    public String getObjectName(int groupID, int objectID) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            if(objectID >= 0 && objectID < grp.objects.size()) {
                TiledMapAdv.GroupObject object = (TiledMapAdv.GroupObject)grp.objects.get(objectID);
                return object.name;
            }
        }

        return null;
    }

    public String getObjectType(int groupID, int objectID) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            if(objectID >= 0 && objectID < grp.objects.size()) {
                TiledMapAdv.GroupObject object = (TiledMapAdv.GroupObject)grp.objects.get(objectID);
                return object.type;
            }
        }

        return null;
    }

    public double getObjectX(int groupID, int objectID) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            if(objectID >= 0 && objectID < grp.objects.size()) {
                TiledMapAdv.GroupObject object = (TiledMapAdv.GroupObject)grp.objects.get(objectID);
                return object.x;
            }
        }

        return -1;
    }

    public double getObjectY(int groupID, int objectID) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            if(objectID >= 0 && objectID < grp.objects.size()) {
                TiledMapAdv.GroupObject object = (TiledMapAdv.GroupObject)grp.objects.get(objectID);
                return object.y;
            }
        }

        return -1;
    }

    public int getObjectWidth(int groupID, int objectID) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            if(objectID >= 0 && objectID < grp.objects.size()) {
                TiledMapAdv.GroupObject object = (TiledMapAdv.GroupObject)grp.objects.get(objectID);
                return object.width;
            }
        }

        return -1;
    }

    public int getObjectHeight(int groupID, int objectID) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            if(objectID >= 0 && objectID < grp.objects.size()) {
                TiledMapAdv.GroupObject object = (TiledMapAdv.GroupObject)grp.objects.get(objectID);
                return object.height;
            }
        }

        return -1;
    }

    public double getObjectRotation(int groupID, int objectID) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            if(objectID >= 0 && objectID < grp.objects.size()) {
                TiledMapAdv.GroupObject object = (TiledMapAdv.GroupObject)grp.objects.get(objectID);
                return object.rotation;
            }
        }

        return 0;
    }

    public String getObjectImage(int groupID, int objectID) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            if(objectID >= 0 && objectID < grp.objects.size()) {
                TiledMapAdv.GroupObject object = (TiledMapAdv.GroupObject)grp.objects.get(objectID);
                if(object == null) {
                    return null;
                }

                return object.image;
            }
        }

        return null;
    }

    public String getObjectProperty(int groupID, int objectID, String propertyName, String def) {
        if(groupID >= 0 && groupID < this.objectGroups.size()) {
            TiledMapAdv.ObjectGroup grp = (TiledMapAdv.ObjectGroup)this.objectGroups.get(groupID);
            if(objectID >= 0 && objectID < grp.objects.size()) {
                TiledMapAdv.GroupObject object = (TiledMapAdv.GroupObject)grp.objects.get(objectID);
                if(object == null) {
                    return def;
                }

                if(object.props == null) {
                    return def;
                }

                return object.props.getProperty(propertyName, def);
            }
        }

        return def;
    }

    protected class GroupObject {
        public int index;
        public String name;
        public String type;
        public double x;
        public double y;
        public int width;
        public int height;
        public double rotation;
        private String image;
        public Properties props;

        public GroupObject(Element element) throws SlickException {
            this.name = element.getAttribute("name");
            this.type = element.getAttribute("type");
            this.x = Double.parseDouble(element.getAttribute("x"));
            this.y = Double.parseDouble(element.getAttribute("y"));
            this.width = Integer.parseInt(element.getAttribute("width").equals("") ? "0" : element.getAttribute("width"));
            this.height = Integer.parseInt(element.getAttribute("height").equals("") ? "0" : element.getAttribute("height"));
            this.rotation = Double.parseDouble(element.getAttribute("rotation").equals("") ? "0" : element.getAttribute("rotation"));
            Element imageElement = (Element)element.getElementsByTagName("image").item(0);
            if(imageElement != null) {
                this.image = imageElement.getAttribute("source");
            }

            Element propsElement = (Element)element.getElementsByTagName("properties").item(0);
            if(propsElement != null) {
                NodeList properties = propsElement.getElementsByTagName("property");
                if(properties != null) {
                    this.props = new Properties();

                    for(int p = 0; p < properties.getLength(); ++p) {
                        Element propElement = (Element)properties.item(p);
                        String name = propElement.getAttribute("name");
                        String value = propElement.getAttribute("value");
                        this.props.setProperty(name, value);
                    }
                }
            }

        }
    }

    protected class ObjectGroup {
        public int index;
        public String name;
        public ArrayList objects;
        public int width;
        public int height;
        public Properties props;

        public ObjectGroup(Element element) throws SlickException {
            this.name = element.getAttribute("name");
            this.width = Integer.parseInt(element.getAttribute("width").equals("") ? "0" : element.getAttribute("width"));
            this.height = Integer.parseInt(element.getAttribute("height").equals("") ? "0" : element.getAttribute("height"));
            this.objects = new ArrayList();
            Element propsElement = (Element)element.getElementsByTagName("properties").item(0);
            NodeList objectNodes;
            int i;
            Element objElement;
            if(propsElement != null) {
                objectNodes = propsElement.getElementsByTagName("property");
                if(objectNodes != null) {
                    this.props = new Properties();

                    for(i = 0; i < objectNodes.getLength(); ++i) {
                        objElement = (Element)objectNodes.item(i);
                        String object = objElement.getAttribute("name");
                        String value = objElement.getAttribute("value");
                        this.props.setProperty(object, value);
                    }
                }
            }

            objectNodes = element.getElementsByTagName("object");

            for(i = 0; i < objectNodes.getLength(); ++i) {
                objElement = (Element)objectNodes.item(i);
                TiledMapAdv.GroupObject var9 = TiledMapAdv.this.new GroupObject(objElement);
                var9.index = i;
                this.objects.add(var9);
            }

        }
    }

}