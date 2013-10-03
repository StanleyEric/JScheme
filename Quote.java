import java.io.*;

class Quote extends Special {
 
    // TODO: Add any fields needed.
    Node symbol;
 
    // TODO: Add an appropriate constructor.
    /**
     * This constructs a quote node, which will contain a symbol of some type.
     * Becuase this thing doesn't actually get evaluated, I'm not sure
     * what to do with it, or how we should handle it. Also, I have no clue how to 
     * use this class in the actual parser becuase it isn't a node.
     * @param symbol The symbol that the quote node holds.
     */
    public Quote(Node symbol)
    {
	this.symbol = symbol;
    }
    void print(Node t, int n, boolean p) {
    }
}
