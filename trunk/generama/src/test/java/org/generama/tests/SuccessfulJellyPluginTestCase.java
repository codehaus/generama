package org.generama.tests;

import org.generama.MetadataProvider;
import org.generama.OneTwoThreeStringMetadataProvider;
import org.generama.Plugin;
import org.generama.WriterMapper;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author Aslak Helles&oslash;y
 * @version $Revision$
 */
public class SuccessfulJellyPluginTestCase extends
		AbstractXMLGeneratingPluginTestCase {
	protected Plugin createPlugin(MetadataProvider metadataProvider,
			WriterMapper writerMapper) throws Exception {
		Plugin plugin = new TestJellyPlugin(metadataProvider, writerMapper);
		plugin.setMergedir("org/generama/tests/");

		return plugin;
	}

	protected URL getExpected() throws MalformedURLException {
		return new URL(
				"memory",
				null,
				""
						+ "<test>"
						+ "  <number name=\"One\"/>"
						+ "  <number name=\"Two\"/>"
						+ "  <number name=\"Three\"/>"
						+ "<!--start merging from source:org/generama/tests/include.jelly-->"
						+ "<yo>This is from a merge file outside the plugin</yo>"
						+ "<!--end merging from source:org/generama/tests/include.jelly-->" 
                      + "<!--start merging from source:org/generama/tests/nonesuchfile.jelly-->"
                      + "<glum>sustitute for merged stuff</glum>" 
                      + "<!--end merging from source:org/generama/tests/nonesuchfile.jelly-->" 
						+ "</test>");
	}

	protected MetadataProvider createMetadataProvider() throws Exception {
		return new OneTwoThreeStringMetadataProvider();
	}
}
