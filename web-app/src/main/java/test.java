import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class test extends JFrame {

	private JTable table;
	private JTextField departmentField;
	private JButton searchButton;
	private JButton clearButton;

	public test() {
		setTitle("Employee Search");
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new BorderLayout());

		// テーブルのヘッダー
		String[] columns = { "ID", "Employee", "Department" };
		Object[][] data = {};

		table = new JTable(data, columns);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane, BorderLayout.CENTER);

		// 検索フィールドとボタン
		JPanel searchPanel = new JPanel(new FlowLayout());
		departmentField = new JTextField(20);
		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchEmployees();
			}
		});
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTable();
			}
		});
		searchPanel.add(new JLabel("Department:"));
		searchPanel.add(departmentField);
		searchPanel.add(searchButton);
		searchPanel.add(clearButton);
		panel.add(searchPanel, BorderLayout.NORTH);

		add(panel);
		setVisible(true);
	}

	private void searchEmployees() {
		try {
			// データベース接続
			String url = "jdbc:ucanaccess://C:/Users/m-ishihara/Desktop/Database21.accdb";
			Connection connection = DriverManager.getConnection(url);

			String department = departmentField.getText();

			// SQL実行
			String sql = "SELECT * FROM Employees WHERE Department = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, department);
			ResultSet resultSet = statement.executeQuery();

			// 新しいテーブルモデルを作成
			DefaultTableModel model = new DefaultTableModel();
			model.setColumnIdentifiers(new Object[] { "ID", "Employee", "Department" });

			// データをテーブルに追加
			while (resultSet.next()) {
				Object[] row = { resultSet.getInt("ID"), resultSet.getString("Employee"),
						resultSet.getString("Department") };
				model.addRow(row);
			}

			// テーブルに新しいモデルを設定
			table.setModel(model);

			// 接続を閉じる
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void clearTable() {
		// 新しい空のDefaultTableModelを作成
		DefaultTableModel defaultModel = new DefaultTableModel();
		// テーブルに新しいモデルを設定
		table.setModel(defaultModel);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new test();
			}
		});
	}
}
