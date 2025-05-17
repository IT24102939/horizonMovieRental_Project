package com.movierental.servlet.user;

import com.movierental.model.user.RegularUser;
import com.movierental.model.user.User;
import com.movierental.model.user.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for handling user profile updates
 */
@WebServlet("/update-profile")
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - display the update profile form
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }


        String userId = (String) session.getAttribute("userId");


        UserManager userManager = new UserManager(getServletContext());
        User user = userManager.getUserById(userId);

        if (user == null) {

            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }


        request.setAttribute("user", user);


        request.getRequestDispatcher("/user/update-profile.jsp").forward(request, response);
    }

    /**
     * Handles POST requests - process the update profile form
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {

            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }


        String userId = (String) session.getAttribute("userId");


        UserManager userManager = new UserManager(getServletContext());
        User user = userManager.getUserById(userId);

        if (user == null) {

            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }


        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String upgradeAccount = request.getParameter("upgradeAccount");


        if (newPassword != null && !newPassword.trim().isEmpty()) {
            if (currentPassword == null || !user.authenticate(currentPassword)) {
                request.setAttribute("errorMessage", "Current password is incorrect");
                request.setAttribute("user", user);
                request.getRequestDispatcher("/user/update-profile.jsp").forward(request, response);
                return;
            }


            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("errorMessage", "New passwords do not match");
                request.setAttribute("user", user);
                request.getRequestDispatcher("/user/update-profile.jsp").forward(request, response);
                return;
            }


            user.setPassword(newPassword);
        }


        if (email != null && !email.trim().isEmpty()) {
            user.setEmail(email);
        }

        if (fullName != null && !fullName.trim().isEmpty()) {
            user.setFullName(fullName);
        }


        if ("yes".equals(upgradeAccount) && user instanceof RegularUser) {
            userManager.upgradeToPremium(userId);

            user = userManager.getUserById(userId);
        } else {

            userManager.updateUser(user);
        }


        session.setAttribute("user", user);


        request.setAttribute("successMessage", "Profile updated successfully!");
        request.setAttribute("user", user);


        request.getRequestDispatcher("/user/update-profile.jsp").forward(request, response);
    }
}