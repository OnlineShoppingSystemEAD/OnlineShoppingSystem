import React, { useEffect, useState } from 'react';
import userService from '../../../api/services/UserService'; // Adjust path as needed

const AccountDetails = () => {
  const [accountData, setAccountData] = useState(() => {
    const cachedData = localStorage.getItem('userProfileCache');
    return cachedData ? JSON.parse(cachedData).userProfile : {};
  });
  const [isLoading, setIsLoading] = useState(!localStorage.getItem('userProfileCache'));
  const [error, setError] = useState('');
  const [isUpdating, setIsUpdating] = useState(false); // State to track update process

  useEffect(() => {
    const fetchAccountData = async () => {
      try {
        if (!localStorage.getItem('userProfileCache')) {
          const userId = userService.getUserId();
          const role = userService.getUserRole();
          const response = await userService.getUserProfileById(userId, userId, role);
          console.log(response);

          if (response && response.userProfile) {
            setAccountData(response.userProfile);
            localStorage.setItem('userProfileCache', JSON.stringify(response));
          } else {
            throw new Error('Invalid response structure');
          }
        }
      } catch (err) {
        console.error('Error fetching account data:', err);
        setError('Failed to load account details. Please try again later.');
      } finally {
        setIsLoading(false);
      }
    };

    fetchAccountData();
  }, []);

  const handleUpdateProfile = async () => {
    setIsUpdating(true);
    try {
      const userId = userService.getUserId();
      const updatedProfile = { ...accountData }; // Prepare updated data (modify if needed)
      const response = await userService.updateUserProfile(userId, updatedProfile);
      console.log('Updated Profile Response:', response);

      if (response) {
        // Update cache and refresh the page
        localStorage.setItem('userProfileCache', JSON.stringify({ userProfile: response }));
        window.location.reload(); // Reload the page to fetch updated data
      }
    } catch (err) {
      console.error('Error updating profile:', err);
      alert('Failed to update the profile. Please try again.');
    } finally {
      setIsUpdating(false);
    }
  };

  if (isLoading) {
    return (
        <div style={{ position: 'relative', width: '100%', height: '100vh', textAlign: 'center' }}>
          <p style={{ marginTop: '20px', fontSize: '18px', color: '#555' }}>Loading...</p>
        </div>
    );
  }

  if (error) {
    return <div className="text-red-500">{error}</div>;
  }

  return (
      <div>
        <h2 className="text-xl font-bold mb-4">Personal Details</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-gray-600">First Name</label>
            <input
                type="text"
                className="border border-gray-300 p-2 rounded w-full"
                value={accountData.firstName || ''}
                onChange={(e) => setAccountData({ ...accountData, firstName: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-gray-600">Last Name</label>
            <input
                type="text"
                className="border border-gray-300 p-2 rounded w-full"
                value={accountData.lastName || ''}
                onChange={(e) => setAccountData({ ...accountData, lastName: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-gray-600">Email Address</label>
            <input
                type="email"
                className="border border-gray-300 p-2 rounded w-full"
                value={accountData.email || ''}
                onChange={(e) => setAccountData({ ...accountData, email: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-gray-600">Contact Number</label>
            <input
                type="tel"
                className="border border-gray-300 p-2 rounded w-full"
                value={accountData.contactNumber || ''}
                onChange={(e) => setAccountData({ ...accountData, contactNumber: e.target.value })}
            />
          </div>
        </div>

        <h2 className="text-xl font-bold mt-8 mb-4">Billing Address</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-gray-600">House Number</label>
            <input
                type="text"
                className="border border-gray-300 p-2 rounded w-full"
                value={accountData.houseNumber || ''}
                onChange={(e) => setAccountData({ ...accountData, houseNumber: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-gray-600">Address Line 1</label>
            <input
                type="text"
                className="border border-gray-300 p-2 rounded w-full"
                value={accountData.addressLine1 || ''}
                onChange={(e) => setAccountData({ ...accountData, addressLine1: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-gray-600">Address Line 2</label>
            <input
                type="text"
                className="border border-gray-300 p-2 rounded w-full"
                value={accountData.addressLine2 || ''}
                onChange={(e) => setAccountData({ ...accountData, addressLine2: e.target.value })}
            />
          </div>
          <div>
            <label className="block text-gray-600">Postal Code</label>
            <input
                type="text"
                className="border border-gray-300 p-2 rounded w-full"
                value={accountData.postalCode || ''}
                onChange={(e) => setAccountData({ ...accountData, postalCode: e.target.value })}
            />
          </div>
        </div>

        <button
            className="bg-black text-white py-2 px-4 mt-6 rounded"
            onClick={handleUpdateProfile}
            disabled={isUpdating}
        >
          {isUpdating ? 'Updating...' : 'Update'}
        </button>
      </div>
  );
};

export default AccountDetails;
