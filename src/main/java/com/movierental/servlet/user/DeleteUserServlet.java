package com.movierental.servlet.user;

import com.movierental.model.user.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for handling user account deletion
 */
@WebServlet("/delete-account")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests - display the delete account confirmation
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // Not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }


        request.getRequestDispatcher("/user/delete-account.jsp").forward(request, response);
    }

    /**
     * Handles POST requests - process the account deletion
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // Not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }


        String userId = (String) session.getAttribute("userId");
        System.out.println("DeleteUserServlet: Attempting to delete user: " + userId);

        // Get confirmation parameter
        String confirmDelete = request.getParameter("confirmDelete");

        if (!"yes".equals(confirmDelete)) {
            // User did not confirm deletion
            System.out.println("DeleteUserServlet: Deletion not confirmed");
            response.sendRedirect(request.getContextPath() + "/update-profile");
            return;
        }

        try {
            // Create UserManager with ServletContext to ensure proper file path
            UserManager userManager = new UserManager(getServletContext());
            System.out.println("DeleteUserServlet: UserManager created with ServletContext");

            // Attempt to delete user
            boolean deleted = userManager.deleteUser(userId);
            System.out.println("DeleteUserServlet: Delete result: " + deleted);

            if (deleted) {
                // Invalidate session
                session.invalidate();
                System.out.println("DeleteUserServlet: Session invalidated");

                // Create a new session to display success message
                session = request.getSession(true);
                session.setAttribute("successMessage", "Your account has been successfully deleted.");

                // Redirect to home page
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                // Deletion failed
                System.out.println("DeleteUserServlet: Deletion failed");
                request.setAttribute("errorMessage", "Failed to delete account. Please try again.");
                request.getRequestDispatcher("/user/delete-account.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Log any exceptions
            System.err.println("DeleteUserServlet: Exception occurred during account deletion:");
            e.printStackTrace();

            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/user/delete-account.jsp").forward(request, response);
        }
    }
}