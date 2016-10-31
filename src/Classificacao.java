import java.io.File;
import java.util.Collection;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;

public class Classificacao  {
	
//	public static void main(String[] args) {
//
//	   // String parserModel = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
//	   // classifica(parserModel, "teste.txt");
//		
//		NLP.init();
//		String tweet = "I feel very good today";
//		System.out.println(tweet + " " +  NLP.findSentiment(tweet));
//	}
	
	public static void classifica(String parserModel, String filename){
	    LexicalizedParser lp = LexicalizedParser.loadModel(parserModel);
	    TreebankLanguagePack tlp = lp.treebankLanguagePack();
	    GrammaticalStructureFactory gsf = null;
	    
	    gsf = tlp.grammaticalStructureFactory();
	    
	    for (List<HasWord> sentence : new DocumentPreprocessor(filename)) {
	      Tree parse = lp.apply(sentence);
	      parse.pennPrint();
	      System.out.println();

	      if (gsf != null) {
	        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
	        Collection tdl = gs.typedDependenciesCCprocessed();
	        System.out.println(tdl);
	        System.out.println();
	      }
	    }
	}
}
