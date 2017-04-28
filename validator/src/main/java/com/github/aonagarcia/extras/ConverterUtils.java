package com.github.aonagarcia.extras;




import java.io.StreamTokenizer;
import java.io.Serializable;
import java.io.IOException;


public class ConverterUtils implements Serializable {

  
  public static void getFirstToken(StreamTokenizer tokenizer) 
    throws IOException {
    
    while (tokenizer.nextToken() == StreamTokenizer.TT_EOL){};
    if ((tokenizer.ttype == '\'') ||
	(tokenizer.ttype == '"')) {
      tokenizer.ttype = StreamTokenizer.TT_WORD;
    } else if ((tokenizer.ttype == StreamTokenizer.TT_WORD) &&
	       (tokenizer.sval.equals("?"))) {
      tokenizer.ttype = '?';
    }
  }

  
  public static void getToken(StreamTokenizer tokenizer) throws IOException {
    
    tokenizer.nextToken();
    if (tokenizer.ttype== StreamTokenizer.TT_EOL) {
      return;
    }

    if ((tokenizer.ttype == '\'') ||
	(tokenizer.ttype == '"')) {
      tokenizer.ttype = StreamTokenizer.TT_WORD;
    } else if ((tokenizer.ttype == StreamTokenizer.TT_WORD) &&
	       (tokenizer.sval.equals("?"))) {
      tokenizer.ttype = '?';
    }
  }

  
  public static void errms(StreamTokenizer tokenizer, String theMsg) 
    throws IOException {
    
    throw new IOException(theMsg + ", se leyó " + tokenizer.toString());
  }
}


