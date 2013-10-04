import java.io.*;

class Regular extends Special {
 
    // TODO: Add any fields needed.
    Node car;
    Node cdr;
 
    // TODO: Add an appropriate constructor.
    public Regular(Node car, Node cdr)
    {
	this.car = car;
	this.cdr = cdr;
    }
    void print(Node t, int n, boolean p) {
	for(int i=n; i>0; i--)
	{
	    System.out.print(" ");
	}
	if(p) //have we printed the LPAREN yet?
	    {
	    	System.out.print("(");
	    	car.print(0,true);
		System.out.print(" ");
		cdr.print(0,true);
		System.out.print(")");
	    }
	else
	{
	    	car.print(0,true);
	    	if(!cdr.isNull());
	    		cdr.print(0,true);
	}
    }
    
    void print(int n, boolean p) {
	for(int i=n; i>0; i--)
	{
	    System.out.print(" ");
	}
	if(p) //have we printed the LPAREN yet?
	    {
	    	System.out.print("(");
	    	car.print(0,true);
		System.out.print(" ");
		cdr.print(0,true);
		System.out.print(")");
	    }
	else
	{
	    	car.print(0,true);
	    	if(!car.isNull());
	    		cdr.print(0,true);
	}
    }
}
