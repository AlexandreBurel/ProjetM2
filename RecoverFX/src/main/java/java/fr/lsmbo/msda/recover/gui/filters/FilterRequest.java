/*
 * 
 */
package fr.lsmbo.msda.recover.gui.filters;


import fr.lsmbo.msda.recover.gui.io.ExportBatch;
import fr.lsmbo.msda.recover.gui.lists.IonReporters;
import fr.lsmbo.msda.recover.gui.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.gui.lists.Spectra;
import fr.lsmbo.msda.recover.gui.model.IonReporter;
import fr.lsmbo.msda.recover.gui.model.Spectrum;
import fr.lsmbo.msda.recover.gui.model.StatusFilterType;

/**
 * Compute and apply different filters to spectra. Recover filters used and scan
 * all the spectrum to apply these filters.
 * 
 * @author Aromdhani
 * 
 * @see IonReporterFilter
 * @see LowIntensityThresholdFilter
 * @see ColumnFilters
 *
 */
public class FilterRequest {

	private LowIntensityThresholdFilter filterLIT = null;
	private IdentifiedSpectraFilter filterIS = null;
	private IonReporterFilter filterIR = null;

	/**
	 * First of all, check if the filter identified spectra is used and apply
	 * this filter: scan all the title given and find the associated spectrum
	 * for this title. Then, scan the different spectrum and for all this
	 * spectrum,scan the different filter used (as an array of index, each index
	 * corresponding to a specific filter @see Filters). First filter
	 * encountered set the value of recover for this spectrum. If there are more
	 * than one filter, before applied the second filter check if the first
	 * filter return false for the spectrum (in this case, move to the next
	 * spectrum) If the value was true: apply this filter (true or false) and do
	 * it again for the third or more filter(check the value etc...)
	 * 
	 * If IonReporterFilter was used: special treatment (method
	 * recoverIfSeveralIons) because if a first ion reporter return a value of
	 * recover true we keep this value even if we have more ion reporter And
	 * finally, calculate number of spectrum recovered
	 * 
	 * @see IdentifiedSpectraFilter
	 * 
	 * 
	 * 
	 */
	public FilterRequest() {
		if (ColumnFilters.getApplied().containsKey("LIT"))
			this.filterLIT = (LowIntensityThresholdFilter) ColumnFilters.getApplied().get("LIT").get(0);
		if (ColumnFilters.getApplied().containsKey("IS"))
			this.filterIS = (IdentifiedSpectraFilter) ColumnFilters.getApplied().get("IS").get(0);
		if (ColumnFilters.getApplied().containsKey("IR"))
			this.filterIR = (IonReporterFilter) ColumnFilters.getApplied().get("IR").get(0);
	}

	/**
	 * Apply low intensity threshold filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public Boolean applyLIT() {
		Boolean isFinished = false;
		Spectra spectraToFilter = new Spectra();
		if (!ExportBatch.useBatchSpectra) {
			spectraToFilter = ListOfSpectra.getFirstSpectra();
		} else {
			spectraToFilter = ListOfSpectra.getBatchSpectra();
		}
		Integer numberOfSpectrum = spectraToFilter.getSpectraAsObservable().size();
		// Scan all the spectrum
		if (ColumnFilters.getApplied().containsKey("LIT")) {
			for (int i = 0; i < numberOfSpectrum; i++) {
				Spectrum spectrum = spectraToFilter.getSpectraAsObservable().get(i);
				spectrum.setIsRecovered(filterLIT.isValid(spectrum));
			}
			isFinished = true;
		}
		return isFinished;
	}

	/**
	 * Apply is identified filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public Boolean applyIS() {
		Boolean isFinished = false;
		Spectra spectraToFilter = new Spectra();
		if (!ExportBatch.useBatchSpectra) {
			spectraToFilter = ListOfSpectra.getFirstSpectra();
		} else {
			spectraToFilter = ListOfSpectra.getBatchSpectra();
		}
		Integer numberOfSpectrum = spectraToFilter.getSpectraAsObservable().size();
		// Scan all the spectrum
		if (ColumnFilters.getApplied().containsKey("IS")) {
			for (int i = 0; i < numberOfSpectrum; i++) {
				Spectrum spectrum = spectraToFilter.getSpectraAsObservable().get(i);
				spectrum.setIsRecovered(filterIS.isValid(spectrum));
			}
			isFinished = true;
		}
		return isFinished;
	}

	/**
	 * Apply ion reporter filter for all spectrum.
	 * 
	 * @return <code>true</code> if all spectrum have been checked.
	 */
	public Boolean applyIR() {
		Boolean isFinished = false;
		Spectra spectraToFilter = new Spectra();
		if (!ExportBatch.useBatchSpectra) {
			spectraToFilter = ListOfSpectra.getFirstSpectra();
		} else {
			spectraToFilter = ListOfSpectra.getBatchSpectra();
		}
		Integer numberOfSpectrum = spectraToFilter.getSpectraAsObservable().size();
		// Scan all the spectrum
		if (ColumnFilters.getApplied().containsKey("IR")) {
			for (int i = 0; i < numberOfSpectrum; i++) {
				Spectrum spectrum = spectraToFilter.getSpectraAsObservable().get(i);
				Integer nbIon = IonReporters.getIonReporters().size();
				for (int k = 0; k < nbIon; k++) {
					IonReporter ionReporter = IonReporters.getIonReporters().get(k);
					// Initialize parameter for an ion(i)
					filterIR.setParameters(ionReporter.getName(), ionReporter.getMoz(), ionReporter.getTolerance());

					if (k >= 1)
						spectrum.setIonReporter(recoverIfSeveralIons(spectrum, filterIR));
					else
						spectrum.setIonReporter(filterIR.isValid(spectrum));
				}
			}
			isFinished = true;
		}
		return isFinished;

	}

	/**
	 * Determines whether the spectrum is recovered 
	 * 
	 * @param spectrum
	 *            the spectrum to check
	 * @return <code> true</code> if the spectrum is recovered
	 */
	public Boolean isRecover(Spectrum spectrum) {
		return spectrum.getIsRecovered();
	}

	/**
	 * 
	 * @param spectrum
	 *            a specific spectrum
	 * @param filter
	 * @return if the value of recover for a spectrum is true, return true else,
	 *         check if an ion reporter is present for this spectrum and return
	 *         true or false in the different case.
	 * 
	 */
	public Boolean recoverIfSeveralIons(Spectrum spectrum, BasicFilter filter) {
		if (spectrum.getIsRecovered())
			return true;
		else
			return filter.isValid(spectrum);

	}

	/** Restore default values: reset the value of recover and UPN */
	public static void restoreDefaultValues() {
		Spectra spectra = ListOfSpectra.getFirstSpectra();
		for (Spectrum sp : spectra.getSpectraAsObservable()) {
			sp.setIsRecovered(false);
			sp.setUpn(-1);
			sp.setIsRecoverHIT(StatusFilterType.NOT_USED);
			sp.setIsRecoverLIT(StatusFilterType.NOT_USED);
			sp.setIsRecoverFI(StatusFilterType.NOT_USED);
			sp.setIsRecoverWC(StatusFilterType.NOT_USED);
			sp.setIsRecoverIS(StatusFilterType.NOT_USED);
			sp.setIsRecoverIR(StatusFilterType.NOT_USED);
		}
		
	}

}
