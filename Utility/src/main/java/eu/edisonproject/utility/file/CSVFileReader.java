/*
 * Copyright 2016 S. Koulouzis.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.edisonproject.utility.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author S. Koulouzis
 */
public class CSVFileReader {

    private static Map<String, Set<String>> nGramsMap;

    public static Map<String, String> csvFileToMap(String csvFilePath, String delimeter) throws IOException {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(csvFilePath)))) {
            for (String text; (text = br.readLine()) != null;) {
                String[] parts = text.split(delimeter);
                map.put(parts[0], parts[1]);
            }
        }
        return map;
    }

    public static Map<CharSequence, Map<String, Double>> tfidfResult2Map(String path) throws IOException {
        Map<CharSequence, Map<String, Double>> featureVectors = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
            for (String text; (text = br.readLine()) != null;) {
                String[] parts = text.split("\t");
                String id = parts[0];
                String[] vector = parts[1].split("/");
                Map<String, Double> featureVector = new TreeMap<>();
                for (int i = 0; i < vector.length / 2; i++) {
                    String[] termValuePair = vector[i].split(":");
                    featureVector.put(termValuePair[0], Double.valueOf(termValuePair[1]));
                }
                featureVectors.put(id, featureVector);
            }
        }
        return featureVectors;
    }

    public static Set<String> getNGramsForTerm(String term, String itemsFilePath, String delimeter, String wordSeperator) throws IOException {
        if (nGramsMap == null) {
            nGramsMap = new HashMap<>();
        }
        Set<String> nGrams = nGramsMap.get(term);
        if (nGrams != null) {
            return nGrams;
        }
        nGrams = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(itemsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String keyword;
                if (line.contains(delimeter)) {
                    keyword = line.split(delimeter)[0];
                } else {
                    keyword = line;
                }
                boolean found = false;
                if (keyword.contains(wordSeperator) && keyword.contains(term) && !keyword.equals(term)) {

                    String[] parts = keyword.split(wordSeperator);
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for (String p : parts) {
                        if (p.equals(term)) {
                            found = true;
                            break;
                        }
                        if (i > 0 && i < parts.length - 1) {
                            sb.append("_");
                        }
                        sb.append(p);

                        if (sb.toString().equals(term)) {
                            found = true;
                            break;
                        }
                        i++;
                    }
                    if (found) {
                        parts = keyword.replaceAll(term, "").split(wordSeperator);
                        for (String p : parts) {
                            if (p.length() > 0) {
                                nGrams.add(p);
                            }
                        }
                    }
                }
            }
        }
        nGramsMap.put(term, nGrams);
        return nGrams;
    }
}
