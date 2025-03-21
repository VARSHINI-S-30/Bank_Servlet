import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String contact = request.getParameter("contact");
        String dob = request.getParameter("dob");
        String accountType = request.getParameter("accountType");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_db", "root", "Sank14@ar");

            String query = "INSERT INTO users(name, contact, dob, accountType) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setString(2, contact);
            pstmt.setString(3, dob);
            pstmt.setString(4, accountType);

            int rowsInserted = pstmt.executeUpdate();
            con.close();

            if (rowsInserted > 0) {
                response.sendRedirect("register.html?success=Registration+Successful!");
            } else {
                response.sendRedirect("register.html?error=Failed+to+register.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.html?error=Something+went+wrong.");
        }
    }
}
