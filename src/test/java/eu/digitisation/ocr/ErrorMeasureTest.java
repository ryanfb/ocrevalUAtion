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
package eu.digitisation.ocr;

import eu.digitisation.io.TextBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafa
 */
public class ErrorMeasureTest {

    public ErrorMeasureTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of trim method, of class ErrorMeasure.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testMerge() throws Exception {
        System.out.println("merge");
        URL resourceUrl = getClass().getResource("/text2.txt");
        File file = Paths.get(resourceUrl.toURI()).toFile();
        String encoding = "utf8";
        String expResult = "mi en hora buena";
        String result = TextBuilder.trimmed(file, encoding).toString();

        //System.out.println(result.replaceAll(" ", "*"));
        assertEquals(expResult, result);
    }

    /**
     * Test of cer method, of class ErrorMeasure.
     */
    @Test
    public void testCer() throws IOException {
        System.out.println("cer");
        File file1 = new File("./target/test-classes/text1.txt");
        String encoding1 = "utf8";
        File file2 = new File("./target/test-classes/text2.txt");
        String encoding2 = "utf8";
        double expResult = 3.0 / 14;
        double result = ErrorMeasure.cer(file1, encoding1, file2, encoding2);
        assertEquals(expResult, result, 0.001);
    }

    /**
     * Test of wer method, of class ErrorMeasure.
     */
    @Test
    public void testWer() throws IOException {
        System.out.println("wer");
        File file1 = new File("./target/test-classes/text1.txt");
        String encoding1 = "utf8";
        File file2 = new File("./target/test-classes/text2.txt");
        String encoding2 = "utf8";
        double expResult = 0.5;
        double result = ErrorMeasure.wer(file1, encoding1, file2, encoding2);
        assertEquals(expResult, result, 0.001);

    }
}
