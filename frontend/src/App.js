import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import About from './pages/user/About';
import Home from './pages/user/Home';
import Shop from './pages/user/Shop';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './index.css';
import Login from "./pages/login/Login";
import SignUp from "./pages/login/SignUp";
import Orders from "./pages/admin/Orders";
import Categories from "./pages/admin/Categories";
import Order from "./pages/admin/Order";
import Category from "./pages/admin/Category";
import Account from "./pages/user/Account";
import Cart from "./pages/user/Cart";
import Contact from "./pages/user/Contact";
import Checkout from "./pages/user/Checkout";
import PrivateRoute from "./components/PrivateRoute";
import userService from "./api/services/UserService";
import NotFound from "./pages/NotFound";
import EmailVerification from "./pages/login/EmailVerification";
import ResetPassword from "./pages/login/ResetPassword";
import ForgotPassword from "./pages/login/ForgotPassword";



function App() {
    const isLoggedIn = userService.isTokenValid(); // Check if the user is logged in
    console.log("ss" + isLoggedIn);
  return (
      <Router>
          <Routes>
              {/* Public Routes with Redirection for Authenticated Users */}
              {/* Handle Login Route */}
              <Route
                  path="/login"
                  element={
                      isLoggedIn ? (
                          <PrivateRoute allowedRoles={null} redirectIfAuthenticated>
                              <Login />
                          </PrivateRoute>
                      ) : (
                          <Login />
                      )
                  }
              />

              {/* Handle SignUp Route */}
              <Route
                  path="/signup"
                  element={
                      isLoggedIn ? (
                          <PrivateRoute allowedRoles={null} redirectIfAuthenticated>
                              <SignUp />
                          </PrivateRoute>
                      ) : (
                          <SignUp />
                      )
                  }
              />
              <Route path="/about" element={<About />} />
              <Route path="/shop" element={<Shop />} />
              <Route path="/contact" element={<Contact />} />
              <Route path="/" element={<Home />} />
              <Route path="*" element={<NotFound />} /> {/* Catch-all route */}
              <Route path="/emailVerification" element={<EmailVerification />} />
              <Route path="/forgot-password" element={<ForgotPassword />} />
              <Route path="/reset-password" element={<ResetPassword />} />

              {/* Protected User Routes */}
              <Route
                  path="/account"
                  element={
                      <PrivateRoute allowedRoles={["USER"]}>
                          <Account />
                      </PrivateRoute>
                  }
              />
              <Route
                  path="/cart"
                  element={
                      <PrivateRoute allowedRoles={["USER"]}>
                          <Cart />
                      </PrivateRoute>
                  }
              />
              <Route
                  path="/checkout"
                  element={
                      <PrivateRoute allowedRoles={["USER"]}>
                          <Checkout />
                      </PrivateRoute>
                  }
              />

              {/* Protected Admin Routes */}
              <Route
                  path="/orders"
                  element={
                      <PrivateRoute allowedRoles={["ADMIN"]}>
                          <Orders />
                      </PrivateRoute>
                  }
              />
              <Route
                  path="/categories"
                  element={
                      <PrivateRoute allowedRoles={["ADMIN"]}>
                          <Categories />
                      </PrivateRoute>
                  }
              />
              <Route
                  path="/order/:id"
                  element={
                      <PrivateRoute allowedRoles={["ADMIN"]}>
                          <Order />
                      </PrivateRoute>
                  }
              />
              <Route
                  path="/category/:id"
                  element={
                      <PrivateRoute allowedRoles={["ADMIN"]}>
                          <Category />
                      </PrivateRoute>
                  }
              />
          </Routes>
      </Router>
  );
}

export default App;
