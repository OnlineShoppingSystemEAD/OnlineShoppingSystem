// Login.js
import React, { useState } from 'react';
import '../styles/login.css';
import {useNavigate} from "react-router-dom"; // Create a CSS file for custom styles

const Login = () => {
    const [passwordVisible, setPasswordVisible] = useState(false);
    const navigate = useNavigate();

    const togglePasswordVisibility = () => {
        setPasswordVisible(!passwordVisible);
    };
    const handleSignUpClick = () => {
        navigate('/signup'); // Redirect to /signup
    };
    return (
        <div className="login-container">
            <div className="inline">
                <h1 className="logo1">COZA</h1>
                <h1 className="logo2">STORE</h1>
            </div>
            <h2 className="admin-heading">Administration</h2>
            <form className="login-form">
                <div className="input-group">
                    <input type="text" placeholder="Username / Email" className="input-field"/>
                </div>
                <div className="input-group">
                    <input
                        type={passwordVisible ? 'text' : 'password'}
                        placeholder="Password"
                        className="input-field"
                    />
                    <span className="toggle-password" onClick={togglePasswordVisibility}>
            {passwordVisible ? '🙈' : '👁️'}
          </span>
                </div>
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
