import java.util.HashMap;

public class Command {
    private boolean httpc;
    private boolean help;
    private boolean get;
    private boolean post;
    private boolean v;
    private boolean h;
    private boolean d;
    private boolean f;

    private HashMap<String, String> hArg;
    private String dArg;
    private String fArg;
    private String url;

    private boolean valid;

    public void setHttpc(boolean httpc) {
        this.httpc = httpc;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public void setGet(boolean get) {
        this.get = get;
    }


    public void setPost(boolean post) {
        this.post = post;
    }


    public void setV(boolean v) {
        this.v = v;
    }


    public void setH(boolean h) {
        this.h = h;
        if (hArg == null) {
            hArg = new HashMap<>();
        }

    }


    public void setD(boolean d) {
        if(get|f|!post){
            setInvalid();
        }
        this.d = d;
    }

    private void setInvalid() {
        valid=false;
        System.out.println("Invalid Command.");
        printHelp();
    }


    public void setF(boolean f) {
        if(get|d|!post){
            setInvalid();
        }
        this.f = f;
    }


    public void setUrl(String url) {
        if((!get || post) && (get || !post)){
            setInvalid();
        }
        this.url = url.replace("'", "");
    }

    public void setdArg(String dArg) {
        this.dArg = dArg;
    }


    public void setfArg(String fArg) {
        this.fArg = fArg;
    }


    public Command() {
        httpc = false;
        help = false;
        get = false;
        post = false;
        v = false;
        h = false;
        d = false;
        f = false;
        hArg = null;
        dArg = null;
        fArg = null;
        url = null;

        valid=true;
    }


    public void addHArg(String arg) {
        String[] a=arg.split(":");
        for(int i=0;i<a.length;i=i+2){
            hArg.put(a[i],a[i+1]);
        }
    }

    public String toString() {
        String s = "";
        s += "httpc: " + httpc + ", ";
        s += "help: " + help + ", ";
        s += "get: " + get + ", ";
        s += "post: " + post + ", ";
        s += "v: " + v + ", ";
        s += "h: " + h + ", ";
        s += "d: " + f + ", ";
        s += "dArg: " + dArg + ", ";
        s += "fArg: " + fArg + ", ";
        s += "url: " + url + ", ";
        return s;
    }

    public boolean checkValidity() {
        if(!valid)
            return false;
        String option = "";
        if (!httpc) {
            setInvalid();
        } else if (help) {
            if (get) {
                option = "get";
            } else if (post) {
                option = "post";
            } else {
                option = "httpc";
            }
            printHelp(option);
        } else if ((!get || post) && (get || !post)) {
            setInvalid();
        } else if (url == null) {
            setInvalid();
        }

        return option.isEmpty();
    }


    void printHelp(String option) {
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
            printHelp();
        } else {
            System.out.println("Invalid Command.");
            printHelp();
        }
    }
    public void printHelp(){
        System.out.println("\nhttpc is a curl-like application but supports HTTP protocol only.\n" +
                "Usage:\n" +
                "   httpc command [arguments]\n" +
                "The commands are:\n" +
                "   get executes a HTTP GET request and prints the response.\n" +
                "   post executes a HTTP POST request and prints the response.\n" +
                "   help prints this screen.\n" +
                "Use \"httpc help [command]\" for more information about a command.");
    }

    public boolean isValid() {
        return valid;
    }
}
