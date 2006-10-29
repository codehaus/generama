
package org.generama.jelly;

import java.io.InputStream;

import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.generama.Plugin;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MergeTag extends TagSupport {
	private static final Log log = LogFactory.getLog(MergeTag.class);

	/**
	 * The file to be imported. Mutually exclusive with uri. uri takes
	 * precedence.
	 */
	private String file;

	/**
	 * Whether the imported script has access to the caller's variables
	 */
	private boolean inherit;

	/**
	 * Create a new Import tag.
	 */
	public MergeTag() {
	}

	// Tag interface
	// -------------------------------------------------------------------------
	/**
	 * Perform tag processing
	 * 
	 * @param output
	 *            the destination for output
	 * @throws MissingAttributeException
	 *             if a required attribute is missing
	 * @throws JellyTagException
	 *             on any other errors
	 */
	public void doTag(XMLOutput output) throws MissingAttributeException,
			JellyTagException {

		try {
			Plugin plugin = (Plugin) context.getVariable("plugin");
			String mergefile = (plugin.getMergedir() != null?  plugin.getMergedir() : "<undefined merge dir>/") + file;
			output.writeComment("start merging from source: "
					+ mergefile);
			InputStream is = context.getResourceAsStream(plugin.getMergedir()
					+ file);
            //        System.err.println("************* input stream for merging: " + is);
			if (is != null) {
				log.info("Merging file " + file.toString());
				context.runScript(new InputSource(is), output, true,
						isInherit());
			} else {
				//output.writeComment(getBodyText());
				getBody().run(context,output);
			}
			output.writeComment("end merging from source: "
					+ mergefile);
		} catch (JellyException e) {
			throw new JellyTagException("could not import script", e);
		} catch (SAXException e) {
			throw new JellyTagException(
					"error while writing mergepoint comment", e);
		}
	}

	// Properties
	// -------------------------------------------------------------------------

	/**
	 * @return whether property inheritence is enabled
	 */
	public boolean isInherit() {
		return inherit;
	}

	/**
	 * Sets the file for the script to evaluate.
	 * 
	 * @param file
	 *            The file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * Sets whether property inheritence is enabled or disabled
	 */
	public void setInherit(boolean inherit) {
		this.inherit = inherit;
	}
}
