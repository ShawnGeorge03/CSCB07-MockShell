package driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class Man extends Command{
	Hashtable<String, String> my_dict = new Hashtable<String, String>();
	
	ArrayList<String> args;
	
	public Man(String[] args) {
		this.args = new ArrayList<String>(Arrays.asList(args)); 
	}
	
	public void setDictionary() {
		my_dict.put("mkdir", "");
		my_dict.put("cat", "");
		my_dict.put("cd", "");
		my_dict.put("echo", "");
		my_dict.put("history", "");
		my_dict.put("ls", "");
		my_dict.put("pushd", "");
		my_dict.put("popd", "");
		my_dict.put("pwd", "");
		my_dict.put("speak", "");
	}
	
	public void printDocumentation(){
		setDictionary();
		
		if (args.size() == 0) {
			//Error Class
			return;
		}
		
		for (int i = 0; i < args.size(); i++) {
			if (my_dict.containsKey(args.get(i))) {
				System.out.println(args.get(i));
				System.out.println(my_dict.get(args.get(i)));
			} else {
				System.out.println(args.get(i));
				System.out.println("That command does not exist in this shell!");
			}
		}
	}
	
	
}
