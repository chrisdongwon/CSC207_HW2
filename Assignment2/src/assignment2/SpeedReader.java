package assignment2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.FileNotFoundException;

public class SpeedReader {
  public static void main(String[] args) throws FileNotFoundException, InterruptedException {
    parse(args);
  }

  /*
   *  parse: driver procedure for the SpeedReader program
   *  @param: args, command line arguments
   *  @pre: none
   *  @post: error thrown if argument number doesn't match
   *         program executes with the graphics 
   */
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

  /*
   *  focus: returns the index of the character that
   *         needs to be centered and highlighted
   *  @param: str, the word being displayed
   *  @pre: nonempty string
   *  @post: no changes to str
   *         index of the desired position returned
   */
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

  /*
   *  print: displays the string with one of the
   *         characters (at the center) red
   *  @param: g - Graphics object with
   *             x0 * 2 width, y0 * 2 height
   *          str - the word being displayed
   *          x0, y0 - width/2, height/2 of
   *                   the graphics panel 
   *  @pre: nonempty string, nonzero x0 and y0,
   *        graphics g object instantiated
   *  @post: str printed with the red character
   *         in the center of the graphics window
   */
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
  
  /*
   *  draw: wrapper function for displaying
   *        words with the given font size and
   *        speed
   *  @param: seed, a WordGenerator object
   *          spec, an int array where
   *                1st elem. is width
   *                2nd elem. is height
   *                3rd elem. is font size
   *                4th elem. is words per min.
   *  @pre: *width and height both nonzero
   *        *font size within the rectangle with the
   *         aforementioned width and height
   *        * wpm is positive
   *  @post: InterruptedException can be thrown
   *         otherwise, the program executes as desired:
   *            words pop up with the red character at the center
   *            for the duration determined by wpm (last elem.)
   */
  private static void draw(WordGenerator seed, int[] spec) throws InterruptedException {
    DrawingPanel p = new DrawingPanel(spec[0], spec[1]);
    Graphics g = p.getGraphics();
    g.setFont(new Font("Courier", Font.BOLD, spec[2]));

    while (seed.hasNext()) {
      print(g, seed.next(), spec[0] / 2, spec[1] / 2);
      Thread.sleep((int) (60000.0 / spec[3]));
      p.clear();
    }
    print(g, "Sentence Count: "+seed.getSentenceCount(), spec[0] / 2, spec[1] / 2);
    Thread.sleep((int) (60000.0 / spec[3]));
    p.clear();
    print(g, "Word Count: "+seed.getWordCount(), spec[0]/2, spec[1]/2);
  }
}
