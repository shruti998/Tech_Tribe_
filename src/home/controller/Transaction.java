package home.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;
import java.util.function.UnaryOperator;

public class Transaction {
	Random random = new Random();

	public String groupName;
	private String userName = "Farida";
	public String transactionId;

	public String transactionName;
	public double amount;
	public String paidBy;

	boolean isSettle;

	public List<String> members;

	class TransactionSplit {
		public String splitId;
		public double amount;
		public boolean selected;

		TransactionSplit(String splitId, double amount) {
			this.splitId = splitId;
			this.amount = amount;
			this.selected = true;
		}

		TransactionSplit() {
			this.splitId = null;
			this.amount = 0;
			this.selected = false;
		}
	}

	public Map<String, TransactionSplit> memberSplit;

	UnaryOperator<Change> filter = change -> {
		String controllerText = change.getControlNewText();
		return controllerText.matches("([0-9]*[.])?[0-9]*") ? change : null;
	};

	@FXML
	public Label groupNameLabel;

	@FXML
	public VBox splitVBox;

	@FXML
	public TextField transactionNameField;

	@FXML
	public TextField amountField;

	@FXML
	public ComboBox paidByPicker;

	@FXML
	void initialize() {
		// get transaction details from id
		getTransaction();

		// render misc
		renderMisc();

		// Get group members names
		getMemberNames();

		// Get the member Splits
		getMemberSplits();

		// render the combo box
		renderComboBox();

		// render the vbox contents
		renderSplitBox();
	}

	void getTransaction() {
		if (this.transactionId != null && this.transactionId.length() > 0) {
			try (Connection connection = DatabaseConnection.Connect()) {
				String transactionQuery = "SELECT * FROM transaction WHERE id = '" + this.transactionId + "'";
				PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery);
				ResultSet rs = transactionStatement.executeQuery();
				while (rs.next()) {
					this.transactionName = rs.getString(5);
					this.amount = rs.getDouble(4);
					this.paidBy = rs.getString(3);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	void getMemberNames() {
		members = new ArrayList<String>();
		try (Connection connection = DatabaseConnection.Connect()) {
			String memberQuery = "SELECT memberName FROM all_groups WHERE groupName = '" + this.groupName + "'";
			PreparedStatement memberStatement = connection.prepareStatement(memberQuery);
			ResultSet rs = memberStatement.executeQuery();
			while (rs.next()) {
				this.members.add(rs.getString(1));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	void getMemberSplits() {
		this.memberSplit = new HashMap<>();
		for (String member : this.members) {
			this.memberSplit.put(member, null);
		}

		if (this.transactionId != null) {
			try (Connection connection = DatabaseConnection.Connect()) {
				String splitQuery = "SELECT id, payeeName, individualAmount FROM transaction_split WHERE transactionId = '"
						+ this.transactionId + "'";
				PreparedStatement splitStatement = connection.prepareStatement(splitQuery);
				ResultSet rs = splitStatement.executeQuery();
				while (rs.next()) {
					String splitId = rs.getString(1);
					String payeeName = rs.getString(2);
					double amount = rs.getDouble(3);

					this.memberSplit.put(payeeName, new TransactionSplit(splitId, amount));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	void renderMisc() {
		// Set the group name label here
		groupNameLabel.setText(this.groupName);
		transactionNameField.setText(this.transactionName);
		amountField.setText("" + this.amount);

		amountField.setTextFormatter(new TextFormatter<>(filter));

		amountField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (newValue.length() > 0)
					amount = Double.parseDouble(newValue);
				else
					amount = 0;
			}
		});

		transactionNameField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				transactionName = newValue;
			}
		});

		if (this.isSettle) {
			transactionNameField.setText("Settle with ");
			transactionNameField.setEditable(false);
		}
	}

	void renderComboBox() {
		ObservableList<Label> pickerList = FXCollections.observableArrayList();

		for (String member : this.members) {
			Label tempLabel = new Label(member);
			tempLabel.setTextFill(Color.BLACK);
			pickerList.add(new Label(member));
		}

		paidByPicker.setItems(pickerList);

		paidByPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Label selectedLabel = (Label) paidByPicker.getSelectionModel().getSelectedItem();
				selectedLabel.setTextFill(Color.BLACK);
				setPaidBy(selectedLabel.getText());
			}
		});

		// Set the payerName to selected in the ComboBox
		if (this.paidBy != null) {
			for (int i = 0; i < pickerList.size(); i++) {
				if (this.paidBy != null && pickerList.get(i).getText().equals(this.paidBy)) {
					paidByPicker.getSelectionModel().clearAndSelect(i);
					((Label) paidByPicker.getSelectionModel().getSelectedItem()).setTextFill(Color.BLACK);
				}
			}
		}
	}

	void renderSplitBox() {
		ObservableList<Node> children = splitVBox.getChildren();
		children.clear();

		int padding = 0;
		for (String member : this.members)
			padding = Math.max(padding, member.length());

		for (Map.Entry<String, TransactionSplit> e : this.memberSplit.entrySet()) {
			HBox tempHbox = new HBox(30);
			tempHbox.setAlignment(Pos.CENTER);

			StringBuilder padBuilder = new StringBuilder();
			for (int i = 0; i < padding - e.getKey().length(); i++)
				padBuilder.append(' ');

			CheckBox cb = new CheckBox(e.getKey() + padBuilder);
			TextField amountField = new TextField();
			amountField.setPromptText("0.0");

			if (e.getValue() != null) {
				cb.setSelected(e.getValue().selected);
				amountField.setText(e.getValue().amount + "");
				amountField.setDisable(!e.getValue().selected);
			} else {
				amountField.setDisable(true);
			}

			cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					boolean selected = cb.isSelected();

					if (selected && e.getValue() == null) {
						e.setValue(new TransactionSplit());
					}

					e.getValue().selected = selected;

					amountField.setDisable(!selected);

					if (isSettle && selected) {
						// uncheck all other boxes and update the transaction name
						for (Node child : children) {
							HBox hb = (HBox) child;
							CheckBox tempCb = (CheckBox) hb.getChildren().get(0);
							if (cb != tempCb) {
								tempCb.setSelected(false);
							} else {
								System.err.print("FOUND ME");
							}
						}

						transactionNameField.setText("Settle with " + e.getKey());
					}
				}
			});

			amountField.setTextFormatter(new TextFormatter(filter));
			amountField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					if (newValue.length() > 0)
						e.getValue().amount = Double.parseDouble(newValue);
					else
						e.getValue().amount = 0;
				}
			});

			tempHbox.getChildren().addAll(cb, amountField);
			children.add(tempHbox);
		}
	}

	void saveNewTransactionSplit(int transactionId, String payeeName, double amount) {
		try (Connection connection = DatabaseConnection.Connect()) {
			String createTransactionSplitQuery = "INSERT INTO transaction_split " + "VALUES (?, ?, ?, ?)";
			PreparedStatement createSplitStatement = connection.prepareStatement(createTransactionSplitQuery);
			createSplitStatement.setInt(1, random.nextInt());
			createSplitStatement.setInt(2, transactionId);
			createSplitStatement.setString(3, payeeName);
			createSplitStatement.setDouble(4, amount);

			createSplitStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	void saveNewTransaction() {
		try (Connection connection = DatabaseConnection.Connect()) {
			// Create a transaction
			int id = random.nextInt();

			String createTransactionQuery = "INSERT INTO transaction " + " VALUES (?, ?, ?, ?, ?)";

			PreparedStatement createStatement = connection.prepareStatement(createTransactionQuery);
			createStatement.setInt(1, id);
			createStatement.setString(2, this.groupName);
			createStatement.setString(3, this.paidBy);
			createStatement.setDouble(4, this.amount);
			createStatement.setString(5, this.transactionName);

			createStatement.executeUpdate();

			// create transaction splits
			for (Map.Entry<String, TransactionSplit> e : this.memberSplit.entrySet()) {
				if (e.getValue() != null && e.getValue().selected) {
					saveNewTransactionSplit(id, e.getKey(), e.getValue().amount);
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	void updateTransaction() {
		// Update an existing transaction
		try (Connection connection = DatabaseConnection.Connect()) {
			String updateTransactionQuery = "UPDATE transaction "
					+ "SET payerName = ?, amount = ?, transactionName = ? " + "WHERE id = ?";

			PreparedStatement updateStatement = connection.prepareStatement(updateTransactionQuery);
			updateStatement.setString(1, this.paidBy);
			updateStatement.setDouble(2, this.amount);
			updateStatement.setString(3, this.transactionName);
			updateStatement.setInt(4, Integer.parseInt(this.transactionId));

			updateStatement.executeUpdate();

			// update transaction splits
			for (Map.Entry<String, TransactionSplit> e : this.memberSplit.entrySet()) {
				if (e.getValue() != null && e.getValue().selected) {
					if (e.getValue().splitId != null) {
						String updateTransactionSplitQuery = "UPDATE transaction_split "
								+ "SET payeeName = ?, individualAmount = ? " + "WHERE id = ?";
						PreparedStatement updateSplitStatement = connection
								.prepareStatement(updateTransactionSplitQuery);
						updateSplitStatement.setString(1, e.getKey());
						updateSplitStatement.setDouble(2, e.getValue().amount);
						updateSplitStatement.setInt(3, Integer.parseInt(e.getValue().splitId));

						updateSplitStatement.executeUpdate();
					} else {
						saveNewTransactionSplit(Integer.parseInt(this.transactionId), e.getKey(), e.getValue().amount);
					}
				} else if (e.getValue() != null && !e.getValue().selected && e.getValue().splitId != null) {
					// Delete this record from the split table
					String deleteTransactionSplitQuery = "DELETE FROM transaction_split WHERE id = ?";
					PreparedStatement deleteStatement = connection.prepareStatement(deleteTransactionSplitQuery);
					deleteStatement.setInt(1, Integer.parseInt(e.getValue().splitId));

					deleteStatement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public void openGroupDetailsWindow(Event event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
		try {
			URL url = new File("src/home/fxml/GroupDetails.fxml").toURI().toURL();

			FXMLLoader loader = new FXMLLoader(url);

			GroupDetails controller = new GroupDetails();
			controller.setGroupName(this.groupName);

			loader.setController(controller);

			Parent root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			System.err.println(String.format("Error: %s", e.getMessage()));
		}
	}

	@FXML
	void onSaveClick(ActionEvent event) {
		double splitAmount = 0;
		for (Map.Entry<String, TransactionSplit> e : this.memberSplit.entrySet()) {
			if (e.getValue() != null && e.getValue().selected) {
				splitAmount += e.getValue().amount;
			}
		}

		if (transactionName == null || transactionName.trim().length() == 0) {
			// Error, there must be a transaction name.
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Something's missing!");
			alert.setHeaderText("Please specify a transaction name!");
			alert.showAndWait();
			return;
		}

		if (paidBy == null || paidBy.trim().length() == 0) {
			// Error, there must be a payer.
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Something's missing!");
			alert.setHeaderText("Please specify who's paying!");
			alert.showAndWait();
			return;
		}

		if (splitAmount != this.amount) {
			// Error, amount in split should add up to total amount.
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("It doesn't add up!");
			alert.setHeaderText("The amount in split does not add up to the total amount of the transaction!");
			alert.showAndWait();
			return;
		}

		if (this.amount == 0) {
			// Error, amount in split should add up to total amount.
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("No money moved");
			alert.setHeaderText("The amount cannot be 0!");
			alert.showAndWait();
			return;
		}

		if (this.transactionId == null || this.transactionId.length() == 0) {
			// Create a new transaction
			saveNewTransaction();
		} else {
			// Update existing transaction
			updateTransaction();
		}

		openGroupDetailsWindow(event);
	}

	@FXML
	void onSplitClick(ActionEvent event) {
		int totalChecked = 0;
		for (Map.Entry<String, TransactionSplit> e : this.memberSplit.entrySet()) {
			if (e.getValue() != null && e.getValue().selected) {
				totalChecked++;
			}
		}
		if (totalChecked > 0) {
			for (Node t : splitVBox.getChildren()) {
				HBox child = (HBox) t;
				CheckBox cb = (CheckBox) child.getChildren().get(0);
				TextField field = (TextField) child.getChildren().get(1);
				if (cb.isSelected()) {
					field.setText(this.amount / totalChecked + "");
				}
			}
		}
	}

	@FXML
	void onBackClick(ActionEvent event) {
		openGroupDetailsWindow(event);
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public boolean isSettle() {
		return isSettle;
	}

	public void setSettle(boolean isSettle) {
		this.isSettle = isSettle;
	}
}
