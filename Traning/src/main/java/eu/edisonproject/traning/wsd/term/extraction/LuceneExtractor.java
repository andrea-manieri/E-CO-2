package eu.edisonproject.traning.wsd.term.extraction;

import eu.edisonproject.utility.file.ConfigHelper;
import eu.edisonproject.utility.text.processing.Cleaner;
import eu.edisonproject.utility.text.processing.NGramGenerator;
import eu.edisonproject.utility.text.processing.StanfordLemmatizer;
import eu.edisonproject.utility.text.processing.StopWord;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.util.CharArraySet;

/**
 *
 * @author S. Koulouzis
 */
public class LuceneExtractor implements TermExtractor {

    private int maxNgrams;
    private String itemsFilePath;
    private String stopWordsPath;
    Cleaner ng;
    private Cleaner tokenizer;
    private Cleaner lematizer;

    @Override
    public void configure(Properties prop) {
        String strMaxNgrams = System.getProperty("max.ngrams");
        if (strMaxNgrams == null) {
            maxNgrams = Integer.valueOf(prop.getProperty("max.ngrams", "4"));
        } else {
            maxNgrams = Integer.valueOf(strMaxNgrams);
        }

        stopWordsPath = System.getProperty("stop.words.file");

        if (stopWordsPath == null) {
            stopWordsPath = prop.getProperty("stop.words.file", ".." + File.separator + "etc" + File.separator + "stopwords.csv");
        }

        itemsFilePath = System.getProperty("itemset.file");
        if (itemsFilePath == null) {
            itemsFilePath = prop.getProperty("itemset.file", ".." + File.separator + "etc" + File.separator + "itemset.csv");
        }
    }

    @Override
    public Map<String, Double> termXtraction(String inDir) throws IOException, FileNotFoundException, MalformedURLException {
        File dir = new File(inDir);
        Map<String, Double> termDictionaray = new HashMap();
        String[] terms = null;
        int count = 0;

        CharArraySet stopwordsCharArray = new CharArraySet(ConfigHelper.loadStopWords(stopWordsPath), true);
        tokenizer = new StopWord(stopwordsCharArray);
        lematizer = new StanfordLemmatizer();
        ng = new NGramGenerator(stopwordsCharArray, maxNgrams);

        try {
            if (dir.isDirectory()) {

                for (File f : dir.listFiles()) {
                    count++;
                    Logger.getLogger(LuceneExtractor.class.getName()).log(Level.INFO, "{0}: {1} of {2}", new Object[]{f.getName(), count, dir.list().length});
                    if (FilenameUtils.getExtension(f.getName()).endsWith("txt")) {
                        terms = extractFromFile(f);
                    }
                }
            } else if (dir.isFile()) {
                terms = extractFromFile(dir);
            }

            try (BufferedReader br = new BufferedReader(new FileReader(new File(itemsFilePath)))) {
                for (String text; (text = br.readLine()) != null;) {
                    String aprioryTerm = text.split("/")[0];
                    for (String term : terms) {
                        String t = term.replaceAll("_", " ");
                        if (aprioryTerm.equals(t)) {
                            Double tf;
                            if (termDictionaray.containsKey(t)) {
                                tf = termDictionaray.get(t);
                                tf++;
                            } else {
                                tf = 1.0;
                            }
                            termDictionaray.put(t, tf);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(LuceneExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return termDictionaray;
    }

    private String[] extractFromFile(File f) throws IOException, MalformedURLException, Exception {
        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            for (String text; (text = br.readLine()) != null;) {
                fileContents.append(text.toLowerCase()).append(" ");
            }
        }
        fileContents.deleteCharAt(fileContents.length() - 1);
        fileContents.setLength(fileContents.length());

        tokenizer.setDescription(fileContents.toString());
        String cleanText = tokenizer.execute();
        lematizer.setDescription(cleanText);
//        System.err.println(cleanText);
        String lematizedText = lematizer.execute();
        ng.setDescription(lematizedText);
        String ngText = ng.execute();
        ngText += lematizedText;
        return ngText.split(" ");

        //Read asosiation rules file and check if we have the same terms 
//        try (BufferedReader br = new BufferedReader(new FileReader(new File(itemsFilePath)))) {
//            for (String text; (text = br.readLine()) != null;) {
//
//                for (String t : tokens) {
//
//                    Double tf;
//                    if (termDictionaray.containsKey(t)) {
//                        tf = termDictionaray.get(t);
//                        tf++;
//                    } else {
//                        tf = 1.0;
//                    }
//                    termDictionaray.put(t, tf);
//                }
//
//                for (String t : ngrams) {
//                    Double tf;
//                    if (termDictionaray.containsKey(t)) {
//                        tf = termDictionaray.get(t);
//                        tf++;
//                    } else {
//                        tf = 1.0;
//                    }
//                    termDictionaray.put(t, tf);
//                }
//            }
//        }
//        return termDictionaray;
    }

}