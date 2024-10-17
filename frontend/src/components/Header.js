import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShoppingCart, faSearch, faUser } from '@fortawesome/free-solid-svg-icons';
import CartSidebar from './CartSidebar';

const Header = () => {
    const [isCartOpen, setCartOpen] = useState(false);
    const [isSearchOpen, setSearchOpen] = useState(false);
    const location = useLocation();

    const openCart = () => setCartOpen(true);
    const closeCart = () => setCartOpen(false);
    const toggleSearch = () => setSearchOpen(!isSearchOpen);
    const isActive = (path) => location.pathname === path;

    return (
        <header className="bg-white shadow-md py-4 sticky top-0 z-50">
            <div className="container mx-auto flex justify-between items-center relative">
                <div className="text-xl font-bold">SHOPZEN</div>
                <nav className="space-x-6">
                    <Link to="/" className={`text-gray-700 hover:text-purple-500 ${isActive('/') ? 'font-bold text-purple-500' : ''}`}>
                        Home
                    </Link>
                    <Link to="/shop" className={`text-gray-700 hover:text-purple-500 ${isActive('/shop') ? 'font-bold text-purple-500' : ''}`}>
                        Shop
                    </Link>
                    <Link to="/cart" className={`text-gray-700 hover:text-purple-500 ${isActive('/cart') ? 'font-bold text-purple-500' : ''}`}>
                        Cart
                    </Link>
                    <Link to="/about" className={`text-gray-700 hover:text-purple-500 ${isActive('/about') ? 'font-bold text-purple-500' : ''}`}>
                        About
                    </Link>
                    <Link to="/contact" className={`text-gray-700 hover:text-purple-500 ${isActive('/contact') ? 'font-bold text-purple-500' : ''}`}>
                        Contact
                    </Link>
                </nav>
                <div className="flex items-center space-x-4 relative">
                    {/* Search Icon and Input */}
                    <div className="relative flex items-center">
                        <FontAwesomeIcon
                            icon={faSearch}
                            className="text-gray-700 hover:text-purple-500 cursor-pointer"
                            onClick={toggleSearch}
                        />
                        {isSearchOpen && (
                            <input
                                type="text"
                                placeholder="Search products..."
                                className="border border-gray-300 p-2 w-64 rounded-md focus:outline-none focus:border-purple-500 absolute left-50 transform -translate-x-80 top-1/2 -translate-y-1/2"
                            />
                        )}
                    </div>

                    {/* Cart Icon */}
                    <span className="relative" onClick={openCart}>
                        <FontAwesomeIcon icon={faShoppingCart} className="text-gray-700 hover:text-purple-500" />
                        <span className="absolute -top-1 -right-1 bg-purple-500 text-white text-xs rounded-full w-4 h-4 text-center">3</span>
                    </span>

                    {/* Profile Icon */}
                    <span className="cursor-pointer">
                        <FontAwesomeIcon icon={faUser} className="text-gray-700 hover:text-purple-500" />
                    </span>
                </div>
            </div>

            {/* Cart Sidebar */}
            <CartSidebar isOpen={isCartOpen} onClose={closeCart} />
        </header>
    );
};

export default Header;
