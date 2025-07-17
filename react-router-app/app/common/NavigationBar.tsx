import React from 'react';
import RedirectionForm from "~/common/RedirectionForm";


export default function NavigationBar({user}: Record<string, any>) {
    return (
        <>
            <RedirectionForm action={"products"} text={"Products"}/>
        {
        user ?
            <>
                <div>Howdy {user.username}</div>
                <RedirectionForm action={"logout"} text={"Logout"}/>
            </> :
            <>
                <RedirectionForm action={"login"} text={"Login"}/>
                <RedirectionForm action={"registration"} text={"Register"}/>
            </>
        }
        </>
    );
}


