// Navbar.js
import React, { useState } from 'react';
import '../../styles/navbar.css'; // Create a CSS file for the styles
import { FaBell, FaUser } from 'react-icons/fa';
import NotificationDropdown from "./NotificationDropdown"; // For icons, you can use react-icons

const Navbar = () => {
    const notificationsData = [
        { message: 'New Order Alert', date: '04/10' },
        { message: 'Delivery Completed', date: '04/10' }
    ];
    const [isNotificationOpen, setNotificationOpen] = useState(false);
    const [notifications, setNotifications] = useState(notificationsData);

    const toggleNotificationDropdown = () => setNotificationOpen(!isNotificationOpen);
    const markAsRead = () => {
        setNotifications([]); // Clear notifications
        setNotificationOpen(false); // Close the dropdown
    };

    return (
        <div className="navbar">
            <div className="navbar-left">
                <h1 className="logo"><strong>COZA</strong> STORE</h1>
                <nav className="nav-links">
                    <a href="/orders">Orders</a>
                    <a href="/categories">Categories</a>
                </nav>
            </div>
            <div className="navbar-right">
                <div className="icon-container" onClick={toggleNotificationDropdown}>
                    <FaBell className="icon" />
                    {notifications.length > 0 && <span className="notification-badge">{notifications.length}</span>}
                    {isNotificationOpen && (
                        <div className="notification-dropdown-container">
                            <NotificationDropdown
                                isOpen={isNotificationOpen}
                                notifications={notifications}
                                onMarkAsRead={markAsRead}
                            />
                        </div>
                    )}
                </div>
                <FaUser className="icon" />
            </div>
        </div>
    );
};

export default Navbar;
