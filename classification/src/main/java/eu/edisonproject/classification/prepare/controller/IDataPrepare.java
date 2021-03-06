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

package eu.edisonproject.classification.prepare.controller;

import eu.edisonproject.classification.prepare.model.DocumentObject;
/*
 * @author Michele Sparamonti
 */
public interface IDataPrepare {

	public void execute();
	
	public void extract(DocumentObject jp, String path);
	//public void clean(DocumentObject jp);
        public void clean(String description);
	
	
}
