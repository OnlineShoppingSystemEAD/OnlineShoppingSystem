import React from 'react';
import { Link } from 'react-router-dom'; 

const Header = () => {
    return (
        <header className="bg-white shadow-md py-4">
            <div className="container mx-auto flex justify-between items-center">
                <div className="text-xl font-bold">COZA STORE</div>
                <nav className="space-x-6">
                    <Link to="/" className="text-gray-700 hover:text-purple-500">Home</Link>
                    <Link to="/shop" className="text-gray-700 hover:text-purple-500">Shop</Link>
                    <Link to="/features" className="text-gray-700 hover:text-purple-500">Features</Link>
                    <Link to="/about" className="text-purple-500 font-bold">About</Link>
                    <Link to="/contact" className="text-gray-700 hover:text-purple-500">Contact</Link>
                </nav>
                <div className="flex items-center space-x-4">
                    <span className="relative">
                        <i className="icon-search"></i>
                    </span>
                    <span className="relative">
                        <i className="icon-cart"></i>
                        <span className="absolute -top-1 -right-1 bg-purple-500 text-white text-xs rounded-full w-4 h-4 text-center">2</span>
                    </span>
                    <span>
                        <i className="icon-heart"></i>
                    </span>
                </div>
            </div>
        </header>
    );
}

export default Header;

