import React from "react";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import CartSection from "../../components/CartSection";

const Cart = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <Header />
      <main className="flex-grow">
        <CartSection />
      </main>
      <Footer />
    </div>
  );
};

export default Cart;
