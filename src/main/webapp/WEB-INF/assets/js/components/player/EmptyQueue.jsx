import React from "react";

const EmptyQueue = () => {
    return (<div className="queue-empty">
        <div className="material-empty">
            <div className="empty-message">Queue is empty</div>
            <div className="empty-submessage">Start listening to music. Your playlist will be here.</div>
        </div>
    </div>);
};

export default EmptyQueue;

