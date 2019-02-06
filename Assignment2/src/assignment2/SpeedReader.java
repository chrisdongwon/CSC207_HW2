package assignment2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.FileNotFoundException;

// https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
// https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html
// https://mathbits.com/MathBits/Java/Graphics/Color.htm

public class SpeedReader {
  public static void main(String[] args) throws FileNotFoundException, InterruptedException {
    parse(args);
  }

  private static void parse(String[] args) throws FileNotFoundException, InterruptedException {
    if (args.length != 5) {
      System.err.println("Incorrect number of arguments.");
      System.exit(1);
    } else {
      WordGenerator seed = new WordGenerator(args[0]);
      int[] spec = new int[4];
      for (int i = 0; i < 4; i++)
        spec[i] = Integer.parseInt(args[i + 1]);
      draw(seed, spec);
    }
  }

  private static int focus(String str) {
    if (str.length() == 1)
      return 0;
    else if (str.length() < 6)
      return 1;
    else if (str.length() < 10)
      return 2;
    else if (str.length() < 14)
      return 3;
    else
      return 4;
  }

  private static void print(Graphics g, String str, int x0, int y0) {
    FontMetrics m = g.getFontMetrics();
    int center = focus(str);
    String front = str.substring(0, center);
    int centerWidth = m.charWidth(str.charAt(center));
    int frontWidth = m.stringWidth(front);
    int y1 = y0 + (m.getHeight() / 2);
    int x1 = x0 - (frontWidth + (centerWidth / 2));

    g.drawString(front, x1, y1);

    g.setColor(Color.red);
    x1 += frontWidth;
    g.drawString(str.substring(center, center + 1), x1, y1);

    g.setColor(Color.black);
    x1 += centerWidth;
    g.drawString(str.substring(center + 1), x1, y1);
  }

  private static void draw(WordGenerator seed, int[] spec) throws InterruptedException {
    DrawingPanel p = new DrawingPanel(spec[0], spec[1]);
    Graphics g = p.getGraphics();
    g.setFont(new Font("Courier", Font.BOLD, spec[2]));

    while (seed.hasNext()) {
      print(g, seed.next(), spec[0] / 2, spec[1] / 2);
      Thread.sleep((int) (60000.0 / spec[3]));
      p.clear();
    }
  }
}
