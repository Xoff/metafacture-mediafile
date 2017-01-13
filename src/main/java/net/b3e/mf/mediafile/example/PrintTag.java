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
package net.b3e.mf.mediafile.example;

import org.culturegraph.mf.formeta.FormetaEncoder;
import org.culturegraph.mf.formeta.formatter.FormatterStyle;
import org.culturegraph.mf.io.ObjectStdoutWriter;

import net.b3e.mf.mediafile.converter.AudioTagReader;

/**
 * Prints the id3 tag of an mp3 file.
 *
 * @author Christoph Böhme
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

		final AudioTagReader tagReader = new AudioTagReader();
		final FormetaEncoder encoder = new FormetaEncoder();
		encoder.setStyle(FormatterStyle.MULTILINE);
		final ObjectStdoutWriter<String> writer = new ObjectStdoutWriter<>();

		tagReader
				.setReceiver(encoder)
				.setReceiver(writer);

		tagReader.process(args[0]);
		tagReader.closeStream();
	}

}
