package core.resourcemanager;//

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LayerAdv {
    private static byte[] baseCodes = new byte[256];
    private final TiledMapAdv map;
    public int index;
    public String name;
    public int[][][] data;
    public int width;
    public int height;
    public Properties props;

    public LayerAdv(TiledMapAdv map, Element element) throws SlickException {
        this.map = map;
        this.name = element.getAttribute("name");
        this.width = Integer.parseInt(element.getAttribute("width"));
        this.height = Integer.parseInt(element.getAttribute("height"));
        this.data = new int[this.width][this.height][3];
        Element propsElement = (Element)element.getElementsByTagName("properties").item(0);
        if(propsElement != null) {
            NodeList dataNode = propsElement.getElementsByTagName("property");
            if(dataNode != null) {
                this.props = new Properties();

                for(int encoding = 0; encoding < dataNode.getLength(); ++encoding) {
                    Element compression = (Element)dataNode.item(encoding);
                    String e = compression.getAttribute("name");
                    String enc = compression.getAttribute("value");
                    this.props.setProperty(e, enc);
                }
            }
        }

        Element var16 = (Element)element.getElementsByTagName("data").item(0);
        String var17 = var16.getAttribute("encoding");
        String var18 = var16.getAttribute("compression");
        if(var17.equals("base64") && var18.equals("gzip")) {
            try {
                Node var19 = var16.getFirstChild();
                char[] var20 = var19.getNodeValue().trim().toCharArray();
                byte[] dec = this.decodeBase64(var20);
                GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(dec));

                for(int y = 0; y < this.height; ++y) {
                    for(int x = 0; x < this.width; ++x) {
                        byte tileId = 0;
                        int var21 = tileId | is.read();
                        var21 |= is.read() << 8;
                        var21 |= is.read() << 16;
                        var21 |= is.read() << 24;
                        if(var21 == 0) {
                            this.data[x][y][0] = -1;
                            this.data[x][y][1] = 0;
                            this.data[x][y][2] = 0;
                        } else {
                            TileSetAdv set = map.findMadTileSet(var21);
                            if(set != null) {
                                this.data[x][y][0] = set.index;
                                this.data[x][y][1] = var21 - set.firstGID;
                            }

                            this.data[x][y][2] = var21;
                        }
                    }
                }

            } catch (IOException var15) {
                Log.error(var15);
                throw new SlickException("Unable to decode base 64 block");
            }
        } else {
            throw new SlickException("Unsupport tiled map type: " + var17 + "," + var18 + " (only gzip base64 supported)");
        }
    }

    public int getTileID(int x, int y) {
        return this.data[x][y][2];
    }

    public void setTileID(int x, int y, int tile) {
        if(tile == 0) {
            this.data[x][y][0] = -1;
            this.data[x][y][1] = 0;
            this.data[x][y][2] = 0;
        } else {
            TileSetAdv set = this.map.findMadTileSet(tile);
            this.data[x][y][0] = set.index;
            this.data[x][y][1] = tile - set.firstGID;
            this.data[x][y][2] = tile;
        }

    }

    public void render(int x, int y, int sx, int sy, int width, int ty, boolean lineByLine, int mapTileWidth, int mapTileHeight) {
        for(int tileset = 0; tileset < this.map.getMadTileSetCount(); ++tileset) {
            TileSetAdv set = null;

            for(int tx = 0; tx < width; ++tx) {
                if(sx + tx >= 0 && sy + ty >= 0 && sx + tx < this.width && sy + ty < this.height && this.data[sx + tx][sy + ty][0] == tileset) {
                    if(set == null) {
                        set = this.map.getMadTileSet(tileset);
                        set.tiles.startUse();
                    }

                    int sheetX = set.getTileX(this.data[sx + tx][sy + ty][1]);
                    int sheetY = set.getTileY(this.data[sx + tx][sy + ty][1]);
                    int tileOffsetY = set.tileHeight - mapTileHeight;
                    set.tiles.renderInUse(x + tx * mapTileWidth, y + ty * mapTileHeight - tileOffsetY, sheetX, sheetY);
                }
            }

            if(lineByLine) {
                if(set != null) {
                    set.tiles.endUse();
                    set = null;
                }

                this.map.renderedLine(ty, ty + sy, this.index);
            }

            if(set != null) {
                set.tiles.endUse();
            }
        }

    }

    private byte[] decodeBase64(char[] data) {
        int temp = data.length;

        int len;
        for(len = 0; len < data.length; ++len) {
            if(data[len] > 255 || baseCodes[data[len]] < 0) {
                --temp;
            }
        }

        len = temp / 4 * 3;
        if(temp % 4 == 3) {
            len += 2;
        }

        if(temp % 4 == 2) {
            ++len;
        }

        byte[] out = new byte[len];
        int shift = 0;
        int accum = 0;
        int index = 0;

        for(int ix = 0; ix < data.length; ++ix) {
            byte value = data[ix] > 255?-1:baseCodes[data[ix]];
            if(value >= 0) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if(shift >= 8) {
                    shift -= 8;
                    out[index++] = (byte)(accum >> shift & 255);
                }
            }
        }

        if(index != out.length) {
            throw new RuntimeException("Data length appears to be wrong (wrote " + index + " should be " + out.length + ")");
        } else {
            return out;
        }
    }

    static {
        int i;
        for(i = 0; i < 256; ++i) {
            baseCodes[i] = -1;
        }

        for(i = 65; i <= 90; ++i) {
            baseCodes[i] = (byte)(i - 65);
        }

        for(i = 97; i <= 122; ++i) {
            baseCodes[i] = (byte)(26 + i - 97);
        }

        for(i = 48; i <= 57; ++i) {
            baseCodes[i] = (byte)(52 + i - 48);
        }

        baseCodes[43] = 62;
        baseCodes[47] = 63;
    }
}
