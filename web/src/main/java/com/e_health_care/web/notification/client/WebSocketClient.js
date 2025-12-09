import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';

const NotificationComponent = ({ userId }) => {
    const [notification, setNotification] = useState(null);

    useEffect(() => {
        if (!userId) return;

        // 1. Create a SockJS connection to your backend endpoint
        const socket = new SockJS('http://localhost:8080/ws'); // Your Spring Boot app URL
        const stompClient = Stomp.over(socket);

        // 2. Connect to the STOMP server
        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);

            // 3. Subscribe to the user-specific notification queue
            // The client subscribes to '/user/queue/notifications', which the server
            // resolves to a unique destination for this user.
            stompClient.subscribe(`/user/${userId}/queue/notifications`, (message) => {
                console.log('Received notification: ' + message.body);
                // Display the notification
                setNotification(JSON.parse(message.body).content);
                // You can use a library like react-toastify to show a toast
                // toast.info(JSON.parse(message.body).content);
            });
        }, (error) => {
            console.error('STOMP error: ' + error);
        });

        // Cleanup on component unmount
        return () => {
            if (stompClient) {
                stompClient.disconnect();
            }
        };
    }, [userId]);

    return (
        <div>
            {notification && (
                <div className="notification-banner">
                    <p>{notification}</p>
                </div>
            )}
        </div>
    );
};

export default NotificationComponent;