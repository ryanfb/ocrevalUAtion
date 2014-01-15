/*
 * Copyright (C) 2013 Universidad de Alicante
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package eu.digitisation.math;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Creates toy histograms
 *
 * @author R.C.C.
 */
public class Histogram {

    private static final long serialVersionUID = 1L;
    int[] X;
    int[] Y;
   

    /**
     * Create points for plot (one point per bar, equally spaced).
     *
     * @param <Type>
     * @param counter  a Counter
     */
    public <Type extends Comparable<Type>> Histogram (Counter<Type> counter) {
        int[][] points = new int[2][counter.size()];
        int n = 0;

        for (Type key : counter.keySet()) {
            Object obj = key;
            if (obj instanceof Integer) {
               Integer iobj = (Integer)obj;
               X[n] = iobj.intValue();
            } else {
               X[n] =  n;
            }
            Y[n] = counter.get(key);
            ++n;
        }
    }

 /**
     * Integer exponentiation (for axis)
     *
     * @param base base
     * @param exp exponent
     * @return the exp-th power of base
     */
    private int pow(int base, int exp) {
        int result = 1;
        while (exp != 0) {
            if ((exp & 1) == 1) {
                result *= base;
            }
            exp >>= 1;
            base *= base;
        }
        return result;
    }

    /**
     * Display histogram on screen
     *
     * @param width display width (in pixels)
     * @param height display height (in pixels)
     * @param margin display margins (in pixels)
     */
    public void show(int width, int height, int margin) {
        BufferedImage bim
                = new BufferedImage(width + 2 * margin,
                        height + 2 * margin,
                        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bim.createGraphics();
        int xhigh = ArrayMath.max(X);
        int xlow = ArrayMath.min(X);
        int xrange = xhigh - xlow;
        int yhigh = ArrayMath.max(Y);
        int ylow = 0; //ArrayMath.min(Y);
        int yrange = yhigh - ylow;

        // draw bars
        g.setColor(Color.RED);
        for (int n = 0; n < X.length; ++n) {
            int xpos = (width * (X[n] - xlow)) / xrange;
            int ypos = (height * (Y[n] - ylow)) / yrange;
            g.fillRect(margin + xpos - 1, height + margin - ypos, 3, ypos);
        }

        // draw X and Y axes
        g.setColor(Color.BLUE);
        g.drawRect(margin, margin, width, height);

        // draw Y-tics
        int e = (int) Math.ceil(Math.log(yrange) / Math.log(10)) - 1;
        int ystep = (e > 0) ? pow(10, e) : 1;
        for (int y = ylow - ylow % ystep; y <= yhigh; y += ystep) {
            int ypos = (height * (y - ylow)) / yrange;
            g.drawString(String.valueOf(y) + "-", 0, height + margin - ypos);
        }

        // draw X-tics
        e = (int) Math.ceil(Math.log(xrange) / Math.log(10)) - 1;
        int xstep = (e > 0) ? pow(10, e) : 1;
        for (int x = xlow - xlow % xstep; x <= xhigh; x += xstep) {
            int xpos = (width * (x - xlow)) / xrange;
            g.drawString(String.valueOf(x) , margin + xpos - 6 * e, height + margin + 12);
            g.drawLine(margin + xpos, height + margin,
                    margin + xpos, height + margin - 5);
        }

        g.dispose();
        eu.digitisation.image.Display.draw(bim);
    }
}