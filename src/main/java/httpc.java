import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;

public class httpc {
    public static void main(String[] args) throws IOException {
        OptionParser parser = new OptionParser() {
            {
                accepts("v","Verbose")
                .withOptionalArg()
                .defaultsTo("false");

                acceptsAll(asList("http:","'http:"));



            }
        };

        String input;

        while ((input = readCommand()).length() > 0) {
            System.out.println("Input: " + input);
            handleInput(parser, input);
        }

        System.out.println("Exiting...");
    }

    private static void handleInput(OptionParser parser, String input) {
        if (validAndNotHelpCmd(input)) {
            OptionSet opts = parser.parse(input.split(" "));
            List nonOpts=opts.nonOptionArguments();
System.out.println("Verbose: "+opts.valueOf("v"));

            if(nonOpts.size()!=3){
                for(Object o:nonOpts){
                    System.out.println(o.toString());
                }
                System.out.println("Invalid Command. here");
//                printHelp("httpc");
            }
            else{
                String url=nonOpts.get(2).toString();

                if(nonOpts.contains("get")){

                }
                else if(nonOpts.contains("post")){

                }
                else{

                }
            }

        }
    }

    /**
     * This function returns true if given input is valid and not a help command, otherwise, false
     *
     * @param input
     * @return
     */
    private static boolean validAndNotHelpCmd(String input) {
        if (input.startsWith("httpc get ") || input.startsWith("httpc post ")) {
            if(input.contains("http:")){
                input.replace("http:","-http:");
            }
            else if(input.contains("'http:")){
                input.replace("'http:","-http:");
                input.substring(0,input.length()-2);
            }
            return true;
        }

        String option = "httpc";

        if (input.trim().equalsIgnoreCase("httpc help get")) {
            option = "get";
        } else if (input.trim().equalsIgnoreCase("httpc help post")) {
            option = "post";
        }
        else if (!input.startsWith("httpc help")) {
            System.out.println("Invalid Command.");
        }

        printHelp(option);
        return false;
    }

    /**
     * This function returns the next line on system console as string.
     *
     * @return
     */
    private static String readCommand() {
        System.out.println("\nEnter your command below or press 'RETURN' key to exit.");
        return (new Scanner(System.in)).nextLine();
    }

    private static void printHelp(String option) {
        if (option.equals("get")) {
            System.out.println("\nusage: httpc get [-v] [-h key:value] URL\n" +
                    "Get executes a HTTP GET request for a given URL.\n" +
                    "   -v Prints the detail of the response such as protocol, status,\n" +
                    "   and headers.\n" +
                    "   -h key:value Associates headers to HTTP Request with the format\n" +
                    "   'key:value'.");
        } else if (option.equals("post")) {
            System.out.println("\nusage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\n" +
                    "Post executes a HTTP POST request for a given URL with inline data or from\n" +
                    "file.\n" +
                    "   -v Prints the detail of the response such as protocol, status,\n" +
                    "   and headers.\n" +
                    "   -h key:value Associates headers to HTTP Request with the format\n" +
                    "   'key:value'.\n" +
                    "   -d string Associates an inline data to the body HTTP POST request.\n" +
                    "   -f file Associates the content of a file to the body HTTP POST\n" +
                    "   request.\n" +
                    "Either [-d] or [-f] can be used but not both.");
        } else if (option.equals("httpc")) {
            System.out.println("\nhttpc is a curl-like application but supports HTTP protocol only.\n" +
                    "Usage:\n" +
                    "   httpc command [arguments]\n" +
                    "The commands are:\n" +
                    "   get executes a HTTP GET request and prints the response.\n" +
                    "   post executes a HTTP POST request and prints the response.\n" +
                    "   help prints this screen.\n" +
                    "Use \"httpc help [command]\" for more information about a command.");
        } else {
            System.out.println("Invalid Command.");
        }
    }
}
