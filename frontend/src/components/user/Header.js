import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faShoppingCart,
  faSearch,
  faUser,
  faRightFromBracket,
} from "@fortawesome/free-solid-svg-icons";
import CartSidebar from "../user/Cart/CartSidebar";
import userService from "../../api/services/UserService"; // Import userService

const Header = () => {
  const [isCartOpen, setCartOpen] = useState(false);
  const [isSearchOpen, setSearchOpen] = useState(false);
  const location = useLocation();

  const openCart = () => setCartOpen(true);
  const closeCart = () => setCartOpen(false);
  const toggleSearch = () => setSearchOpen(!isSearchOpen);
  const isActive = (path) => location.pathname === path;

  const isLoggedIn = userService.isTokenValid(); // Check if the user is logged in

  const handleLogout = () => {
    userService.logout(); // Clear tokens from localStorage
    window.location.reload(); // Reload the page to update UI
  };

  return (
    <header className="sticky top-0 z-50 py-4 bg-white shadow-md">
      <div className="container relative flex items-center justify-between mx-auto">
        <div className="text-xl font-bold">SHOPZEN</div>

        {/* Navigation Links */}
        <nav className="space-x-6">
          <Link
            to="/"
            className={`text-gray-700 hover:text-purple-500 ${
              isActive("/") ? "font-bold text-purple-500" : ""
            }`}
          >
            Home
          </Link>
          <Link
            to="/shop"
            className={`text-gray-700 hover:text-purple-500 ${
              isActive("/shop") ? "font-bold text-purple-500" : ""
            }`}
          >
            Shop
          </Link>
          <Link
            to="/cart"
            className={`text-gray-700 hover:text-purple-500 ${
              isActive("/cart") ? "font-bold text-purple-500" : ""
            }`}
          >
            Cart
          </Link>
          <Link
            to="/about"
            className={`text-gray-700 hover:text-purple-500 ${
              isActive("/about") ? "font-bold text-purple-500" : ""
            }`}
          >
            About
          </Link>
          <Link
            to="/contact"
            className={`text-gray-700 hover:text-purple-500 ${
              isActive("/contact") ? "font-bold text-purple-500" : ""
            }`}
          >
            Contact
          </Link>
          {/* Sign In link */}
          {!isLoggedIn && (
            <Link
              to="/signUp"
              className="text-gray-700 hover:text-purple-500"
            >
              Sign In
            </Link>
          )}
        </nav>

        {/* Right-side Icons */}
        <div className="relative flex items-center space-x-4">
          {/* Search Icon and Input */}
          <div className="relative flex items-center">
            <FontAwesomeIcon
              icon={faSearch}
              className={`cursor-pointer ${
                isSearchOpen ? "text-purple-500" : "text-gray-700"
              } hover:text-purple-500`}
              onClick={toggleSearch}
            />
            {isSearchOpen && (
              <input
                type="text"
                placeholder="Search products..."
                className="absolute w-64 p-2 transform -translate-y-1/2 border border-gray-300 rounded-md focus:outline-none focus:border-purple-500 left-50 -translate-x-80 top-1/2"
              />
            )}
          </div>

          {/* Cart Icon */}
          <span className="relative" onClick={openCart}>
            <FontAwesomeIcon
              icon={faShoppingCart}
              className="text-gray-700 cursor-pointer hover:text-purple-500"
            />
            <span className="absolute w-4 h-4 text-xs text-center text-white bg-purple-500 rounded-full -top-1 -right-1">
              3
            </span>
          </span>

          {/* Profile or Logout */}
          {isLoggedIn ? (
            <FontAwesomeIcon
              icon={faRightFromBracket}
              className="text-gray-700 cursor-pointer hover:text-purple-500"
              onClick={handleLogout}
            />
          ) : (
            <Link to="/account">
              <FontAwesomeIcon
                icon={faUser}
                className={`cursor-pointer ${
                  isActive("/account") ? "text-purple-500" : "text-gray-700"
                } hover:text-purple-500`}
              />
            </Link>
          )}
        </div>
      </div>

      {/* Cart Sidebar */}
      <CartSidebar isOpen={isCartOpen} onClose={closeCart} />
    </header>
  );
};

export default Header;
