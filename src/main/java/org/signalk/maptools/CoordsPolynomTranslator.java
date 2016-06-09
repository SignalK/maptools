package org.signalk.maptools;

/**
 * Project: capcode.basics CoordsKapPolynomTranslator.java created 11 Aug 2008
 * by rosaycy Copyright (c) 2007 Capcode Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
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
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. CAPCODE AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE THIS SOFTWARE, EVEN IF CAPCODE HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * - You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any nuclear
 * facility or airborne software. - You acknowledge that using this software for
 * the purpose of navigation does not engage the developer responsability in
 * case of damage, injuries or casualities due to the use of this software.
 */
/**
 * CVS markups last update by : $Author: crosay $ $Date: 2009-08-31 22:18:08 $
 * $Header:
 * /media/disk/capcodeCVSbackup/capcode.basics/src/capcode/maps/transform/CoordsPolynomTranslator.java,v
 * 1.9 2009-08-31 22:18:08 crosay Exp $ $Log: not supported by cvs2svn $
 * Revision 1.8 2009/08/25 22:33:59 crosay Complete # add reference points to
 * maps http://sourceforge.net/apps/trac/capcode/ticket/22 Incomplete # move the
 * display of the VMG arrow from map display plugin to polar display plugin
 * http://sourceforge.net/apps/trac/capcode/ticket/29
 *
 * Revision 1.7 2009/08/15 15:13:28 crosay Complete # add reference points to
 * maps http://sourceforge.net/apps/trac/capcode/ticket/22
 *
 * Revision 1.6 2009/07/01 22:10:13 crosay Complete - taskSourceForge.net:
 * CapCode-software suite for sailors: Detail: 2798790 - solve bug 2798789 error
 * with some BSB 2 maps
 * https://sourceforge.net/tracker/?func=detail&aid=2798790&group_id=100698&atid=1045270
 *
 * Revision 1.5 2009/06/22 17:05:19 crosay Incomplete - taskSourceForge.net:
 * CapCode-software suite for sailors: Detail: 2798790 - solve bug 2798789 error
 * with some BSB 2 maps
 * https://sourceforge.net/tracker/?func=detail&aid=2798790&group_id=100698&atid=1045270
 *
 * Revision 1.4 2008/09/11 15:29:56 crosay task 2042679 (BSB Maps format)
 * continued finalisation
 *
 * Revision 1.3 2008/08/23 19:32:46 crosay task 2042679 (BSB Maps format)
 * continued added class Sector
 *
 * Revision 1.2 2008/08/18 21:50:16 crosay task 2042679 (BSB Maps format)
 * continued added mapping functions
 *
 * Revision 1.1 2008/08/14 13:39:23 crosay task 2042679 (BSB Maps format)
 * continued new Coords transformers
 *
 * $Revision: 1.9 $ $State: Exp $
 */
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rosaycy
 *
 */
//public class CoordsPolynomTranslator extends CoordsTranslator {
public class CoordsPolynomTranslator {

    public Polynom2D wpxPoly = new Polynom2D();
    public Polynom2D wpyPoly = new Polynom2D();
    public Polynom2D pwxPoly = new Polynom2D();
    public Polynom2D pwyPoly = new Polynom2D();
    private double cph = 0.00;
    private double latitudeShift = 0.0;
    private double longitudeShift = 0.0;
    private AffineTransform at = null;
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CoordsPolynomTranslator.class);

    /**
     * constructor of a polynomial translator given its coefs.
     *
     * @param cph
     * @param wpx
     * @param wpy
     * @param pwx
     * @param pwy
     */
    public CoordsPolynomTranslator(double correctiveLongitudePhase,
            double[] wpx, double[] wpy, double pwx[], double pwy[], AffineTransform trans) {
        super();
        this.cph = correctiveLongitudePhase;
        wpxPoly.setCoeffs(wpx);
        wpyPoly.setCoeffs(wpy);
        pwxPoly.setCoeffs(pwx);
        pwyPoly.setCoeffs(pwy);
        at = trans;
    }

    /**
     * constructor of a polynomial translator given the references points.
     *
     * @param correctiveLongitudePhase
     * @param references
     */
    public CoordsPolynomTranslator(double correctiveLongitudePhase,
            Vector<MapReference> references, AffineTransform trans) {
        super();
        int nbrefs = references.size();
        if (nbrefs > 0) {
            this.cph = correctiveLongitudePhase;
            double[] refXArray = new double[nbrefs];
            double[] refLonArray = new double[nbrefs];
            double[] refYArray = new double[nbrefs];
            double[] refLatArray = new double[nbrefs];
            int bcl = 0;
            for (MapReference ref : references) {
                refXArray[bcl] = ref.point.x;
                refYArray[bcl] = ref.point.y;

                // longitude must take into account the cph
                // for maps crossing the day change line
                double lon = ref.position.getLongitude();
                refLonArray[bcl] = (lon < 0) ? lon + cph : lon - cph;
                refLatArray[bcl] = ref.position.getLatitude();
                bcl++;
            }
            pwxPoly = Polynom2D.setData(refLonArray, refXArray, refYArray);
            pwyPoly = Polynom2D.setData(refLatArray, refXArray, refYArray);
            wpxPoly = Polynom2D.setData(refXArray, refLonArray, refLatArray);
            wpyPoly = Polynom2D.setData(refYArray, refLonArray, refLatArray);
        }
        at = trans;
    }

    /*
         * (non-Javadoc)
         *
         * @see
         * net.sourceforge.capcode.maps.transform.CoordsTranslator#getPointFromPosition
         * (net.sourceforge.capcode.maps.position.Position)
     */
    public Point getAbsolutePointFromPosition(Position pos) {
        Point res = new Point(0, 0);
        if (pos != null) {
            return getAbsolutePointFromPosition(pos.getLongitude(), pos.getLatitude());
        }
        return res;
    }

    public Point getAbsolutePointFromPosition(double lon, double lat) {
        Point res = new Point(0, 0);
        Point rotatedPoint = new Point(0, 0);
        StringBuffer sb = new StringBuffer();
        lon = lon - longitudeShift;
        lat = lat - latitudeShift;
        sb.append(String.format("(lat, lon) = (%12.8f, %12.8f) --> ", lat, lon));
        double lon2 = (lon < 0) ? lon + cph : lon - cph;
        res.x = (int) wpxPoly.getValue(lon2, lat);
        res.y = (int) wpyPoly.getValue(lon2, lat);
        sb.append("("+res.x+", "+res.y+")");
        if (at == null) {
            logger.debug(sb.toString());
            return res;
        } else {
            at.transform(res, rotatedPoint);
            sb.append(" --> ("+rotatedPoint.x+", "+rotatedPoint.y+")");
            logger.debug(sb.toString());
            return rotatedPoint;
        }
    }

    /*
         * (non-Javadoc)
         *
         * @see
         * net.sourceforge.capcode.maps.transform.CoordsTranslator#getPositionFromPoint
         * (java.awt.Point)
     */
    public Position getAbsolutePositionFromPoint(Point q) {

        Point p = new Point(0, 0);
        StringBuffer sb = new StringBuffer();
        sb.append("(x,y) = ("+q.x+", "+q.y+") -->");
        if (at == null) {
            p = q;
        } else {
//            logger.debug("Transformed x,y = " + q.x + ", " + q.y);
            try {
                at.inverseTransform(q, p);
                sb.append("("+p.x+", "+p.y+")");
//                logger.debug("Not Transformed x,y = " + p.x + ", " + p.y);
            } catch (NoninvertibleTransformException ex) {
                Logger.getLogger(CoordsPolynomTranslator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        double lon = pwxPoly.getValue(p.x, p.y);
        lon = (lon < 0) ? lon + cph : lon - cph;
        lon = lon + longitudeShift;
        double lat = pwyPoly.getValue(p.x, p.y);
        lat = lat + latitudeShift;
        sb.append(String.format(" --> (%12.8f, %12.8f)", lat, lon));
        logger.debug(sb.toString());
        return new Position(lat, lon);
    }

    public String toString() {
        return super.toString()
                + String.format(
                        " maxErrorXY:%f; maxErrorLonLat:%f", Math.max(wpxPoly.getMaxError(), wpyPoly.getMaxError()), Math.max(pwxPoly.getMaxError(), pwyPoly.getMaxError())); //$NON-NLS-1$
    }

    /**
     * return true if all the polynom are valid.
     *
     * @return
     */
    public boolean isValid() {
        return !pwxPoly.isSingularMatrix() && !pwyPoly.isSingularMatrix()
                && !wpxPoly.isSingularMatrix() && !wpyPoly.isSingularMatrix();
    }

    public void setLatShift(double latitudeShift) {
        this.latitudeShift = latitudeShift;

    }

    public void setLonShift(double longitudeShift) {
        this.longitudeShift = longitudeShift;

    }

}
