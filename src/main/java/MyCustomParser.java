import java.util.List;
import java.util.Stack;

public class MyCustomParser {
    private List<MyCustomOption> options;
    public MyCustomParser(String input){


    }
    public MyCustomOption addOption(List<String> names){
        MyCustomOption o=new MyCustomOption(names);
        options.add(o);
        return o;
    }
    public MyCustomOptionSet parse(String[] input){
        MyCustomOptionSet opts=new MyCustomOptionSet();
        MyCustomOption lastOption=null;
        for(String str:input){
            
            if(isValidOption(str)){
                MyCustomOption option=getOptionFromName(str);
                if(!opts.hasOption(option)) {
                    opts.addOption(option);
                    lastOption=option;
                    continue;
                }
            }
            else{
                if(lastOption.canTakeArguments()){
                    opts.addArgumentToOption(str,lastOption);
                }
            }
            
        }
        return opts;
    }

    private MyCustomOption getOptionFromName(String name) {
        for(MyCustomOption o:options){
            if(o.hasName(name))
                return o;
        }
        return null;
    }

    public boolean isValidOption(String name){
        for(MyCustomOption o:options){
            if(o.hasName(name))
                return true;
        }
        return false;
    }
    
}

class MyCustomOption{
    private List<String> names;
    private boolean hasArguments=true;
    private String defaultsTo;


    public MyCustomOption(List<String> names) {
        this.names=names;
    }
    public void hasArguments(boolean b){
        this.hasArguments=b;
    }
    public boolean canTakeArguments(){
        return hasArguments;
    }
    public void defaultsTo(String defaultVal){
        this.defaultsTo=defaultVal;
    }
    
    public boolean hasName(String name){
        return names.contains(name);
    }

    public String getNames() {
        String str="";
        for(String name:names){
            str+=" '"+name+"'";
        }
        return str;
    }

}


