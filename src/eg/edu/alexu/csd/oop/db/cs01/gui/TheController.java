package eg.edu.alexu.csd.oop.db.cs01.gui;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurDriver;
import eg.edu.alexu.csd.oop.db.cs01.jdbc.OurJDBC;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class TheController implements Initializable {

	@FXML
	private TextField entreQuery;

	@FXML
	private Button runBtn;

	@FXML
	private Label errorLable;

	@FXML
	private TableView<Object> fillTable;

	private boolean pathDone;

	@FXML
	void runBtnAct() {
		errorLable.setTextFill(Color.GREEN);
		errorLable.setText("");
		if (entreQuery.getText().equals("")) {
			errorLable.setTextFill(Color.RED);
			errorLable.setText("Please entre a query.");
		} else {
			try {
				if (!pathDone) {
					OurJDBC.getInstance().setPath(entreQuery.getText());
					entreQuery.setPromptText("Enter URL");
					entreQuery.setText("");
					pathDone = true;
					return;
				}
				if (OurDriver.getInstance().acceptsURL(entreQuery.getText())) {
					entreQuery.setPromptText("Enter your Query");
					errorLable.setText(OurJDBC.getInstance().run(entreQuery.getText()));
					entreQuery.setText("");
				} else {
					errorLable.setText(OurJDBC.getInstance().run(entreQuery.getText()));
					buildTable(OurJDBC.getInstance().getResultSet());
				}

			} catch (SQLException e) {
				errorLable.setTextFill(Color.RED);
				errorLable.setText(e.getMessage());
			}

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	void buildTable(ResultSet resultSet) throws SQLException {
		if (resultSet == null) {
			return;
		}
		fillTable.getItems().clear();
		fillTable.getColumns().clear();
		ObservableList<Object> data = FXCollections.observableArrayList();
		for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
			// We are using non property style for making dynamic table
			final int j = i;
			TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
			col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
				public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
					return new SimpleStringProperty(param.getValue().get(j).toString().replaceAll("'", ""));
				}
			});
			col.setResizable(true);
			col.setPrefWidth(fillTable.getWidth() / resultSet.getMetaData().getColumnCount());
			fillTable.getColumns().addAll(col);
			System.out.println("Column [" + i + "] ");
		}

		while (resultSet.next()) {
			// Iterate Row
			ObservableList<Object> row = FXCollections.observableArrayList();
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				// Iterate Column
				if (resultSet.getObject(i) != null) {
					row.add(resultSet.getObject(i));
				} else {
					row.add("null");
				}
			}
			System.out.println("Row [1] added " + row);
			data.add(row);
		}
		fillTable.setItems(data);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		entreQuery.setPromptText("Enter The Path Of Our Database");
		pathDone = false;
	}

	@FXML
	public void pressEnter(KeyEvent e) {
		switch (e.getCode()) {
		case ENTER:
			runBtnAct();
			break;
		default:
			return;
		}
	}
}
