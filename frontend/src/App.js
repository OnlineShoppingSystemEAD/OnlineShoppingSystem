import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import About from './pages/About';
import Home from './pages/Home';
import Shop from './pages/Shop';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './index.css';
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import Orders from "./components/Orders";
import Categories from "./components/Categories";
import Order from "./components/Order";
import Category from "./components/Category";
import Account from './pages/Account';

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
            </Routes>
        </Router>
    );
}

export default App;
