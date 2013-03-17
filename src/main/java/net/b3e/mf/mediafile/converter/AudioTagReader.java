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
package net.b3e.mf.mediafile.converter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.culturegraph.mf.exceptions.MetafactureException;
import org.culturegraph.mf.framework.DefaultObjectPipe;
import org.culturegraph.mf.framework.StreamReceiver;
import org.culturegraph.mf.framework.annotations.Description;
import org.culturegraph.mf.framework.annotations.In;
import org.culturegraph.mf.framework.annotations.Out;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.TagTextField;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Reads metadata tags in audiofiles
 * 
 * @author Christoph Böhme
 *
 */
@Description("Reads metadata tags in audiofiles")
@In(String.class)
@Out(StreamReceiver.class)
public final class AudioTagReader extends DefaultObjectPipe<String, StreamReceiver> {

	{
		// JAudiotagger uses java util logging but Metafacture aims 
		// to do all logging via slf4j a bridge. Hence, log messages
		// from JAudiotagger need to be intercepted and redirected:
		if (!SLF4JBridgeHandler.isInstalled()) {
			SLF4JBridgeHandler.removeHandlersForRootLogger();
			SLF4JBridgeHandler.install();
		}
	}
	
	public void process(final String fileName) {
		final AudioFile file;
		try {
			file = AudioFileIO.read(new File(fileName));
		} catch (CannotReadException|IOException|TagException
				|ReadOnlyFileException|InvalidAudioFrameException e) {
			throw new MetafactureException(e);
		}
		
		getReceiver().startRecord("");
		final Tag tag = file.getTag();
		if (tag != null) {
			final Iterator<TagField> it = tag.getFields(); 
			while(it.hasNext()) {
				final TagField tf = it.next();
				if (tf instanceof TagTextField) {
					final TagTextField ttf = (TagTextField) tf;
					getReceiver().literal(ttf.getId(), ttf.getContent());
				}
			}
		}
		getReceiver().endRecord();
	}

}
