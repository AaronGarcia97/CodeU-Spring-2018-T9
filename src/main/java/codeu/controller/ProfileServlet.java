package codeu.controller;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.data.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet class responsible for the profile page.
 */
public class ProfileServlet extends HttpServlet {

    /**
     * Store class that gives access to Conversations.
     */
    private ConversationStore conversationStore;

    /**
     * Store class that gives access to Messages.
     */
    private MessageStore messageStore;

    /**
     * Store class that gives access to Users.
     */
    private UserStore userStore;

    /**
     * Set up state for handling chat requests.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        setConversationStore(ConversationStore.getInstance());
        setMessageStore(MessageStore.getInstance());
        setUserStore(UserStore.getInstance());
    }

    /**
     * Sets the ConversationStore used by this servlet. This function provides a common setup method
     * for use by the test framework or the servlet's init() function.
     */
    void setConversationStore(ConversationStore conversationStore) {
        this.conversationStore = conversationStore;
    }

    /**
     * Sets the MessageStore used by this servlet. This function provides a common setup method for
     * use by the test framework or the servlet's init() function.
     */
    void setMessageStore(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method for use
     * by the test framework or the servlet's init() function.
     */
    void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    /**
     * This function fires when a user navigates to a profile page. It checks if the user exists from the username of the URL requested.
     * It then forwards to profile.jsp for rendering.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String requestUrl = request.getRequestURI();
        String username = requestUrl.substring("/users/".length());

        // see if user exists
        User user = userStore.getUser(username);

        if (user == null) {
            System.out.println("No user exists.");
        } else {
            // forward request to profile.jsp
            request.setAttribute("profilePage", username);
            request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
        }
    }

    /**
     *
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


    }
}