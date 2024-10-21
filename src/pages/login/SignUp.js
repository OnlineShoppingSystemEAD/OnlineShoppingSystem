// SignUp.js
import React, { useState } from 'react';
import '../../styles/signup.css'; // Create a CSS file for custom styles

const SignUp = () => {
    const [passwordVisible, setPasswordVisible] = useState(false);
    const [confirmPasswordVisible, setConfirmPasswordVisible] = useState(false);

    const togglePasswordVisibility = () => {
        setPasswordVisible(!passwordVisible);
    };

    const toggleConfirmPasswordVisibility = () => {
        setConfirmPasswordVisible(!confirmPasswordVisible);
    };

    return (
        <div className="signup-container">
            <div className="inline">
                <h1 className="logo1">COZA</h1>
                <h1 className="logo2">STORE</h1>
            </div>
            <h2 className="admin-heading">Sign Up</h2>
            <form className="signup-form">
                <div className="input-group">
                    <input type="text" placeholder="Username" className="input-field" />
                </div>
                <div className="input-group">
                    <input type="email" placeholder="Email" className="input-field" />
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
                <div className="input-group">
                    <input
                        type={confirmPasswordVisible ? 'text' : 'password'}
                        placeholder="Confirm Password"
                        className="input-field"
                    />
                    <span className="toggle-password" onClick={toggleConfirmPasswordVisibility}>
                        {confirmPasswordVisible ? '🙈' : '👁️'}
                    </span>
                </div>
                <button type="submit" className="signup-button">Sign Up</button>
                <div className="login-link">
                    <p>Already have an account? <a href="/src/pages/login/Login">Log In</a></p>
                </div>
            </form>
        </div>
    );
};

export default SignUp;
