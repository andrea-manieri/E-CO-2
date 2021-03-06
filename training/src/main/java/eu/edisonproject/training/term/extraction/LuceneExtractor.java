package eu.edisonproject.training.term.extraction;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import eu.edisonproject.utility.file.ConfigHelper;
import eu.edisonproject.utility.text.processing.Cleaner;
import eu.edisonproject.utility.text.processing.NGramGenerator;
import eu.edisonproject.utility.text.processing.Stemming;
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
//    private Cleaner lematizer;
    private Stemming stemer;
    private String taggerPath;

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
            itemsFilePath = prop.getProperty("itemset.file", ".." + File.separator + "etc" + File.separator + "allTerms.csv");
        }

        taggerPath = System.getProperty("model.path");

        if (taggerPath == null) {
            taggerPath = prop.getProperty("model.path", ".." + File.separator + "etc" + File.separator + "model");
        }

        taggerPath+=  File.separator + "stanford" + File.separator + "english-left3words-distsim.tagger";
    }

    @Override
    public Map<String, Double> termXtraction(String inDir) throws IOException, FileNotFoundException, MalformedURLException {
        File dir = new File(inDir);

        try {
            int count = 0;
            CharArraySet stopwordsCharArray = new CharArraySet(ConfigHelper.loadStopWords(stopWordsPath), true);
            tokenizer = new StopWord(stopwordsCharArray);
//            lematizer = new StanfordLemmatizer();
            stemer = new Stemming();

            ng = new NGramGenerator(stopwordsCharArray, maxNgrams);

//            Map<String, String> itemsMap = CSVFileReader.csvFileToMap(itemsFilePath, ",");
            if (dir.isDirectory()) {
                for (File f : dir.listFiles()) {
                    count++;
                    Logger.getLogger(LuceneExtractor.class.getName()).log(Level.INFO, "{0}: {1} of {2}", new Object[]{f.getName(), count, dir.list().length});
                    if (FilenameUtils.getExtension(f.getName()).endsWith("txt")) {
                        return extractFromFile(f);
                    }
                }
            } else if (dir.isFile()) {
                if (FilenameUtils.getExtension(dir.getName()).endsWith("txt")) {
                    return extractFromFile(dir);
                }

            }

            //Indicate null to garbage collector
            System.gc();
        } catch (Exception ex) {
            Logger.getLogger(LuceneExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Map<String, Double> extractFromFile(File f) throws IOException, MalformedURLException, Exception {

        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            for (String text; (text = br.readLine()) != null;) {
                fileContents.append(text.toLowerCase()).append(" ");
            }
        }
        fileContents.deleteCharAt(fileContents.length() - 1);
        fileContents.setLength(fileContents.length());

        String contents = fileContents.toString().trim().replaceAll("_", " ");
        contents = contents.replaceAll("\\s{2,}", " ");

        tokenizer.setDescription(contents.trim());
        String cleanText = tokenizer.execute();
//        lematizer.setDescription(cleanText.trim());
//        System.err.println(cleanText);
        String lematizedText = cleanText;// lematizer.execute();
        ng.setDescription(lematizedText.trim());
        String ngText = ng.execute();
        ngText += lematizedText;
        Map<String, Double> termDictionaray = new HashMap();
        ngText = ngText.trim();
        MaxentTagger tagger = new MaxentTagger(taggerPath);
        for (String term : ngText.split(" ")) {
//            stemer.setDescription(term);
//            String stemTerm = stemer.execute();
//            if (itemsMap.containsKey(term)) {
//            }

            term = term.toLowerCase().trim().replaceAll(" ", "_");
            while (term.endsWith("_")) {
                term = term.substring(0, term.lastIndexOf("_"));
            }
            while (term.startsWith("_")) {
                term = term.substring(term.indexOf("_") + 1, term.length());
            }

            Double tf;
            String tagged = null;
            tagged = tagger.tagString(term);
            boolean add = true;
            if (tagged != null) {
                if (!tagged.contains("NN") || tagged.contains("RB")) {
                    add = false;
                }
            } else {
                add = true;
            }
            if (add) {
                if (termDictionaray.containsKey(term)) {
                    tf = termDictionaray.get(term);
                    tf++;
                } else {
                    tf = 1.0;
                }
                termDictionaray.put(term, tf);
            }

            if (termDictionaray.containsKey(term)) {
                tf = termDictionaray.get(term);
                tf++;
            } else {
                tf = 1.0;
            }
            termDictionaray.put(term, tf);
//            }
        }

        return termDictionaray;

    }

    @Override
    public Map<String, Double> rank(String inDir) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
