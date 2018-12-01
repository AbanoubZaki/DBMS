package eg.edu.alexu.csd.oop.db.cs01.gui;

import java.sql.SQLException;

import com.sun.javafx.scene.control.skin.PaginationSkin;

import eg.edu.alexu.csd.oop.db.cs01.GeneralParser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class TheController {

	@FXML
	private TextField entreQuery;

	@FXML
	private Button runBtn;

	@FXML
	private Label errorLable;

	@FXML
	private TableView<?> fillTable;

	@FXML
	void runBtnAct(ActionEvent event) {
		errorLable.setText("");
		if (entreQuery.getText().equals("")) {
			errorLable.setText("Please entre a query.");
		} else {
			try {
				GeneralParser.getInstance().parse(entreQuery.getText());
				if (GeneralParser.getInstance().isCheckerExecute()) {
					errorLable.setTextFill(Color.GREEN);
					errorLable.setText("You have made a change : "+GeneralParser.getInstance().getUpdatedRows());
				}
			} catch (SQLException e) {
				errorLable.setText(e.getMessage());
			}

		}
	}
}
