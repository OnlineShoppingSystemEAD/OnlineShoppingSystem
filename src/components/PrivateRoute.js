import React, { useState, useEffect } from "react";
import { Navigate } from "react-router-dom";
import userService from "../api/services/UserService";

const PrivateRoute = ({ children, allowedRoles, redirectIfAuthenticated = false }) => {
    const [isVerified, setIsVerified] = useState(false);
    const [isLoading, setIsLoading] = useState(true);
    const [userRole, setUserRole] = useState(null);

    useEffect(() => {
        const verifyToken = async () => {
            try {
                // Verify the token using the userService
                const response = await userService.verifyToken();
                setUserRole(response.role); // Extract role from verification response
                setIsVerified(true); // Set verification status to true if token is valid
            } catch (error) {
                console.error("Token verification failed:", error);
                setIsVerified(false); // Set verification status to false if token is invalid
            } finally {
                setIsLoading(false); // Stop loading once verification is done
            }
        };

        verifyToken();
    }, []);

    // Show a YouTube video as the loading screen while verifying the token
    if (isLoading) {
        return (
            <div style={{ position: "relative", width: "100%", height: "100vh", textAlign: "center" }}>
                <p style={{ marginTop: "20px", fontSize: "18px", color: "#555" }}>Verifying your session...</p>
            </div>
        );
    }

    // If the user is logged in and redirectIfAuthenticated is true, redirect based on role
    if (redirectIfAuthenticated && isVerified) {
        return userRole === "ADMIN" ? (
            <Navigate to="/orders" replace /> // Redirect admin to admin dashboard
        ) : (
            <Navigate to="/" replace /> // Redirect regular user to homepage
        );
    }

    // If the user is not verified (invalid token), redirect to login
    if (!isVerified) {
        return <Navigate to="/login" replace />;
    }

    // Check if the user's role is allowed to access this route
    if (allowedRoles && !allowedRoles.includes(userRole)) {
        // Redirect based on role mismatch
        return userRole === "ADMIN" ? (
            <Navigate to="/orders" replace /> // Redirect admin to admin dashboard
        ) : (
            <Navigate to="/" replace /> // Redirect regular user to homepage
        );
    }

    // Allow access to the route
    return children;
};

export default PrivateRoute;
