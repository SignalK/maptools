/**
 * Project: capcode.basics KAPParser.java created 7 Aug 2008 by rosaycy
 * Copyright (c) 2007 Capcode Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form SKmust reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * - Neither the name of Capcode, Inc. or the names of authors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * - This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. CAPCODE AND ITS
 * LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A
 * RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 * IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT
 * OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR
 * PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY,
 * ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF CAPCODE HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * - You acknowledge that this software is not designed, licensed or intended for
 * use in the design, construction, operation or maintenance of any nuclear
 * facility or airborne software.
 * - You acknowledge that using this software for the purpose of navigation does not
 * engage the developer responsability in case of damage, injuries or casualities due
 * to the use of this software.
 */
/**
 * CVS markups
 * last update by : $Author: crosay $
 * $Date: 2009-11-02 00:10:37 $
 * $Header: /media/disk/capcodeCVSbackup/capcode.basics/src/capcode/maps/files/KAPParser.java,v 1.30 2009-11-02 00:10:37 crosay Exp $
 * $Log: not supported by cvs2svn $
 * Revision 1.29 2009/09/22 22:31:04 crosay
 * Complete # Error in chart editor
 * http://sourceforge.net/apps/trac/capcode/ticket/35
 *
 * Revision 1.28 2009/09/13 12:10:39 crosay
 * Incomplete # wordwind plugin for capcode
 * http://sourceforge.net/apps/trac/capcode/ticket/6
 *
 * Revision 1.27 2009/09/03 21:49:20 crosay
 * Incomplete # change the logging of event
 * http://sourceforge.net/apps/trac/capcode/ticket/33
 *
 * Revision 1.26 2009/08/31 22:18:08 crosay
 * Incomplete # preparation for delivery to version 0.9.3
 * http://sourceforge.net/apps/trac/capcode/ticket/17
 *
 * Revision 1.25 2009/08/28 23:34:21 crosay
 * Complete # add reference points to maps
 * http://sourceforge.net/apps/trac/capcode/ticket/22
 *
 * Revision 1.24 2009/08/25 22:33:59 crosay
 * Complete # add reference points to maps
 * http://sourceforge.net/apps/trac/capcode/ticket/22
 * Incomplete # move the display of the VMG arrow from map display plugin to polar display plugin
 * http://sourceforge.net/apps/trac/capcode/ticket/29
 *
 * Revision 1.23 2009/08/15 15:12:33 crosay
 * Complete # add reference points to maps
 * http://sourceforge.net/apps/trac/capcode/ticket/22
 *
 * Revision 1.22 2009/08/02 22:18:20 crosay
 * Complete # update the dispay algorithm for the boat trace
 * http://sourceforge.net/apps/trac/capcode/ticket/12
 *
 * Revision 1.21 2009/07/19 09:28:48 crosay
 * Incomplete task federate all maps directories
 *
 * Incomplete task optimisation of the display
 *
 * Revision 1.20 2009/07/01 22:10:13 crosay
 * Complete - taskSourceForge.net: CapCode-software suite for sailors: Detail: 2798790 - solve bug 2798789 error with some BSB 2 maps
 * https://sourceforge.net/tracker/?func=detail&aid=2798790&group_id=100698&atid=1045270
 *
 * Revision 1.19 2009/06/22 17:04:38 crosay
 * Incomplete - taskSourceForge.net: CapCode-software suite for sailors: Detail: 2798790 - solve bug 2798789 error with some BSB 2 maps
 * https://sourceforge.net/tracker/?func=detail&aid=2798790&group_id=100698&atid=1045270
 *
 * Revision 1.18 2009/01/29 10:31:28 crosay
 * Incomplete - task add gps hypbrid mode (in true wind computation) task 2533241 GPS speed spare mode
 * https://sourceforge.net/tracker2/?func=detail&aid=2533241&group_id=100698&atid=1045270
 * Complete - task save status and position of toolbars
 * https://sourceforge.net/tracker2/?func=detail&aid=2531530&group_id=100698&atid=1045270
 *
 * Revision 1.17 2009/01/24 20:52:36 crosay
 * Complete - taskRemoval of sqlite
 *
 * Complete - tasksuppression of debug display for preference saving
 *
 * Complete - tasktask 2531747 animation of the wearther
 * https://sourceforge.net/tracker2/?func=detail&aid=2024403&group_id=100698&atid=1045270
 *
 * Revision 1.16 2009/01/13 18:04:49 crosay
 * correction of code after unit test:
 * - SystemUtils: added exception in case of property not found
 * - SpeedPolar, SpeedPolarFile, WindandSpeedProcess: management of this exception
 *
 * Correction of a bug in Coords management (DMS function)
 *
 * Revision 1.15 2008/12/19 16:04:59 crosay
 * Complete - task optimisation of map reading
 *
 * Revision 1.14 2008/12/15 15:16:06 crosay
 * Incomplete - task optimisation of map reading
 *
 * Revision 1.13 2008/11/10 10:42:05 crosay
 * undocumented changes (code washup)
 *
 * Revision 1.12 2008/10/10 21:00:36 crosay
 * automatic selection of the best map to display
 *
 * Revision 1.11 2008/09/17 10:59:53 crosay
 * added day/night color selection
 *
 * Revision 1.10 2008/09/14 21:30:50 crosay
 * added day/night color selection
 *
 * Revision 1.9 2008/09/12 19:28:55 crosay
 * code cleaning
 *
 * Revision 1.8 2008/09/11 15:29:56 crosay
 * task 2042679 (BSB Maps format) continued
 * finalisation
 *
 * Revision 1.7 2008/08/23 19:32:46 crosay
 * task 2042679 (BSB Maps format) continued
 * added class Sector
 *
 * Revision 1.6 2008/08/18 21:50:16 crosay
 * task 2042679 (BSB Maps format) continued
 * added mapping functions
 *
 * Revision 1.5 2008/08/14 13:37:11 crosay
 * task 2042679 (BSB Maps format) continued
 * validating optimisation
 *
 * Revision 1.4 2008/08/10 19:38:42 crosay
 * task 2042679 (BSB Maps format) continued
 * validating optimisation
 *
 * Revision 1.3 2008/08/10 19:11:56 crosay
 * task 2042679 (BSB Maps format) continued
 * trying some optimisation for the decoding
 *
 * Revision 1.2 2008/08/09 12:38:16 crosay
 * task 2042679 (BSB Maps format) continued
 *
 * Revision 1.1 2008/08/08 09:22:10 crosay
 * start implementing task 2042679 (BSB Maps format)
 * creation of new classes
 * BSBParser and KAPParser
 * the embedded class Refrence in CcMapXmlParser is now MapReference
 * CcMapXMLParser changed into CcMapParser
 * CcMapViewer moved to net.sourceforge.capcode.maps.viewers
 *
 * $Revision: 1.30 $
 * $State: Exp $
 */
package org.signalk.maptools;

import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * Class used to decode KAP files. typical use: <code>
 *   KAPParser p = new KAPParser(filename);
 *   BufferedImage image = p.getImage();
 * </code>
 *
 * @author rosaycy
 *
 */
public class KAPParser {

    /**
     * define the palette used for this map.
     */
    private class ColorPalette {

        public byte[] reds;
        public byte[] greens;
        public byte[] blues;
        public int nbColors = 0;

        /**
         * define the sizes of all components but DO NOT define nbColors.
         * nbColors is to be defined later, when reading the kap rgb section.
         *
         * @param aSize
         */
        public ColorPalette(int aSize) {
            reds = new byte[aSize];
            greens = new byte[aSize];
            blues = new byte[aSize];
        }

        public ColorPalette(IndexColorModel icm) {
            this(icm.getMapSize());
            nbColors = icm.getMapSize();
            icm.getReds(reds);
            icm.getBlues(blues);
            icm.getGreens(greens);
        }

        private short toUnsignedByte(byte v) {
            return (short) (v < 0 ? 128 + (v & 0x7F) : v);
        }

        public short[] toUnsignedBytes(int i) {
            return (i < reds.length) ? new short[]{toUnsignedByte(reds[i]), toUnsignedByte(greens[i]), toUnsignedByte(blues[i])} : new short[]{0, 0, 0};
        }
    }

    private static Logger logger = Logger.getLogger(KAPParser.class);
    private double version = 3.0;
    private String mapName = "no name";
    private String fileName;
    private String mapNum = "0"; //$NON-NLS-1$
    protected int mapWidthPixels = 0;
    protected int mapHeightPixels = 0;
//    private int rawMapWidthPixels = 0;
//    private int rawMapHeightPixels = 0;
    private int resolutionDPI = 1;
    /**
     * the scale as read in the KAP file header
     */
    private int mapFileScale = 0;
    private String datum = "unknown";
    private String projection = "undefined";
    private String projectionParameter = "unknown";
    /**
     * the number of image lines between strip offset values
     */
    private int ost = 1;
    // private ByteBuffer buffer = null;
    private final int imageFormat = BufferedImage.TYPE_INT_RGB;
    private BufferedImage image = null;
    // TODO perform a ROT-9 conversion in case of encrypted file
    private final boolean encrypted = false;
    private AffineTransform tx;
    /**
     * IFM records define the SB compression type of the binary image data.<br>
     * Where supported CompressionType values and their interpretation are:<br>
     * 1 NOS Compression Type 1, 1 bit color (Bilevel, 2 colors)<br>
     * 2 NOS Compression Type 2, 2 bit color (2 or 3 colors)<br>
     * 3 NOS Compression Type 3, 3 bit color (4 to 7 colors)<br>
     * 4 NOS Compression Type 4, 4 bit color (8 to 15 colors)<br>
     * 5 NOS Compression Type 5, 5 bit color (16 to 31colors)<br>
     * 6 NOS Compression Type 6, 6 bit color (32 to 63 colors)<br>
     * 7 NOS Compression Type 7, more than 63 colors<br>
     */
    private int ifm = 7;
    /**
     * RGB records define a default color palette to be used when graphically
     * representing to binary image data. <br>
     * Each image file header must contain a minimum of two RGB records.<br>
     * Each RGB record defines the color palette index and the red, green, and
     * blue additive color values assigned to that palette index meaning. <br>
     * Color values range from 0 to 255 and relate directly to percentages where
     * 0=0%, 127=50%, and 255=100%. <br>
     * An RGB record defines the color parameters in the following manner:
     * RGB/PaletteIndex,Red,Green,Blue Where:<br>
     * PaletteIndex defines the palette index.<br>
     * Red defines the additive red component (0-255)<br>
     * Green defines the additive green component (0-255)<br>
     * Blue defines the additive blue component (0-255)<br>
     * The red, green, and blue color values contained within the palette <br>
     * for the binary image data are recommended display colors for the BSB
     * image file.<br>
     * They may be remapped to other more appropriate colors <br>
     * (night readable, monochrome, gray scale, etc.) <br>
     * for a specific software or hardware display environment.<br>
     */
    private ColorPalette defaultPalette, dayPalette, nightPalette;
    private final Vector<MapReference> references;
    private final Vector<Position> displayLimits; //displayLimits - the edges of the map, not including border
    // corrective phase factor
    private double correctiveLongitudePhase = 0;

    enum Mode {
        DEFAULT, BSB, KNP, WPX, WPY, PWX, PWY, CED, NTM
    }

    private int offsetSection = 0;

    private Rectangle bounds;
    private Rectangle visibleBounds;
    private Sector mapUseableSector;
    /**
     * boolean headerLoaded: true if the KAP file header has been loaded.
     */
    private boolean mapReady = false;
    private int marginLeft = Integer.MAX_VALUE;
    private int marginRight = 0;
    private int marginTop = Integer.MAX_VALUE;
    private int marginBottom = 0;

    private boolean wpxDone = false;
    private boolean wpyDone = false;
    private boolean pwxDone = false;
    private boolean pwyDone = false;
    private boolean polynomLoaded = false;
    private double latitudeShift = 0;
    private double longitudeShift = 0;
    private int rasterDataOffset = -1;
    // first line number. It should be 1 but it seems that some charts starts at
    // 0
    private byte offsetY = 0;
    private final Vector<String> comments;
    private String PI = ""; //$NON-NLS-1$
    private String SP = ""; //$NON-NLS-1$
    private float SK = 0;
    private String SD = ""; //$NON-NLS-1$
    private String UN = ""; //$NON-NLS-1$
    private float DY = 0;
    private float DX = 0;
    private String SE = ""; //$NON-NLS-1$
    private String RE = ""; //$NON-NLS-1$
    private String ED = ""; //$NON-NLS-1$
    @SuppressWarnings("unused")
    private String NE = ""; //$NON-NLS-1$
    private String ND = ""; //$NON-NLS-1$
    private String BD = ""; //$NON-NLS-1$
    private CoordsPolynomTranslator coordTranslator;

    /**
     * @return true is the map is ready for display.
     */
    // @Override
    public boolean isChartReady() {
        return mapReady;
    }

    /**
     * constructor. the load MUST pass at least a KAP fileName.
     */
    public KAPParser() {
        super();
        references = new Vector<MapReference>(5);
        mapReady = false;
        comments = new Vector<String>(5);
        displayLimits = new Vector<Position>(4);
    }

    /**
     * find the lines offset section of the file. located at the end of the
     * file. as data is coded on 4 bytes, the first entry must starts with 2
     * nulls. from the algorithm principle, they cannot be two null bytes in the
     * raster section. so the research algorith is to detect the first occurence
     * of 2 null bytes.
     *
     * @return
     */
    private synchronized int findOffsetSection(ByteBuffer buffer) {
        int res = 0;
        if (buffer != null) {
            byte[] array = buffer.array();
            int length = buffer.capacity();
            for (int i = 0; i < length; i++) {
                if (array[i] == 0 && array[i + 1] == 0 && array[i + 2] == 0) {
                    res = i + 1;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * constructor. no argument is passed. the load MUST pass at least a KAP
     * fileName.
     *
     * @throws Exception
     */
    public KAPParser(String fileName) throws Exception {
        this();
        this.fileName = fileName;
        loadHeader();
    }

    /**
     * retrieves the address of a line in the raster format. This is done by
     * reading the Strip Offset Values section<br>
     * <u><b>The Strip Offset Values</u></b> section of a BSB image file
     * contains strip offset values which define pointers which directly
     * reference lines within the binary image data. A strip within an image
     * consists of 1 image line. Each strip offset meaning points directly to
     * the byte offset at the start of the referenced strip, relative to the
     * start of the file having offset compression form. Strip offset tags are
     * coded as <b>four sequential bytes</b>.<br>
     * The final string in the NOS image file points to the first of these tags.
     * The first tag points to the first raster line, or group if so specified
     * in the header. Since each tag is four bytes, it is easy to locate the
     * address of the pointers to any line.<br>
     * Note certain information redundancies of the raster data file: every line
     * is identified by an offset tag.
     *
     * @param line
     * @return
     */
    public synchronized int getLineAddress(ByteBuffer buffer, int line) {
        int res = -1;

        byte[] ptr = new byte[4];
        int lineAddressPointer = (offsetSection + (4 * (line)));
        if (lineAddressPointer + 4 <= buffer.limit()) {
            buffer.position(lineAddressPointer);
            buffer.get(ptr, 0, 4);
            // trick to keep the unsigned byte format
            // when a prt[] is > 128 it becomes negative (signed).
            // so we need to
            long x3 = ptr[0];
            int x2 = ptr[1];
            int x1 = ptr[2];
            int x = ptr[3];
            if (x3 < 0) {
                x3 = x3 + 0x100 & 0xff;
            }
            if (x2 < 0) {
                x2 = x2 + 0x100 & 0xff;
            }
            if (x1 < 0) {
                x1 = x1 + 0x100 & 0xff;
            }
            if (x < 0) {
                x = x + 0x100 & 0xff;
            }
            // compute the final address
            x3 = x3 << 24;
            x2 = x2 << 16;
            x1 = x1 << 8;
            res = (int) (x3 + x2 + x1 + x);
        }
        return res;
    }

    /**
     * provide the multiplier factor depending on the image format.
     *
     * @param depth given by the ifm
     * @return
     */
    private byte getMaskingMultiplier(int depth) {
        /* Table used for computing multiplier */
        byte mask[] = {127, 63, 31, 15, 7, 3, 1, 0};
        return mask[depth];
    }

    /**
     * get the decompressed part of the image defined by left, top, width,
     * height fro, the png image file. the following pre-controls are made: if
     * left
     *
     * @param left
     * @param top
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    public synchronized BufferedImage getImage(int aLeft, int aTop, int aWidth, int aHeight) {
//        ByteBuffer buffer = fillBuffer();
        BufferedImage tempImage = null;
        if (mapReady && aWidth > 0 && aHeight > 0) {
            // first control the width and height
            // int left = aLeft < bounds.x ? bounds.x : aLeft;
            // int top = aTop < bounds.y ? bounds.y : aTop;
            // int width = (int) (left + aWidth > bounds.getMaxX() ? bounds.getMaxX() - left : aWidth);
            // int height = (int) (top + aHeight > bounds.getMaxY() ? bounds.getMaxY() - top : aHeight);
            int left = aLeft < 0 ? 0 : aLeft;
            int top = aTop < 0 ? 0 : aTop;
            int width = (int) (left + aWidth > mapWidthPixels ? mapWidthPixels - left : aWidth);
            int height = (int) (top + aHeight > mapHeightPixels ? mapHeightPixels - top : aHeight);
            logger.debug("Image adjusted: aTop:" + top + ", aLeft:" + left + ", width:" + width + ", height:" + height);

            //Get the image from the from the copy of the image held in the KAPParser instance.
            try {
                tempImage = image.getSubimage(left, top, width, height);

            } catch (Exception e) {
                // Activator.getDefault();
                // Log.severe(Activator.getBundle(), "error creating image", e);
                logger.error(e.getMessage(), e);
                tempImage = null;
            }
        }
        return tempImage;
    }

    private void readRasterLine(ByteBuffer buffer, WritableRaster raster, byte maskingMultiplier, int yImage, int left, int width) {
        byte b = 0;
        // multiplier is the running number for this pixel
        int multiplier = 1;
        // the pixel = index of color in the rgb array
        byte pixel = 0;
        // xBuffer is the column in the raster buffer
        int xBuffer = 0;
        boolean eol = false;

        // decode pixel and multiplier until end of line
        while (!eol && buffer.remaining() > 0) {
            // read next byte
            b = buffer.get();
            // only the 7 low bits are significant for a pixel
            pixel = (byte) ((b & 0x7F) >> (7 - ifm));
            // number of time the pixel has to be repeated
            multiplier = b & maskingMultiplier;
            // if highest weight bit set to 1 then more data for multiplier
            while (b < 0) {
                b = buffer.get();
                multiplier = (multiplier << 7) + (b & 0x7F);
            }
            multiplier++;
            // define the max size of a decoded line
            int xMax = left + width;
            // check that we are in the required bounds
            if (xBuffer + multiplier >= left && xBuffer < xMax) {
                if (xBuffer + multiplier > xMax) {
                    // bound to the required width
                    multiplier = xMax - xBuffer;
                    // and prepare to exit this line
                    eol = true;
                }
                pixel--;
                // protect from incorrect multiplier
                // (in case we bound close to the beginning of a line)
                if (multiplier > 0) {
                    // define the array of pixel of the same color
                    int arraySize = multiplier;
                    int xImage = xBuffer - left;
                    // case where we don't start the image at the beginning
                    if (xImage < 0) {
                        arraySize = arraySize + xImage;
                        xImage = 0;
                    }
                    int[] pixelArray = new int[arraySize];
                    // fill a record with the pixel
                    Arrays.fill(pixelArray, pixel);
                    // add it to the raster
                    raster.setPixels(xImage, yImage, arraySize, 1, pixelArray);
                }
            }
            // next pixel position is shifted by 'multiplier'
            xBuffer += multiplier;
            // check end of line
            eol = eol || (xBuffer >= this.mapWidthPixels);
        }
    }

    /**
     * load and decompress the full image.<br>
     * Each line begins with a line number. Each line must end with the correct
     * count of pixels. The image data section begins with a single binary
     * meaning indicating the compression type. This meaning should be
     * equivalent to the meaning defined within the IFM image file header
     * record. The binary image data is compressed and stored on a line by line
     * basis within the image data section. The binary image data within a BSB
     * image file is stored in the prototype BSB compression scheme. The BSB
     * compression scheme is a non-destructive run-length format of bit stream
     * fields. The color/repeat doublet has a minimum size of one byte. The
     * maximum is a large as needed to accommodate long run-lengths or large
     * palettes. Run Length (Repeat) is a count of the number of identical
     * sequential pixels (patterns). The format is geared to the specific
     * requirements of cartographic charts and maps: long run lengths of a few
     * colors, interrupted by short run lengths of crossings of contour, grid
     * lines and the vertical staffs of letters. Each line begins with a line
     * number. Each line must end with the correct count of pixels. Each line
     * must also terminate with a binary 0 meaning. The NOS compressed data is
     * encoded as a bit stream with byte boundaries. A unit of information uses
     * as many bytes as required for its magnitude. The 7 least significant bits
     * (LSB) of the byte are the meaning. The most significant bit (MBS) is a
     * concatenation flag. If the high order bit is 1, the meaning requires
     * concatenation with the next byte, etc. It is analogous to the ASCII
     * representation of numerical data. Each additional byte of the decimal
     * string increases the magnitude 10x. Each additional byte of the prototype
     * NOS compressed string increases the magnitude 128x. Each line of raster
     * image data is encoded as follows: LineNumber Color1,Run1 � Color n, Run n
     * NULL LineNumber=Sequential line count encoded in the MSB concatenation
     * scheme. At least three null bytes follow the raster data (one for the end
     * of line, two for the first address high weight).
     *
     * @throws Exception
     * @return BufferedImage
     */
    // @Override
    public BufferedImage getImage() {
        if (image == null) {
            ByteBuffer buffer = fillBuffer();
            int width = mapWidthPixels;
            int height = mapHeightPixels;
            if (!mapReady) {
                try {
                    loadHeader();
                } catch (IOException e) {
                    // Activator.getDefault();
                    logger.debug("error reading image", e);
                    return null;
                }
            }
            // define the multiplier factor, depending on the colorDepth (size
            // for a color)
            byte maskingMultiplier = getMaskingMultiplier(ifm);
            // create the colorModel
            // the different red green and blue color arrays must have been net
            ColorPalette p = defaultPalette;

            ColorModel colorModel = new IndexColorModel(ifm, p.nbColors, p.reds, p.greens, p.blues, Transparency.OPAQUE);
            // create a raster to set directly the index of colors in
            try {
                WritableRaster raster = colorModel.createCompatibleWritableRaster(width, height);
                // create the associate image
                image = new BufferedImage(colorModel, raster, false, null);
                if (buffer != null) {
                    // go to the first line
                    buffer.position(getRasterOffset(buffer));
                    // compute the byte multiplier from the depth of the picture
                    // end of data is identified by:
                    // the line number being equals to the height of the image
                    // and a sequence of two null
                    for (boolean endOfRasterData = false; !endOfRasterData;) {
                        int lineNumber = readLineNumber(buffer);
                        int y = lineNumber - offsetY;
                        int multiplier = 1;
                        byte pixel = 0;
                        int x = 0;
                        for (boolean eol = false; !eol;) {
                            byte b = buffer.get();
                            eol = (b == 0);
                            if (!eol) {
                                pixel = (byte) ((b & 0x7F) >> (7 - ifm));
                                // the expected lenght of the line
                                multiplier = b & maskingMultiplier;
                                // some more data for this color
                                while ((b & 0x80) == 0x80) {
                                    b = buffer.get();
                                    multiplier = (multiplier << 7) + (b & 0x7F);
                                }
                                pixel--;
                                multiplier++;
                                if (y >= 0 && y < mapHeightPixels) {
                                    // define the array of pixel of the same
                                    // color
                                    if (x + multiplier > mapWidthPixels) {
                                        // bound to the required width
                                        multiplier = mapWidthPixels - x;
                                    }
                                    if (multiplier > 0) {
                                        int[] pixelArray = new int[multiplier];
                                        // fill a record with the pixel
                                        Arrays.fill(pixelArray, pixel);
                                        // add it to the raster
                                        raster.setPixels(x, y, multiplier, 1, pixelArray);
                                    }
                                }
                                // next pixel position is shifted by
                                // 'multiplier'
                                x += multiplier;
                            } else {
                                endOfRasterData = (buffer.get() == 0);
                                if (!endOfRasterData) {
                                    buffer.position(buffer.position() - 1);
                                }
                            }
                        }
                    }
                }
            } catch (OutOfMemoryError e) {
                // Activator.getDefault();
                logger.error(e.getMessage());
                image = null;
            }

        }
        return image;
    }

    private ByteBuffer fillBuffer() {
        InputStream file;
        ByteBuffer res = ByteBuffer.allocate((int) new File(fileName).length());
        try {
            file = new DataInputStream(new FileInputStream(fileName));
            file.read(res.array());
            file.close();
        } catch (IOException e) {
            res = null;
        }
        offsetSection = this.findOffsetSection(res);
        return res;
    }

    /**
     * returns the starting point of the raster data. if the stored meaning = -
     * 1, then search the 0x1A-0x00 couple in the data.
     *
     * @return the absolute position of the first information of the raster
     * data.
     *
     */
    private int getRasterOffset(ByteBuffer buffer) {
        int res = rasterDataOffset;
        if (res == -1) {
            res = searchForRasterOffset(buffer);
        }
        return res;
    }

    /**
     * search in the whole file the couple 0x1A-0x00 that marks the end of
     * header. after that it skip 1 char again to ignore the second code of ifm.
     *
     * @return the absolute position of the second char after this mark.
     */
    private int searchForRasterOffset(ByteBuffer buffer) {
        int res = -1;
        boolean eof = false;
        boolean found = false;
        do {
            if (buffer.hasRemaining()) {
                Short shortValue = buffer.getShort();
                found = shortValue == 0x1A00;
            } else {
                eof = true;
            }
        } while (!found && !eof);
        if (found) {
            // skip the next (code ifm)
            @SuppressWarnings("unused")
            byte ignore = buffer.get();
            res = buffer.position();
        }
        return res;
    }

    /**
     * reads the line number at buffer present position.
     *
     * @param buff
     * @return
     */
    private int readLineNumber(ByteBuffer buff) {
        int res = 0;
        byte b = 0;
        do {
            b = buff.get();
            // cheat to have an unsigned byte
            int bi = b;
            if (bi < 0) {
                bi = bi + 0x100 & 0xff;
            }
            // yBuffer is the line in the raster buffer
            res = ((res & 0x7F) << 7) + bi;
        } while (b < 0);
        return res;
    }

    /**
     *
     * Header of a KAP file.<br>
     * <i>! comment line.</i><br>
     * <b> BSB </b><br>
     * defines image file parameters.<br>
     * NA=Name : The chart or map image file name<br>
     * NU=Num : Number of chart (useful when more than one chart)<br>
     * RA=Width,Height : The image extents (range of values) in pixels
     * units.<br>
     * DU=DrawingUnits: The image file drawing units in pixels/inch<br>
     * <b>OST</b><br>
     * OST records define the number of image lines between strip offset values.
     * OST/ImageLines where ImageLines defines the number of image lines between
     * strip offset values.<br>
     * <b>VER</b><br>
     * Version number of BSB format e.g. 1, 2.0, 3.0, 3.07, 4.0<br>
     * <b>IFM </b><br>
     * defines the NOS compression type of the binary image data.<br>
     * IMF/CompressionType where supported CompressionType values and their
     * interpretation are: 1 NOS Compression Type 1, 1 bit color (Bilevel, 2
     * colors) 2 NOS Compression Type 2, 2 bit color (2 or 3 colors) 3 NOS
     * Compression Type 3, 3 bit color (4 to 7 colors) 4 NOS Compression Type 4,
     * 4 bit color (8 to 15 colors) 5 NOS Compression Type 5, 5 bit color (16 to
     * 31colors) 6 NOS Compression Type 6, 6 bit color (32 to 63 colors) 7 NOS
     * Compression Type 7, more than 63 colors <b>RGB Record.</b><br>
     * RGB records define a default color palette to be used when graphically
     * representing to binary image data. Each image file header must contain a
     * minimum of two RGB records. Each RGB record defines the color palette
     * index and the red, green, and blue additive color values assigned to that
     * palette index meaning. Color values range from 0 to 255 and relate
     * directly to percentages where 0=0%, 127=50%, and 255=100%. An RGB record
     * defines the color parameters in the following manner:
     * RGB/PaletteIndex,Red,Green,Blue Where: PaletteIndex defines the palette
     * index. Red defines the additive red component (0-255) Green defines the
     * additive green component (0-255) Blue defines the additive blue component
     * (0-255) The red, green, and blue color values contained within the
     * palette for the binary image data are recommended display colors for the
     * BSB image file. They may be remapped to other more appropriate colors
     * (night readable, monochrome, gray scale, etc.) for a specific software or
     * hardware display environment.
     *
     * @param file
     * @throws IOException
     */
    public synchronized void loadHeader() throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fr);
        try {
            int bcl = 0;
            double[] wpx, wpy, pwx, pwy;

            wpx = new double[12];
            wpy = new double[12];
            pwx = new double[12];
            pwy = new double[12];
            boolean endOfSection = false;
            Mode mode = Mode.DEFAULT;
            int totalCharRead = 0;
            // read the header
            while (!endOfSection) {
                String line = reader.readLine();
                if (line == null) {
                    // Activator.getDefault();
                    logger.debug(fileName + "Error Message EOF");
                    break;
                }
                endOfSection = line.length() > 1 && line.charAt(0) == 0x1A && line.charAt(1) == 0x00;
                if (!endOfSection) {
                    totalCharRead += (line.length() + 2);
                    // remove space at start and end of string
                    line = line.trim();
                    if (line.startsWith("!")) { //$NON-NLS-1$
                        comments.add(line);
                    } else if (line.toUpperCase().startsWith("VER/")) { //$NON-NLS-1$
                        try {
                            version = Double.parseDouble(new Scanner(line).useDelimiter("VER/").next());//$NON-NLS-1$
                        } catch (Exception e) {
                            version = -1;
                        }
                        mode = Mode.DEFAULT;
                    } else if (line.toUpperCase().startsWith("BSB/")) { //$NON-NLS-1$
                        mode = Mode.BSB;
                        line = line.replace("BSB/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    } // compatibility with old formats
                    else if (line.toUpperCase().startsWith("NOS/")) { //$NON-NLS-1$
                        mode = Mode.BSB;
                        line = line.replace("NOS/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    } else if (line.toUpperCase().startsWith("KNP/")) { //$NON-NLS-1$
                        mode = Mode.KNP;
                        line = line.replace("KNP/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    } else if (line.toUpperCase().startsWith("CED/")) { //$NON-NLS-1$
                        mode = Mode.CED;
                        line = line.replace("CED/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    } else if (line.toUpperCase().startsWith("NTM/")) { //$NON-NLS-1$
                        mode = Mode.NTM;	// Notice to Mariners
                        line = line.replace("NTM/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                    } else if (line.toUpperCase().startsWith("KNQ/")) { //$NON-NLS-1$
                        mode = Mode.DEFAULT;
                        line = line.replace("KNQ/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        // TODO not treated yet
                    } else if (line.toUpperCase().startsWith("OST/")) { //$NON-NLS-1$
                        mode = Mode.DEFAULT;
                        line = line.replace("OST/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        ost = Integer.parseInt(line);
                    } else if (line.toUpperCase().startsWith("IFM/")) { //$NON-NLS-1$
                        ifm = new Scanner(line).useDelimiter("IFM/").nextInt(); //$NON-NLS-1$
                        defineColorPalettes(ifm);

                        mode = Mode.DEFAULT;
                    } else if (line.toUpperCase().startsWith("RGB/")) { //$NON-NLS-1$
                        line = line.replace("RGB/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        try (Scanner sc = new Scanner(line).useDelimiter(",")) {
                            fillPalette(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), defaultPalette);
                            mode = Mode.DEFAULT;
                        }
                    } else if (line.toUpperCase().startsWith("DAY/")) { //$NON-NLS-1$
                        line = line.replace("DAY/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        try (Scanner sc = new Scanner(line).useDelimiter(",")) {
                            fillPalette(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), dayPalette);
                            mode = Mode.DEFAULT;
                        }
                    } else if (line.toUpperCase().startsWith("NGT/")) { //$NON-NLS-1$
                        line = line.replace("NGT/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        try (Scanner sc = new Scanner(line).useDelimiter(",")) { //$NON-NLS-1$
                            fillPalette(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), nightPalette);
                            mode = Mode.DEFAULT;
                        }
                    } else if (line.toUpperCase().startsWith("REF/")) { //$NON-NLS-1$
                        line = line.replace("REF/", ""); //$NON-NLS-1$ //$NON-NLS-2$

                        // REF/2,598,592,71.5006416667,-173.0833333333
                        try (Scanner sc = new Scanner(line).useDelimiter(",")) { //$NON-NLS-1$
                            sc.useLocale(Locale.US);
                            sc.nextInt();// num ignored
                            int x = sc.nextInt();
                            int y = sc.nextInt();
                            if (x < marginLeft) {
                                marginLeft = x;
                            }
                            if (x > marginRight) {
                                marginRight = x;
                            }
                            if (y < marginTop) {
                                marginTop = y;
                            }
                            if (y > marginBottom) {
                                marginBottom = y;
                            }
                            Point p = new Point(x, y);
                            // gets the lat, lon
                            Position pos = new Position(sc.nextDouble(), sc.nextDouble());
                            references.add(new MapReference(pos, p));
                            logger.debug("reference " + references.lastElement().toString());
                            mode = Mode.DEFAULT;
                        }
                    } else if (line.toUpperCase().startsWith("PLY/")) { //$NON-NLS-1$
                        line = line.replace("PLY/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        try (Scanner sc = new Scanner(line).useDelimiter(",")) { //$NON-NLS-1$
                            sc.useLocale(Locale.US);
                            sc.nextInt();// num ignored
                            Position pos = new Position(sc.nextDouble(), sc.nextDouble());
                            displayLimits.add(pos);
                            logger.debug("ply " + displayLimits.lastElement().toString());
                            mode = Mode.DEFAULT;
                        }
                    } else if (line.toUpperCase().startsWith("CPH/")) { //$NON-NLS-1$
                        mode = Mode.DEFAULT;
                        line = line.replace("CPH/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        correctiveLongitudePhase = Double.parseDouble(line);
                    } else if (line.toUpperCase().startsWith("DTM/")) { //$NON-NLS-1$
                        mode = Mode.DEFAULT;
                        line = line.replace("DTM/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        line = line.replaceAll(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        try (Scanner sc = new Scanner(line).useDelimiter(",")) { //$NON-NLS-1$
                            sc.useLocale(Locale.US);
                            latitudeShift = sc.nextDouble() / 60;
                            longitudeShift = sc.nextDouble() / 60;
                        }
                    } else if (line.toUpperCase().startsWith("WPX/")) { //$NON-NLS-1$
                        line = line.replace("WPX/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        bcl = 0;
                        mode = Mode.WPX;
                    } else if (line.toUpperCase().startsWith("WPY/")) { //$NON-NLS-1$
                        line = line.replace("WPY/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        bcl = 0;
                        mode = Mode.WPY;
                    } else if (line.toUpperCase().startsWith("PWX/")) { //$NON-NLS-1$
                        line = line.replace("PWX/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        bcl = 0;
                        mode = Mode.PWX;
                    } else if (line.toUpperCase().startsWith("PWY/")) { //$NON-NLS-1$
                        line = line.replace("PWY/", ""); //$NON-NLS-1$ //$NON-NLS-2$
                        bcl = 0;
                        mode = Mode.PWY;
                    } else if (line.toUpperCase().startsWith("ERR/")) { //$NON-NLS-1$
                        // not treated yet
                        mode = Mode.DEFAULT;
                    }

                    try (Scanner sc = new Scanner(line).useDelimiter(",")) { //$NON-NLS-1$

                        sc.useLocale(Locale.US);
                        switch (mode) {
                            case DEFAULT:
                                break;
                            case BSB:
                                while (sc.hasNext()) {
                                    String field = sc.next();
                                    if (field.contains("NA=")) { //$NON-NLS-1$
                                        mapName = field.replace("NA=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("NU=")) { //$NON-NLS-1$
                                        mapNum = field.replace("NU=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("RA=")) { //$NON-NLS-1$
                                        mapWidthPixels = Integer.parseInt(field.replace("RA=", "")); //$NON-NLS-1$ //$NON-NLS-2$
                                        mapHeightPixels = sc.nextInt();
//                                        rawMapWidthPixels = mapWidthPixels;
//                                        rawMapHeightPixels = mapHeightPixels;
                                    } else if (field.contains("DU=")) { //$NON-NLS-1$
                                        resolutionDPI = Integer.parseInt(field.replace("DU=", "")); //$NON-NLS-1$ //$NON-NLS-2$
                                    }
                                }
                                break;
                            case KNP:
                                while (sc.hasNext()) {
                                    String field = sc.next();
                                    if (field.contains("SC=")) { //$NON-NLS-1$
                                        mapFileScale = Integer.parseInt(field.replace("SC=", "")); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("GD=")) { //$NON-NLS-1$
                                        datum = field.replace("GD=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("PR=")) { //$NON-NLS-1$
                                        projection = field.replace("PR=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("PP=")) { //$NON-NLS-1$
                                        projectionParameter = field.replace("PP=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("PI=")) { //$NON-NLS-1$
                                        PI = field.replace("PI=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("SP=")) { //$NON-NLS-1$
                                        SP = field.replace("SP=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("SK=")) { //$NON-NLS-1$
                                        SK = Float.parseFloat(field.replace("SK=", "")); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("UN=")) { //$NON-NLS-1$
                                        UN = field.replace("UN=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("SD=")) { //$NON-NLS-1$
                                        SD = field.replace("SD=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("DX=")) { //$NON-NLS-1$
                                        DX = Float.parseFloat(field.replace("DX=", "")); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("DY=")) { //$NON-NLS-1$
                                        DY = Float.parseFloat(field.replace("DY=", "")); //$NON-NLS-1$ //$NON-NLS-2$
                                    }
                                }
                                break;
                            case CED:
                                while (sc.hasNext()) {
                                    String field = sc.next();
                                    if (field.contains("SE=")) { //$NON-NLS-1$
                                        SE = field.replace("SE=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("RE=")) { //$NON-NLS-1$
                                        RE = field.replace("RE=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("ED=")) { //$NON-NLS-1$
                                        ED = field.replace("ED=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    }
                                }
                                break;
                            case NTM:
                                while (sc.hasNext()) {
                                    String field = sc.next();
                                    if (field.contains("NE=")) { //$NON-NLS-1$
                                        NE = field.replace("NE=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("ND=")) { //$NON-NLS-1$
                                        ND = field.replace("ND=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    } else if (field.contains("BD=")) { //$NON-NLS-1$
                                        BD = field.replace("BD=", ""); //$NON-NLS-1$ //$NON-NLS-2$
                                    }
                                }
                                break;
                            case WPX:
                                wpxDone = true;
                                if (bcl == 0) {
                                    sc.next(); // ignore order
                                }
                                while (sc.hasNext()) {
                                    wpx[bcl++] = sc.nextDouble();
                                }
                                break;
                            case WPY:
                                wpyDone = true;
                                if (bcl == 0) {
                                    sc.next(); // ignore order
                                }
                                while (sc.hasNext()) {
                                    wpy[bcl++] = sc.nextDouble();
                                }
                                break;
                            case PWX:
                                pwxDone = true;
                                if (bcl == 0) {
                                    sc.next(); // ignore order
                                }
                                while (sc.hasNext()) {
                                    pwx[bcl++] = sc.nextDouble();
                                }
                                break;
                            case PWY:
                                pwyDone = true;
                                if (bcl == 0) {
                                    sc.next(); // ignore order
                                }
                                while (sc.hasNext()) {
                                    pwy[bcl++] = sc.nextDouble();
                                }
                                break;

                        }
                    }
                } // END OF SECTION FOUND
                else {
                    // we should now read a binary version of the ifm
                    byte binaryCodedIFM = (byte) line.charAt(2);
                    // check that it is equal to the textual one, if not force to
                    // the binary
                    if (binaryCodedIFM != ifm) {
                        // Activator.getDefault();
                        logger.error(String.format("error bad IFM %d<>%d", ifm, binaryCodedIFM)); //$NON-NLS-1$
                        ifm = binaryCodedIFM;
                    }
                    // store the position of the raster data for future use (reading
                    // the image)
                    // ship 0x1A, 0x00 and the IFM (3 bytes)
                    rasterDataOffset = totalCharRead + 3;
                    // read the first line to get the first line number as an offset
                    // (badly coded charts)
                    try {
                        offsetY = (byte) line.charAt(3);
                    } catch (Exception e) {
                        offsetY = 0;
                        logger.error("error reading header of " + fileName, e);
                    }
                }

            }
            // validate the map only when the header has been completely read
            if (endOfSection) {
                //
                // build the coordinate translator
                polynomLoaded = wpxDone && wpyDone && pwxDone && pwyDone;
                tx = null;
                if (polynomLoaded) {
                    coordTranslator = new CoordsPolynomTranslator(correctiveLongitudePhase, wpx, wpy, pwx, pwy, tx);
                } else {
                    coordTranslator = new CoordsPolynomTranslator(correctiveLongitudePhase, references, tx);
                }
                // convert from decimal minutes to decimal degrees!
                coordTranslator.setLatShift(latitudeShift / 60d);
                coordTranslator.setLonShift(longitudeShift / 60d);
                mapReady = true;

                BufferedImage tempImage = getImage();               
                if (logger.isDebugEnabled()){
                    saveAsPNG(tempImage, new File(fileName + ".png"));
                    logger.debug("Saving "+fileName + ".png ");
                }
                Point2D.Double [] corners = new Point2D.Double[displayLimits.size()];
                logger.debug("displayLimits - the edges of the map, not including border");
                for (int i = 0; i < displayLimits.size(); i++) {
                    Point temp = coordTranslator.getAbsolutePointFromPosition(displayLimits.get(i));
                    corners[i] = new Point2D.Double((double) temp.x, (double) temp.y);
                }
                
                logger.info("skew = " + getSkew());
                if (getSkew() == 0.) {
                    tx = null;
                } else {
                    tx = new AffineTransform();
                    
                    // get the original bounds from the corners;
                    Rectangle2D.Double originalBounds = getBounds(corners);
                    
                    // get the middle of the Rectangle
                    double midX = originalBounds.x + originalBounds.width/2.;
                    double midY = originalBounds.y + originalBounds.height/2.;
                    
                    // Rotate by skew about middle of chart
                    tx.rotate(-Math.toRadians(getSkew()), midX, midY);
                    Point2D.Double tempPt[] = new Point2D.Double[displayLimits.size()];

                    tx.transform(corners, 0, tempPt, 0, corners.length);
                    logger.debug("Rotated corners");
                    for (int i = 0; i < corners.length; i++) {
                        logger.debug(String.format("corner[%d]: (%7.1f, %7.1f) --> (%7.1f, %7.1f)", i, corners[i].getX(), corners[i].getY(), tempPt[i].getX(), tempPt[i].getY()));
                    }
                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                    image = null;
                    if (logger.isDebugEnabled()){
                        image = op.filter(tempImage, null);
                        saveAsPNG(image, new File(fileName + "_Rot.png"));
                        logger.debug("Saving "+fileName + "_Rot.png " );
                    }

                    Rectangle2D.Double rotatedBounds = getBounds(tempPt);
                    Point2D[] transCorner = new Point2D.Double[displayLimits.size()];

                    // translate to bring the corners back into the plotable area (x>0 and y>0)
//                    if (minX < 0) {
                    double xTrans = -rotatedBounds.x * Math.cos(Math.toRadians(getSkew())) + rotatedBounds.y * Math.sin(Math.toRadians(getSkew()));
                    double yTrans = -rotatedBounds.x * Math.sin(Math.toRadians(getSkew())) - rotatedBounds.y * Math.cos(Math.toRadians(getSkew()));
                    tx.translate(xTrans, yTrans);
                    tx.transform(corners, 0, transCorner, 0, corners.length);
                    logger.debug("Rotated Translated display limits");
                    for (int i = 0; i < corners.length; i++) {
                        logger.debug(String.format("corner[%d]: (%7.1f, %7.1f) --> (%7.1f, %7.1f)", i, tempPt[i].getX(), tempPt[i].getY(), transCorner[i].getX(), transCorner[i].getY()));
                    }

                    op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                    image = null;
                    image = op.filter(tempImage, null);

                    // The mapWidth/mapHeight includes the border!
                    // to get only the chart, wothout the border, parse th corners for maxX and maxY values
                    mapWidthPixels = image.getWidth();
                    mapHeightPixels = image.getHeight();

                    if (logger.isDebugEnabled()){
                        saveAsPNG(image, new File(fileName + "_RotTrans.png"));
                        logger.debug("Saving "+fileName + "_Rot.png ");
                    }
                    
                    // rebuild the CoordsPolynomTranslator with the rotation-translation transform
                    if (polynomLoaded) {
                        coordTranslator = new CoordsPolynomTranslator(correctiveLongitudePhase, wpx, wpy, pwx, pwy, tx);
                    } else {
                        coordTranslator = new CoordsPolynomTranslator(correctiveLongitudePhase, references, tx);
                    }
                }

                mapUseableSector = extractVisibleSectorFromBounds(displayLimits);

                Point p = coordTranslator.getAbsolutePointFromPosition(mapUseableSector.getNorthWest());
                int leftBound = p.x;
                int topBound = p.y;
                p = coordTranslator.getAbsolutePointFromPosition(mapUseableSector.getSouthEast());
                int bottomBound = p.y;
                int rightBound = p.x;

                // max and min are set to correct the issue with map
                // across the 180 longitude
                bounds = new Rectangle(Math.min(leftBound, rightBound), topBound, Math.max(rightBound - leftBound, leftBound - rightBound), bottomBound
                        - topBound);
                logger.debug("Translated bounds " + bounds.toString());

                mapReady = true;
            }
        } finally {
            reader.close();
        }
    }

    /**
     * extract the UpperLeft,lowerRight and middle points from the input array points
     *
     * @param points
     * @return the bounding Rectangle2D.Double 
     */
    public Rectangle2D.Double getBounds(Point2D.Double[] points) {
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;

        double tempX = 0.;
        double tempY = 0.;
        for (int i = 0; i < points.length; i++){
            tempX = points[i].x;
            tempY = points[i].y;
            if (minX > tempX){
                minX = tempX;
            }
            if (maxX < tempX){
                maxX = tempX;
            }
            if (minY > tempY){
                minY = tempY;
            }
            if (maxY < tempY){
                maxY = tempY;
            }
        }
        Rectangle2D.Double results = new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
        return results;
    }
    
    /**
     * extract the usable Sector from the list of bounds (found in PLY/
     * sections).
     *
     * @param bounds
     * @return a Sector
     */
    public Sector extractVisibleSectorFromBounds(Vector<Position> bounds) {
        // define the min/max initial values
        double maxLon = -180;
        double minLon = 180;
        double minLat = 90;
        double maxLat = -90;
        // special case for maps overlapping changing date line
        double maxNegLon = -180;
        for (Position pos : bounds) {
            double lon = pos.getLongitude();
            double lat = pos.getLatitude();
            if (lon > maxLon) {
                maxLon = lon;
            }
            if (lon < minLon) {
                minLon = lon;
            }
            // special case for maps overlapping changing date line
            if (lon < 0 && lon > maxNegLon) {
                maxNegLon = lon;
            }
            if (lat < minLat) {
                minLat = lat;
            }
            if (lat > maxLat) {
                maxLat = lat;
            }
        }

        // Here we get the pixel positions from the lat/lon display limits
        // Then we use the pixel positions to recalculate the lat/Lon of the limiting corners
        // and calculatge the map height and width in pixels
        logger.debug(" marginTop:" + marginTop + ", marginBottom:" + marginBottom + ", marginLeft:" + marginLeft + ", marginRight:" + marginRight);
        logger.debug(" maxLat:" + maxLat + " minLon:" + minLon);
        // get point corresponding to maxLat, minLon
        logger.debug("Point corresponding to (maxLat, minLon)");
        Point p = coordTranslator.getAbsolutePointFromPosition(minLon, maxLat);
        logger.debug(String.format("(maxLat, minLon) = (%12.7f, %12.7f): --> (%7d, %7d)", maxLat, minLon, p.x, p.y));

        // upper left in transformed oordinates
        Point pp = new Point(0, 0);
        Position topLeft = coordTranslator.getAbsolutePositionFromPoint(pp);
        logger.debug(String.format("topLeft: (%12.7f, %12.7f): --> (%7d, %7d)", topLeft.getLatitude(), topLeft.getLongitude(), pp.x, pp.y));

        //getting bottom right
        Point pix;
        pix = coordTranslator.getAbsolutePointFromPosition(maxLon, minLat);
        Position bottomRight = coordTranslator.getAbsolutePositionFromPoint(pix);
        logger.debug(String.format("bottomRight: (%12.7f, %12.7f): --> (%7d, %7d)", bottomRight.getLatitude(), bottomRight.getLongitude(), pix.x, pix.y));

        // this is for crossing night line
        pix = coordTranslator.getAbsolutePointFromPosition(maxNegLon, minLat);
        Position negRight = coordTranslator.getAbsolutePositionFromPoint(pix);
        logger.debug(String.format("negRight: (%12.7f, %12.7f): --> (%7d, %7d)", negRight.getLatitude(), negRight.getLongitude(), pix.x, pix.y));

        if (isCrossingDayNightLine(topLeft, bottomRight)) {
            topLeft = new Position(maxLat, maxLon);
            bottomRight = new Position(minLat, negRight.getLongitude());
        }
        return new Sector(topLeft, bottomRight);
    }

    private static boolean isCrossingDayNightLine(Position topLeft, Position bottomRight) {
        if (topLeft.getLongitude() > bottomRight.getLongitude()) {
            return true;
        }
        return false;
    }

    /**
     * fill the palette with index color and fill also a rgb array of colors.
     *
     * @param index
     * @param red
     * @param green
     * @param blue
     * @param pal
     * @param rgb
     */
    private void fillPalette(int index, int red, int green, int blue, ColorPalette pal) {
        pal.nbColors = StrictMath.max(pal.nbColors, index);
        if (index >= 0 && index < 128) {
            pal.reds[index - 1] = (byte) red;
            pal.greens[index - 1] = (byte) green;
            pal.blues[index - 1] = (byte) blue;
        }
    }

    /**
     * defines the colors palette.
     *
     * @param depth : the number of bits used to code a color.
     */
    private void defineColorPalettes(int depth) {
        int nbColors = (int) StrictMath.pow(2, depth);
        defaultPalette = new ColorPalette(nbColors);
        dayPalette = new ColorPalette(nbColors);
        nightPalette = new ColorPalette(nbColors);
    }

    /**
     * @return the displayableArea
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * file must have the extention .KAP load and interpret the header 2)
     * decompress the bitmap
     *
     * @param fileName
     * @throws Exception
     */
    // @Override
    public void load(String fileName) throws Exception {
        this.fileName = fileName;
        this.loadHeader();
    }

    /**
     * @return the ifm
     */
    public int getColorMapDepth() {
        return ifm;
    }

    /**
     * @return the offsetStripImage
     */
    public int getOffsetStripImage() {
        return ost;
    }

    /**
     * @return the resolutionDPI
     */
    public int getResolutionDPI() {
        return resolutionDPI;
    }

    /**
     * @return the version
     */
    public double getVersion() {
        return version;
    }

    /**
     * @return the mapNum
     */
    public String getMapNum() {
        return mapNum;
    }

//    /**
//     * @return the rawMapWidthPixels
//     */
//    public int getRawMapWidthPixels() {
//        return rawMapWidthPixels;
//    }

    /**
     * @return the mapWidthPixels
     */
    public int getMapWidthPixels() {
        return mapWidthPixels;
    }

    /**
     * @return the mapHeightPixels
     */
    public int getMapHeightPixels() {
        return mapHeightPixels;
    }

//    /**
//     * @return the rawMapHeightPixels
//     */
//    public int getRawMapHeightPixels() {
//        return rawMapHeightPixels;
//    }
//
    /**
     * @return the datum
     */
    // @Override
    public String getDatum() {
        return datum;
    }

    /**
     * @return the projection
     */
    // @Override
    public String getProjectionName() {
        return projection;
    }

    /**
     * @return the projectionParameter
     */
    public String getProjectionParameter() {
        return projectionParameter;
    }

    /**
     * @return the fileName
     */
    // @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the imageFormat
     */
    public int getImageFormat() {
        return imageFormat;
    }

    // @Override
    public String getName() {
        return this.mapName;
    }

    /**
     * @return the encrypted
     */
    public boolean isEncrypted() {
        return encrypted;
    }

    /**
     * @return the correctiveLongitudePhase
     */
    public double getCorrectiveLongitudePhase() {
        return correctiveLongitudePhase;
    }

    // @Override
    public Rectangle getVisibleBounds() {
        return visibleBounds;
    }

    public void saveAsPNG(BufferedImage img, File file) throws IOException {
        ImageIO.write(img, "png", file); //$NON-NLS-1$
    }

    public void saveAsJPG(BufferedImage img, File file) throws IOException {
        ImageIO.write(img, "jpg", file); //$NON-NLS-1$
    }

    /**
     * @return the mapFileScale
     */
    public int getMapFileScale() {
        return mapFileScale;
    }

    // @Override
    public void setName(String s) {
        mapName = s;
    }

    // @Override
    public void setCph(double d) {
        correctiveLongitudePhase = d;
    }

    // @Override
    public void setDatum(String dt) {
        datum = dt;

    }

    // @Override
    public void setLatitudeShift(double d) {
        latitudeShift = d;
    }

    // @Override
    public void setLongitudeShift(double d) {
        longitudeShift = d;
    }

    // @Override
    public void setScale(int sc) {
        mapFileScale = sc;
    }

    // @Override
    public Vector<Position> getBoundaries() {
        return this.displayLimits;
    }

    // @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;

    }

    // @Override
    public void setImage(BufferedImage image) throws Exception {
        ColorModel cm = image.getColorModel();
        this.ifm = StrictMath.min(7, cm.getPixelSize());
        if (!(cm instanceof IndexColorModel)) {
            this.image = null;
            throw new Exception("not an indexed image");
        } else {
            this.image = image;
            this.mapWidthPixels = image.getWidth();
            this.mapHeightPixels = image.getHeight();
            IndexColorModel icm = (IndexColorModel) cm;
            defaultPalette = new ColorPalette(icm);
        }
    }

    // @Override
    public double getCph() {
        return this.correctiveLongitudePhase;
    }

    // @Override
    public double getLatShift() {
        return this.latitudeShift;
    }

    // @Override
    public double getLonShift() {
        return this.longitudeShift;
    }

    // @Override
    public void setDPI(int x) {
        resolutionDPI = x;
    }

    // @Override
    public int getDPI() {
        return this.resolutionDPI;
    }

    public float getSkew() {
        return this.SK;
    }

    // @Override
    public String getSoundings() {
        return this.UN;
    }

    public AffineTransform getTransform() {
        return tx;
    }

    // @Override
    public String getSoundingReference() {
        return this.SD;
    }

    // @Override
    public void setSoundingReference(String soundingReference) {
        this.SD = soundingReference;

    }

    // @Override
    public void setSoundings(String soundings) {
        this.UN = soundings;
    }

    // @Override
    public Object getProductionDate() {
        return String.format("Ed:%s ND:%s BD:%s", ED, ND, BD); //$NON-NLS-1$
    }

    protected Sector getMapUseableSector() {
        return mapUseableSector;
    }

    protected void setMapUseableSector(Sector mapUseableSector) {
        this.mapUseableSector = mapUseableSector;
    }

    protected CoordsPolynomTranslator getCoordTranslator() {
        return coordTranslator;
    }

    protected String getMapName() {
        return mapName;
    }

    protected void setMapName(String mapName) {
        this.mapName = mapName;
    }

    protected String getProjection() {
        return projection;
    }

    protected void setProjection(String projection) {
        this.projection = projection;
    }

    protected void setMapNum(String mapNum) {
        this.mapNum = mapNum;
    }

    protected void setMapFileScale(int mapFileScale) {
        this.mapFileScale = mapFileScale;
    }

}
