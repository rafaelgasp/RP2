import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Preprocessamento{

	public static void main (String args[]){
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Digite algo que o fara se arrepender depois:");
		while(sc.hasNext()){
			String line = sc.nextLine();
			extrairInfo(line);
		}

	}

	public static void extrairInfo(String line){

		String usertag = "((\\s)?(@[\\w]+))";
		String hashtag = "((\\s)?(#[\\w]+))";
		String url = "((\\s)?((http(s)?)|(ftp)://)?([\\d\\w-/]+)(\\.)([a-zA-Z]{2,6})([^\\s]*)(/)?)";
		
		// emotion icons
		String eyes = "([:;=X8])"; 
		String nose = "([-oO^>’\"*]{0,1})";

		String regexPos = "[\\s]?"+eyes+nose+"([)}DpP3]|])"+"\\s?"; 
		String regexPosInv = "[\\s]?"+"([({Cc])"+nose+eyes+"\\s?";

		String regexNeg = "[\\s]?"+eyes+nose+"([({\\/CcSst])"+"\\s?"; 
		String regexNegInv = "[\\s]?"+"([)}\\/DSs]|])"+nose+eyes+"\\s?";

		LinkedList<String> l = new LinkedList<String>();
		l.add(usertag);
		l.add(hashtag);
		l.add(url);
		l.add(regexPos); l.add(regexPosInv);
		l.add(regexNeg); l.add(regexNegInv);

		for(int i = 0 ; i < l.size(); i++){

			// Create a Pattern object
			Pattern r = Pattern.compile(l.get(i));

			// Now create matcher object.
			Matcher m = r.matcher(line);
		
			if(m.find()){
				
				//substituir por leitura de arquivo
				if(i==0) System.out.println(line.replace(line,"USERTAG"));
				else if(i==1) System.out.println(line.replace(line,"HASHTAG"));
				else if(i==2) System.out.println(line.replace(line,"URL"));
				else System.out.println(line.replace(line,"EMOTIONICON"));

				break;
			}
			
		}
		//System.out.println(line);
	}
}

