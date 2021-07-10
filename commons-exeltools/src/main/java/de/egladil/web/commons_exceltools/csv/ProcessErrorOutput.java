// =====================================================
// Project: commons-exeltools
// (c) Heike Winkelvoß
// =====================================================
package de.egladil.web.commons_exceltools.csv;

import org.apache.commons.exec.LogOutputStream;

/**
 * ProcessErrorOutput
 */
public class ProcessErrorOutput extends LogOutputStream {

	private StringBuffer stringBuffer;

	public ProcessErrorOutput(final int level) {

		super(level);
		this.stringBuffer = new StringBuffer();
	}

	@Override
	protected void processLine(final String line, final int logLevel) {

		stringBuffer.append(line);
		stringBuffer.append("\n");
	}

	public String getError() {

		return stringBuffer.toString();
	}
}
