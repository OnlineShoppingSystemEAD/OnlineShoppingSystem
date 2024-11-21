import React, { useState } from "react";
import "../../styles/login.css";
import { useNavigate } from "react-router-dom";
import userService from "../../api/services/UserService"; // Import the userService for API calls
import {jwtDecode} from "jwt-decode"; // Import jwtDecode to decode the token

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordVisible, setPasswordVisible] = useState(false);
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const togglePasswordVisibility = () => {
        setPasswordVisible(!passwordVisible);
    };

    const handleSignUpClick = () => {
        navigate("/signup"); // Redirect to /signup
    };

    const handleLoginSubmit = async (e) => {
        e.preventDefault(); // Prevent default form submission behavior
        setError(""); // Reset error message

        try {
            // Call the signIn function from userService
            const response = await userService.signIn({ email, password });
            console.log("Login successful:", response);

            // Decode the token to get the user's role
            const decodedToken = jwtDecode(response.token);
            console.log("Decoded Token:", decodedToken);
            localStorage.setItem("token", response.token);
            localStorage.setItem("decodedToken", JSON.stringify(decodedToken));

            // Navigate based on the user's role
            if (decodedToken.role === "ADMIN") {
                navigate("/orders"); // Navigate to orders page for admin
            } else if (decodedToken.role === "USER") {
                navigate("/"); // Navigate to home page for user
            } else {
                console.error("Unknown user role:", decodedToken.role);
                setError("Invalid user role. Please contact support.");
            }
        } catch (err) {
            console.error("Login failed:", err);
            // Display error message to the user
            setError(err.message || "Login failed. Please try again.");
        }
    };

    return (
        <div className="login-container">
            <div className="inline">
                <h1 className="logo1">COZA</h1>
                <h1 className="logo2">STORE</h1>
            </div>
            <h2 className="admin-heading">Welcome Back</h2>
            <form className="login-form" onSubmit={handleLoginSubmit}>
                <div className="input-group">
                    <input
                        type="text"
                        placeholder="Username / Email"
                        className="input-field"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)} // Update email state
                        required
                    />
                </div>
                <div className="input-group">
                    <input
                        type={passwordVisible ? "text" : "password"}
                        placeholder="Password"
                        className="input-field"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)} // Update password state
                        required
                    />
                    <span className="toggle-password" onClick={togglePasswordVisibility}>
                        {passwordVisible ? "🙈" : "👁️"}
                    </span>
                </div>
                {error && <div className="error-message">{error}</div>} {/* Display error message */}
                <div className="-col-end-13">
                    <button type="submit" className="login-button">Log In</button>
                    <button type="button" className="sign-up-button" onClick={handleSignUpClick}>
                        Sign Up
                    </button>
                </div>
                <div className="forgot-password">
                    <a href="/forgot-password">Forgot Password?</a>
                </div>
            </form>
        </div>
    );
};

export default Login;
