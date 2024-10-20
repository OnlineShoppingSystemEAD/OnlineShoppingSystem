import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faShoppingCart, faSearch, faHeart } from '@fortawesome/free-solid-svg-icons';
import CartSidebar from './CartSidebar';

const Header = () => {
    const [isCartOpen, setCartOpen] = useState(false);

    const openCart = () => setCartOpen(true);
    const closeCart = () => setCartOpen(false);

    return (
        <header className="bg-white shadow-md py-4">
            <div className="container mx-auto flex justify-between items-center">
                <div className="text-xl font-bold">COZA STORE</div>
                <nav className="space-x-6">
                    <Link to="/" className="text-gray-700 hover:text-purple-500">Home</Link>
                    <Link to="/shop" className="text-gray-700 hover:text-purple-500">Shop</Link>
                    <Link to="/cart" className="text-gray-700 hover:text-purple-500">Cart</Link>
                    <Link to="/about" className="text-purple-500 font-bold">About</Link>
                    <Link to="/contact" className="text-gray-700 hover:text-purple-500">Contact</Link>
                </nav>
                <div className="flex items-center space-x-4">
                    <span className="relative">
                        <FontAwesomeIcon icon={faSearch} className="text-gray-700 hover:text-purple-500" />
                    </span>
                    <span className="relative" onClick={openCart}>
                        <FontAwesomeIcon icon={faShoppingCart} className="text-gray-700 hover:text-purple-500" />
                        <span className="absolute -top-1 -right-1 bg-purple-500 text-white text-xs rounded-full w-4 h-4 text-center">2</span>
                    </span>
                    <span>
                        <FontAwesomeIcon icon={faHeart} className="text-gray-700 hover:text-purple-500" />
                    </span>
                </div>
            </div>
            {/* Cart Sidebar */}
            <CartSidebar isOpen={isCartOpen} onClose={closeCart} />
        </header>
    );
};

export default Header;
