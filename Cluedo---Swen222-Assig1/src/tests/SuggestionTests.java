package tests;

import static org.junit.Assert.*;
import game.Suggestion;

import org.junit.Test;

public class SuggestionTests {

  @Test
  public void test_equals() {
    Suggestion A  = new Suggestion("person","weapon","room");
    Suggestion A_ = new Suggestion("person","weapon","room");
    Suggestion B  = new Suggestion("XXXXXX","weapon","room");
    Suggestion C  = new Suggestion("person","XXXXXX","room");
    Suggestion D  = new Suggestion("person","weapon","XXXX");
    
    assertTrue(A.equals(A));
    assertTrue(A.equals(A_));
    assertFalse(A.equals(null));
    assertFalse(A.equals(B));
    assertFalse(A.equals(C));
    assertFalse(A.equals(D));
  }

}
