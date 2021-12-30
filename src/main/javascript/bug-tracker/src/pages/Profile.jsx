import React from "react";

const Profile = () => {

    const user = JSON.parse(localStorage.getItem("user")).Users[0]

    return (
        <div>
            <h2 className="page-header">
                {
                    `${user.firstName} ${user.lastName}`
                }
            </h2>
            <div className="row">
                <div className="col-12">
                    <div className="card">
                        <div className="card__body">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Profile;