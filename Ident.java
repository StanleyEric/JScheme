import java.io.*;
class Ident extends Node {
  private String name;

  public Ident(String n) { name = n; }

  public void print(int n) {
    for (int i = 0; i < n; i++)
      System.out.print(" ");

    System.out.println(name);
  }
  
  @Override
  public boolean isSymbol()
  {
      return true;
  }
  
  /**
   * added this as a way for the cons node to tell if the identifier was something like an "if" or 
   * "+" symbol. I'm pretty sure it matters if not now, then later.
   * @return
   */
  @Override
  public String toString()
  {
      return name;
  }
}
