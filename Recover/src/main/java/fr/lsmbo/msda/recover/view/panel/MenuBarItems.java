package fr.lsmbo.msda.recover.view.panel;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.control.MenuBar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import fr.lsmbo.msda.recover.util.FileUtils;
import fr.lsmbo.msda.recover.util.IconFactory;
import fr.lsmbo.msda.recover.util.IconFactory.ICON;
import fr.lsmbo.msda.recover.view.popup.About;
import fr.lsmbo.msda.recover.view.popup.ApplyFilter;
import fr.lsmbo.msda.recover.view.popup.EditParsingRules;
import fr.lsmbo.msda.recover.view.popup.Exit;
import fr.lsmbo.msda.recover.gui.Recover;

import java.io.File;

import com.sun.media.jfxmedia.logging.Logger;

import fr.lsmbo.msda.recover.Main;

/**
 * 
 * @author aromdhani
 *
 */
public class MenuBarItems extends MenuBar {
	/**
	 * 
	 * @return menuBar
	 */
	public MenuBarItems() {

		// file menu items
		Menu fileMenu = new Menu(" File ");

		// load file
		MenuItem openFile = new MenuItem(" Open File  ...  Ctrl+O ");
		openFile.setGraphic(new ImageView(IconFactory.getImage(ICON.LOAD)));
		openFile.setOnAction((ActionEvent t) -> {
			final FileChooser fileChooser = new FileChooser();
			FileUtils.cofigureFileChooser(fileChooser, "Select .mgf or .raw files");
			File file = fileChooser.showOpenDialog(Recover.mainStage);
			if (file != null) {
				FileUtils.open(file);
			}
		});

		// export file
		MenuItem exportFile = new MenuItem(" Export File  ...  Ctrl+E ");
		exportFile.setGraphic(new ImageView(IconFactory.getImage(ICON.EXPORT)));

		// export in batch
		MenuItem exportInBatchFile = new MenuItem(" Export in batch  ... ");
		exportInBatchFile.setGraphic(new ImageView(IconFactory.getImage(ICON.EXPORT_DATA)));

		// exit Recover
		MenuItem exitFile = new MenuItem(" Exit Ctrl+Q ");
		exitFile.setGraphic(new ImageView(IconFactory.getImage(ICON.CROSS)));
		exitFile.setOnAction((ActionEvent t) -> {
			new Exit("Exit Recover", "Are you sure you want to exit Recover ? ", Recover.mainStage);
			System.out.println("Info - Recover will be closed.");
		});
		fileMenu.getItems().addAll(openFile, exportFile, exportInBatchFile, exitFile);
		// action menu items
		Menu actionsMenu = new Menu(" Actions ");

		// apply filter
		MenuItem applyQFilterAction = new MenuItem(" Apply Quality Filter ");
		applyQFilterAction.setGraphic(new ImageView(IconFactory.getImage(ICON.APPLYFILTER)));
		applyQFilterAction.setOnAction((ActionEvent t) -> {
			new ApplyFilter("Apply Filter", Recover.mainStage);
		});
		
		// edit Parsing Rules
		MenuItem editPRulesAction = new MenuItem(" Edit Parsing Rules ");
		editPRulesAction.setGraphic(new ImageView(IconFactory.getImage(ICON.EDIT)));
		editPRulesAction.setOnAction((ActionEvent t) -> {
			new EditParsingRules("Edit Parsing Rules", Recover.mainStage);
		});

		// get Identified Spectra
		MenuItem getIdentifiedSpecAction = new MenuItem(" Get Identified Spectra ");
		getIdentifiedSpecAction.setGraphic(new ImageView(IconFactory.getImage(ICON.GETSPECTRUM)));

		// Use Identified Axis
		MenuItem getIdentifiedAxisAction = new MenuItem(" Use Identified Axis ");
		getIdentifiedAxisAction.setGraphic(new ImageView(IconFactory.getImage(ICON.USEAXIS)));

		// reset Recover
		MenuItem resetRecoverAction = new MenuItem(" Reset Recover ");
		resetRecoverAction.setGraphic(new ImageView(IconFactory.getImage(ICON.RESET)));

		// flagged Spectrum
		MenuItem flaggedSpecAction = new MenuItem(" Flagged Spectrum ");
		flaggedSpecAction.setGraphic(new ImageView(IconFactory.getImage(ICON.FLAG)));

		actionsMenu.getItems().addAll(applyQFilterAction, editPRulesAction, getIdentifiedSpecAction,
				getIdentifiedAxisAction, resetRecoverAction, flaggedSpecAction);

		/** help menu items */
		// get started menu item
		Menu helpMenu = new Menu(" Help ");
		MenuItem getStartedHelp = new MenuItem(" Get started ");
		getStartedHelp.setGraphic(new ImageView(IconFactory.getImage(ICON.HELP)));
		// about menu item
		MenuItem aboutHelp = new MenuItem(" About ");
		aboutHelp.setGraphic(new ImageView(IconFactory.getImage(ICON.INFORMATION)));
		aboutHelp.setOnAction((ActionEvent t) -> {
			new About("About Recover",
					"Recover and RecoverFX have been developped by \n Alexandre Walter, Alexandre Burel ,Aymen Romdhani and Benjamin Lombart at LSMBO,\n "
							+ "IPHC UMR7178, CNRS FRANCE. Recover is available on the MSDA web site:",
					new Hyperlink("https://msda.unistra.fr"), Recover.mainStage);
			System.out.println("Info - About Recover.");
		});
		helpMenu.getItems().addAll(getStartedHelp, aboutHelp);
		this.getMenus().addAll(fileMenu, actionsMenu, helpMenu);
	}

}
