// Parser.java -- the implementation of class Parser

/* Defines
 *
 *   class Parser;
 *
 * Parses the language
 *
 *   exp  ->  ( rest
 *         |  #f
 *         |  #t
 *         |  ' exp
 *         |  integer_constant
 *         |  string_constant
 *         |  identifier
 *    rest -> )
 *         |  exp+ [. exp] )
 *
 * and builds a parse tree.  Lists of the form (rest) are further
 * `parsed' into regular lists and special forms in the constructor
 * for the parse tree node class Cons.  See Cons.parseList() for
 * more information.
 *
 * The parser is implemented as an LL(0) recursive descent parser.
 * I.e., parseExp() expects that the first token of an exp has not
 * been read yet.  If parseRest() reads the first token of an exp
 * before calling parseExp(), that token must be put back so that
 * it can be reread by parseExp() or an alternative version of
 * parseExp() must be called.
 *
 * If EOF is reached (i.e., if the scanner returns a NULL) token,
 * the parser returns a NULL tree.  In case of a parse error, the
 * parser discards the offending token (which probably was a DOT
 * or an RPAREN) and attempts to continue parsing with the next token.
 */

/*
 * Notes from class concerning the parser: 
 * 
 * When implementing the dot notation (a b c .d) (meaning we want the d last in the
 * parse tree) you want to modify the grammar, not hack it in.
 * 
 * exp -> ( rest
 * |...
 * |...
 * | .exp)
 * 
 * will not work. We need to handle this in the grammar for rest. 
 * 
 */

class Parser {
    private Scanner scanner;
    Node parseTree = new Node();
    Nil utilNil = new Nil(); //This will apparently make future parts 
    			     //of the project easier.
    
    public Parser(Scanner s) {
	scanner = s;
    }
    /**
     * This is what gets called from Main. Just a glorified ability to start
     * the process.
     * @return The completed parse tree.
     */
    public Node parseExp() {
	return parseExp(scanner.getNextToken());
    }

    protected Node parseRest() 
    {
	// TODO: write code for parsing rest
	Token t = scanner.getNextToken();
	if(t.getType() == Token.RPAREN)
	{
	    Main.addToDebugStream("Detected RPAREN. Adding Nil Node");
	    return utilNil;
	}
	else if(t.getType() == Token.INT || t.getType() == Token.STRING ||
		t.getType() == Token.IDENT || t.getType() == Token.TRUE ||
		t.getType() == Token.TRUE || t.getType() == Token.FALSE) //Terminators
	{
	    Main.addToDebugStream("Forming Cons node. Token for the car is " + t);
	    return new Cons(parseExp(t), parseRest());
	}
	else
	{
	    Node n = parseExp(t);
	    return new Cons(n, parseRest());
	}
    }

    /**
     * Parse an expression with a one token lookahead.
     * 
     * @param t the lookahead token
     * @return a valid parse tree.
     */
    protected Node parseExp(Token t) {
	if(t == null)//This can happen when we reach the end of the file
	{
	    return utilNil;
	}
	else if(t.getType() == TokenType.QUOTE)
	{
	    Node n = parseExp(scanner.getNextToken());
	    Quote q = new Quote(n);
	    return null; //TODO figure out how to return a node here.
	}
	else if(t.getType() == TokenType.LPAREN)
	{
	    Main.addToDebugStream("Found a LPAREN. parseRest().");
	    return parseRest();
	}
	else if(t.getType() == TokenType.DOT)
	{
	    
	}
	else if(t.getType() == TokenType.TRUE)
	{
	    return new BooleanLit(true);
	}
	else if(t.getType() == TokenType.FALSE)
	{
	    return new BooleanLit(false);	    
	}
	else if(t.getType() == TokenType.INT)
	{
	    return new IntLit(t.getIntVal());
	}
	else if(t.getType() == TokenType.STRING)
	{
	    return new StrLit(t.getStrVal());
	}
	else if(t.getType() == TokenType.IDENT)
	{
	    Main.addToDebugStream("Found an identifier with name: " + t.getName());
	    return new Ident(t.getName());
	}
	else
	{
	    System.out.println("We goofed up somewhere. parseExp didn't recognize the token: " + t);
	}
	return new Nil();
    }
};
