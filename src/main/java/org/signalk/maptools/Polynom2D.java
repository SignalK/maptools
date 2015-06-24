package org.signalk.maptools;

/**
 * Project: capcode.basics Polynom2D.java created 24 juin 2009 by cyrille 
 * Copyright (c) 2007 Capcode Inc. All Rights Reserved.
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
 * $Date: 2009-08-31 22:18:08 $ 
 * $Header: /media/disk/capcodeCVSbackup/capcode.basics/src/capcode/maths/Polynom2D.java,v 1.5 2009-08-31 22:18:08 crosay Exp $
 * $Log: not supported by cvs2svn $
 * Revision 1.4  2009/08/25 22:33:59  crosay
 * Complete # add reference points to maps
 * http://sourceforge.net/apps/trac/capcode/ticket/22
 * Incomplete # move the display of the VMG arrow from map display plugin to polar display plugin
 * http://sourceforge.net/apps/trac/capcode/ticket/29
 *
 * Revision 1.3  2009/08/15 15:14:06  crosay
 * Complete # add reference points to maps
 * http://sourceforge.net/apps/trac/capcode/ticket/22
 *
 * Revision 1.2  2009/07/19 09:29:01  crosay
 * Incomplete task add references points to ccmaps
 *
 * Revision 1.1  2009/07/01 22:10:12  crosay
 * Complete - taskSourceForge.net: CapCode-software suite for sailors: Detail: 2798790 - solve bug 2798789 error with some BSB 2 maps
 * https://sourceforge.net/tracker/?func=detail&aid=2798790&group_id=100698&atid=1045270
 *
 * $Revision: 1.5 $ 
 * $State: Exp $ 
 */

import java.util.Arrays;

import Jama.Matrix;




/**
 * @author cyrille
 *
 */
public class Polynom2D  {
        private double[] arrayX;
        private double[] arrayY;
        private double[] arrayF;
        private double[] coeffs;
        
// the following array are used to compute the weight of the x, y in the polynom
//                        1, 
//                        x, y, 
//                        x*x, x*y, y*y, 
//                        x*x*x, x*x*y, x*y*y, y*y*y,
//                        x*x*x*x, x*x*x*y, x*x*y*y, x*y*y*y, y*y*y*y};
        private final int[] powX = {
                        0, 
                        1, 0, 
                        2, 1, 0,
                        3, 2, 1, 0,
                        4, 3, 2, 1, 0};
        
        private final int[] powY = {
                        0, 
                        0, 1, 
                        0, 1, 2,
                        0, 1, 2, 3,
                        0, 1, 2, 3, 4};
        
        private int nbCoeff;        
        private int order;
        //decision to limit the search of the polynom to a certain order
        private int maxOrder = 4;
        //set to true if the matrix used is singular
        private boolean singularMatrix = false;
        
        /**
         * Purpose: Generate a least squares fit of the f(x,y) samples to
         *  a 2-D polynomial of order 1=bilinear, 2=biquadratic, 3=bicubic etc..
         * Description:
         *   This routine fits a surface of order depending on the number of parameters
         *   the order is limited to 3 for the moment
         *   (order = 1 - bilinear, 2 - biquadratic, 3 - bicubic, 4 - ???)
         * @param f - array of values for f(x,y)
         * @param x - array of x values
         * @param y - array of y values
         */
        public Polynom2D(double[] fxy, double[] xs, double[] ys) {
                arrayX = xs.clone();
                arrayY = ys.clone();
                arrayF = fxy.clone();
                int nbPoints = arrayX.length;
                coeffs = computeCoeffs(nbPoints);
        }
        
        /**
         * compute the coefficients of the polynom.
         * uses a jama.Matrix
         * @return
         */
        private double[] computeCoeffs(int nbPoints) {
                order = maxOrder;
                nbCoeff = ( order + 1 ) * ( order + 2 ) / 2;                
                while (nbPoints < nbCoeff ){
                        order--;
                        nbCoeff = ( order + 1 ) * ( order + 2 ) / 2;                
                } 
                //special case
                if (nbPoints == 2) {
                        order = 1;
                        nbCoeff = 2;
                }
                
                if (order > 0 ) {
                        Matrix m = new Matrix(nbPoints, nbCoeff);
                        for (int i = 0; i < nbPoints; i++) {
                                double x = arrayX[i];
                                double y = arrayY[i];
                                //a0 + a1*x + a2 * y + a3 * x^2 + a4 * x * y + a5 * y^2 + a6 * x^3 + a7 * x^2 * y + a8 * x * y^2 + a9 * y^3
                                for (int j = 0; j < nbCoeff; j++) {
                                        double f = Math.pow(x, powX[j]) * Math.pow(y, powY[j]);
                                        m.set(i, j, f);
                                }
                        }
                        Matrix bmat = new Matrix(nbPoints, 1);
                        for (int i = 0; i < nbPoints; i++) {
                                bmat.set(i, 0, arrayF[i]);
                        }
                        try {
                                //coefficient matrix
                                Matrix mc = m.solve(bmat);
                                //transform into array
                                coeffs = mc.getRowPackedCopy();
                        }catch(RuntimeException re) {
                                singularMatrix = true;
                                //try to solve ignoring the last entry
                                int newNbPoints = nbPoints-1;
                                coeffs = computeCoeffs(newNbPoints);
                        }
                }
                return coeffs;
        }

        /**
         * used to print the content of the matrix for debug purpose.
         * @param m
         * @return
         */
        public static String matrixAsString(Matrix m) {
                String s = ""; //$NON-NLS-1$
                for (int i = 0; i < m.getColumnDimension(); i++) {
                        s = s + Arrays.toString(m.getArray()[i]) + "\n"; //$NON-NLS-1$
                }
                return s;
        }

        public Polynom2D() {
                super();
        }

        /**
         * @return the coefficients of the 2D polynomial
         */
        public double[] getCoeffs(){
                return coeffs;
        }
        
        /**
         * @return the errors (computed vs reference).
         */
        public double[] getErrors(){
                double[] res= null;
                if (arrayF != null && arrayX != null && arrayY != null) {
                        res = new double[arrayF.length];
                        for (int i = 0; i<this.arrayX.length; i++) {
                                res[i] = arrayF[i] - getValue(arrayX[i], arrayY[i]);
                        }
                }
                else {
                        res = new double[] {0};
                }
                return res;
        }
        
        /**
         *         return f(x,y) as<br>
         * a0 + a1*x + a2 * y + a3 * x^2 + a4 * x * y + a5 * y^2 + a6 * x^3 + a7 * x^2 * y + a8 * x * y^2 + a9 * y^3
         * @param x
         * @param y
         * @return the meaning of the 2D polynomial depending on x,y and on the order.
         */
        public double getValue(double x, double y){
                double res = 0;
                for (int j = 0; j < nbCoeff; j++) {
                        double f = Math.pow(x, powX[j]) * Math.pow(y, powY[j]);
                        res += (coeffs[j] * f);
                }
                return (res);
        }


        /**
         * 
         * @return the order of the 2D polynom
         */
        public int getOrder() {
                return order;
        }

        /**
         * 
         * @return the error max of the polynom.
         */
        public double getMaxError() {
                double res = -Double.MAX_VALUE;
                for (double d:this.getErrors()) {
                        res = Math.max(res, d);
                }
                return res;
        }

        /**
         * set the coefficients of the polynom2D.
         * @param c
         */
        public void setCoeffs(double[] c) {
                coeffs = c;
                nbCoeff = c.length;
                //compute the order of the polynom
                for (order = maxOrder ; nbCoeff <= ( order + 1 ) * ( order + 2 ) / 2; order--) {
                        if (order<1) break;
                }
        }

       public static Polynom2D setData(double[] f, double[] x, double[] y) {
                return new Polynom2D(f, x ,y);
        }

        /**
         * @return true if the Matrix is singular
         */
        public boolean isSingularMatrix() {
                return singularMatrix;
        }
        
        public String toString() {
                String res = String.format("order: %d", order);  //$NON-NLS-1$
                for (int i = 0; i < nbCoeff; i++) {
                        String cs = coeffs[i] == 0 ? "" : String.format("%e", coeffs[i]); //$NON-NLS-1$ //$NON-NLS-2$
                        String sx = powX[i] == 0 ? "" : powX[i] == 1 ? "x" : "x^"+Integer.toString(powX[i]); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        String sy = powY[i] == 0 ? "" : powY[i] == 1 ? "y" : "y^"+Integer.toString(powY[i]); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        String sign = (i > 0 && coeffs[i] > 0) ? "+" : ""; //$NON-NLS-1$ //$NON-NLS-2$
                        res += coeffs[i] != 0 ? sign + cs + "*" + sx + sy : ""; //$NON-NLS-1$ //$NON-NLS-2$
                }
                res += " errors: " + Arrays.toString(this.getErrors()); //$NON-NLS-1$
                return res;
        }
                 
}

