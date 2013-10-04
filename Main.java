// Main.java -- the main program

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    /*
     * This string contains all the debug information from the entire parser.
     * Anyone can write to it and, if we provide -d as a program arg from the
     * console, we will print it at the conclusion of execution.
     */
    private static String debugStream = "";
    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:mmm"); //This is so the debug lines have a timestamp

    // Array of token names used for debugging the scanner
    public static final String TokenName[] = { "QUOTE", // '
	    "LPAREN", // (
	    "RPAREN", // )
	    "DOT", // .
	    "TRUE", // #t
	    "FALSE", // #f
	    "INT", // integer constant
	    "STRING", // string constant
	    "IDENT" // identifier
    };

    public static void addToDebugStream(String s)
    {
	Date date = new Date();
	debugStream = debugStream + dateFormat.format(date) + " " + s + '\n';
    }

    public static void main(String argv[]) {
	// create scanner that reads from standard input
	Scanner scanner = new Scanner(System.in);

	if (argv.length > 2) {
	    System.err.println("Usage: java Main " + "[-d]");
	    System.exit(1);
	}

	// Create parser
	Parser parser = new Parser(scanner);
	Node root = parser.parseExp();

	root.print(0);

	// Parse and pretty-print each input expression
	// root = parser.parseExp();
	// while (root != null) {
	// root.print(0);
	// root = parser.parseExp();
	// }

	// if commandline option -d is provided, print debug information
	if (argv.length == 1 && argv[0].equals("-d")) {
	    System.out.println( '\n' + debugStream);
	}

	System.exit(0);
    }
}
