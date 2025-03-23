import React from 'react';
import {Form, Outlet} from "react-router";
import {selectUser} from "~/features/userSlice";
import {useAppSelector} from "~/hooks";

export default function Layout() {
    const user = useAppSelector(selectUser);
    let result;
    if (Object.keys(user).length === 0) {
        result = <>
            <p>This is Layout</p>
            <Form action="login">
                <button type="submit">Login</button>
            </Form>
            <Form action="test">
                <button type="submit">Test</button>
            </Form>
            <Form action="registration">
                <button type="submit">Register</button>
            </Form>
            <Outlet/>
        </>;
    }
    if ("username" in user && typeof user.username === "string") {
        result = <div>Howdy {user.username}</div>;

    }
    return result;
}