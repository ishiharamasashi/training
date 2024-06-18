import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class test2 {

	public static void main(String[] args) {
		// DB接続用定数
		String DATABASE_NAME = "webdb";
		String PROPERTIES = "?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
		String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + PROPERTIES;
		// DB接続用・ユーザ定数
		String USER = "root";
		String PASS = "password"; // パスワードなしの場合は空文字列にする

		try {
			// MySQL に接続する
			Class.forName("com.mysql.cj.jdbc.Driver");
			// データベースに接続
			Connection conn = DriverManager.getConnection(URL, USER, PASS);

			// データベースに対する処理
			System.out.println("データベースに接続に成功");

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}


