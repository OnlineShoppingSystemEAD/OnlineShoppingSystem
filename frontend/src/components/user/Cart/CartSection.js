import React, { useState } from "react";
import blue from "../../../assets/blue-shirt.png";
import black from "../../../assets/black-shirt.png";

const CartSection = () => {
  const [quantities, setQuantities] = useState({ p01: 1, p02: 1 });
  const [selectedCountry, setSelectedCountry] = useState("");
  const [zipcode, setZipcode] = useState("");
  const [state, setState] = useState("");

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
    setSelectedCountry(event.target.vaderlue);
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
        <div className="flex justify-between  mt-20 h-[25cm]">
          <div className="m-10 ml-44 mb-[6.5cm] h-[13cm] rounded-xl ">
            <table className="w-[20cm] h-[10cm] border border-collapse border-gray-300  mt-0">
              <thead>
                <tr>
                  <th className="p-2 border-b border-gray-400">PRODUCT</th>
                  <th className="p-2 border-b border-gray-400">PRICE</th>
                  <th className="p-2 border-b border-gray-400">QUANTITY</th>
                  <th className="p-2 border-b border-gray-400">TOTAL</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td className="p-2 text-center border-b border-gray-400">
                    <img
                      src={blue} // Use the imported local image
                      alt="Product 01"
                      className="inline-block w-12 h-12 mr-2" // Tailwind classes for size and spacing
                    />
                    <br></br>
                    Product 01
                  </td>
                  <td className="p-2 text-center border-b border-gray-400">
                    $ 28
                  </td>
                  <td className="p-2 text-center border-b border-gray-400">
                    <div className="flex items-center justify-center">
                      <button
                        onClick={(event) => {
                          event.preventDefault();
                          decreaseQuantity("p01");
                        }}
                        className="px-2 border border-gray-400"
                      >
                        -
                      </button>
                      <span className="mx-2">{quantities.p01}</span>
                      <button
                        onClick={(event) => {
                          event.preventDefault();
                          increaseQuantity("p01");
                        }}
                        className="px-2 border border-gray-400"
                      >
                        +
                      </button>
                    </div>
                  </td>
                  <td className="p-2 text-center border-b border-gray-400">
                    $ {28 * quantities.p01}
                  </td>
                </tr>
                <tr>
                  <td className="p-2 text-center border-b border-gray-400">
                    <img
                      src={black} // Use the imported local image
                      alt="Product 02"
                      className="inline-block w-12 h-12 mr-2" // Tailwind classes for size and spacing
                    />
                    <br></br>
                    Product 02
                  </td>
                  <td className="p-2 text-center border-b border-gray-400">
                    $ 31
                  </td>
                  <td className="p-2 text-center border-b border-gray-400">
                    <div className="flex items-center justify-center">
                      <button
                        onClick={(event) => {
                          event.preventDefault();
                          decreaseQuantity("p02");
                        }}
                        className="px-2 border border-gray-400"
                      >
                        -
                      </button>
                      <span className="mx-2"> {quantities.p02}</span>
                      <button
                        onClick={(event) => {
                          event.preventDefault();
                          increaseQuantity("p02");
                        }}
                        className="px-2 border border-gray-400"
                      >
                        +
                      </button>
                    </div>
                  </td>
                  <td className="p-2 text-center border-b border-gray-400">
                    $ {31 * quantities.p02}
                  </td>
                </tr>
              </tbody>
            </table>
            <div className="border border-gray-300 h-[3cm] w-[20cm]">
              <div className="flex items-center justify-between mt-8 ml-8 mr-8 space-x-8">
                <input
                  type="text"
                  placeholder="Coupon Code"
                  className="p-2 m-2 border rounded-full w-50 "
                />
                <button className="w-40 p-2 m-2 text-white bg-blue-500 rounded-full">
                  Apply coupon
                </button>
                <button className="w-40 p-2 m-2 text-white bg-red-500 rounded-full">
                  Update Cart
                </button>
              </div>
            </div>
          </div>
          <div className=" border border-gray-300 w-[12cm] h-[20cm] mr-[4cm] rounded-xl p-5 mt-9 ">
            <h2 className="mt-5 ml-10">CART TOTALS</h2>
            <div className="flex">
              <h3 className="m-2 ml-10 text-lg text-gray-600">Subtotals:</h3>
              <div className="m-2 text-lg text-gray-600 ml-14">$6266</div>
            </div>
            <hr></hr>
            <div className="  h-[auto]">
              <div className="flex items-start m-2 mt-6 ml-10">
                <h3 className="text-lg text-gray-600">Shipping: </h3>
                <p className="ml-2 text-lg text-gray-600">
                  There are no shipping methods available. Please double check
                  your address, or contact us if you need any help.
                </p>
              </div>

              <div>
                <div className="flex-col items-center h-[400px] ml-10 text-[120%] bg p-6 pr-1">
                  <p className="m-2 ml-10 text-lg text-gray-600">
                    CALCULATE SHIPPING
                  </p>
                  <select
                    value={selectedCountry}
                    onChange={handleCountryChange}
                    className="p-2 border border-gray-300 rounded-md "
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
                    className="w-full p-2 mt-4 text-lg text-gray-600 border border-gray-300 rounded-md"
                  />
                  {/* Text Box for Zip Code */}
                  <input
                    type="text"
                    value={zipcode}
                    onChange={handleZipcodeChange}
                    placeholder="Enter Zip Code"
                    className="w-full p-2 mt-4 border border-gray-300 rounded-md"
                  />
                  <button className="w-auto px-6 py-2 mt-5 text-lg font-semibold text-gray-600 transition duration-300 ease-in-out bg-gray-200 rounded-full shadow hover:bg-blue-300 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75">
                    Update Total
                  </button>
                  <div className="mt-8">
                    <div className="flex mr-10">
                      <h3 className="m-0 text-lg text-gray-600">Subtotals:</h3>
                      <div className="text-lg text-gray-600 ml-14">$6266</div>
                    </div>
                    <button className="p-2 mt-5 m-0 w-[8cm] text-lg font-semibold text-white transition duration-300 ease-in-out bg-black shadow rounded-full hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75">
                      Proceed to Checkout
                    </button>
                  </div>
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
