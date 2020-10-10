
import java.io.*;
import java.util.Scanner;

public class httpc {
    public static void main(String[] args) throws IOException {

        String input;

        //read input until client press 'return' key
        while ((input = readCommand()).length() > 0) {
            Command cmd = new Command();
            input = input.replace("--", "-");

            handleInput(cmd, input);

            if (cmd.checkValidity()) {
                HTTPClient httpClient = new HTTPClient();
                String output = httpClient.getOutput(cmd);

                if (cmd.outToFile()) {
                    BufferedWriter br = new BufferedWriter(new FileWriter(cmd.getFileName()));
                    br.write(output);
                    br.close();
                } else {
                    System.out.println(output);
                }
            }

        }

        System.out.println("Exiting...");
    }

    private static void handleInput(Command cmd, String input) throws IOException {

        while (input.length() > 0 && cmd.isValid()) {

            int ind = getFirstWordIndx(input);
            String word;

            if (ind < input.length() && input.charAt(ind) == '\'') {
                ind++;
            }
            if (ind == input.length()) {
                word = input;
                input = "";
            } else {

                word = input.substring(0, ind);
                input = input.substring(ind + 1);
            }

            if (isOption(word)) {
                if (needArgument(word)) {
                    int argind = getFirstWordIndx(input);
                    String arg;

                    if (argind < input.length() && input.charAt(argind) == '\'') {
                        argind++;
                    }
                    if (argind == input.length()) {
                        arg = input;
                        input = "";
                    } else {

                        arg = input.substring(0, argind);
                        input = input.substring(argind + 1);
                    }

                    if (arg.isEmpty()) {
                        cmd.printHelp(word);
                        return;
                    }


                    handleOptionAndArg(cmd, word, arg);
                } else {
                    handleOption(cmd, word);
                }

            } else if (word.contains("http:")) {
                cmd.setUrl(word);
            } else {
                cmd.setInvalid();
                return;
            }
        }


    }

    private static void handleOption(Command cmd, String option) {
        if (option.equalsIgnoreCase("httpc")) {
            cmd.setHttpc(true);
        } else if (option.equalsIgnoreCase("help")) {
            cmd.setHelp(true);
        } else if (option.equalsIgnoreCase("get")) {
            cmd.setGet(true);
        } else if (option.equalsIgnoreCase("post")) {
            cmd.setPost(true);
        } else if (option.equalsIgnoreCase("-v")) {
            cmd.setV(true);
        }
    }

    private static void handleOptionAndArg(Command cmd, String option, String arg) {
        if (option.equalsIgnoreCase("-h")) {
            cmd.setH(true);
            cmd.addHArg(arg);
        } else if (option.equalsIgnoreCase("-d")) {
            cmd.setD(true);
            cmd.setdArg(arg);
        } else if (option.equalsIgnoreCase("-f")) {
            cmd.setF(true);
            try {
                BufferedReader br = new BufferedReader(new FileReader(arg));
                arg = "\'";
                String s = br.readLine();
                while (s != null) {
                    arg += s;
                    s = br.readLine();
                }
            } catch (Exception e) {
                System.out.println("Error reading file.");
                cmd.setInvalid();
            }

            cmd.setfArg(arg + "\'");
        } else if (option.equalsIgnoreCase("-o")) {
            cmd.setO(arg);
        }
    }

    private static boolean needArgument(String word) {
        if (word.equalsIgnoreCase("-h")
                || word.equalsIgnoreCase("-d")
                || word.equalsIgnoreCase("-f")
                || word.equalsIgnoreCase("-o")) {
            return true;
        }
        return false;
    }

    private static boolean isOption(String word) {
        if (word.equalsIgnoreCase("httpc")
                || word.equalsIgnoreCase("help")
                || word.equalsIgnoreCase("get")
                || word.equalsIgnoreCase("post")
                || word.equalsIgnoreCase("-v")
                || word.equalsIgnoreCase("-h")
                || word.equalsIgnoreCase("-d")
                || word.equalsIgnoreCase("-f")
                || word.equalsIgnoreCase("-o")) {
            return true;
        }
        return false;
    }

    private static int getFirstWordIndx(String input) {
        char delimiter = ' ';
        for (int i = 0; i < input.length(); i++) {

            if (delimiter == ' ' && input.charAt(i) == '\'') {
                delimiter = '\'';
                continue;
            }
            if (input.charAt(i) == delimiter) {
                return i;
            }

        }
        return input.length();
    }


    /**
     * This function returns the next line on system console as string.
     */
    private static String readCommand() {
        System.out.println("\nEnter your command below or press 'RETURN' key to exit.");
        return (new Scanner(System.in)).nextLine();
    }


}


