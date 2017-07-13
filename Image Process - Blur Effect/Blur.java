/*
 * Copyright (c) 2002, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * -Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 * Neither the name of Oracle nor the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES  SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that Software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

import java.awt.*;
import java.applet.Applet;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;


@SuppressWarnings("serial")
public class Blur extends Applet {

    private BufferedImage bi;
    float[] elements = { .1111f, .1111f, .1111f,
                         .1111f, .1111f, .1111f,
                         .1111f, .1111f, .1111f};

    public Blur() {

            setBackground(Color.white);

            Image img = getToolkit().getImage("/Users/JXproject/Desktop/AAA.jpg");
            try {
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(img, 0);
                tracker.waitForID(0);
            } catch (Exception e) {}

            int iw = img.getWidth(this);
            int ih = img.getHeight(this);
            bi = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();
            big.drawImage(img,0,0,this);

    }


    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int w = getSize().width;
        int h = getSize().height;
        int bw = bi.getWidth(this);
        int bh = bi.getHeight(this);

        AffineTransform at = new AffineTransform();
        at.scale(w/2.0/bw, h/1.0/bh);

        BufferedImageOp biop = null;

        BufferedImage bimg = new BufferedImage(bw,bh,BufferedImage.TYPE_INT_RGB);

        Kernel kernel = new Kernel(3, 3, elements);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(bi,bimg);
        
        BufferedImage [] bimg2 = new BufferedImage [10];
        bimg2[0] = new BufferedImage(bw,bh,BufferedImage.TYPE_INT_RGB);
        cop.filter(bimg,bimg2[0]);
        for(int i=1;i<bimg2.length;i++){
        	bimg2[i] = new BufferedImage(bw,bh,BufferedImage.TYPE_INT_RGB);
        	cop.filter(bimg2[i-1],bimg2[i]);
        }
        
        
        biop = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        g2.drawImage(bi, biop, 0, 0);
        g2.drawImage(bimg2[bimg2.length-1], biop, w/2+3,0);

    }

    @SuppressWarnings("deprecation")
	public static void main(String s[]) {
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        };
        Frame f = new Frame("Blur");
        f.addWindowListener(l);
        f.add("Center", new Blur());
        f.pack();
        f.setSize(new Dimension(600, 300));
        f.show();
    }
}

//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//
//import javax.swing.JComponent;
//import javax.swing.JFrame;
//
//public class MotionBlur
//{
//	public static void main(String[] args)
//	{
//		JFrame frame = new JFrame();
//		frame.setTitle("Motion Blur with Graphics2D | Vallentin Source");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		
//		
//		JComponent c = new JComponent()
//		{
//			int x = 0;
//			
//			@Override
//			public void paint(Graphics g)
//			{
//				Graphics2D g2d = (Graphics2D) g;
//				
//				g2d.setColor(new Color(0, 0, 0, 55));
//				g2d.fillRect(0, 0, getWidth(), getHeight());
//				
//				
//				g2d.setColor(Color.RED);
//				g2d.fillOval(x - 40, getHeight() / 2 - 40 + (int) (Math.cos(Math.toRadians(x)) * 20), 80, 80);
//				
//				x += 10;
//				
//				if (x - 50 > getWidth())
//				{
//					x = -50;
//				}
//				
//				try { Thread.sleep(10); } catch (Exception ex) {}
//				
//				repaint();
//			}
//		};
//		
//		c.setOpaque(true);
//		
//		frame.add(c);
//		
//		
//		
//		frame.setSize(400, 400);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//	}
//}
