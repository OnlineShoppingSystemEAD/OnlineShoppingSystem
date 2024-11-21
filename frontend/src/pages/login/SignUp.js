import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import userService from "../../api/services/UserService";
import "../../styles/login.css";

const Signup = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [confirmPasswordVisible, setConfirmPasswordVisible] = useState(false);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // Validation helpers
  const isValidEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  const isValidUsername = (username) =>
      username.length >= 3 && /^[a-zA-Z\s]+$/.test(username); // Allow letters and spaces
  const isStrongPassword = (password) =>
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/.test(password);

  const togglePasswordVisibility = () => setPasswordVisible(!passwordVisible);
  const toggleConfirmPasswordVisibility = () =>
      setConfirmPasswordVisible(!confirmPasswordVisible);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!isValidEmail(email)) {
      setError("Please enter a valid email address.");
      return;
    }

    if (!isValidUsername(username)) {
      setError(
          "Username must be at least 3 characters long and contain only letters and spaces."
      );
      return;
    }

    if (!isStrongPassword(password)) {
      setError(
          "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character."
      );
      return;
    }

    if (password !== confirmPassword) {
      setError("Passwords do not match.");
      return;
    }

    try {
      localStorage.setItem(
          "signupForm",
          JSON.stringify({ username, email, password, confirmPassword })
      );
      // Complete signup with the stored data
      await userService.signUp({
        username: username,
        email: email,
        password: password,
      });
      console.log("Email verification sent.");
      navigate("/emailVerification");
    } catch (err) {
      console.error("Sign Up or Email Verification failed:", err);
      setError(err.message || "Sign Up failed. Please try again.");
    }
  };

  return (
      <div className="flex flex-col items-center justify-center h-screen bg-gray-100">
        <h1 className="absolute top-16 text-6xl font-bold font-montserrat">
          COZA STORE
        </h1>

        <form className="flex flex-col mt-20" onSubmit={handleSubmit}>
          <div className="relative mb-4">
            <input
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="p-2 text-base border border-gray-300 rounded-2xl w-80"
                required
            />
          </div>

          <div className="relative mb-4">
            <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="p-2 text-base border border-gray-300 rounded-2xl w-80"
                required
            />
          </div>

          <div className="relative mb-4">
            <input
                type={passwordVisible ? "text" : "password"}
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="p-2 text-base border border-gray-300 rounded-2xl w-80"
                required
            />
            <span
                className="absolute transform -translate-y-1/2 cursor-pointer right-2 top-1/2"
                onClick={togglePasswordVisibility}
            >
            {passwordVisible ? "🙈" : "👁️"}
          </span>
          </div>

          <div className="relative mb-4">
            <input
                type={confirmPasswordVisible ? "text" : "password"}
                placeholder="Confirm Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                className="p-2 text-base border border-gray-300 rounded-2xl w-80"
                required
            />
            <span
                className="absolute transform -translate-y-1/2 cursor-pointer right-2 top-1/2"
                onClick={toggleConfirmPasswordVisibility}
            >
            {confirmPasswordVisible ? "🙈" : "👁️"}
          </span>
          </div>

          {error && <div className="text-red-500 mb-4">{error}</div>}

          <button type="submit" className="p-2 mt-4 text-white bg-black rounded-full w-80">
            Sign Up
          </button>

          <button
              type="button"
              onClick={() => navigate("/login")}
              className="mt-2 text-sm text-blue-500 hover:underline"
          >
            Already have an account? Sign In
          </button>
        </form>
      </div>
  );
};

export default Signup;
