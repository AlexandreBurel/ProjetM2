package fr.lsmbo.msda.recover.view.component;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.control.MenuBar;
import javafx.event.ActionEvent;
import fr.lsmbo.msda.recover.util.FileUtils;
import fr.lsmbo.msda.recover.util.IconResource;
import fr.lsmbo.msda.recover.util.IconResource.ICON;
import fr.lsmbo.msda.recover.view.popup.About;
import fr.lsmbo.msda.recover.view.popup.ApplyFilter;
import fr.lsmbo.msda.recover.view.popup.ParsingRules;
import fr.lsmbo.msda.recover.view.popup.Exit;
import fr.lsmbo.msda.recover.Session;
import fr.lsmbo.msda.recover.gui.Recover;
import fr.lsmbo.msda.recover.io.PeaklistReader;
import fr.lsmbo.msda.recover.lists.ListOfSpectra;
import fr.lsmbo.msda.recover.task.TaskExecutor;

import java.io.File;
import java.util.concurrent.Future;

/**
 * 
 * @author aromdhani
 *
 */
public class MenuBarItems extends MenuBar {
	// menu Items
	private MenuItem openFile = null;

	public MenuItem getOpenFile() {
		return openFile;
	}

	public void setOpenFile(MenuItem openFile) {
		this.openFile = openFile;
	}

	public MenuItem getExportFile() {
		return exportFile;
	}

	public void setExportFile(MenuItem exportFile) {
		this.exportFile = exportFile;
	}

	public MenuItem getExportInBatchFile() {
		return exportInBatchFile;
	}

	public void setExportInBatchFile(MenuItem exportInBatchFile) {
		this.exportInBatchFile = exportInBatchFile;
	}

	public MenuItem getExitFile() {
		return exitFile;
	}

	public void setExitFile(MenuItem exitFile) {
		this.exitFile = exitFile;
	}

	public MenuItem getApplyQFilterAction() {
		return applyQFilterAction;
	}

	public void setApplyQFilterAction(MenuItem applyQFilterAction) {
		this.applyQFilterAction = applyQFilterAction;
	}

	public MenuItem getEditPRulesAction() {
		return editPRulesAction;
	}

	public void setEditPRulesAction(MenuItem editPRulesAction) {
		this.editPRulesAction = editPRulesAction;
	}

	public MenuItem getGetIdentifiedSpecAction() {
		return getIdentifiedSpecAction;
	}

	public void setGetIdentifiedSpecAction(MenuItem getIdentifiedSpecAction) {
		this.getIdentifiedSpecAction = getIdentifiedSpecAction;
	}

	public MenuItem getGetIdentifiedAxisAction() {
		return getIdentifiedAxisAction;
	}

	public void setGetIdentifiedAxisAction(MenuItem getIdentifiedAxisAction) {
		this.getIdentifiedAxisAction = getIdentifiedAxisAction;
	}

	public MenuItem getResetRecoverAction() {
		return resetRecoverAction;
	}

	public void setResetRecoverAction(MenuItem resetRecoverAction) {
		this.resetRecoverAction = resetRecoverAction;
	}

	public MenuItem getFlaggedSpecAction() {
		return flaggedSpecAction;
	}

	public void setFlaggedSpecAction(MenuItem flaggedSpecAction) {
		this.flaggedSpecAction = flaggedSpecAction;
	}

	public MenuItem getGetStartedHelp() {
		return getStartedHelp;

	}

	public void setGetStartedHelp(MenuItem getStartedHelp) {
		this.getStartedHelp = getStartedHelp;
	}

	public MenuItem getAboutHelp() {
		return aboutHelp;
	}

	public void setAboutHelp(MenuItem aboutHelp) {
		this.aboutHelp = aboutHelp;
	}

	private MenuItem exportFile = null;
	private MenuItem exportInBatchFile = null;
	private MenuItem exitFile = null;
	private MenuItem applyQFilterAction = null;
	private MenuItem editPRulesAction = null;
	private MenuItem getIdentifiedSpecAction = null;
	private MenuItem getIdentifiedAxisAction = null;
	private MenuItem resetRecoverAction = null;
	private MenuItem flaggedSpecAction = null;
	private MenuItem getStartedHelp = null;
	private MenuItem aboutHelp = null;

	private static class Holder {
		private static final MenuBarItems MenuBarItems = new MenuBarItems();
	}

	public static MenuBarItems getInstance() {
		return Holder.MenuBarItems;
	}

	private MenuBarItems() {
		TaskExecutor task = TaskExecutor.getInstance();
		// file menu items
		Menu fileMenu = new Menu(" File ");
		// load file
		MenuItem openFile = new MenuItem(" Open File  ...  Ctrl+O ");
		openFile.setGraphic(new ImageView(IconResource.getImage(ICON.LOAD)));
		openFile.setOnAction((ActionEvent t) -> {
			final FileChooser fileChooser = new FileChooser();
			FileUtils.cofigureFileChooser(fileChooser, "Select .mgf or .raw files");
			File file = fileChooser.showOpenDialog(Recover.mainStage);
			if (file != null) {
				FileUtils.open(file);
				if (file != null) {
					long startTime = System.currentTimeMillis();
					System.out.println("Info loading file...");
					System.out.println(
							"Info the file: " + file.getAbsolutePath() + " has been imported with success. ");
					PeaklistReader.load(file);
					long endTime = System.currentTimeMillis();
					long totalTime = endTime - startTime;
					System.out.println("Info loading time: " + (double) totalTime / 1000 + " sec");
					System.out.println("Info " + ListOfSpectra.getFirstSpectra().getNbSpectra() + " spectra");
					System.out.println("Info " + ListOfSpectra.getSecondSpectra().getNbSpectra() + " spectra");
					Table.getInstance().setItems(ListOfSpectra.getFirstSpectra().getSpectraAsObservable());
				}
			}
		});

		// export file
		MenuItem exportFile = new MenuItem(" Export File  ...  Ctrl+E ");
		exportFile.setGraphic(new ImageView(IconResource.getImage(ICON.EXPORT)));

		// export in batch
		MenuItem exportInBatchFile = new MenuItem(" Export in batch  ... ");
		exportInBatchFile.setGraphic(new ImageView(IconResource.getImage(ICON.EXPORT_DATA)));

		// exit Recover
		MenuItem exitFile = new MenuItem(" Exit Ctrl+Q ");
		exitFile.setGraphic(new ImageView(IconResource.getImage(ICON.EXIT)));
		exitFile.setOnAction((ActionEvent t) -> {
			new Exit("Exit Recover", "Are you sure you want to exit Recover ? ", Recover.mainStage);
			System.out.println("Info Recover will be closed.");
		});
		fileMenu.getItems().addAll(openFile, exportFile, exportInBatchFile, exitFile);
		// action menu items
		Menu actionsMenu = new Menu(" Actions ");

		// apply filter
		MenuItem applyQFilterAction = new MenuItem(" Apply Quality Filter ");
		applyQFilterAction.setGraphic(new ImageView(IconResource.getImage(ICON.APPLYFILTER)));
		applyQFilterAction.setOnAction((ActionEvent t) -> {
			new ApplyFilter("Apply Filter", Recover.mainStage);
		});

		// edit Parsing Rules
		MenuItem editPRulesAction = new MenuItem(" Edit Parsing Rules ");
		editPRulesAction.setGraphic(new ImageView(IconResource.getImage(ICON.EDIT)));
		editPRulesAction.setOnAction((ActionEvent t) -> {
			new ParsingRules("Edit Parsing Rules", Recover.mainStage);

		});

		// get Identified Spectra
		MenuItem getIdentifiedSpecAction = new MenuItem(" Get Identified Spectra ");
		getIdentifiedSpecAction.setGraphic(new ImageView(IconResource.getImage(ICON.GETSPECTRUM)));

		// Use Identified Axis
		MenuItem getIdentifiedAxisAction = new MenuItem(" Use Identified Axis ");
		getIdentifiedAxisAction.setGraphic(new ImageView(IconResource.getImage(ICON.USEAXIS)));

		// reset Recover
		MenuItem resetRecoverAction = new MenuItem(" Reset Recover ");
		resetRecoverAction.setGraphic(new ImageView(IconResource.getImage(ICON.RESET)));

		// flagged Spectrum
		MenuItem flaggedSpecAction = new MenuItem(" Flagged Spectrum ");
		flaggedSpecAction.setGraphic(new ImageView(IconResource.getImage(ICON.FLAG)));

		actionsMenu.getItems().addAll(applyQFilterAction, editPRulesAction, getIdentifiedSpecAction,
				getIdentifiedAxisAction, resetRecoverAction, flaggedSpecAction);

		/** help menu items */
		// get started menu item
		Menu helpMenu = new Menu(" Help ");
		MenuItem getStartedHelp = new MenuItem(" User guide ");
		getStartedHelp.setGraphic(new ImageView(IconResource.getImage(ICON.HELP)));
		// about menu item
		MenuItem aboutHelp = new MenuItem(" About ");
		aboutHelp.setGraphic(new ImageView(IconResource.getImage(ICON.INFORMATION)));
		aboutHelp.setOnAction((ActionEvent t) -> {
			new About("About Recover",
					"Recover and RecoverFX have been developped by \n Alexandre Walter, Alexandre Burel ,Aymen Romdhani and Benjamin Lombart at LSMBO,\n "
							+ "IPHC UMR7178, CNRS FRANCE. Recover is available on the MSDA web site:",
					new Hyperlink("https://msda.unistra.fr"), Recover.mainStage);
			System.out.println("Info about Recover.");
		});
		helpMenu.getItems().addAll(getStartedHelp, aboutHelp);
		this.getMenus().addAll(fileMenu, actionsMenu, helpMenu);
	}

}