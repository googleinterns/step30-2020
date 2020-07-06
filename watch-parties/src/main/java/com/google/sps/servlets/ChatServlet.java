import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chatmessages")
public class ChatServlet extends HttpServlet {
    String justSettingUp = "lmao";

    response.setContentType("text/html;");
    response.getWriter().println(justSettingUp);
}