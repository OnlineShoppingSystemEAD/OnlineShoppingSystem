import React, { useState } from "react";
import Header from "../../components/user/Header";

const Checkout = () => {
  const [paymentMethod, setPaymentMethod] = useState("");

  const handlePaymentChange = (e) => {
    setPaymentMethod(e.target.value);
  };

  return (
    <div className="min-h-screen">
      {/* Import and display Navbar */}
      <Header />

      <div className="pl-[8cm] pr-[8cm] pt-10">
        {/* Top Section: Checkout heading on the left and buttons on the right */}
        <div className="flex items-center justify-between mb-6">
          <h1 className="text-3xl font-semibold">Checkout</h1>
          <div className="mr-2 space-x-8">
            <button className="w-[3cm] p-2 text-xl bg-black text-white hover:text-white rounded-xl transform transition-transform hover:scale-105 hover:bg-gray-700">
              Back
            </button>
            <button className="w-[3cm] p-2 text-xl text-white bg-blue-700 rounded-xl transform transition-transform hover:scale-105 hover:bg-blue-600">
              Pay
            </button>
          </div>
        </div>

        {/* Form Section */}
        <form className="space-y-8">
          {/* Personal Details Section */}
          <div>
            <h2 className="mb-4 text-xl font-semibold text-gray-500">
              Personal Details
            </h2>
            <div className="grid grid-cols-1 gap-4">
              <div className="grid grid-cols-2 gap-20">
                <input
                  type="text"
                  placeholder="First Name"
                  className="p-2 bg-gray-100 border border-gray-600 rounded-2xl"
                />
                <input
                  type="text"
                  placeholder="Last Name"
                  className="p-2 bg-gray-100 border border-gray-600 rounded-2xl"
                />
              </div>
              <div className="grid grid-cols-2 gap-20 mt-5">
                <input
                  type="email"
                  placeholder="Email Address"
                  className="p-2 bg-gray-100 border border-gray-600 rounded-2xl"
                />
                <div></div>
              </div>
            </div>
          </div>

          {/* Billing Address Section */}
          <div>
            <h2 className="mb-4 text-xl font-semibold">Billing Address</h2>
            <div className="grid grid-cols-1 gap-4">
              <div className="grid grid-cols-2 gap-20">
                <input
                  type="text"
                  placeholder="Street Address"
                  className="p-2 bg-gray-100 border border-gray-600 rounded-2xl"
                />
                <input
                  type="text"
                  placeholder="City"
                  className="p-2 bg-gray-100 border border-gray-600 rounded-2xl"
                />
              </div>

              <div className="grid grid-cols-2 gap-20 mt-4">
                <input
                  type="text"
                  placeholder="State"
                  className="p-2 bg-gray-100 border border-gray-600 rounded-2xl"
                />
                <input
                  type="text"
                  placeholder="Zip Code"
                  className="p-2 bg-gray-100 border border-gray-600 rounded-2xl"
                />
              </div>
            </div>
          </div>

          {/* Payment Method Section */}
          <div className="flex flex-col items-center justify-between md:flex-row">
            <div className="w-full md:w-1/2">
              <h2 className="mb-4 text-xl font-semibold ">Payment Method</h2>
              <select
                className="w-[10cm] border border-gray-300 rounded-2xl"
                value={paymentMethod}
                onChange={handlePaymentChange}
              >
                <option value="">Select Payment Method</option>
                <option value="credit">Credit Card</option>
                <option value="paypal">PayPal</option>
                <option value="bank">Bank Transfer</option>
              </select>
            </div>
            <div className="mt-7">
              <button className="p-3 mt-0 mb-0 mr-2 text-xl text-white bg-black rounded-2xl">
                + Add Payment Method
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Checkout;
