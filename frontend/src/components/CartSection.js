import React, { useState } from "react";
import blue from "../assets/blue-shirt.png"; // Import your local image
import black from "../assets/black-shirt.png"; // Import John Doe's image if needed

const CartSection = () => {
  const [quantities, setQuantities] = useState({ john: 1, jane: 1 }); // State to hold quantities
  const [selectedCountry, setSelectedCountry] = useState("");
  const [zipcode, setZipcode] = useState(""); // State for the zip code
  const [state, setState] = useState(""); // State for the state input

  // List of countries
  const countries = [
    "United States",
    "Canada",
    "United Kingdom",
    "Australia",
    "India",
  ];

  // Function to increase quantity
  const increaseQuantity = (product) => {
    setQuantities((prev) => ({ ...prev, [product]: prev[product] + 1 }));
  };

  // Function to decrease quantity
  const decreaseQuantity = (product) => {
    setQuantities((prev) => ({
      ...prev,
      [product]: Math.max(prev[product] - 1, 0), // Prevent negative quantity
    }));
  };

  const handleCountryChange = (event) => {
    setSelectedCountry(event.target.value);
  };

  const handleZipcodeChange = (event) => {
    setZipcode(event.target.value);
  };

  const handleStateChange = (event) => {
    setState(event.target.value);
  };

  return (
    <form>
      <div>
        <div className="flex justify-between mt-14">
          <div className="m-10 border border-black w-[44%] ml-56 mb-[6.5cm]">
            <table className="w-full h-[10cm] border border-collapse border-gray-400">
              <thead>
                <tr>
                  <th className="p-2 border-b border-gray-400">PRODUCT</th>
                  <th className="p-2 border-b border-gray-400">PRICE</th>
                  <th className="p-2 border-b border-gray-400">QUANTITY</th>
                  <th className="p-2 border-b border-gray-400">TOTAL</th>
                </tr>
              </thead>
              <tbody>
                <tr className="text-center">
                  <td className="flex items-center justify-center p-2 border-b border-gray-400">
                    {/* Product Image for John */}
                    <div className="flex items-center justify-center w-16 h-16">
                      <img
                        src={blue} // Use the imported local image
                        alt="Product 1"
                        className="object-cover w-full h-full"
                      />
                    </div>
                    <span className="ml-2">John Doe</span>
                  </td>
                  <td className="p-2 border-b border-gray-400">28</td>
                  <td className="p-2 border-b border-gray-400">
                    <div className="flex items-center justify-center">
                      <button
                        onClick={(event) => {
                          event.preventDefault();
                          decreaseQuantity("john");
                        }}
                        className="px-2 border border-gray-400"
                      >
                        -
                      </button>
                      <span className="mx-2">{quantities.john}</span>
                      <button
                        onClick={(event) => {
                          event.preventDefault();
                          increaseQuantity("john");
                        }}
                        className="px-2 border border-gray-400"
                      >
                        +
                      </button>
                    </div>
                  </td>
                  <td className="p-2 border-b border-gray-400">
                    {28 * quantities.john}
                  </td>
                </tr>
                <tr className="text-center">
                  <td className="flex items-center justify-center p-2 border-b border-gray-400">
                    {/* Product Image for Jane */}
                    <div className="flex items-center justify-center w-16 h-16">
                      <img
                        src={black}
                        alt="Product 2"
                        className="object-cover w-full h-full"
                      />
                    </div>
                    <span className="ml-2">Jane Doe</span>
                  </td>
                  <td className="p-2 border-b border-gray-400">32</td>
                  <td className="p-2 border-b border-gray-400">
                    <div className="flex items-center justify-center">
                      <button
                        onClick={(event) => {
                          event.preventDefault();
                          decreaseQuantity("jane");
                        }}
                        className="px-2 border border-gray-400"
                      >
                        -
                      </button>
                      <span className="mx-2">{quantities.jane}</span>
                      <button
                        onClick={(event) => {
                          event.preventDefault();
                          increaseQuantity("jane");
                        }}
                        className="px-2 border border-gray-400"
                      >
                        +
                      </button>
                    </div>
                  </td>
                  <td className="p-2 border-b border-gray-400">
                    {32 * quantities.jane}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div className="m-10 border border-black w-[40%] h-[650px] mr-[6cm]">
            <h2 className="mt-10 ml-10">CART TOTALS</h2>
            <div className="flex">
              <h3 className="ml-10 text-[125%] m-2">Subtotals:</h3>
              <div className="ml-10 text-[125%] m-2">$6266</div>
            </div>
            <hr></hr>
            <div className="flex h-[100px]">
              <h3 className="ml-10 text-[125%] m-2 w-auto">Shipping:</h3>
              <div>
                <div className="ml-10 text-[125%] m-2">
                  There are no shipping methods available. Please double check
                  your address, or contact us if you need any help.
                </div>
                <div className="flex flex-col items-center h-[100px] mt-0 ml-8 mr-8">
                  <p className="m-2 ml-10">CALCULATE SHIPPING</p>
                  <select
                    value={selectedCountry}
                    onChange={handleCountryChange}
                    className="p-2 border border-gray-300 rounded-md"
                  >
                    <option value="">-- Choose a Country --</option>
                    {countries.map((country, index) => (
                      <option key={index} value={country}>
                        {country}
                      </option>
                    ))}
                  </select>
                  {/* Text Box for State */}
                  <input
                    type="text"
                    value={state}
                    onChange={handleStateChange}
                    placeholder="Enter State"
                    className="w-full p-2 mt-4 border border-gray-300 rounded-md"
                  />
                  {/* Text Box for Zip Code */}
                  <input
                    type="text"
                    value={zipcode}
                    onChange={handleZipcodeChange}
                    placeholder="Enter Zip Code"
                    className="w-full p-2 mt-4 border border-gray-300 rounded-md"
                  />
                  <button className="w-auto px-6 py-2 mt-5 font-semibold text-white transition duration-300 ease-in-out bg-blue-500 shadow rounded-xl hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75">
                    Update Total
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  );
};

export default CartSection;
