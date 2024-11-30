import React, { useState, useEffect } from 'react';
import Header from '../../components/user/Header';
import AccountDetails from '../../components/user/Account/AccountDetails';
import Payment from '../../components/user/Account/Payment';
import Ongoing from '../../components/user/Account/Ongoing';
import Completed from '../../components/user/Account/Completed';
import userService from '../../api/services/UserService'; // Adjust path as needed

const Account = () => {
  const [activeTab, setActiveTab] = useState('general');
  const [userData, setUserData] = useState(null); // Store user data
  const [isLoading, setIsLoading] = useState(true); // Manage loading state
  const [error, setError] = useState(''); // Store error messages
  const [profilePicture, setProfilePicture] = useState('https://cdn-icons-png.flaticon.com/512/3135/3135715.png'); // Default profile picture

  useEffect(() => {
    const fetchAccountData = async () => {
      try {
        const userId = userService.getUserId(); // Fetch dynamic user ID
        const role = userService.getUserRole(); // Fetch dynamic role
        const response = await userService.getUserProfileById(userId, userId, role); // Call the API
        console.log(response);

        // Ensure correct response structure
        if (response && response.userProfile) {
          setUserData(response.userProfile); // Store the user profile data
          localStorage.setItem('userProfileCache', JSON.stringify(response));
        } else {
          throw new Error('Invalid response structure');
        }
      } catch (err) {
        console.error('Error fetching account data:', err);
        setError('Failed to load account details. Please try again later.');
      } finally {
        setIsLoading(false); // Stop loading
      }
    };

    fetchAccountData();
  }, []);

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        setProfilePicture(reader.result); // Set the uploaded image as profile picture
      };
      reader.readAsDataURL(file);
    }
  };

  // Function to render the active tab content
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

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <p className="text-lg text-gray-500">Loading...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <p className="text-red-500 text-lg">{error}</p>
      </div>
    );
  }

  return (
    <div className="bg-gray-100 min-h-screen">
      {/* Render Header */}
      <Header />

      {/* Account Page Content */}
      <div className="container mx-auto py-8">
        {/* User Profile Section */}
        <div className="flex flex-col items-center">
          <label htmlFor="profile-upload">
            <img
              className="w-24 h-24 rounded-full cursor-pointer"
              src={profilePicture}
              alt="Profile"
            />
          </label>
          <input
            id="profile-upload"
            type="file"
            accept="image/*"
            className="hidden"
            onChange={handleImageChange}
          />
          <h2 className="mt-4 text-2xl font-bold">
            {`${userData?.firstName || ''} ${userData?.lastName || ''}`}
          </h2>
          <span className="text-sm text-gray-500">⭐ 4.3</span>
        </div>

        {/* Tab Navigation */}
        <div className="flex justify-center mt-6 space-x-4">
          {['general', 'payment', 'ongoing', 'completed'].map((tab) => (
            <button
              key={tab}
              className={`px-4 py-2 rounded-full ${
                activeTab === tab ? 'bg-gray-300' : ''
              }`}
              onClick={() => setActiveTab(tab)}
            >
              {tab.charAt(0).toUpperCase() + tab.slice(1)}
            </button>
          ))}
        </div>

        {/* Render Active Tab Content */}
        <div className="mt-8">{renderContent()}</div>
      </div>
    </div>
  );
};

export default Account;
