import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class test3 {

    private static HttpServer server;
    private static final int PORT = 8002; // 別のポート番号を指定

    public static void main(String[] args) {
        try {
            // HTTPサーバーの作成（別のポート番号を指定）
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            // ハンドラーを追加
            server.createContext("/users", new UsersHandler());
            // サーバーを起動
            server.start();

            System.out.println("サーバーが起動しました。http://localhost:" + PORT + "/users でユーザーリストが表示されます。");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class UsersHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder response = new StringBuilder();
            response.append("<html><body>");

            // データベース接続用の情報
            String DATABASE_NAME = "webdb";
            String PROPERTIES = "?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
            String URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME + PROPERTIES;
            String USER = "root";
            String PASS = "password";

            try {
                // MySQLに接続
                Connection conn = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("データベースに接続に成功");

                // SQLクエリを実行して結果をHTMLに追加する
                Statement stmt = conn.createStatement();
                String sql = "SELECT user_id, user_name, address, phone FROM webdb.users";
                ResultSet rs = stmt.executeQuery(sql);

                response.append("<h2>ユーザーリスト</h2>");
                response.append("<ul>");
                while (rs.next()) {
                    long userId = rs.getLong("user_id");
                    String username = rs.getString("user_name");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    response.append("<li>").append("ID: ").append(userId).append(", 名前: ").append(username)
                            .append(", 住所: ").append(address).append(", 電話: ").append(phone).append("</li>");
                }
                response.append("</ul>");

                // HTMLの終了
                response.append("</body></html>");

                // HTTPレスポンスを設定
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();

            } catch (SQLException e) {
                System.err.println("SQLエラーが発生しました: " + e.getMessage());
                e.printStackTrace();
            }

        }
    }
}
