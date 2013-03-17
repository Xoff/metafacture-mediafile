/*
 *  Copyright 2013 Christoph Böhme
 *
 *  Licensed under the Apache License, Version 2.0 the "License";
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.culturegraph.audiofile.example;

import org.culturegraph.audiofile.converter.TagReader;
import org.culturegraph.mf.stream.converter.FormetaEncoder;
import org.culturegraph.mf.stream.converter.FormetaEncoder.Style;
import org.culturegraph.mf.stream.sink.ObjectStdoutWriter;

/**
 * Prints the id3 tag of an mp3 file.
 * 
 * @author christoph
 *
 */
public final class PrintTag {

	private PrintTag() {
		// No instances allowed
	}
	
	public static void main(final String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: PrintTag FILE");
			System.exit(-1);
		}
		
		final TagReader tagReader = new TagReader();
		final FormetaEncoder encoder = new FormetaEncoder();
		encoder.setStyle(Style.MULTILINE);
		final ObjectStdoutWriter<String> writer = new ObjectStdoutWriter<>();
		
		tagReader
				.setReceiver(encoder)
				.setReceiver(writer);
		
		tagReader.process(args[0]);
		tagReader.closeStream();
	}

}
