import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test4 {

	public static void main(String[] args) {
		// データベース接続用の情報
		String DATABASE_NAME = "webdb";
		String PROPERTIES = "?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
		String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + PROPERTIES;
		String USER = "root";
		String PASS = "password";

		// HTMLファイルの保存先パス（デスクトップ上）
		String htmlFilePath = System.getProperty("user.home") + "/Desktop/users.html";

		try {
			// MySQLに接続
			Connection conn = DriverManager.getConnection(URL, USER, PASS);
			System.out.println("データベースに接続に成功");

			// SQLクエリを実行して結果をHTMLに追加する
			Statement stmt = conn.createStatement();
			String sql = "SELECT user_id, user_name, address, phone FROM webdb.users";
			ResultSet rs = stmt.executeQuery(sql);

			StringBuilder htmlContent = new StringBuilder();
			htmlContent.append("<html><head><style>")
					.append("body { font-family: Arial, sans-serif; }")
					.append("table { width: 80%; border-collapse: collapse; margin: 20px auto; }")
					.append("table, th, td { border: 1px solid #ccc; }")
					.append("th, td { padding: 8px; text-align: center; }")
					.append("th { background-color: #f2f2f2; }")
					.append("tr:nth-child(even) { background-color: #f9f9f9; }")
					.append(".left-align {text-align: left; }")
					.append("</style></head>")
					.append("<body>");
			htmlContent.append("<h2 style=\"text-align:center;\">ユーザーリスト</h2>");
			htmlContent.append("<table>");
			htmlContent.append("<tr>")
					.append("<th>ID</th>")
					.append("<th>名前</th>")
					.append("<th>住所</th>")
					.append("<th>電話</th>")
					.append("</tr>");

			while (rs.next()) {
				long userId = rs.getLong("user_id");
				String username = rs.getString("user_name");
				String address = rs.getString("address");
				String phone = rs.getString("phone");

				htmlContent.append("<tr>")
						.append("<td>").append(userId).append("</td>")
						.append("<td class=\"left-align\">").append(username).append("</td>")
						.append("<td class=\"left-align\">").append(address).append("</td>")
						.append("<td class=\"left-align\">").append(phone).append("</td>")
						.append("</tr>");
			}

			htmlContent.append("</table>");
			htmlContent.append("</body></html>");

			// HTMLファイルに保存
			try (FileWriter fileWriter = new FileWriter(htmlFilePath)) {
				fileWriter.write(htmlContent.toString());
				System.out.println("HTMLファイルを保存しました: " + htmlFilePath);

				// デスクトップ上のHTMLファイルをデフォルトのブラウザで開く
				File htmlFile = new File(htmlFilePath);
				Desktop.getDesktop().browse(htmlFile.toURI());

			} catch (IOException e) {
				System.err.println("HTMLファイルの保存中にエラーが発生しました: " + e.getMessage());
				e.printStackTrace();
			}

		} catch (SQLException e) {
			System.err.println("SQLエラーが発生しました: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
