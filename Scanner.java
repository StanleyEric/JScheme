// Scanner.java -- the implementation of class Scanner

import java.io.*;

class Scanner {
    private PushbackInputStream in;

    public Scanner(InputStream i) {
	in = new PushbackInputStream(i);
    }

    public Token getNextToken() {
	int bite = -1;

	// It would be more efficient if we'd maintain our own input buffer
	// and read characters out of that buffer, but reading individual
	// characters from the input stream is easier.
	try {
	    bite = in.read();
	} catch (IOException e) {
	    System.err.println("We fail: " + e.getMessage());
	}

	// skip white space and comments

	if (Character.isWhitespace(bite)) // This will recursively help us skip
					  // whitespace chars -Mitch
	{
	    return this.getNextToken();
	}

	if (bite == ';') // While this should take care of comments -Mitch
	    try {
		/*
		 * These are (almost) all the unicode newline chars. I didn't
		 * really want to implement the CR (U+000D) followed by LF
		 * (U+000A) as a single newline. The code should ignore them
		 * anyway. This code works well enough for now. It still doesn't
		 * recognize all unicode supported newlines, and that's a
		 * problem.
		 */
		while (bite != 10 && bite != 11 && bite != 12 && bite != 13) {
		    bite = in.read();
		}
	    } catch (IOException e) {
		System.err.println("We fail: " + e.getMessage());
	    }

	if (bite == -1)
	    return null;

	char ch = (char) bite;

	// Special characters
	if (ch == '\'')
	    return new Token(Token.QUOTE);
	else if (ch == '(')
	    return new Token(Token.LPAREN);
	else if (ch == ')')
	    return new Token(Token.RPAREN);
	else if (ch == '.')
	    // We ignore the special identifier `...'.
	    return new Token(Token.DOT);

	// Boolean constants
	else if (ch == '#') {
	    try {
		bite = in.read();
	    } catch (IOException e) {
		System.err.println("We fail: " + e.getMessage());
	    }

	    if (bite == -1) {
		System.err.println("Unexpected EOF following #");
		return null;
	    }
	    ch = (char) bite;
	    if (ch == 't')
		return new Token(Token.TRUE);
	    else if (ch == 'f')
		return new Token(Token.FALSE);
	    else {
		System.err.println("Illegal character '" + (char) ch
			+ "' following #");
		return getNextToken();
	    }
	}

	// String constants
	else if (ch == '"') {
	    // TODO: scan a string into the buffer variable buf
	    String str = "";
	    try{
		bite = in.read();
		if(bite == -1)
		    {
		    	System.err.println("Unexpected EOF following #");
			return null;
		    }
		ch = (char) bite;
		while(ch != '"')
		{
		    str += ch;
		    bite = in.read();
		    ch = (char) bite;
		}
	    }catch(Exception e)
	    {
		System.err.println("We done goofed: " + e.getLocalizedMessage());
	    }
	    return new StrToken(str);
	}

	// Integer constants
	else if (ch >= '0' && ch <= '9') {
	    // TODO: scan the number and convert it to an integer
	    String i = "";
	    while (Character.isDigit(ch)) {
		i += ch;
		try {
		    bite = in.read();
		    if (bite == -1) {
			System.err.println("Unexpected EOF following an int");
			return null;
		    }
		    ch = (char) bite;
		} catch (Exception e) {
		    System.err.println("We failed :" + e.getLocalizedMessage());
		}
	    }
	    return new IntToken(Integer.parseInt(i));
	}

	// Identifiers
	
	else if (Character.isLetter(ch)
	/* or ch is some other valid first character for an identifier */) {
	    String ident = "";
	    while (!Character.isWhitespace(ch)) 
	    {
		ident += ch;
		try {
		    bite = in.read();
		    if(ch == ')')
		    {
			/**
			 * If we hava a ')', the identifier is over. We need to move the input 
			 * stream back to the ')' so that we can return a RPAREN token,
			 * and return what we have. 
			 */
			in.unread(ch);
			ident = ident.substring(0, ident.length() -1);
			return new IdentToken(ident);
		    }
		    if (bite == -1) {
			System.err.println("Unexpected EOF in an identifier");
			return null;
		    }
		    ch = (char) bite;
		} catch (Exception e) {
		    System.err.println("We failed :" + e.getLocalizedMessage());
		}
	    }
	    return new IdentToken(ident);
	}

	// Illegal character
	else {
	    System.err.println("Illegal input character '" + (char) ch + '\'');
	    return getNextToken();
	}
    };
}
