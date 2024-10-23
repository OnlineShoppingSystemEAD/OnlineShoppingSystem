import React, { useState } from 'react';
import Header from '../../components/Header';
import AccountDetails from '../../components/AccountDetails';
import Payment from '../../components/Payment';
import Ongoing from '../../components/Ongoing';
import Completed from '../../components/Completed';

const Account = () => {
  const [activeTab, setActiveTab] = useState('general');

  // Function to render the active component
  const renderContent = () => {
    switch (activeTab) {
      case 'general':
        return <AccountDetails />;
      case 'payment':
        return <Payment />;
      case 'ongoing':
        return <Ongoing />;
      case 'completed':
        return <Completed />;
      default:
        return <AccountDetails />;
    }
  };

  return (
    <div className="bg-gray-100 min-h-screen">
      {/* Render Header at the top */}
      <Header />

      {/* Account Page Content */}
      <div className="container mx-auto py-8">
        {/* User Profile Section */}
        <div className="flex flex-col items-center">
          <img
            className="w-24 h-24 rounded-full"
            src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
            alt="Profile"
          />
          <h2 className="mt-4 text-2xl font-bold">Hevindu Tilakasena</h2>
          <span className="text-sm text-gray-500">⭐ 4.3</span>
        </div>

        {/* Tab Navigation */}
        <div className="flex justify-center mt-6 space-x-4">
          <button
            className={`${
              activeTab === 'general' ? 'bg-gray-300' : ''
            } px-4 py-2 rounded-full`}
            onClick={() => setActiveTab('general')}
          >
            General
          </button>
          <button
            className={`${
              activeTab === 'payment' ? 'bg-gray-300' : ''
            } px-4 py-2 rounded-full`}
            onClick={() => setActiveTab('payment')}
          >
            Payment
          </button>
          <button
            className={`${
              activeTab === 'ongoing' ? 'bg-gray-300' : ''
            } px-4 py-2 rounded-full`}
            onClick={() => setActiveTab('ongoing')}
          >
            Ongoing
          </button>
          <button
            className={`${
              activeTab === 'completed' ? 'bg-gray-300' : ''
            } px-4 py-2 rounded-full`}
            onClick={() => setActiveTab('completed')}
          >
            Completed
          </button>
        </div>

        {/* Render the active content below the tab */}
        <div className="mt-8">{renderContent()}</div>
      </div>
    </div>
  );
};

export default Account;
