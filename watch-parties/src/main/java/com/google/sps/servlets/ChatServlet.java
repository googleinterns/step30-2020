import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

import com.google.sps.data.Chat;

@WebServlet("/chatmessages")
public class ChatServlet extends HttpServlet {
    String justSettingUp = "lmao";
    ArrayList<Chat> comments = new ArrayList<>();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String comment = request.getParameter("submitted-comment");
        Date timestamp = new Date();
        int authorID = 3;

        Chat c = new Chat(comment, authorID, new Timestamp(timestamp.getTime()));
        comments.add(c);

        response.setContentType("application/json;");
        for (Chat thisComment : comments) {
            response.getWriter().println(thisComment.toJSON());
        }

        //System.out.println(justSettingUp);
    }
}