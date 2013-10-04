class Cons extends Node {
    private Node car;
    private Node cdr;
    private Special form;
  
    // parseList() `parses' special forms, constructs an appropriate
    // object of a subclass of Special, and stores a pointer to that
    // object in variable form.  It would be possible to fully parse
    // special forms at this point.  Since this causes complications
    // when using (incorrect) programs as data, it is easiest to let
    // parseList only look at the car for selecting the appropriate
    // object from the Special hierarchy and to leave the rest of
    // parsing up to the interpreter.
    void parseList() { 
	if (car.isBoolean() || car.isNumber() || car.isString() || car.isNull())
	{
	    form = new Regular(car, cdr);
	}
	if(car.isSymbol())
	{
	    Ident i = (Ident) car;
	    if(i.toString().equals("if"))
	    {
		form = new If();
	    }
	    else if(i.toString().equals("define"))
	    {
		form = new Define();
	    }
	    else if(i.toString().equals("lambda"))
	    {
		form = new Lambda();
	    }
	    else if(i.toString().equals("begin"))
	    {
		form = new Begin();
	    }
	    else if(i.toString().equals("let"))
	    {
		form = new Let();
	    }
	    else if(i.toString().equals("lambda"))
	    {
		form = new Lambda();
	    }
	    else if(i.toString().equals("quote"))
	    {
		//form = new Quote();
		//TODO figure out the Quote node...
	    }
	    else if(i.toString().equals("Set"))
	    {
		form = new Set();
	    }
	    else
	    {
		form = new Regular(car, cdr);
	    }
	}
    }
    // TODO: Add any helper functions for parseList as appropriate.

    public Cons(Node a, Node d) {
	car = a;
	cdr = d;
	parseList();
	Main.addToDebugStream("Cons node " + this + " has the form " + form.getClass().toString() + " with car = " + car + " and cdr = " + cdr);
    }

    void print(int n) {
	form.print(this, n, false);
    }

    void print(int n, boolean p) {
	form.print(this, n, p);
    }
    
    @Override
    public boolean isPair()
    {
	return true;
    }

}
