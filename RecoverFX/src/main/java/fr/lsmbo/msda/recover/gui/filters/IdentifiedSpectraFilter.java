package fr.lsmbo.msda.recover.gui.filters;

import fr.lsmbo.msda.recover.gui.model.Spectrum;

/**
 * Filter to identify a spectrum with the title and to set the value of recover
 * in different case(keep or rejected spectrum identified or keep or rejected
 * spectrum non identified).
 * 
 * @author BL
 * @author Aromdhani
 */

public class IdentifiedSpectraFilter implements BasicFilter {

	private Boolean recoverSpectrumIdentified = false;
	private Boolean recoverSpectrumNonIdentified = false;

	public void setParameters(Boolean _recoverSpectrumIdentified, Boolean _recoverSpectrumNonIdentified) {
		recoverSpectrumIdentified = _recoverSpectrumIdentified;
		recoverSpectrumNonIdentified = _recoverSpectrumNonIdentified;
	}

	/**
	 * Determines whether the spectrum is valid.
	 * 
	 * @param spectrum
	 *            the spectrum to check
	 */
	public Boolean isValid(Spectrum spectrum) {
		if (spectrum.getIsIdentified() == true) {
			if (recoverSpectrumIdentified)
				return true;
		}
		if (spectrum.getIsIdentified() == false) {
			if (recoverSpectrumNonIdentified)
				return true;
		}
		return false;
	}

	/***
	 * Return the description of the filter
	 * 
	 * @return the full description of the filter .
	 */
	@Override
	public String getFullDescription() {
		StringBuilder filterStr = new StringBuilder();
		filterStr.append("###Parameters used for Identidfied Spectra Filter:").append("\n")
				.append("###recover spectrum identified: ").append(getRecoverSpectrumIdentified()).append(" ; ")
				.append("recover spectrum non identified: ").append(getRecoverSpectrumNonIdentified()).append("\n");
		return filterStr.toString();
	}

	/**
	 * Determeines whether the spectrum is identified
	 * 
	 * @return <code>true</code> if identified
	 */
	public Boolean getRecoverSpectrumIdentified() {
		return recoverSpectrumIdentified;
	}

	/**
	 * Determines whether the spectrum is non identified
	 * 
	 * @return <code>true</code> if non identified
	 */
	public Boolean getRecoverSpectrumNonIdentified() {
		return recoverSpectrumNonIdentified;
	}

	@Override
	public String getType() {
		return this.getClass().getName();
	}
}