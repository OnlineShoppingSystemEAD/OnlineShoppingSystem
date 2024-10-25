import React, { useState } from "react";

const AuthForm = () => {
  const [isSignUp, setIsSignUp] = useState(true); // To toggle between Sign Up and Sign In
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [confirmPasswordVisible, setConfirmPasswordVisible] = useState(false);

  const togglePasswordVisibility = () => {
    setPasswordVisible(!passwordVisible);
  };

  const toggleConfirmPasswordVisibility = () => {
    setConfirmPasswordVisible(!confirmPasswordVisible);
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gray-100 ">
      <div className="absolute flex items-center mb-96">
        <h1 className="text-6xl font-bold font-montserrat">Welcome Back</h1>
      </div>

      {/* Navigation Buttons */}
      <div className="absolute flex space-x-0 mb-60 ">
        <button
          onClick={() => setIsSignUp(false)}
          className={`px-4 py-2 text-lg ${
            !isSignUp
              ? "bg-gray-500 text-white rounded-2xl shadow-2xl"
              : "bg-gray-300 text-black rounded-2xl shadow-2xl"
          }`}
        >
          Sign In
        </button>
        <button
          onClick={() => setIsSignUp(true)}
          className={`px-4 py-2 text-lg ${
            isSignUp
              ? "bg-gray-500 text-white rounded-3xl  drop-shadow-2xl"
              : "bg-gray-300 text-black rounded-3xl shadow-2xl"
          }`}
        >
          Sign Up
        </button>
      </div>

      {/* Conditionally render Sign Up or Sign In */}
      <form className="flex flex-col mt-14">
        {!isSignUp && (
          <div className="relative mb-6">
            <input
              type="email"
              placeholder="Email"
              className="p-2 text-base border border-gray-300 rounded-2xl w-80"
            />
          </div>
        )}
        <div className="relative mb-4">
          <input
            type={passwordVisible ? "text" : "password"}
            placeholder="Password"
            className="p-2 text-base border border-gray-300 rounded-2xl w-80"
          />
          <span
            className="absolute transform -translate-y-1/2 cursor-pointer right-2 top-1/2"
            onClick={togglePasswordVisibility}
          >
            {passwordVisible ? "🙈" : "👁️"}
          </span>
        </div>
        {isSignUp && (
          <>
            <div className="relative mb-4">
              <input
                type="text"
                placeholder="Username"
                className="p-2 text-base border border-gray-300 rounded-2xl w-80"
              />
            </div>
            <div className="relative mb-4">
              <input
                type={confirmPasswordVisible ? "text" : "password"}
                placeholder="Confirm Password"
                className="p-2 text-base border border-gray-300 rounded-2xl w-80"
              />
              <span
                className="absolute transform -translate-y-1/2 cursor-pointer right-2 top-1/2"
                onClick={toggleConfirmPasswordVisibility}
              >
                {confirmPasswordVisible ? "🙈" : "👁️"}
              </span>
            </div>
          </>
        )}
        <button
          type="submit"
          className="p-2 mt-2 text-white bg-black rounded-full w-80"
        >
          {isSignUp ? "Sign Up" : "Sign In"}
        </button>
      </form>
    </div>
  );
};

export default AuthForm;
