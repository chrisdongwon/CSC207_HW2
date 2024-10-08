package assignment2;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class WordGenerator {
  private Scanner text;
  private int words;
  private int sentences;

  public WordGenerator(String filename) throws FileNotFoundException {
    text = new Scanner(new File(filename));
    words = 0;
    sentences = 0;
  }
  /*
   * @ return if the text has a word following the current one
   * 
   */
  public boolean hasNext() {
    return this.text.hasNext();
  }
  /*
   * @ return next word in text 
   * post: increments word count and sentence count when necessary
   * 
   */
  public String next() {
    String str = text.next();
    this.words++;
    if (str.endsWith(".") || str.endsWith("?") || str.endsWith("!"))
      this.sentences++;
    return str;
  }
  /*
   * @return gets the word count
   */
  public int getWordCount() {
    return this.words;
  }
  /*
   * @return gets the sentence count
   */
  public int getSentenceCount() {
    return this.sentences;
  }
}
