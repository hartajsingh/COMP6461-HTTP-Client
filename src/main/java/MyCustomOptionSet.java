import com.sun.deploy.util.JVMParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyCustomOptionSet {
    private HashMap<MyCustomOption, List<String>> options;

    public MyCustomOptionSet(){
        this.options=new HashMap<>();
    }
    public void addOption(MyCustomOption option){
        if(options.containsKey(option)){
            System.out.println("Option Set already has this Option: "+option.getNames());
            return;
        }
        options.put(option,new ArrayList<String>());
    }
    public boolean hasOption(MyCustomOption option){
        return options.containsKey(option);
    }

    public void addArgumentToOption(String arg,MyCustomOption option){
        if(!options.containsKey(option)){
            System.out.println("Option Set do not have this Option: "+option.getNames());
            return;
        }
        options.get(option).add(arg);
    }
}
