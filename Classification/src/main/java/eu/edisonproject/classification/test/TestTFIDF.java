/*
 * Copyright 2016 Michele Sparamonti & Spiros Koulouzis.
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
package eu.edisonproject.classification.test;

import eu.edisonproject.classification.tfidf.mapreduce.TFIDFDriver;
import java.io.File;

/**
 *
 * @author Michele Sparamonti (michele.sparamonti@eng.it)
 */
public class TestTFIDF {

    public static void execute(String[] args) {
        String inputPath = args[0] + File.separator + "etc" + File.separator + 
                "Classification" + File.separator + "Avro Document" + File.separator;
        TFIDFDriver tfidfDriver = new TFIDFDriver("post",args[0]);
        tfidfDriver.executeTFIDF(inputPath);
        //tfidfDriver.readDistancesOutputAndPrintCSV();
    }

}
