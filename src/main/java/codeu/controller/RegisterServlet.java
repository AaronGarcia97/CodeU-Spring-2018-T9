package codeu.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import codeu.model.data.PasswordUtils;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

import codeu.model.data.Activity;
import codeu.model.data.Activity.ActivityType;
import codeu.model.store.basic.ActivityStore;


public class RegisterServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  /** Store class that gives access to activities. */
	private ActivityStore activityStore;

  /**
   * Set up state for handling registration-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setActivityStore(activityStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the ActivityStore used by this servlet. This function provides a common setup method for
   * use by the test framework or the servlet's init() function.
   */
  void setActivityStore(ActivityStore activityStore) {
    this.activityStore = activityStore;
  }


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String reTypedPassword = request.getParameter("reTypedPassword");
    String email = request.getParameter("email");

    if(username.length()<3 || username.length()>14){
        request.setAttribute("error", "username must be between 3 and 14 characters");
        request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
        return;
    }

    if(!PasswordUtils.isPasswordCorrect(password)){
        request.setAttribute("error", "Password must be between 5 and 13 characters and contain both letters and numbers.");
        request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
          return;
    }

    if (!password.equals(reTypedPassword)) {
      request.setAttribute("error", "Passwords must match.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }


    if (!username.matches("[\\w*\\s*]*")) {
      request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }

    if (userStore.isUserRegistered(username)) {
      request.setAttribute("error", "That username is already taken.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }

    if (userStore.isEmailRegistered(email)) {
      request.setAttribute("error", "That email is already linked to an account.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }



    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

    User user = new User(UUID.randomUUID(), username, hashedPassword, email, Instant.now(), false);
    userStore.addUser(user);

    Activity activity = new Activity(UUID.randomUUID(), 0, Instant.now(), "joined the site!", user.getId(), user.getName(), ActivityType.REGISTRATION, null, null, new double[4], -50);

    activityStore.addActivity(activity);

    response.sendRedirect("/login");
  }
}
