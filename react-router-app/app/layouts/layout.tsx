import React from 'react';
import {Form, Outlet} from "react-router";

export default function Layout() {

    return (
        <>
            <p>This is Layout</p>
            <Form action="login">
                <button type="submit">Login</button>
            </Form>
            <Form action="registration">
                <button type="submit">Register</button>
            </Form>
            <Outlet/>
        </>
    );
}