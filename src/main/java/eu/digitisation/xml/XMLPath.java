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
package eu.digitisation.xml;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Evaluate XPath expressions.
 */
public class XMLPath {

    final static XPath xpath;
    
    static {
        xpath = XPathFactory.newInstance().newXPath();
    }

    /**
     * Evaluate XPath expression
     *
     * @param doc the container document
     * @param expression XPath expression
     * @return the list of nodes matching the query
     *
     */
    public static NodeList evaluate(Document doc, String expression) {
        try {            
            return (NodeList) xpath.evaluate(expression, doc,
                    XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLPath.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static NodeList evaluate(File file, String expression) {
         Document doc = DocumentParser.parse(file);
          try {            
            return (NodeList) xpath.evaluate(expression, doc,
                    XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLPath.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static NodeList evaluate(Element element, String expression) {
        try {            
            return (NodeList) xpath.evaluate(expression, element,
                    XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(XMLPath.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /*
     private String toString(Element e) {
     NodeList nodes = e.getElementsByTagName("*");
     int n = nodes.getLength();
     if (n > 0) {
     StringBuilder buff = new StringBuilder();
     buff.append("<").append(e.getTagName()).append(">");
     for (int k = 0; k < n; ++k) {
     buff.append(toString((Element) nodes.item(k)));
     }
     buff.append("</").append(e.getTagName()).append(">");
     return buff.toString();
     } else {
     return "<" + e.getTagName() + "/>";
     }
     }
     */
    // Sample main
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("usage: XMLpath filename xpath");
        } else {
            File file = new File(args[0]);
            String expr = args[1];
            try {
                NodeList nodes = XMLPath.evaluate(file, expr);
                System.out.println(nodes.getLength());
                for (int n = 0; n < nodes.getLength(); ++n) {
                    System.out.println(nodes.item(n));
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
