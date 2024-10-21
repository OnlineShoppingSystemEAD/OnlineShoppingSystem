import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import About from './pages/user/About';
import Home from './pages/user/Home';
import Shop from './pages/user/Shop';
import Account from './pages/user/Account';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './index.css';
import Login from "./pages/login/Login";
import SignUp from "./pages/login/SignUp";
import Orders from "./pages/admin/Orders";
import Categories from "./pages/admin/Categories";
import Order from "./pages/admin/Order";
import Category from "./pages/admin/Category";
import Contact from "./pages/user/Contact";
import Cart from "./pages/user/Cart";


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/about" element={<About />} />
                <Route path="/shop" element={<Shop />} />
                <Route path="/login" element={<Login />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/orders" element={<Orders />} />
                <Route path="/categories" element={<Categories />} />
                <Route path="/order/:id" element={<Order />} />
                <Route path="/category/:id" element={<Category />} />
                <Route path="/account" element={<Account/>} />
                <Route path="/cart" element={<Cart/>} />
                <Route path="/contact" element={<Contact/>} />
            </Routes>
        </Router>
    );
}

export default App;
