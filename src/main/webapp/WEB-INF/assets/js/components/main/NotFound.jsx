import React from "react";

const NotFound = () => {
    return (
        <div className="not-found">
            <img src="/assets/img/404.png" alt=""/>
            <h1 className="empty-message">404</h1>
            <p className="empty-submessage">We tried hard but could not find this page.</p>
            <p className="empty-submessage">Please use form above <i className="fas fa-search"/> to find artists, albums
                and songs.</p>
        </div>
    );
};

export default NotFound;
