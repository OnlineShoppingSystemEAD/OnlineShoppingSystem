import React from "react";
import { Link } from "react-router-dom";

const CartSidebar = ({ isOpen, onClose }) => {
  return (
    <div
      className={`fixed top-0 right-0 h-full w-96 bg-white shadow-lg transform ${
        isOpen ? "translate-x-0" : "translate-x-full"
      } transition-transform duration-300 ease-in-out z-50`}
    >
      <div className="p-6">
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-2xl font-bold">Your Cart</h2>
          <button onClick={onClose} className="text-2xl text-gray-700">
            &times;
          </button>
        </div>
        <div className="space-y-6 overflow-y-auto max-h-[70vh] pr-4">
          {/* Cart Item 1 */}
          <div className="flex items-center space-x-4">
            <img
              src="https://themewagon.github.io/cozastore/images/item-cart-01.jpg"
              alt="White Shirt Pleat"
              className="object-cover w-20 h-20 rounded"
            />
            <div className="flex-1">
              <p className="text-lg font-semibold">White Shirt Pleat</p>
              <p className="text-gray-500">1 x $19.00</p>
            </div>
            <p className="text-lg font-semibold">$19.00</p>
          </div>
          {/* Cart Item 2 */}
          <div className="flex items-center space-x-4">
            <img
              src="https://themewagon.github.io/cozastore/images/item-cart-02.jpg"
              alt="Converse All Star"
              className="object-cover w-20 h-20 rounded"
            />
            <div className="flex-1">
              <p className="text-lg font-semibold">Converse All Star</p>
              <p className="text-gray-500">1 x $39.00</p>
            </div>
            <p className="text-lg font-semibold">$39.00</p>
          </div>
          {/* Cart Item 3 */}
          <div className="flex items-center space-x-4">
            <img
              src="https://themewagon.github.io/cozastore/images/item-cart-03.jpg"
              alt="Nixon Porter Leather"
              className="object-cover w-20 h-20 rounded"
            />
            <div className="flex-1">
              <p className="text-lg font-semibold">Nixon Porter Leather</p>
              <p className="text-gray-500">1 x $17.00</p>
            </div>
            <p className="text-lg font-semibold">$17.00</p>
          </div>
        </div>
        <div className="pt-6 mt-6 border-t">
          <div className="flex justify-between mb-6">
            <span className="text-lg font-bold">Total:</span>
            <span className="text-lg font-bold">$75.00</span>
          </div>
          <div className="flex space-x-4">
            <div className="w-full py-3 text-center text-white bg-purple-600 rounded-lg hover:bg-dark transition">
              <Link to="Cart">
              <button>VIEW CART</button>
              </Link>
            </div>
            <div className="w-full py-3 text-center text-white bg-purple-600 rounded-lg hover:bg-dark transition">
              <Link to="Checkout">
                <button>CHECK OUT</button>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CartSidebar;
