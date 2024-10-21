import React from 'react';

const AccountDetails = () => {
  return (
    <div>
      <h2 className="text-xl font-bold mb-4">Personal Details</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-gray-600">First Name</label>
          <input type="text" className="border border-gray-300 p-2 rounded w-full" placeholder="First Name" />
        </div>
        <div>
          <label className="block text-gray-600">Last Name</label>
          <input type="text" className="border border-gray-300 p-2 rounded w-full" placeholder="Last Name" />
        </div>
        <div>
          <label className="block text-gray-600">Email Address</label>
          <input type="email" className="border border-gray-300 p-2 rounded w-full" placeholder="Email Address" />
        </div>
        <div>
          <label className="block text-gray-600">Contact Number</label>
          <input type="tel" className="border border-gray-300 p-2 rounded w-full" placeholder="Contact Number" />
        </div>
      </div>

      <h2 className="text-xl font-bold mt-8 mb-4">Billing Address</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-gray-600">House Number</label>
          <input type="text" className="border border-gray-300 p-2 rounded w-full" placeholder="House Number" />
        </div>
        <div>
          <label className="block text-gray-600">Address Line 1</label>
          <input type="text" className="border border-gray-300 p-2 rounded w-full" placeholder="Address Line 1" />
        </div>
        <div>
          <label className="block text-gray-600">Address Line 2</label>
          <input type="text" className="border border-gray-300 p-2 rounded w-full" placeholder="Address Line 2" />
        </div>
        <div>
          <label className="block text-gray-600">Postal Code</label>
          <input type="text" className="border border-gray-300 p-2 rounded w-full" placeholder="Postal Code" />
        </div>
      </div>

      <button className="bg-black text-white py-2 px-4 mt-6 rounded">Update</button>
    </div>
  );
};

export default AccountDetails;
