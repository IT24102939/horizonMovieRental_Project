<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.movierental.model.user.User" %>
<%@ page import="com.movierental.model.user.PremiumUser" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Account - Horizon</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: #2563eb;
            --primary-hover: #1d4ed8;
            --secondary: #ec4899;
            --dark: #0f172a;
            --darker: #020617;
            --light-text: #f1f5f9;
            --dark-text: #1e293b;
            --gray-text: #94a3b8;
            --card-bg: #1e293b;
            --input-bg: #334155;
            --border-color: #475569;
            --success: #10b981;
            --danger: #ef4444;
            --warning: #f59e0b;
        }

        body {
            background-color: var(--darker);
            color: var(--light-text);
            font-family: 'Poppins', sans-serif;
            min-height: 100vh;
            padding-bottom: 3rem;
            background-image:
                radial-gradient(circle at 90% 10%, rgba(37, 99, 235, 0.1) 0%, transparent 40%),
                radial-gradient(circle at 10% 90%, rgba(236, 72, 153, 0.1) 0%, transparent 40%);
        }

        /* Navbar */
        .navbar {
            background-color: rgba(15, 23, 42, 0.9);
            backdrop-filter: blur(10px);
            border-bottom: 1px solid var(--border-color);
            padding: 1rem 0;
        }

        .navbar-brand {
            font-weight: 700;
            font-size: 1.5rem;
            background: linear-gradient(to right, var(--primary), var(--secondary));
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
        }

        .nav-link {
            color: var(--light-text);
            font-weight: 500;
            padding: 0.5rem 1rem;
            border-radius: 0.5rem;
            transition: all 0.3s;
        }

        .nav-link:hover, .nav-link.active {
            background-color: rgba(37, 99, 235, 0.15);
            color: var(--primary);
        }

        .nav-link i {
            margin-right: 0.5rem;
        }

        .delete-container {
            margin-top: 3rem;
        }

        .card {
            background-color: var(--card-bg);
            border-radius: 1rem;
            border: 1px solid var(--border-color);
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
            overflow: hidden;
        }

        .card-header {
            background-color: rgba(239, 68, 68, 0.15);
            border-bottom: 1px solid var(--border-color);
            color: var(--danger);
            font-weight: 600;
            padding: 1.2rem 1.5rem;
        }

        .card-header i {
            margin-right: 0.75rem;
        }

        .card-body {
            padding: 2rem;
        }

        .warning-icon {
            font-size: 3rem;
            color: var(--danger);
            margin-bottom: 1.5rem;
        }

        .warning-title {
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 1rem;
            color: var(--danger);
        }

        .warning-text {
            margin-bottom: 1.5rem;
            color: var(--gray-text);
        }

        .consequences {
            background-color: rgba(239, 68, 68, 0.05);
            border-radius: 0.75rem;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            border-left: 4px solid var(--danger);
        }

        .consequences-title {
            font-weight: 600;
            margin-bottom: 0.75rem;
            color: var(--light-text);
        }

        .consequences ul {
            color: var(--gray-text);
            margin-bottom: 0;
        }

        .consequences li {
            margin-bottom: 0.5rem;
        }

        .form-label {
            font-weight: 500;
            color: var(--light-text);
            margin-bottom: 0.5rem;
        }

        .form-control {
            background-color: var(--input-bg);
            border: 1px solid var(--border-color);
            color: var(--light-text);
            border-radius: 0.5rem;
            padding: 0.75rem 1rem;
        }

        .form-control:focus {
            background-color: var(--input-bg);
            border-color: var(--danger);
            box-shadow: 0 0 0 0.25rem rgba(239, 68, 68, 0.25);
            color: var(--light-text);
        }

        .form-check-input {
            background-color: var(--input-bg);
            border: 1px solid var(--border-color);
        }

        .form-check-input:checked {
            background-color: var(--danger);
            border-color: var(--danger);
        }

        .form-check-label {
            color: var(--light-text);
        }

        .btn-confirm {
            background-color: var(--danger);
            border: none;
            color: white;
            font-weight: 600;
            border-radius: 0.5rem;
            padding: 0.75rem 1.5rem;
            transition: all 0.3s;
        }

        .btn-confirm:hover {
            background-color: #dc2626;
            transform: translateY(-2px);
            box-shadow: 0 10px 15px rgba(239, 68, 68, 0.35);
        }

        .btn-cancel {
            background-color: transparent;
            border: 1px solid var(--border-color);
            color: var(--light-text);
            font-weight: 500;
            border-radius: 0.5rem;
            padding: 0.75rem 1.5rem;
            transition: all 0.3s;
        }

        .btn-cancel:hover {
            background-color: rgba(255, 255, 255, 0.05);
            color: var(--light-text);
        }

        .alert {
            border-radius: 0.5rem;
            padding: 1rem;
            margin-bottom: 1.5rem;
        }

        .alert-danger {
            background-color: rgba(239, 68, 68, 0.15);
            color: #f87171;
            border-left: 4px solid var(--danger);
            border-top: none;
            border-right: none;
            border-bottom: none;
        }
    </style>
</head>
<body>
    <%
        // Get user from session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
    %>

    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark sticky-top">
        <div class="container">
            <a class="navbar-brand" href="<%= request.getContextPath() %>/">Horizon</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/">
                            <i class="bi bi-house-fill"></i> Home
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/search-movie">
                            <i class="bi bi-film"></i> Movies
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/rental-history">
                            <i class="bi bi-collection-play"></i> My Rentals
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/view-watchlist">
                            <i class="bi bi-bookmark-star"></i> Watchlist
                        </a>
                    </li>
                </ul>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="<%= request.getContextPath() %>/user/profile.jsp">
                            <i class="bi bi-person-circle"></i> <%= user.getUsername() %>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/logout">
                            <i class="bi bi-box-arrow-right"></i> Logout
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container delete-container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <i class="bi bi-exclamation-triangle-fill"></i> Delete Account
                    </div>
                    <div class="card-body text-center">
                        <% if(request.getAttribute("errorMessage") != null) { %>
                            <div class="alert alert-danger mb-4">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                <%= request.getAttribute("errorMessage") %>
                            </div>
                        <% } %>

                        <i class="bi bi-exclamation-octagon-fill warning-icon"></i>
                        <h3 class="warning-title">Are you sure you want to delete your account?</h3>
                        <p class="warning-text">
                            This action cannot be undone. Once you delete your account, all your data will be
                            permanently removed from our system.
                        </p>

                        <div class="consequences text-start">
                            <h5 class="consequences-title">By deleting your account, you will lose:</h5>
                            <ul>
                                <li>All your rental history and associated records</li>
                                <li>Your watchlist and saved movies</li>
                                <li>Your reviews and ratings</li>
                                <li>Your personal recommendations</li>
                                <li>Any premium subscription benefits (if applicable)</li>
                            </ul>
                        </div>

                        <form action="<%= request.getContextPath() %>/delete-account" method="post">
                            <div class="mb-4">
                                <label for="deleteConfirmation" class="form-label text-start d-block">
                                    Please type "DELETE" to confirm:
                                </label>
                                <input type="text" id="deleteConfirmation" class="form-control"
                                       placeholder="Type DELETE" required>
                            </div>

                            <div class="mb-4 form-check text-start">
                                <input class="form-check-input" type="checkbox" id="deleteConfirm"
                                       name="confirmDelete" value="yes" required>
                                <label class="form-check-label" for="deleteConfirm">
                                    I understand that this action is permanent and cannot be undone
                                </label>
                            </div>

                            <div class="d-flex justify-content-center mt-4 gap-3">
                                <a href="<%= request.getContextPath() %>/user/profile.jsp" class="btn btn-cancel">
                                    <i class="bi bi-arrow-left me-2"></i> Cancel
                                </a>
                                <button type="submit" class="btn btn-confirm" id="deleteAccountBtn" disabled>
                                    <i class="bi bi-trash me-2"></i> Delete My Account
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Add validation for the DELETE confirmation
        const deleteConfirmationInput = document.getElementById('deleteConfirmation');
        const deleteAccountBtn = document.getElementById('deleteAccountBtn');

        deleteConfirmationInput.addEventListener('input', function() {
            if (this.value === 'DELETE') {
                deleteAccountBtn.disabled = false;
            } else {
                deleteAccountBtn.disabled = true;
            }
        });
    </script>
</body>
</html>