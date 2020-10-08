import java.util.Scanner;

public class httpc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        String[] options = input.split(" ");
        if(options[0].equalsIgnoreCase("httpc")) {
            if(options[1].equalsIgnoreCase("get") || options[1].equalsIgnoreCase("post")) {
                
            } else if(options[1].equalsIgnoreCase("help")) {
                if(options.length == 3) {
                    printHelp(options[2]);
                } else if(options.length == 2) {
                    printHelp(options[0]);
                } else {
                    System.out.println("Invalid Command");
                }
            }
        } else {
            System.out.println("Invalid Command");
        }
    }

    private static void printHelp(String option) {
        if(option.equalsIgnoreCase("get")) {
            System.out.println("usage: httpc get [-v] [-h key:value] URL\n" +
                    "Get executes a HTTP GET request for a given URL.\n" +
                    "   -v Prints the detail of the response such as protocol, status,\n" +
                    "   and headers.\n" +
                    "   -h key:value Associates headers to HTTP Request with the format\n" +
                    "   'key:value'.");
        } else if(option.equalsIgnoreCase("post")) {
            System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\n" +
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
        } else if(option.equalsIgnoreCase("httpc")) {
            System.out.println("httpc is a curl-like application but supports HTTP protocol only.\n" +
                    "Usage:\n" +
                    "   httpc command [arguments]\n" +
                    "The commands are:\n" +
                    "   get executes a HTTP GET request and prints the response.\n" +
                    "   post executes a HTTP POST request and prints the response.\n" +
                    "   help prints this screen.\n" +
                    "Use \"httpc help [command]\" for more information about a command.");
        }
        else {
            System.out.println("Invalid Command");
        }
    }
}
