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

  public boolean hasNext() {
    return this.text.hasNext();
  }

  public String next() {
    String str = text.next();
    this.words++;
    if (str.endsWith(".") || str.endsWith("?") || str.endsWith("!"))
      this.sentences++;
    return str;
  }

  public int getWordCount() {
    return this.words;
  }

  public int getSentenceCount() {
    return this.sentences;
  }
}
